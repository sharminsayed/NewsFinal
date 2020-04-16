package com.gh0stcr4ck3r.news.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.gh0stcr4ck3r.news.R;
import com.gh0stcr4ck3r.news.response.Article;
import com.gh0stcr4ck3r.news.ui.activity.ArticleList;
import com.gh0stcr4ck3r.news.ui.activity.Article_Details;
import com.gh0stcr4ck3r.news.utils.DatePref;

import java.text.ParseException;
import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder>{
    Context mcontext;
    List<Article>articleList;
    Article articleModel;
    OnItemClick litener;

    public ArticleAdapter(Context mcontext, List<Article> articleList) {
        this.mcontext = mcontext;
        this.articleList = articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mcontext).inflate(R.layout.activity_article_cardview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Article articleModel=articleList.get(position);
        holder.title.setText(articleModel.getTitle());
        try {
            holder.date.setText(String.format("Date: %s", DatePref.ConvertToNewFormate(articleList.get(position).getUpdatedAt())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.details.setText(stripHtml(articleModel.getDetails()));
        Glide.with(mcontext).load(articleList.get(position).getThumbnail()).into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gId= articleList.get(position).getId().toString();
                Intent intent=new Intent(mcontext, Article_Details.class);
                intent.putExtra("article_id",gId);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//activityr permission explicity

                mcontext.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title,details,date;
        ImageView imageView;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title=(itemView).findViewById(R.id.article_title);
            details=(itemView).findViewById(R.id.article_details);
            date=(itemView).findViewById(R.id.article_date);
            imageView=(itemView).findViewById(R.id.article_image);
            cardView=(itemView).findViewById(R.id.article_card);
        }
    }
    public interface OnItemClick{

    }

    public String stripHtml(String html){
        return Html.fromHtml(html).toString();
    }
}
