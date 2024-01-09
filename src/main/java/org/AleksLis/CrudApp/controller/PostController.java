package org.AleksLis.CrudApp.controller;

import org.AleksLis.CrudApp.model.Post;
import org.AleksLis.CrudApp.services.PostService;

import java.util.List;

public class PostController {

    private final PostService postService;
    public PostController() {
        this.postService = new PostService();
    }

    public Post getById(Long id){
        return postService.getById(id);
    };

    public List<Post> getAll(){
        return postService.getAll();
    };

    public Post savePost(Post post){
        return postService.savePost(post);
    };

    public Post updatePost(Post post){
        return postService.updatePost(post);
    };

    public void deletePost(Long id){
        postService.deleteById(id);
    };
}
