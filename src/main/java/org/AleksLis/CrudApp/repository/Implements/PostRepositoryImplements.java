package org.AleksLis.CrudApp.repository.Implements;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.AleksLis.CrudApp.exceptions.EmptyDBException;
import org.AleksLis.CrudApp.exceptions.IdExistException;
import org.AleksLis.CrudApp.exceptions.IdNotExistException;
import org.AleksLis.CrudApp.model.Post;
import org.AleksLis.CrudApp.model.StatusEntity;
import org.AleksLis.CrudApp.repository.PostRepository;
import org.AleksLis.CrudApp.repository.util.Util;
import org.AleksLis.CrudApp.systemMessages.SystemMessages;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

public class PostRepositoryImplements implements PostRepository {

    @Override
    public Post getById(Long id) {
        Post postFromDB = null;
        try {
            List<Post> listPosts = getListPostsFromJsonString(getStringFromJson());
            Util.emptyDb(listPosts.size());
            try {
                List<Post> result = filterListPostsById(id, listPosts);
                Util.idNotExist(result.size());
                postFromDB = result.get(0);
            } catch (IdNotExistException ignored) {
            }
        } catch (EmptyDBException ignored) {
        }
        return postFromDB;
    }

    @Override
    public List<Post> getAll() {
        List<Post> postFromDB = null;
        try {
            postFromDB = getListPostsFromJsonString(getStringFromJson());
            Util.emptyDb(postFromDB.size());
        } catch (EmptyDBException ignored) {
        }
        return postFromDB;
    }

    @Override
    public Post save(Post post) {
        String pathFile = getPathFile();
        List<Post> listPosts = getListPostsFromJsonString(getStringFromJson());
        try {
            List<Post> result = filterListPostsById(post.getId(), listPosts);
            Util.idExist(result.size());
            result.add(post);
            writeListOfPostsToDB(pathFile, result);
        } catch (IdExistException ignored) {
        }
        return post;
    }

    @Override
    public Post update(Post post) {

        Post postFromDB = null;
        String pathFile = getPathFile();
        try {
            List<Post> listPosts = getListPostsFromJsonString(getStringFromJson());
            Util.emptyDb(listPosts.size());
            try {
                List<Post> result = filterListPostsById(post.getId(), listPosts);
                Util.idNotExist(result.size());
                postFromDB = updatePost(result.get(0), post);
                result.add(post);
                writeListOfPostsToDB(pathFile, result);
            } catch (IdNotExistException ignored) {
            }
        } catch (EmptyDBException ignored) {
        }
        return postFromDB;

    }

    @Override
    public void delete(Long id) {
        String pathFile = getPathFile();
        try {
            List<Post> listPosts = getListPostsFromJsonString(getStringFromJson());
            Util.emptyDb(listPosts.size());
            try {
                List<Post> result = filterListPostsById(id, listPosts);
                Util.idNotExist(result.size());
                Post postFromDB = result.get(0);
                postFromDB.setPostStatus(StatusEntity.DELETE);
                result.add(postFromDB);
                writeListOfPostsToDB(pathFile, result);
            } catch (IdNotExistException ignored) {
            }
        } catch (EmptyDBException ignored) {
        }
    }

    private List<Post> filterListPostsById(Long id, List<Post> posts) {
        return posts.stream()
                .filter((ps) -> ps.getId().equals(id))
                .collect(Collectors.toList());
    }

    private List<Post> getListPostsFromJsonString(String fromJson) {
        Type type = new TypeToken<List<Post>>() {
        }.getType();
        return new Gson().fromJson(fromJson, type);
    }

    private Post updatePost(Post postFromDB, Post updatePost) {
        postFromDB.setContent(updatePost.getContent());
        postFromDB.setLabels(updatePost.getLabels());
        postFromDB.setCreated(updatePost.getCreated());
        postFromDB.setUpdated(updatePost.getUpdated());
        return updatePost;
    }

    private void writeListOfPostsToDB(String pathFile, List<Post> posts) {
        try (FileWriter fileWriter = new FileWriter(pathFile)) {
            String listPostsToJson = new Gson().toJson(posts);
            fileWriter.write(listPostsToJson);
        } catch (IOException e) {
            System.out.println(SystemMessages.WRITE_TO_JSON_EX.getMessage());
        }
    }

    private String getPathFile() {
        return Util.PATH + Util.WRITERDB;
    }

    private String getStringFromJson() {
        String fromJson = null;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(getPathFile()))) {
            fromJson = Util.jsonToString(bufferedReader);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return fromJson;
    }



}
