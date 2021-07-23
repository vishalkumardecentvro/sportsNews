package com.myapp.businessnews.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.myapp.businessnews.Connection;
import com.myapp.businessnews.Sport;
import com.myapp.businessnews.adapter.SportsAdapter;
import com.myapp.businessnews.databinding.ActivityMainBinding;

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
        Log.e("--error--", t.toString());

      }
    });
  }
}