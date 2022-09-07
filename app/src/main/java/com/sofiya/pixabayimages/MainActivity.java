package com.sofiya.pixabayimages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.sofiya.pixabayimages.ApiModel.Hits;
import com.sofiya.pixabayimages.ApiModel.Response;
import com.sofiya.pixabayimages.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity implements AdapterClick {

    private ActivityMainBinding binding;
    private List<Hits> imageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        imageList = new ArrayList<>();

        binding.rvImage.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        binding.rvImage.setLayoutManager(gridLayoutManager);

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
                            ImageAdapter adapter = new ImageAdapter(MainActivity.this, imageList, MainActivity.this, 0);
                            binding.rvImage.setAdapter(adapter);
                        }
                    }
                    binding.progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                binding.progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void itemClick(Hits hits, int position) {
        Intent intent = new Intent(MainActivity.this, LargeImageActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }
}