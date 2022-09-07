package com.sofiya.pixabayimages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.SnapHelper;

import com.sofiya.pixabayimages.ApiModel.Hits;
import com.sofiya.pixabayimages.ApiModel.Response;
import com.sofiya.pixabayimages.databinding.ActivityLargeImageBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class LargeImageActivity extends AppCompatActivity {

    private ActivityLargeImageBinding binding;
    private List<Hits> imageList;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLargeImageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        imageList = new ArrayList<>();
        Intent intent = getIntent();
        if (intent != null) {
            position = intent.getIntExtra("position", 0);
        }

        binding.rvLarge.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        binding.rvLarge.setLayoutManager(linearLayoutManager);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(binding.rvLarge);

        getImages();
    }

    private void getImages() {
        Call<Response> responseCall = RetrofitClient.getInstance().getMyApi().getImages(1, 120, "latest");
        responseCall.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.isSuccessful()) {
                    Response myResponse = response.body();
                    if (myResponse != null) {
                        imageList = myResponse.getHits();
                        if (imageList != null && imageList.size() > 0) {
                            ImageAdapter adapter = new ImageAdapter(LargeImageActivity.this, imageList, null, 1);
                            binding.rvLarge.setAdapter(adapter);
                            binding.rvLarge.scrollToPosition(position);
                        }
                        binding.progressBarLarge.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Toast.makeText(LargeImageActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                binding.progressBarLarge.setVisibility(View.GONE);
            }
        });
    }
}