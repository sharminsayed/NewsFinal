package com.gh0stcr4ck3r.news.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.gh0stcr4ck3r.news.R;
import com.gh0stcr4ck3r.news.network.ApiEndpoint;
import com.gh0stcr4ck3r.news.network.RetrofitInstance;
import com.gh0stcr4ck3r.news.response.Author;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignUp extends AppCompatActivity {
    private EditText f_name,l_name,email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        f_name=findViewById(R.id.first_name);
        l_name=findViewById(R.id.last_name);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
    }

    public void SignupMethod(View view) {
        String userFname=f_name.getText().toString();
        String userLname=l_name.getText().toString();
        String Email=email.getText().toString();
        String UserPass=password.getText().toString();
         Author author=new Author();
         author.setFirstName(userFname);
         author.setLastName(userLname);
         author.setEmail(Email);
         author.setPassword(UserPass);
         createUser(author);


    }


    public void createUser(Author author){
        Retrofit retrofit= RetrofitInstance.getRetrofitInstace();
        ApiEndpoint apiEndpoint=retrofit.create(ApiEndpoint.class);
        apiEndpoint.createUser(author).enqueue(new Callback<List<Author>>() {
            @Override
            public void onResponse(Call<List<Author>> call, Response<List<Author>> response) {
                Toast.makeText(SignUp.this, "sign up Successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), Login.class));
                Animatoo.animateSlideLeft(SignUp.this);
            }

            @Override
            public void onFailure(Call<List<Author>> call, Throwable t) {
                Toast.makeText(SignUp.this, "Try again", Toast.LENGTH_SHORT).show();

            }
        });


    }

    public void goToLogin(View view) {
        Intent intent=new Intent(SignUp.this,Login.class);
        startActivity(intent);
        Animatoo.animateShrink(SignUp.this);
        finish();

    }



}
