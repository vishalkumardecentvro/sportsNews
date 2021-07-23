package com.myapp.businessnews.adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myapp.businessnews.R;
import com.myapp.businessnews.Sport;
import com.myapp.businessnews.databinding.RvSportBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SportsAdapter extends RecyclerView.Adapter<SportsAdapter.ViewHolder> {
  private List<Sport.Article> sportArticleList = new ArrayList<>();
  private Context context;

  public SportsAdapter(Context context) {
    this.context = context;
  }

  @NonNull
  @org.jetbrains.annotations.NotNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
    return new ViewHolder(RvSportBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull SportsAdapter.ViewHolder holder, int position) {
    holder.populate(sportArticleList.get(position));
  }

  @Override
  public int getItemCount() {
    return sportArticleList.size();
  }

  public void setSportArticleList(List<Sport.Article> sportArticleList) {
    this.sportArticleList = sportArticleList;
    notifyDataSetChanged();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    private RvSportBinding binding;

    public ViewHolder(RvSportBinding binding) {
      super(binding.getRoot());
      this.binding = binding;

      binding.mcvNews.setOnClickListener(v -> openNewsArticle(getAdapterPosition()));
    }

    private void populate(Sport.Article sportArticle) {
      binding.tvAuthor.setText("by- " + sportArticle.getAuthor());
      binding.tvDescription.setText(sportArticle.getDescription());
      binding.tvSource.setText(sportArticle.getSource().getName());
      binding.tvTitle.setText(sportArticle.getTitle());
      Picasso.with(context).load(sportArticle.getUrlToImage()).fit().into(binding.ivNewsImage);

      if (sportArticle.getAuthor() == null)
        binding.tvAuthor.setVisibility(View.GONE);
      else if (sportArticle.getAuthor().isEmpty())
        binding.tvAuthor.setVisibility(View.GONE);
      else
        binding.tvAuthor.setVisibility(View.VISIBLE);

      if (sportArticle.getUrlToImage() == null)
        binding.ivNewsImage.setImageDrawable(context.getResources().getDrawable(R.drawable.news_reporter));
    }

    private void openNewsArticle(int position) {
      String urlString = sportArticleList.get(position).getUrl();
      Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      intent.setPackage("com.android.chrome");
      try {
        context.startActivity(intent);
      } catch (ActivityNotFoundException exception) {
        intent.setPackage(null);
        context.startActivity(intent);
      }

    }
  }
}
