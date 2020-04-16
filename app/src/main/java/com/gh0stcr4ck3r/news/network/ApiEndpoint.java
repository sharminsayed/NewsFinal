package com.gh0stcr4ck3r.news.network;

import com.gh0stcr4ck3r.news.response.Article;
import com.gh0stcr4ck3r.news.response.Author;
import com.gh0stcr4ck3r.news.response.Category;
import com.gh0stcr4ck3r.news.response.Comment;
import com.gh0stcr4ck3r.news.response.Tag;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiEndpoint {
    //for categoryList
    @Headers("Content-Type:application/json")
    @GET("category/")
    Call<List<Category>> getCategoryList();

    //for articleList
    @Headers("Content-Type:application/json")
    @GET("article/")
    Call<List<Article>>getArticleByCategoryAndTag(@Query("category") String category_id ,@Query("tag") String tag_id);

    //forarticleDetails
    @Headers("Content-Type:application/json")
    @GET("article/{article_id}/")
    Call<Article> getArticleDetails(@Path("article_id") String article_id);


    //For signup
    @Headers("Content-Type:application/json")
    @POST("auth/signup/")
    Call<List<Author>>createUser(@Body Author author);

    //for Login
    @Headers("Content-Type:application/json")
    @POST("auth/login/")
    Call<Author> createLogin(@Body Author author);


    //for Retrive Profile
    @Headers("Content-Type:application/json")
    @GET("user/{user_id}/")
    Call<Author>retriveProfile(@Header("Authorization") String token, @Path("user_id") String user_id);

    //forUpdate Profile
    @Headers("Content-Type:application/json")
    @PUT("user/{user_id}/")
    Call<Author> updateProfile(@Header("Authorization") String token, @Body Author author, @Path("user_id") String user_id);

    //For Delete Profile
    @Headers("Content-Type:application/json")
    @DELETE("user/{user_id}/")
    Call<Author> deleteProfile(@Path("user_id") String user_id);

    @Headers("Content-Type:application/json")
    @GET("tag/")
    Call<List<Tag>>aticleListByTag();

    @Headers("Content-Type:application/json")
    @GET("comment/")
    Call<List<Comment>> getComment(@Query("article") String article_id);


    @Headers("Content-Type:application/json")
    @POST("comment/")
    Call<Comment> postComment(@Header("Authorization") String token, @Body Comment comment);


    @Headers("Content-Type:application/json")
    @PUT("comment/{comment_id}/")
    Call<Comment> updateComment(@Header("Authorization") String token, @Body Comment comment, @Path("comment_id") String comment_id);

    @Headers("Content-Type:application/json")
    @DELETE("comment/{comment_id}/")
    Call<Comment> deleteComment(@Header("Authorization") String token, @Path("comment_id") String comment_id);




}
