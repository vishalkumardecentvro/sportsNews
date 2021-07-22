package com.myapp.businessnews;

import java.util.List;

public class Sport {
  private int totalResults;
  private List<Article> articleList;

  private class Article {
    private Source source;
    private String author, title, description, url, urlToImage, publishedAt;

    public String getAuthor() {
      return author;
    }

    public void setAuthor(String author) {
      this.author = author;
    }

    public String getTitle() {
      return title;
    }

    public void setTitle(String title) {
      this.title = title;
    }

    public String getDescription() {
      return description;
    }

    public void setDescription(String description) {
      this.description = description;
    }

    public String getUrl() {
      return url;
    }

    public void setUrl(String url) {
      this.url = url;
    }

    public String getUrlToImage() {
      return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
      this.urlToImage = urlToImage;
    }

    public String getPublishedAt() {
      return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
      this.publishedAt = publishedAt;
    }

    private class Source {
      private String name;

      public String getName() {
        return name;
      }

      public void setName(String name) {
        this.name = name;
      }
    }
  }

}
