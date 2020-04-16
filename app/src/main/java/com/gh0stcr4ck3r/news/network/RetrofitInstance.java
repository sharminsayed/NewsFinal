package com.gh0stcr4ck3r.news.network;

import com.gh0stcr4ck3r.news.utils.BaseConstant;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    public static Retrofit retrofit;
    public static Retrofit getRetrofitInstace(){
        if (retrofit == null){
            OkHttpClient okHttpClient=new OkHttpClient.Builder().build();
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BaseConstant.BASE_URAL)
                    // .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }

}
