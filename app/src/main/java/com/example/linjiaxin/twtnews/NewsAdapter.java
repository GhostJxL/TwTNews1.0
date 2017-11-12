package com.example.linjiaxin.twtnews;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by linjiaxin on 2017/11/9.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private Context mContext;
    private List<NewsBean> myNewsList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView newsPic;
        TextView newsTitle;
        TextView newsMessage;
        View newsViiew;

        public ViewHolder(View view) {
            super(view);
            newsViiew = view;
            cardView = (CardView) view;
            newsPic = (ImageView) view.findViewById(R.id.news_pic);
            newsTitle = (TextView) view.findViewById(R.id.news_title);
            newsMessage = (TextView) view.findViewById(R.id.new_message);
        }
    }

    public NewsAdapter(List<NewsBean> newsList) {
        myNewsList = newsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.news_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.newsViiew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                NewsBean newsBean = myNewsList.get(position);
                Intent intent = new Intent(mContext,DetailsActivity.class);
                intent.putExtra("index",newsBean.getIndex());
                mContext.startActivity(intent);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NewsBean news = myNewsList.get(position);
        Glide.with(mContext)
                .load(news.getPic())
                .into(holder.newsPic);
        holder.newsTitle.setText(news.getSubject());
        holder.newsMessage.setText(news.getVisitcount()+" "+(news.getComments())+"  ");

    }

    @Override
    public int getItemCount() {

        return myNewsList.size();
    }
}
