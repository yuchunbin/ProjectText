package com.chunbin.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chunbin.app.R;
import com.chunbin.app.activity.PlayVideoActivity;
import com.chunbin.app.entity.Trailers;

import java.util.ArrayList;

public class SmartServicePagerAdapter extends RecyclerView.Adapter<SmartServicePagerAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Trailers> datas;
    private String TAG ="SmartServicePagerAdapter";

    public final static String VIDEO_ACTIVITY_KEY = "VIDEO_ACTIVITY_KEY";
    public final static String VIDEO_ACTIVITY_URL="VIDEO_ACTIVITY_URL";
    public final static String VIDEO_ACTIVITY_NAME="VIDEO_ACTIVITY_NAME";


    public SmartServicePagerAdapter(Context context, ArrayList<Trailers> datas) {
    this.context = context;
    this.datas = datas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = View.inflate(context, R.layout.trailers_view_items,null);
        itemView.setSelected(true);
        return new ViewHolder(itemView);
    }
    private Trailers trailers;
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        trailers = datas.get(position);
        Glide.with(context)
                .load(trailers.getCoverImg().toString())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(holder.trailers_cover_Img);
        holder.trailers_cover_Img.setScaleType(ImageView.ScaleType. CENTER_CROP);
        holder.trailers_movie_Name.setText(trailers.getMovieName().toString());
        holder.trailers_rating.setText(trailers.getRating().toString());
        holder.trailers_summary.setText(trailers.getSummary().toString());
        holder.trailers_type.setText(trailers.getType().toString());
        holder.play_now.setOnClickListener(new MovieOnClickListenrt());
        holder.cache.setOnClickListener(new MovieOnClickListenrt());

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void clearDatas() {
        datas.clear();
        notifyItemRangeChanged(0,datas.size());
    }

    public void addDatas(int position, ArrayList<Trailers> datas) {
        if (datas.size()>0 && datas != null) {
            datas.addAll(position,datas);
            notifyItemRangeChanged(position,datas.size());
        }
    }
    public int getDataCount(){
        return datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView trailers_cover_Img;
        private TextView trailers_movie_Name;
        private TextView trailers_rating;
        private TextView trailers_summary;
        private TextView trailers_type;
        private Button play_now;
        private Button cache;
        public ViewHolder(View itemView) {
            super(itemView);
            trailers_cover_Img = itemView.findViewById(R.id.trailers_cover_Img);
            trailers_movie_Name= itemView.findViewById(R.id.trailers_movie_Name);
            trailers_rating = itemView.findViewById(R.id.trailers_rating);
            trailers_summary = itemView.findViewById(R.id.trailers_summary);
            trailers_type = itemView.findViewById(R.id.trailers_type);
            play_now = itemView.findViewById(R.id.play_now);
            cache = itemView.findViewById(R.id.cache);
        }
    }

    private class MovieOnClickListenrt implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = null;
            Bundle bundle = new Bundle();
            switch (view.getId()){
                case R.id.cache:

                    Toast.makeText(context, "缓存", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.play_now:
                    bundle.putString(VIDEO_ACTIVITY_URL,trailers.getUrl());
                    bundle.putString(VIDEO_ACTIVITY_NAME,trailers.getMovieName());
                    intent = new Intent(context, PlayVideoActivity.class);
                    intent.putExtra(VIDEO_ACTIVITY_KEY,bundle);
                    break;
            }
            context.startActivity(intent);
        }
    }
}
