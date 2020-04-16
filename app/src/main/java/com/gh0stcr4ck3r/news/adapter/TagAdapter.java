package com.gh0stcr4ck3r.news.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.gh0stcr4ck3r.news.R;
import com.gh0stcr4ck3r.news.response.Tag;
import com.gh0stcr4ck3r.news.ui.activity.ArticleList;

import java.util.List;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.ViewHolder> {
    List<Tag> tagList;
    Context mcontext;

    public TagAdapter(List<Tag> tagList, Context mcontext) {
        this.tagList = tagList;
        this.mcontext = mcontext;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mcontext).inflate(R.layout.tag_cardview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {
        holder.tag_name.setText(tagList.get(position).getName());
        holder.tagCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gId=tagList.get(position).getId().toString();
                Intent intent=new Intent(mcontext, ArticleList.class);
                intent.putExtra("tag_id",gId);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//activityr permission explicity

                mcontext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return tagList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        TextView tag_name;
        CardView tagCard;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tag_name=itemView.findViewById(R.id.tag_text);
            tagCard=itemView.findViewById(R.id.tag_card);

        }
    }



}

