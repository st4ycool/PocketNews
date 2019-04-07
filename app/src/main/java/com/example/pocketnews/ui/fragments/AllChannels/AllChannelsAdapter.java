package com.example.pocketnews.ui.fragments.AllChannels;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.pocketnews.R;
import com.example.pocketnews.data.model.Channel;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

import static com.example.pocketnews.ui.fragments.AllChannels.AllChannelsPresenterImpl.FALSE;
import static com.example.pocketnews.ui.fragments.AllChannels.AllChannelsPresenterImpl.TRUE;

public class AllChannelsAdapter extends RecyclerView.Adapter<AllChannelsAdapter.ViewHolder> {

    private final ArrayList<Channel> sources;
    private final PublishSubject<String> onClickSubject = PublishSubject.create();

    public AllChannelsAdapter() {
        sources = new ArrayList<>();
    }

    public void setData(ArrayList<Channel> channels) {
        sources.clear();
        sources.addAll(channels);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AllChannelsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_row_all_channels, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllChannelsAdapter.ViewHolder holder, int position) {
        holder.name.setText(sources.get(position).getName());
        holder.description.setText(sources.get(position).getDescription());

        if (sources.get(position).getIsFavourite() == TRUE) {
            holder.star_button.setChecked(true);
        } else holder.star_button.setChecked(false);

        holder.star_button.setOnClickListener(v -> {

            //if it's favourite:
            if (sources.get(position).getIsFavourite() == TRUE) {
                holder.star_button.setChecked(false);
//                sources.get(position).setIsFavourite(FALSE);
                onClickSubject.onNext(holder.name.getText().toString());

                //if it's not favourite:
            } else if (sources.get(position).getIsFavourite() == FALSE) {
                holder.star_button.setChecked(true);
//                sources.get(position).setIsFavourite(TRUE);
                onClickSubject.onNext(holder.name.getText().toString());
            }
        });
    }

    Observable<String> getPositionClicks() {
        return onClickSubject;
    }

    @Override
    public int getItemCount() {
        return sources.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView description;
        private TextView name;
        private CheckBox star_button;

        ViewHolder(View itemView) {
            super(itemView);

            description = itemView.findViewById(R.id.all_channels_description);
            name = itemView.findViewById(R.id.all_channels_name);
            star_button = itemView.findViewById(R.id.star_button_all_channels);
        }
    }
}