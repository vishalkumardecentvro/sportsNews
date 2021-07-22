package com.myapp.businessnews;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class Connection {

  private static String sportsNewsUrl = "https://newsapi.org/v2/";
  private String apiKey = "442b5ae7bbe14ec7ad9445f9bc48cbbf";

  public static SportHeadlines sportHeadlines = null;

  public static SportHeadlines getSportHeadlineInterface() {
    if (sportHeadlines == null) {
      Retrofit retrofit = new Retrofit.Builder()
              .baseUrl(sportsNewsUrl)
              .addConverterFactory(GsonConverterFactory.create())
              .build();
      sportHeadlines = retrofit.create(SportHeadlines.class);
    }
    return sportHeadlines;
  }

  public interface SportHeadlines {
    @GET("top-headlines?country=in&category=sports&apiKey=442b5ae7bbe14ec7ad9445f9bc48cbbf")
    Call<Sport> getSportsHeadlines();
  }
}
