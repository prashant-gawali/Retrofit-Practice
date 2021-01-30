package com.example.workingwithretrofit;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.workingwithretrofit.model.Comment;
import com.example.workingwithretrofit.model.Post;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    TextView outputText;
    Button btnRun, btnClear;
    MyWebService mWebService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebService = MyWebService.retrofit.create(MyWebService.class);
        initViews();
    }

    private void initViews() {
        outputText = findViewById(R.id.outputText);
        btnRun = findViewById(R.id.btnRun);
        btnClear = findViewById(R.id.btnClear);
    }


    public void runCode(View view) {

//        getPosts();
//        getComments();
//        getComment();
//        getUrl();
//        createPost();
//        createFieldPost();
//        createFieldMapPost();
//        updatePost();
        deletePost();
    }

    private void deletePost() {
        Call<Void> call = mWebService.deletePost(5);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful())
                    outputText.setText(String.valueOf(response.code()));

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    private void updatePost() {
        Post post = new Post(13, "New Title", null);
        Call<Post> putPostCall = mWebService.patchPost(5, post);
        putPostCall.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful()) {
                    outputText.setText(String.valueOf(response.code()));
                    showPutPostData(response.body());
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(MainActivity.this, "#put Error:" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void showPutPostData(Post body) {
        outputText.append("\n#Userid:" + body.getUserId() + "\n");
        outputText.append("#Id:" + body.getId() + "\n");
        outputText.append("#Title:" + body.getTitle() + "\n");
        outputText.append("#Body:" + body.getBody() + "\n\n");

    }

    private void createFieldMapPost() {
        Map<String, String> postMap = new HashMap<>();
        postMap.put("userId", "33");
        postMap.put("title", "My Post Title!");
        postMap.put("body", "This is my Post Body in the Map!!");

        Call<Post> fieldMapPostCall = mWebService.createFieldMapPost(postMap);
        fieldMapPostCall.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                outputText.setText(String.valueOf(response.code()));
                showPostCallData(response.body());
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });
    }

    private void createFieldPost() {
        Call<Post> fieldPostCall = mWebService.createFieldPost(3, "Post Title", "This is Post Body..");
        fieldPostCall.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful()) {
                    outputText.setText(String.valueOf(response.code()));
                    showPostCallData(response.body());

                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });
    }

    private void createPost() {
        Post post = new Post(1, "Post Title", "This is Post Body");
        Call<Post> postCall = mWebService.createPost(post);

        postCall.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful()) {
                    outputText.setText(String.valueOf(response.code()));
                    showPostCallData(response.body());
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error:" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void showPostCallData(Post body) {
        outputText.append("\n#Userid:" + body.getUserId() + "\n");
        outputText.append("#Id:" + body.getId() + "\n");
        outputText.append("#Title:" + body.getTitle() + "\n");
        outputText.append("#Body:" + body.getBody() + "\n\n");

    }

    private void getUrl() {
        Call<List<Comment>> call = mWebService.getUrl("https://jsonplaceholder.typicode.com/posts/13/comments");
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (response.isSuccessful()) {
                    getUrlData(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {

            }
        });
    }

    private void getUrlData(List<Comment> body) {
        for (Comment url : body) {
            outputText.append("id:" + url.getId() + "\n");
            outputText.append("postId:" + url.getPostId() + "\n");
            outputText.append("user:" + url.getName() + "\n");
            outputText.append("email:" + url.getEmail() + "\n");
            outputText.append("body:" + url.getBody() + "\n");

        }

    }

    private void getComment() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("postId", "5");
        parameters.put("_sort", "email");
        parameters.put("_order", "desc");

        Call<List<Comment>> call = mWebService.getComment(parameters);
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (response.isSuccessful()) {
                    showComment(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error:" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void showComment(List<Comment> body) {
        for (Comment comm : body) {

            outputText.append("id:" + comm.getId() + "\n");
            outputText.append("postId:" + comm.getPostId() + "\n");
            outputText.append("user:" + comm.getName() + "\n");
            outputText.append("email:" + comm.getEmail() + "\n");
            outputText.append("body:" + comm.getBody() + "\n");

        }


    }

    private void getComments() {
        Call<List<Comment>> call = mWebService.getComments(3, "id", "desc");
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (response.isSuccessful()) {
                    showComments(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {

            }
        });
    }

    private void showComments(List<Comment> body) {
        for (Comment comment : body) {

            outputText.append("id:" + comment.getId() + "\n");
            outputText.append("postId:" + comment.getPostId() + "\n");
            outputText.append("user:" + comment.getName() + "\n");
            outputText.append("email:" + comment.getEmail() + "\n");
            outputText.append("body:" + comment.getBody() + "\n");

        }
    }

    private void getPosts() {
        Call<List<Post>> call = mWebService.getPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    for (Post post : response.body()) {
                        showPost(post);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {


            }
        });
    }

    private void showPost(Post post) {
        outputText.append("#Userid:" + post.getUserId() + "\n");
        outputText.append("#Id:" + post.getId() + "\n");
        outputText.append("#Title:" + post.getTitle() + "\n");
        outputText.append("#Body:" + post.getBody() + "\n\n");
    }

    public void onClear(View view) {
        outputText.setText("");
    }
}