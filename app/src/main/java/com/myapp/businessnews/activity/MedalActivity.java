package com.myapp.businessnews.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.myapp.businessnews.CountryOlympicMedals;
import com.myapp.businessnews.adapter.CountryMedalsTableAdapter;
import com.myapp.businessnews.databinding.ActivityMedalBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MedalActivity extends AppCompatActivity {
  private ActivityMedalBinding binding;
  private FirebaseFirestore firestore;
  private List<CountryOlympicMedals> countryOlympicMedalsList;
  private CountryMedalsTableAdapter countryMedalsTableAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityMedalBinding.inflate(getLayoutInflater());
    View view = binding.getRoot();
    setContentView(view);

    instantiate();
    initialize();
    listen();
    load();
  }

  private void instantiate() {
    getSupportActionBar().setTitle("Tokyo olympics 2020");

    firestore = FirebaseFirestore.getInstance();

    countryOlympicMedalsList = new ArrayList<>();
    countryMedalsTableAdapter = new CountryMedalsTableAdapter(this);
  }

  private void initialize() {
    binding.tableViewMedals.setAdapter(countryMedalsTableAdapter);

  }

  private void listen() {
  }

  private void load() {
    firestore.collection("tokyoOlympicsMedalTable_2021")
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
              @Override
              public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                  CountryOlympicMedals country = document.toObject(CountryOlympicMedals.class);
                  country.setId(document.getId());

                  CountryOlympicMedals countryOlympicMedals = new CountryOlympicMedals();

                  countryOlympicMedals.setCountryName(country.getCountryName());
                  countryOlympicMedals.setGold(country.getGold());
                  countryOlympicMedals.setSilver(country.getSilver());
                  countryOlympicMedals.setBronze(country.getBronze());
                  countryOlympicMedals.setRank(country.getRank());
                  countryOlympicMedals.setTotal(country.getTotal());

                  countryOlympicMedalsList.add(countryOlympicMedals);
                }
                countryMedalsTableAdapter.setMedals(countryOlympicMedalsList);
              }
            }).addOnFailureListener(new OnFailureListener() {
      @Override
      public void onFailure(@NonNull @NotNull Exception e) {

      }
    });
  }
}