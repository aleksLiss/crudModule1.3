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

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PostRepositoryImplements implements PostRepository {


    @Override
    public Post getById(Long id) {
        Post post = null;
        try {
            List<Post> listPosts = getListPostsFromDB(getStringFromJson());
            throwEmptyDb(listPosts);
            try {
                List<Post> posts = throwIdNotExist(listPosts, id);
                post = posts.get(0);
            } catch (IdNotExistException ex) {
                System.out.println(SystemMessages.OPERATION_FAILED);
                System.out.println(ex.getMessage());
            }
        } catch (EmptyDBException ex) {
            System.out.println(SystemMessages.OPERATION_FAILED);
            System.out.println(ex.getMessage());
        }
        return post;
    }

    @Override
    public List<Post> getAll() {
        List<Post> postsFromDB = null;
        try {
            postsFromDB = getListPostsFromDB(getStringFromJson());
            throwEmptyDb(postsFromDB);
        } catch (EmptyDBException ex) {
            System.out.println(SystemMessages.OPERATION_FAILED);
            System.out.println(ex.getMessage());
        }
        return postsFromDB;
    }

    @Override
    public Post save(Post post) {
        try {
            String pathFile = getPathFile();
            File file = new File(pathFile);
            if (file.length() == 0) {
                try {
                    List<Post> postList = new ArrayList<>();
                    postList.add(post);
                    writeListOfPostsToDB(pathFile, postList);
                    System.out.println(SystemMessages.OPERATION_SUCCESS);
                } catch (Exception ex) {
                    System.out.println(SystemMessages.OPERATION_FAILED);
                    System.out.println(ex.getMessage());
                }
            } else {
                try {
                    List<Post> postsList = getListPostsFromDB(getStringFromJson());
                    List<Post> result = throwIdExist(postsList, post);
                    result.add(post);
                    writeListOfPostsToDB(getPathFile(), result);
                    System.out.println(SystemMessages.OPERATION_SUCCESS);
                } catch (IdExistException ex) {
                    System.out.println(SystemMessages.OPERATION_FAILED);
                    System.out.println(ex.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println(SystemMessages.OPERATION_FAILED);
            System.out.println(e.getMessage());
        }
        return post;
    }

    @Override
    public Post update(Post post) {
        Post postResult = null;
        try {
            List<Post> postList = getListPostsFromDB(getStringFromJson());
            throwEmptyDb(postList);
            try {
                List<Post> filterPostsById = throwIdNotExist(postList, post.getId());
                filterPostsById.remove(0);
                filterPostsById.add(post);
                postResult = filterPostsById.get(0);
                writeListOfPostsToDB(getPathFile(), filterPostsById);
                System.out.println(SystemMessages.OPERATION_SUCCESS);
            } catch (IdNotExistException ex) {
                System.out.println(SystemMessages.OPERATION_FAILED);
                System.out.println(ex.getMessage());
            }
        } catch (EmptyDBException ex) {
            System.out.println(SystemMessages.OPERATION_FAILED);
            System.out.println(ex.getMessage());
        }
        return postResult;
    }

    @Override
    public void delete(Long id) {
        String pathFile = getPathFile();
        try {
            List<Post> postList = getListPostsFromDB(getStringFromJson());
            throwEmptyDb(postList);
            try {
                List<Post> result = throwIdNotExist(postList, id);
                List<Post> res = result.stream()
                        .peek(post-> post.setPostStatus(StatusEntity.DELETE))
                        .collect(Collectors.toList());
                writeListOfPostsToDB(pathFile, res);
                System.out.println(SystemMessages.OPERATION_SUCCESS);
            } catch (IdNotExistException ex) {
                System.out.println(SystemMessages.OPERATION_FAILED);
                System.out.println(ex.getMessage());
            }
        } catch (EmptyDBException ex) {
            System.out.println(SystemMessages.OPERATION_FAILED);
            System.out.println(ex.getMessage());
        }
    }

    private List<Post> getListPostsFromDB(String fromJson) {
        Type type = new TypeToken<List<Post>>() {
        }.getType();
        return new Gson().fromJson(fromJson, type);
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
        return Util.PATH + Util.POSTDB;
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

    public static void throwEmptyDb(List<Post> posts) throws EmptyDBException{
        if(posts == null){
            throw new EmptyDBException(SystemMessages.EMPTY_DB_EX.getMessage());
        }
    }
    private static List<Post> throwIdNotExist(List<Post> posts, Long id) throws IdNotExistException {
        List<Post> result = posts.stream()
                .filter((a) -> a.getId().equals(id))
                .collect(Collectors.toList());
        if (result.size() == 0) {
            throw new IdNotExistException(SystemMessages.ID_NOT_EXIST_EX.getMessage());
        }
        return result;
    }

    private static List<Post> throwIdExist(List<Post> posts, Post post) throws IdExistException {
        List<Post> result = posts.stream()
                .filter((a) -> a.getId().equals(post.getId()))
                .collect(Collectors.toList());
        if (result.size() != 0) {
            throw new IdExistException(SystemMessages.ID_ALREADY_EXIST.getMessage());
        }
        return posts;
    }

}