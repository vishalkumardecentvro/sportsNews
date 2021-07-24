package com.myapp.sportsnews.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.myapp.sportsnews.Connection;
import com.myapp.sportsnews.table.Sport;
import com.myapp.sportsnews.adapter.SportsAdapter;
import com.myapp.sportsnews.databinding.ActivityMainBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
  private ActivityMainBinding binding;
  private SportsAdapter sportsAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityMainBinding.inflate(getLayoutInflater());
    View view = binding.getRoot();
    setContentView(view);

    instantiate();
    initialize();
    listen();
    load();
  }

  private void instantiate() {
    sportsAdapter = new SportsAdapter(this);
  }

  private void initialize() {
    binding.rvSports.setAdapter(sportsAdapter);
  }

  private void listen() {
    binding.mcvMedalTable.setOnClickListener(v -> {
      startActivity(new Intent(this, MedalActivity.class));
    });
  }

  private void load() {
    Call<Sport> sportsHeadlines = Connection.getSportHeadlineInterface().getSportsHeadlines();

    sportsHeadlines.enqueue(new Callback<Sport>() {
      @Override
      public void onResponse(Call<Sport> call, Response<Sport> sports) {
        assert sports.body() != null;
        sportsAdapter.setSportArticleList(sports.body().getArticles());
      }

      @Override
      public void onFailure(Call<Sport> call, Throwable t) {
        Toast.makeText(MainActivity.this, "Please check your internet connection!!", Toast.LENGTH_SHORT).show();
      }
    });
  }
}