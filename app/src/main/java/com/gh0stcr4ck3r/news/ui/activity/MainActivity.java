package com.gh0stcr4ck3r.news.ui.activity;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.gh0stcr4ck3r.news.R;
import com.gh0stcr4ck3r.news.adapter.CategoryAdapter;
import com.gh0stcr4ck3r.news.network.ApiEndpoint;
import com.gh0stcr4ck3r.news.network.RetrofitInstance;
import com.gh0stcr4ck3r.news.response.Category;
import com.gh0stcr4ck3r.news.utils.ProgressDialogUtils;
import com.gh0stcr4ck3r.news.utils.SherdPref;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    CategoryAdapter categoryAdapter;
    RecyclerView recyclerView;
    List<Category>categoryList;
    //Category category;
    private Menu navigationmenu;
    private MenuItem v_loginsignup,v_profile,v_logout;
    private SherdPref sherdPref;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationmenu=navigationView.getMenu();
        v_loginsignup=navigationmenu.getItem(0);
        v_profile=navigationmenu.getItem(1);
        v_logout=navigationmenu.getItem(4);
        sherdPref=new SherdPref(MainActivity.this);
        if(sherdPref.isLoggedIn()){
            v_loginsignup.setVisible(false);
            v_profile.setVisible(true);
            v_logout.setVisible(true);
        }else {
            v_logout.setVisible(true);
            v_profile.setVisible(false);
            v_logout.setVisible(false);
        }













        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);



        categoryList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.category_recycler);
        categoryAdapter = new CategoryAdapter(getApplicationContext(), categoryList);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(categoryAdapter);

        getCategory();

    }



    public void getCategory(){
       final ProgressDialog progressDialog=ProgressDialogUtils.getProgressDialog(MainActivity.this);
       progressDialog.show();
        Retrofit retrofit = RetrofitInstance.getRetrofitInstace();
        ApiEndpoint api = retrofit.create(ApiEndpoint.class);
        api.getCategoryList().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                progressDialog.dismiss();
                if(response.isSuccessful()){
                    categoryList=response.body();
                    Log.d("++++", String.valueOf(categoryList));
                    categoryAdapter.setCategoryList(categoryList);
                    recyclerView.setAdapter(categoryAdapter);
                    categoryAdapter.notifyDataSetChanged();
                   // Animatoo.animateSlideLeft(MainActivity.this);
                }

            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "can not connect", Toast.LENGTH_LONG).show();

            }

    });
    }

        @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            Animatoo.animateSlideLeft(this); //fire the slide left animation

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_auth) {

            Intent intent=new Intent(getApplicationContext(),Login.class);

            startActivity(intent);
           Animatoo.animateSlideUp(this);

        } else if (id == R.id.nav_profile) {
            Intent intent=new Intent(getApplicationContext(),Profile.class);
            startActivity(intent);
            Animatoo.animateInAndOut(this);

        } else if (id == R.id.nav_share) {

            shareApp();
            Animatoo.animateSlideLeft(this);


        }
        else if (id == R.id.nav_rate) {
          showRating();
        }
        else if (id == R.id.nav_logout) {
            sherdPref.clearToken();
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            Animatoo.animateFade(this);
            finish();

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void shareApp() {
        /*Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Check out the App at: " + getString(R.string.app_link));
        sendIntent.setType("text/plain");
        startActivity(sendIntent);*/
    }

    public void showRating() {
      /*  Uri uri = Uri.parse(getString(R.string.rate_app_url));
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(getString(R.string.app_link))));
        }*/
    }
}

