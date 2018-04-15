package com.example.asus.newman;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by asus on 2017/10/24.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private final int TYPE_BODY = 0;
    private final int TYPE_FOOTER = 1;
    private Context mContext;

    private News news = null;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View NewsView;
        ImageView NewsImage;
        TextView NewsName;


        public ViewHolder(View view) {
            super(view);
            NewsView = view;
            NewsImage = (ImageView) view.findViewById(R.id.News_pic);
            NewsName = (TextView) view.findViewById(R.id.News_subject);


        }
    }

    public NewsAdapter(Context context, News news) {

        this.news = news;
        this.mContext = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_BODY) {
            final View view = LayoutInflater.from(mContext)
                    .inflate(R.layout.news_item, parent, false);
            final ViewHolder holder = new ViewHolder(view);
            holder.NewsView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();


                    Toast.makeText(v.getContext(), news.getData().get(position).getSubject(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(v.getContext(), NewsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("1", news.getData().get(position).getIndex());
                    intent.putExtras(bundle);
                    v.getContext().startActivity(intent);

                }
            });

            return holder;
        } else {
            final View view = LayoutInflater.from(mContext)
                    .inflate(R.layout.footer_item, parent, false);
            final FooterViewHolder holder = new FooterViewHolder(view);
            holder.NewsView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();


                    Toast.makeText(v.getContext(), news.getData().get(position).getSubject(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(v.getContext(), NewsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("1", news.getData().get(position).getIndex());
                    intent.putExtras(bundle);
                    v.getContext().startActivity(intent);

                }
            });
            return holder;

        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int type = getItemViewType(position);
        if (type == TYPE_BODY) {
            Glide.with(mContext)
                    .load(news.getData().get(position).getPic())
                    .centerCrop()
                    .placeholder(R.drawable.not)
                    .into(holder.NewsImage);
            holder.NewsName.setText(news.getData().get(position).getSubject());
        }


    }

    @Override
    public int getItemCount() {
        return news.getData().size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == news.getData().size() - 1) {
            return TYPE_FOOTER;
        } else {
            return TYPE_BODY;
        }
    }

    public class FooterViewHolder extends NewsAdapter.ViewHolder {
        public FooterViewHolder(View ItemView) {
            super(ItemView);
        }
    }
}

