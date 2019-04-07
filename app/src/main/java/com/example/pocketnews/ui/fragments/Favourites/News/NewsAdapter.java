package com.example.pocketnews.ui.fragments.Favourites.News;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pocketnews.R;
import com.example.pocketnews.data.model.Article;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

        private ArrayList<Article> sources;
        public NewsAdapter() {
            sources = new ArrayList<>();
        }


        public void setData(ArrayList<Article> articles) {
            sources.clear();
            sources.addAll(articles);
            notifyDataSetChanged();
            }

        @NonNull
        @Override
        public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_row_news, parent, false);
            return new NewsAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder holder, int position) {
                String imageUrl = sources.get(position).getUrlToImage();
            if (imageUrl != null)
            Picasso.get().setIndicatorsEnabled(true);
            Picasso.get().load(imageUrl).into(holder.image_view);

            holder.title.setText(sources.get(position).getTitle());
            holder.description.setText(sources.get(position).getDescription());

        }

        @Override
        public int getItemCount() {
            return sources.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private ImageView image_view;
            private TextView title;
            private TextView description;

            ViewHolder(View itemView) {
                super(itemView);

                image_view = itemView.findViewById(R.id.image_article);
                title = itemView.findViewById(R.id.title_article);
                description = itemView.findViewById(R.id.description_article);
            }
        }
    }