package com.gh0stcr4ck3r.news.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gh0stcr4ck3r.news.R;
import com.gh0stcr4ck3r.news.adapter.CommentAdapter;
import com.gh0stcr4ck3r.news.adapter.TagAdapter;
import com.gh0stcr4ck3r.news.network.ApiEndpoint;
import com.gh0stcr4ck3r.news.network.RetrofitInstance;
import com.gh0stcr4ck3r.news.response.Article;
import com.gh0stcr4ck3r.news.response.Comment;
import com.gh0stcr4ck3r.news.response.Tag;
import com.gh0stcr4ck3r.news.utils.DatePref;
import com.gh0stcr4ck3r.news.utils.ProgressDialogUtils;
import com.gh0stcr4ck3r.news.utils.SherdPref;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Article_Details extends AppCompatActivity {
    RecyclerView CrecyclerView, tagRecyclerView;
    List<Tag> tagList;
    TagAdapter tagAdapter;
    EditText eComment;
    String article_id;
    private TextView title, details, created, update, category, author, tagText, newsCat;;
    private ImageView dImageview;
    List<Comment> commentList;
    CommentAdapter commentAdapter;
    Comment comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article__details);


        Intent intent = getIntent();
        article_id = intent.getStringExtra("article_id");


        title = findViewById(R.id.title);
        newsCat = findViewById(R.id.tv_news_cat);
        details = findViewById(R.id.details);
        created = findViewById(R.id.created);
        dImageview=findViewById(R.id.image_details);
        author = findViewById(R.id.author);
        eComment = findViewById(R.id.detail_comment);
        getDetails(article_id);

        PopulateTagItem();
        PopulateCommentItem();


    }

    //For Article Details
    private void getDetails(String id) {
        final ProgressDialog progressDialog=ProgressDialogUtils.getProgressDialog(Article_Details.this);
        progressDialog.show();
        Retrofit retrofit = RetrofitInstance.getRetrofitInstace();
        ApiEndpoint api = retrofit.create(ApiEndpoint.class);
        api.getArticleDetails(id).enqueue(new Callback<Article>() {
            @Override
            public void onResponse(Call<Article> call, Response<Article> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        title.setText(response.body().getTitle());
                        newsCat.setText(response.body().getCategory().getTitle());
                        details.setText(stripHtml(response.body().getDetails()));
                        Log.d("+++", String.valueOf(response.body().getCreatedAt()));
                        try {
                            created.setText(DatePref.ConvertToNewFormate(response.body().getCreatedAt()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        author.setText(String.format("%s %s", response.body().getAuthor().getFirstName(), response.body().getAuthor().getLastName()));
                        Glide.with(getApplicationContext()).load(response.body().getThumbnail()).into(dImageview);


                    }
                }

            }

            @Override
            public void onFailure(@NotNull Call<Article> call, Throwable t) {
                progressDialog.dismiss();

                Toast.makeText(Article_Details.this, "can not connect", Toast.LENGTH_SHORT).show();

            }
        });
    }

  //populate recyclerview for tag
    public void PopulateTagItem(){
        tagList = new ArrayList<>();
        tagRecyclerView = (RecyclerView) findViewById(R.id.tag_recycler);
        tagAdapter = new TagAdapter(tagList, getApplicationContext());
        tagRecyclerView.setAdapter(tagAdapter);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);


        tagRecyclerView.setLayoutManager(layoutManager);
        getArticleListByTag();

    }

    //get article by tag
    public void getArticleListByTag(){
        Retrofit retrofit=RetrofitInstance.getRetrofitInstace();
        ApiEndpoint apiEndpoint=retrofit.create(ApiEndpoint.class);
        apiEndpoint.aticleListByTag().enqueue(new Callback<List<Tag>>() {
            @Override
            public void onResponse(Call<List<Tag>> call, Response<List<Tag>> response) {
                tagList = response.body();
                tagAdapter.setTagList(tagList);
                tagRecyclerView.setAdapter(tagAdapter);
                tagAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Tag>> call, Throwable t) {
                Toast.makeText(Article_Details.this, "can not connect", Toast.LENGTH_SHORT).show();

            }
        });
    }

    //pipulate recyclerview for Comment

    public void PopulateCommentItem(){
        commentList = new ArrayList<>();
        CrecyclerView = (RecyclerView) findViewById(R.id.rv_comment);
        commentAdapter = new CommentAdapter(commentList, Article_Details.this, new CommentAdapter.OnItemClicked() {
            @Override
            public void onClicked(Comment comment) {

            }

            @Override
            public void onLongClick(final Comment comment) {
                //  Toast.makeText(ArticleDetailsActivity.this, "fuck you", Toast.LENGTH_SHORT).show();
                final Dialog d = new Dialog(Article_Details.this);
                d.setTitle("Action Menu");
                d.setContentView(R.layout.action_dialog);
                Button addBtn, editBtn, deleteBtn;

                editBtn = d.findViewById(R.id.action_edit_btn);
                deleteBtn = d.findViewById(R.id.action_delete_btn);

                editBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateDialogue(comment);
                        d.dismiss();
                    }
                });

                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DeleteComment(comment.getId().toString());

                    }
                });


                d.show();


            }
        });

        CrecyclerView.setAdapter(commentAdapter);
        commentAdapter.notifyDataSetChanged();
        CrecyclerView.setAdapter(commentAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        CrecyclerView.setLayoutManager(linearLayoutManager);
        getComment(article_id);

    }


//get comment

    private void getComment(String id) {
       // progressDialog.show();
        //baseRespons.clear();
        Retrofit retrofit = RetrofitInstance.getRetrofitInstace();
        ApiEndpoint api = retrofit.create(ApiEndpoint.class);
        api.getComment(id).enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
               // progressDialog.dismiss();

                commentList = response.body();
                // title.setText(response.body().getTitle());
                commentAdapter.setCommentList(commentList);
                CrecyclerView.setAdapter(commentAdapter);
                commentAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                //progressDialog.dismiss();
                Toast.makeText(Article_Details.this, "can not eComment", Toast.LENGTH_SHORT).show();

            }
        });
    }
    //post comment

    private void postComment(Comment comment) {
        SherdPref sharedPrefUtils = new SherdPref(Article_Details.this);
        if (sharedPrefUtils.isLoggedIn()) {
            String token = "Token " + sharedPrefUtils.getToken();
            Log.d("++++", String.valueOf(token));
            Retrofit retrofit = RetrofitInstance.getRetrofitInstace();
            ApiEndpoint apiEndpoint = retrofit.create(ApiEndpoint.class);
            apiEndpoint.postComment(token, comment).enqueue(new Callback<Comment>() {
                @Override
                public void onResponse(Call<Comment> call, Response<Comment> response) {
                    if (response.isSuccessful()) {
                        eComment.setText("");
                        Toast.makeText(Article_Details.this, "Comment successful", Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(getApplicationContext(),ArticleActivity.class));
                        getComment(article_id);

                    } else {
                        Toast.makeText(Article_Details.this, "something is fishy", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<Comment> call, Throwable t) {
                    Toast.makeText(Article_Details.this, "Can not connect to server", Toast.LENGTH_SHORT).show();

                }
            });
        } else {
            Toast.makeText(Article_Details.this, "You need to login ", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Article_Details.this,Login.class));
            finish();
        }

    }

    public void postCommentMethod(View view) {
        String Comment_name = eComment.getText().toString();
        Comment comment = new Comment();
        comment.setDetails(Comment_name);
        comment.setArticle(Integer.parseInt(article_id.trim()));
        postComment(comment);
    }


    public void updateComment(Comment comment, String comment_id) {


        SherdPref sharedPrefUtils = new SherdPref(Article_Details.this);
        if (sharedPrefUtils.isLoggedIn()) {
            String token = "Token " + sharedPrefUtils.getToken();
            Log.d("++++", String.valueOf(token));
            Retrofit retrofit = RetrofitInstance.getRetrofitInstace();
            ApiEndpoint apiEndpoint = retrofit.create(ApiEndpoint.class);
            apiEndpoint.updateComment(token, comment, comment_id).enqueue(new Callback<Comment>() {
                @Override
                public void onResponse(Call<Comment> call, Response<Comment> response) {

                    if (response.isSuccessful()) {
                        eComment.setText("");
                        Toast.makeText(Article_Details.this, "Update successful", Toast.LENGTH_SHORT).show();

                        getComment(article_id);
                    } else {
                        Toast.makeText(Article_Details.this, "Try agian", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<Comment> call, Throwable t) {
                    Toast.makeText(Article_Details.this, "cannot connect", Toast.LENGTH_SHORT).show();

                }
            });
        }


    }

    private void updateDialogue(final Comment comment) {
        final Dialog dialog = new Dialog(Article_Details.this);
        dialog.setContentView(R.layout.comment_update_dialog);
        dialog.setCancelable(false);
        Button saveBtn = dialog.findViewById(R.id.save_btn);
        Button cancelBtn = dialog.findViewById(R.id.cancel_btn);
        final EditText et = dialog.findViewById(R.id.et_update_comment);
        et.setText(comment.getDetails());

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                comment.setDetails(et.getText().toString());
                updateComment(comment, String.valueOf(comment.getId()));
                dialog.dismiss();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }


    private void DeleteComment(final String comment_id) {


        SherdPref sharedPrefUtils = new SherdPref(Article_Details.this);
        if (sharedPrefUtils.isLoggedIn()) {
            String token = "Token " + sharedPrefUtils.getToken();
            Retrofit retrofit = RetrofitInstance.getRetrofitInstace();
            ApiEndpoint apiEndpoint = retrofit.create(ApiEndpoint.class);
            apiEndpoint.deleteComment(token, comment_id).enqueue(new Callback<Comment>() {
                @Override
                public void onResponse(Call<Comment> call, Response<Comment> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(Article_Details.this, "Delete successful", Toast.LENGTH_SHORT).show();
                        getComment(article_id);

                    } else {
                        Toast.makeText(Article_Details.this, "Try agin", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<Comment> call, Throwable t) {
                    Toast.makeText(Article_Details.this, "Can not connect to server", Toast.LENGTH_SHORT).show();

                }
            });
        } else {
            startActivity(new Intent(Article_Details.this, Login.class));
            finish();
        }

    }


    public String stripHtml(String html) {
        return Html.fromHtml(html).toString();
    }

}
