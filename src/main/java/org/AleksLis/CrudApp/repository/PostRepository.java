package org.AleksLis.CrudApp.repository;

import org.AleksLis.CrudApp.model.Label;
import org.AleksLis.CrudApp.model.Post;

import java.util.List;

public class PostRepository {

    public Post getById(Long id){};

    public List<Post> getAll(){};

    public Post savePost(Post post){};

    public Post updatePost(Post post){};

    public void deleteById(Long id){};
}
