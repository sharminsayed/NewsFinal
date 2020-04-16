package com.gh0stcr4ck3r.news.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.gh0stcr4ck3r.news.R;
import com.gh0stcr4ck3r.news.response.Category;
import com.gh0stcr4ck3r.news.ui.activity.ArticleList;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    Context mcontext;
  List<Category>categoryList;
  Category categoryModel;

    public CategoryAdapter(Context mcontext, List<Category> categoryList) {
        this.mcontext = mcontext;
        this.categoryList = categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mcontext).inflate(R.layout.category_card_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {
        final Category categoryModel=categoryList.get(position);

        holder.textView.setText(categoryModel.getTitle());
        Glide.with(mcontext).load(categoryList.get(position).getThumbnail()).into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   Animatoo.animateSlideLeft(mcontext); //fire the slide left animation

                String CatID=categoryList.get(position).getId().toString();
                Intent intent=new Intent(mcontext, ArticleList.class);
                intent.putExtra("category_id",CatID);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mcontext.startActivity(intent);
               // Animatoo.animateInAndOut(mcontext); //fire the slide left animation



            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=(itemView).findViewById(R.id.cat_card_image);
            textView=(itemView).findViewById(R.id.car_card_title);
            cardView=(itemView).findViewById(R.id.cat_card);
        }
    }
    public interface listener{

    }
}
