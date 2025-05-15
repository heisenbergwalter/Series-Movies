package com.example.movieseriesv2.ui.home;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cinemaseries.R;
import com.example.movieseriesv2.data.model.Serie;
import com.example.movieseriesv2.ui.details.SerieDetailActivity;

import java.util.List;

public class SerieAdapter extends RecyclerView.Adapter<SerieAdapter.SerieViewHolder> {

    private List<Serie> serieList;

    public SerieAdapter(List<Serie> serieList) {
        this.serieList = serieList;
    }

    @NonNull
    @Override
    public SerieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_serie, parent, false);
        return new SerieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SerieViewHolder holder, int position) {
        Serie serie = serieList.get(position);

        holder.title.setText(serie.getName());
        holder.releaseDate.setText(serie.getFirstAirDate());
        holder.rating.setText(String.valueOf(serie.getVoteAverage()));

        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w500" + serie.getPosterPath())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(holder.poster);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), SerieDetailActivity.class);
            intent.putExtra(SerieDetailActivity.EXTRAID, serie.getId());
            intent.putExtra(SerieDetailActivity.EXTRA_NAME, serie.getName());
            intent.putExtra(SerieDetailActivity.EXTRA_DATE, serie.getFirstAirDate());
            intent.putExtra(SerieDetailActivity.EXTRA_POSTER, serie.getPosterPath());
            intent.putExtra(SerieDetailActivity.EXTRA_OVERVIEW, serie.getOverview());
            intent.putExtra(SerieDetailActivity.EXTRA_VOTE, serie.getVoteAverage());
            intent.putExtra(SerieDetailActivity.EXTRA_POPULARITY, serie.getPopularity());
            intent.putExtra(SerieDetailActivity.EXTRA_LANGUAGE, serie.getOriginalLanguage());

            holder.itemView.getContext().startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return serieList != null ? serieList.size() : 0;
    }

    public void updateSeries(List<Serie> series) {
        this.serieList = series;
        notifyDataSetChanged();
    }

    public static class SerieViewHolder extends RecyclerView.ViewHolder {
        TextView title, releaseDate, rating;
        ImageView poster;

        public SerieViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            releaseDate = itemView.findViewById(R.id.release_Date);
            rating = itemView.findViewById(R.id.rating);
            poster = itemView.findViewById(R.id.poster);
        }
    }
}
