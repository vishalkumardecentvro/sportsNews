package com.myapp.businessnews.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
    }

    private void populate(Sport.Article sportArticle) {
      binding.tvAuthor.setText(sportArticle.getAuthor());
      binding.tvDescription.setText(sportArticle.getDescription());
      binding.tvSource.setText(sportArticle.getSource().getName());
      binding.tvTitle.setText(sportArticle.getTitle());
      Picasso.with(context).load(sportArticle.getUrlToImage()).fit().into(binding.ivNewsImage);
    }
  }
}
