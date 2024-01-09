package org.AleksLis.CrudApp.view;

import org.AleksLis.CrudApp.controller.PostController;
import org.AleksLis.CrudApp.model.Label;
import org.AleksLis.CrudApp.model.Post;
import org.AleksLis.CrudApp.systemMessages.SystemMessages;

import java.util.List;
import java.util.Scanner;

public class PostView {
    private final PostController postController;
    private boolean statusPostView;

    public PostView() {
        this.postController = new PostController();
        this.statusPostView = true;
    }

    private int startAction(){
        System.out.println(SystemMessages.MENU_POST_VIEW);
        System.out.println(SystemMessages.INPUT_CHOOSE);
        return new Scanner(System.in).nextInt();
    }

    public void actionsOnPosts() {
        try {
            int choose = startAction();
            while (statusPostView) {
                switch (choose) {
                    case 1:
                        savePost();
                        choose = startAction();
                        break;
                    case 2:
                        getPost();
                        choose = startAction();
                        break;
                    case 3:
                        getAllPosts();
                        choose = startAction();
                        break;
                    case 4:
                        updatePost();
                        choose = startAction();
                        break;
                    case 5:
                        deletePost();
                        choose = startAction();
                        break;
                    case 6:
                        statusPostView = false;
                        break;
                    default:
                        System.out.println(SystemMessages.OPERATION_FAILED);
                }
            }
        } catch (Exception e) {
            System.out.println(SystemMessages.OPERATION_FAILED);
        }

    }

    public void getPost() {
        try {
            System.out.println(SystemMessages.INPUT_ID);
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
            System.out.println(SystemMessages.INPUT_ID);
            long id = new Scanner(System.in).nextInt();
            System.out.println(SystemMessages.INPUT_CONTENT);
            String content = new Scanner(System.in).next();
            postController.savePost(new Post(id, content));
        } catch (Exception e) {
            System.out.println(SystemMessages.OPERATION_FAILED);
        }
    }

    ;

    public void updatePost() {
        try {
            System.out.println(SystemMessages.INPUT_ID);
            long id = new Scanner(System.in).nextInt();
            Post post = postController.getById(id);
            System.out.println(SystemMessages.INPUT_CONTENT);
            String content = new Scanner(System.in).next();
            post.setContent(content);
            postController.updatePost(post);
        } catch (Exception e) {
            System.out.println(SystemMessages.OPERATION_FAILED);
        }
    }

    ;

    public void deletePost() {
        try {
            System.out.println(SystemMessages.INPUT_ID);
            long id = new Scanner(System.in).nextInt();
            postController.deletePost(id);
        } catch (Exception e) {
            System.out.println(SystemMessages.OPERATION_FAILED);
        }
    }

    ;
}
