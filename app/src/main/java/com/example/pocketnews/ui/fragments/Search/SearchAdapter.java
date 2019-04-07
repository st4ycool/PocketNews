package com.example.pocketnews.ui.fragments.Search;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.pocketnews.R;
import com.example.pocketnews.data.model.Article;
import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private ArrayList<Article> sources;

    public SearchAdapter() {
        sources = new ArrayList<>();
    }

    public void setData(ArrayList<Article> articles) {
        sources.clear();
        sources.addAll(articles);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_row_articles, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(sources.get(position).getSource().getName());
        holder.description.setText(sources.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return sources.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView description;
        private TextView name;

        ViewHolder(View itemView) {
            super(itemView);

            description = itemView.findViewById(R.id.description_articles);
            name = itemView.findViewById(R.id.name_articles);
        }
    }
}