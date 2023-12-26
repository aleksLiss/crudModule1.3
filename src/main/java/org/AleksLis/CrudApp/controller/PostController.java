package org.AleksLis.CrudApp.controller;

import org.AleksLis.CrudApp.model.Post;
import org.AleksLis.CrudApp.services.PostService;

import java.util.List;

public class PostController {

    private PostService postService;
    public Post getById(Long id){
        return postService.getById(id);
    };

    public List<Post> getAll(){
        return postService.getAll();
    };

    public Post saveLabel(Post post){
        return postService.savePost(post);
    };

    public Post updateLabel(Post post){
        return postService.updatePost(post);
    };

    public void deleteById(Long id){
        postService.deleteById(id);
    };
}
