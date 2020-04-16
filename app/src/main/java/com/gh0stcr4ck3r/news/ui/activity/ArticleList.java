package com.gh0stcr4ck3r.news.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.gh0stcr4ck3r.news.R;
import com.gh0stcr4ck3r.news.adapter.ArticleAdapter;
import com.gh0stcr4ck3r.news.network.ApiEndpoint;
import com.gh0stcr4ck3r.news.network.RetrofitInstance;
import com.gh0stcr4ck3r.news.response.Article;
import com.gh0stcr4ck3r.news.utils.ProgressDialogUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ArticleList extends AppCompatActivity {

    ArticleAdapter articleAdapter;
    Article articleModel;
    RecyclerView recyclerView;
    List<Article> articleList;
    private String category_id, tag_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);

        articleList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.article_recycler);
        articleAdapter = new ArticleAdapter(this, articleList);
        recyclerView.setAdapter(articleAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        //recive data from category
        Intent intent = getIntent();
        category_id = intent.getStringExtra("category_id");
        tag_id=intent.getStringExtra("tag_id");
        getArticleList();

    }

    public void getArticleList() {
        final ProgressDialog progressDialog=ProgressDialogUtils.getProgressDialog(ArticleList.this);
        progressDialog.show();
        Retrofit retrofit = RetrofitInstance.getRetrofitInstace();
        ApiEndpoint apiEndpoint = retrofit.create(ApiEndpoint.class);
        apiEndpoint.getArticleByCategoryAndTag(category_id,tag_id).enqueue(new Callback<List<Article>>() {
            @Override
            public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    articleList = response.body();
                    articleAdapter.setArticleList(articleList);
                    recyclerView.setAdapter(articleAdapter);
                    articleAdapter.notifyDataSetChanged();
                }else
                {
                    progressDialog.dismiss();
                    Toast.makeText(ArticleList.this,"something is wrong",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<Article>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ArticleList.this, "can not connect", Toast.LENGTH_SHORT).show();

            }
        });


    }
}
