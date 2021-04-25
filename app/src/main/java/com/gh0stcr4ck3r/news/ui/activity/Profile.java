package com.gh0stcr4ck3r.news.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.gh0stcr4ck3r.news.R;
import com.gh0stcr4ck3r.news.network.ApiEndpoint;
import com.gh0stcr4ck3r.news.network.RetrofitInstance;
import com.gh0stcr4ck3r.news.response.Author;
import com.gh0stcr4ck3r.news.utils.DatePref;
import com.gh0stcr4ck3r.news.utils.SherdPref;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Profile extends AppCompatActivity {
    private EditText fname,lname,email,password;
    private String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        fname=findViewById(R.id.update_first_name);
        lname=findViewById(R.id.update_last_name);
        email=findViewById(R.id.update_email);
        password=findViewById(R.id.update_password);
        SherdPref sharePrefUtils = new SherdPref(Profile.this);
        user_id = sharePrefUtils.getID();
        if (sharePrefUtils.isLoggedIn()) {
            retriveProfile();
        } else {
            Toast.makeText(Profile.this,"please login first", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Profile.this, Login.class));
            Animatoo.animateSlideLeft(Profile.this);
            finish();
        }

    }




    private void retriveProfile() {
        SherdPref sharedPrefUtils = new SherdPref(Profile.this);
        if (sharedPrefUtils.isLoggedIn()) {
            String token = "Token " + sharedPrefUtils.getToken();
            //Log.d("++++", String.valueOf(token));

            Retrofit retrofit = RetrofitInstance.getRetrofitInstace();
            ApiEndpoint apiEndpoint = retrofit.create(ApiEndpoint.class);
            apiEndpoint.retriveProfile(token, user_id).enqueue(new Callback<Author>() {
                @Override
                public void onResponse(Call<Author> call, Response<Author> response) {
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        fname.setText(response.body().getFirstName());
                        lname.setText(response.body().getLastName());
                        email.setText(response.body().getEmail());

                       // Toast.makeText(Profile.this,"", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        Toast.makeText(Profile.this,"Try again", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Author> call, Throwable t) {
                    Toast.makeText(Profile.this,"can not connect", Toast.LENGTH_SHORT).show();

                }
            });


        }
    }

    private void updateProfile(Author author,String user_id){
        SherdPref sherdPref = new SherdPref(Profile.this);
        if (sherdPref.isLoggedIn()) {
            String token = "Token " + sherdPref.getToken();
            Log.d("++++", String.valueOf(token));

            Retrofit retrofit = RetrofitInstance.getRetrofitInstace();
            ApiEndpoint apiEndpoint = retrofit.create(ApiEndpoint.class);
            apiEndpoint.updateProfile(token,author,user_id).enqueue(new Callback<Author>() {
                @Override
                public void onResponse(Call<Author> call, Response<Author> response) {
                    if(response.isSuccessful()) {
                        Toast.makeText(Profile.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                        Animatoo.animateSwipeRight(Profile.this);
                         retriveProfile();

                    }
                    else {
                        Toast.makeText(Profile.this, "Try again", Toast.LENGTH_SHORT).show();


                    }

                }

                @Override
                public void onFailure(Call<Author> call, Throwable t) {
                    Toast.makeText(Profile.this, "can not connect server", Toast.LENGTH_SHORT).show();

                }
            });


        }

    }





    public void saveProfileMethod(View view) {
        String user_f_name=fname.getText().toString();
        String user_l_name=lname.getText().toString();
        String user_email=email.getText().toString();
        String user_password=password.getText().toString();

        Author author=new Author();
        author.setFirstName(user_f_name);
        author.setLastName(user_l_name);
        author.setEmail(user_email);
        author.setPassword(user_password);
        updateProfile(author,user_id);

    }


    public void deleteProfile(String user_id){
        final SherdPref sherdPref = new SherdPref(Profile.this);
        Retrofit retrofit=RetrofitInstance.getRetrofitInstace();
        ApiEndpoint apiEndpoint=retrofit.create(ApiEndpoint.class);
        apiEndpoint.deleteProfile(user_id).enqueue(new Callback<Author>() {
            @Override
            public void onResponse(Call<Author> call, Response<Author> response) {
                sherdPref.clearToken();
                Toast.makeText(Profile.this, "Delete profile successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                Animatoo.animateSwipeRight(Profile.this);
                finish();

            }

            @Override
            public void onFailure(Call<Author> call, Throwable t) {
                Toast.makeText(Profile.this, "Try again", Toast.LENGTH_SHORT).show();


            }
        });


    }
    public void deleteProfileMethod(View view) {
        final Dialog dialog=new Dialog(this);
        dialog.setTitle("Action Bar");
        dialog.setContentView(R.layout.activity_delete_dialog);
        Button addBtn, editBtn, deleteBtn;

        editBtn=dialog.findViewById(R.id.Cancel);
        deleteBtn=dialog.findViewById(R.id.Delete);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteProfile(user_id);

            }
        });



        dialog.show();

    }
}
