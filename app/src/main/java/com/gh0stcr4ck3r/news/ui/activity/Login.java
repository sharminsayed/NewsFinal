package com.gh0stcr4ck3r.news.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.gh0stcr4ck3r.news.R;
import com.gh0stcr4ck3r.news.network.ApiEndpoint;
import com.gh0stcr4ck3r.news.network.RetrofitInstance;
import com.gh0stcr4ck3r.news.response.Author;
import com.gh0stcr4ck3r.news.utils.SherdPref;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Login extends AppCompatActivity {
    private EditText email,password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.et_email);
        password = findViewById(R.id.et_pass);

    }

    public void LoginMethod(View view) {

        String user_email=email.getText().toString();
        String user_password=password.getText().toString();

        Author author=new Author();
        author.setUsername(user_email);
        author.setPassword(user_password);
        CreateLogin(author);

    }



    public void CreateLogin(Author author){
        Retrofit retrofit= RetrofitInstance.getRetrofitInstace();
        ApiEndpoint apiEndpoint=retrofit.create(ApiEndpoint.class);
        apiEndpoint.createLogin(author).enqueue(new Callback<Author>() {
            @Override
            public void onResponse(Call<Author> call, Response<Author> response) {
                if (response.isSuccessful()){
                    String token=response.body().getToken();
                    Integer user_id = response.body().getId();
                    SherdPref sherdPref=new SherdPref(Login.this);
                    sherdPref.saveTokenAndID(token, String.valueOf(user_id));
                    Toast.makeText(Login.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    Animatoo.animateSlideRight(Login.this);
                }
            }

            @Override
            public void onFailure(Call<Author> call, Throwable t) {
                Toast.makeText(Login.this,"can not connect",Toast.LENGTH_SHORT).show();



            }
        });

    }


    public void gotoSignupActivity(View view) {
        startActivity(new Intent(Login.this, SignUp.class));
        Animatoo.animateDiagonal(Login.this);
        finish();
    }
}
