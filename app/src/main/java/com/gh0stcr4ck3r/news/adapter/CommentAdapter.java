package com.gh0stcr4ck3r.news.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gh0stcr4ck3r.news.R;
import com.gh0stcr4ck3r.news.response.Author;
import com.gh0stcr4ck3r.news.response.Comment;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    List<Comment>commentList;
    Context mcontext;
    private OnItemClicked listener;

    public CommentAdapter(List<Comment> commentList, Context mcontext, OnItemClicked listener) {
        this.commentList = commentList;
        this.mcontext = mcontext;
        this.listener = listener;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mcontext).inflate(R.layout.comment_cardview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {
        final Comment comment=commentList.get(position);

        holder.Cname.setText(String.format("%s %s", commentList.get(position).getUser().getFirstName(), commentList.get(position).getUser().getLastName()));;
        holder.Cdetails.setText(commentList.get(position).getDetails());
        Glide.with(mcontext).load(commentList.get(position).getUser().getProfilePic()).placeholder(R.drawable.dummy).into(holder.imageView);



        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                listener.onLongClick(comment);
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView imageView;
        CardView cardView;
        TextView Cname,Cdetails;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.comment_image);
            cardView=itemView.findViewById(R.id.comment_card);
            Cname=itemView.findViewById(R.id.comment_name);
            Cdetails=itemView.findViewById(R.id.comment_details);
        }
    }

    public interface OnItemClicked {
        void onClicked(Comment comment);
        void onLongClick(Comment comment);
    }

}

