package com.myapp.businessnews;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class Connection {

  private String sportsNewsUrl = "https://newsapi.org/v2/";

  private BusinessHeadlines businessHeadlines = null;

  public BusinessHeadlines getBusinessHeadlines() {
    if (businessHeadlines != null) {
      Retrofit retrofit = new Retrofit.Builder()
              .baseUrl(sportsNewsUrl)
              .addConverterFactory(GsonConverterFactory.create())
              .build();
      businessHeadlines = retrofit.create(BusinessHeadlines.class);
    }
    return businessHeadlines;
  }

  public interface BusinessHeadlines {
    @GET("top-headlines?country=in&category=business&apiKey=442b5ae7bbe14ec7ad9445f9bc48cbbf")
    Call<Sport> getSportsHeadlines();
  }
}
