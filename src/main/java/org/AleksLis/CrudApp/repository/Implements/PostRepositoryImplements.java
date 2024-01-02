package org.AleksLis.CrudApp.repository.Implements;

import org.AleksLis.CrudApp.exceptions.IdNotExistException;
import org.AleksLis.CrudApp.model.Post;
import org.AleksLis.CrudApp.model.Writer;
import org.AleksLis.CrudApp.repository.PostRepository;
import org.AleksLis.CrudApp.repository.util.Util;
import org.AleksLis.CrudApp.systemMessages.SystemMessages;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

public class PostRepositoryImplements implements PostRepository {

    @Override
    public Post getById(Long id) {
        Post post = null;
        String pathFile = Util.PATH + Util.POSTDB;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(pathFile))) {
            String fromJson = Util.JsonToString(bufferedReader);
            List<Post> listPost = Util.getListPostsFromJson(fromJson, Util.getTypeOfListWriters());
            try {
                post = Util.getWriterById(listPost, id);
            } catch (IdNotExistException e) {
                System.out.println(SystemMessages.ID_NOT_EXIST_EX.getMessage());
            }
        } catch (Exception e) {
            System.out.println(SystemMessages.JSON_NOT_EXIST_EX.getMessage());
        }
        return post;
    }

    @Override
    public List<Post> getAll() {
        return null;
    }

    @Override
    public Post save(Post post) {
        return null;
    }

    @Override
    public Post update(Post post) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
