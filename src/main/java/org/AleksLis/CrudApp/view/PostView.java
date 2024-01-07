package org.AleksLis.CrudApp.view;

import org.AleksLis.CrudApp.controller.PostController;
import org.AleksLis.CrudApp.controller.WriterController;
import org.AleksLis.CrudApp.model.Label;
import org.AleksLis.CrudApp.model.Post;
import org.AleksLis.CrudApp.systemMessages.SystemMessages;

import java.util.List;
import java.util.Scanner;

public class PostView {
    private final PostController postController;
    public static final String INPUT_ID = "Input id: ";
    public static final String INPUT_CONTENT = "Input content: ";
    public static final String ACTION_ON_POSTS = "Choose action on posts: ";
    public static final String MENU =
            "1. Create new post\n" +
                    "2. Get post by id\n" +
                    "3. Get all posts\n" +
                    "4. Update post by id\n" +
                    "5. Delete post by id\n" +
                    "6. Exit\n";

    public PostView() {
        this.postController = new PostController();
    }

    public void actionsOnPosts() {
        try {
            System.out.println(ACTION_ON_POSTS);
            System.out.println(MENU);
            int choose = new Scanner(System.in).nextInt();
            switch (choose) {
                case 1:
                    savePost();
                    break;
                case 2:
                    getPost();
                    break;
                case 3:
                    getAllPosts();
                    break;
                case 4:
                    updatePost();
                    break;
                case 5:
                    deletePost();
                    break;
                case 6:
                    break;
                default:
                    System.out.println(SystemMessages.OPERATION_FAILED);
            }
        } catch (Exception e) {
            System.out.println(SystemMessages.OPERATION_FAILED);
        }

    }

    public void getPost() {
        try {
            System.out.println(INPUT_ID);
            long id = new Scanner(System.in).nextInt();
            Post post = postController.getById(id);
            System.out.println("Id: " + post.getId());
            System.out.println("Content: " + post.getContent());
            System.out.println("Created: " + post.getCreated());
            System.out.println("Updated: " + post.getUpdated());
            List<Label> labelList = post.getLabels();
            for (Label label : labelList) {
                System.out.println("Label id: " + label.getId());
                System.out.println("Label name: " + label.getName());
                System.out.println("Label status: " + label.getLabelStatus());
            }
            System.out.println("Status: " + post.getPostStatus());
        } catch (Exception e) {
            System.out.println(SystemMessages.OPERATION_FAILED);
        }
    }

    ;

    public void getAllPosts() {
        try {
            List<Post> postList = postController.getAll();
            for (Post post : postList) {
                System.out.println("Id: " + post.getId());
                System.out.println("Content: " + post.getContent());
                System.out.println("Created: " + post.getCreated());
                System.out.println("Updated: " + post.getUpdated());
                List<Label> labelList = post.getLabels();
                for (Label label : labelList) {
                    System.out.println("Label id: " + label.getId());
                    System.out.println("Label name: " + label.getName());
                    System.out.println("Label status: " + label.getLabelStatus());
                }
                System.out.println("Status: " + post.getPostStatus());
            }
        } catch (Exception e) {
            System.out.println(SystemMessages.OPERATION_FAILED);
        }
    }

    ;

    public void savePost() {
        try {
            System.out.println(INPUT_ID);
            long id = new Scanner(System.in).nextInt();
            System.out.println(INPUT_CONTENT);
            String content = new Scanner(System.in).next();
            postController.savePost(new Post(id, content));
            System.out.println(SystemMessages.OPERATION_SUCCESS);
        } catch (Exception e) {
            System.out.println(SystemMessages.OPERATION_FAILED);
        }
    }

    ;

    public void updatePost() {
        try {
            System.out.println(INPUT_ID);
            long id = new Scanner(System.in).nextInt();
            Post post = postController.getById(id);
            System.out.println(INPUT_CONTENT);
            String content = new Scanner(System.in).next();
            post.setContent(content);
            postController.updatePost(post);
            System.out.println(SystemMessages.OPERATION_SUCCESS);
        } catch (Exception e) {
            System.out.println(SystemMessages.OPERATION_FAILED);
        }
    }

    ;

    public void deletePost() {
        try {
            System.out.println(INPUT_ID);
            long id = new Scanner(System.in).nextInt();
            postController.deletePost(id);
            System.out.println(SystemMessages.OPERATION_SUCCESS);
        } catch (Exception e) {
            System.out.println(SystemMessages.OPERATION_FAILED);
        }
    }

    ;
}
