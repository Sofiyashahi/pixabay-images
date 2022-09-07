package com.sofiya.pixabayimages;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sofiya.pixabayimages.ApiModel.Hits;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter {

    private final Context context;
    private final List<Hits> imgList;
    private final AdapterClick adapterClick;
    private final int adapterType;

    public ImageAdapter(Context context, List<Hits> imgList, AdapterClick adapterClick, int adapterType) {
        this.context = context;
        this.imgList = imgList;
        this.adapterClick = adapterClick;
        this.adapterType = adapterType;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        if (adapterType == 0) {
            View view = layoutInflater.inflate(R.layout.row_image, parent, false);
            return new ImageViewHolder(view);
        } else {
            View view = layoutInflater.inflate(R.layout.row_large_image, parent, false);
            return new LargeImageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (adapterType == 0) {
            ((ImageViewHolder) holder).bindData(imgList.get(position));
        } else {
            ((LargeImageViewHolder) holder).bindData(imgList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return imgList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.img_thumbnail);
            itemView.setOnClickListener(this);
        }

        public void bindData(Hits hits) {
            Glide.with(context).load(hits.getLargeImageURL()).into(imageView);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            adapterClick.itemClick(imgList.get(position), position);
        }
    }

    public class LargeImageViewHolder extends RecyclerView.ViewHolder {

        ImageView largeImageView;

        public LargeImageViewHolder(@NonNull View itemView) {
            super(itemView);

            largeImageView = itemView.findViewById(R.id.img_large);
        }

        public void bindData(Hits hits) {
            Glide.with(context).load(hits.getLargeImageURL()).into(largeImageView);
        }
    }
}
