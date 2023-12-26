package org.AleksLis.CrudApp.services;

import org.AleksLis.CrudApp.model.Post;
import org.AleksLis.CrudApp.repository.PostRepository;

import java.util.List;

public class PostService {

    private PostRepository postRepository;


    public Post getById(Long id){
        return postRepository.getById(id);
    };

    public List<Post> getAll(){
        return postRepository.getAll();
    };

    public Post savePost(Post post){
        return postRepository.savePost(post);
    };

    public Post updatePost(Post post){
        return postRepository.updatePost(post);
    };

    public void deleteById(Long id){
        postRepository.deleteById(id);
    };
}
