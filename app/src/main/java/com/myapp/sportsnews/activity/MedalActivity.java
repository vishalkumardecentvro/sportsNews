package com.myapp.sportsnews.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.evrencoskun.tableview.listener.ITableViewListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.myapp.sportsnews.adapter.MedalsTableAdapter;
import com.myapp.sportsnews.databinding.ActivityMedalBinding;
import com.myapp.sportsnews.table.CountryMedals;
import com.myapp.sportsnews.table.MedalTableUpdatedAt;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MedalActivity extends AppCompatActivity {
  private ActivityMedalBinding binding;
  private FirebaseFirestore firestore;
  private List<CountryMedals> countryMedalsList;
  private MedalsTableAdapter countryMedalsTableAdapter;
  private MedalTableUpdatedAt medalTableUpdatedAt;


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

    countryMedalsList = new ArrayList<>();
    countryMedalsTableAdapter = new MedalsTableAdapter(this);
  }

  private void initialize() {
    binding.tableViewMedals.setAdapter(countryMedalsTableAdapter);

  }

  private void listen() {
    binding.tableViewMedals.setTableViewListener(new MedalTableListener());
  }

  private void load() {
    firestore.collection("tokyoOlympicsMedalTable_2021")
            .orderBy("rank")
            .get()
            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
              @Override
              public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                getUpdatedTimestamp();
              }
            })
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
              @Override
              public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                  CountryMedals country = document.toObject(CountryMedals.class);
                  country.setId(document.getId());

                  CountryMedals countryMedals = new CountryMedals();

                  countryMedals.setCountryName(country.getCountryName());
                  countryMedals.setGold(country.getGold());
                  countryMedals.setSilver(country.getSilver());
                  countryMedals.setBronze(country.getBronze());
                  countryMedals.setRank(country.getRank());
                  countryMedals.setTotal(country.getTotal());

                  countryMedalsList.add(countryMedals);
                }
                countryMedalsTableAdapter.setMedals(countryMedalsList);
              }
            }).addOnFailureListener(new OnFailureListener() {
      @Override
      public void onFailure(@NonNull @NotNull Exception e) {

      }
    });
  }

  private void getUpdatedTimestamp() {
    firestore.collection("updated_at")
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
              @Override
              public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot document : task.getResult()) {

                  medalTableUpdatedAt = document.toObject(MedalTableUpdatedAt.class);
                  binding.tvUpdatedAt.setText("Last updated at: " + medalTableUpdatedAt.getUpdated_at());
                }
              }
            }).addOnFailureListener(new OnFailureListener() {
      @Override
      public void onFailure(@NonNull @NotNull Exception e) {
        Toast.makeText(MedalActivity.this, "Not able to fetch current updated timestamp", Toast.LENGTH_SHORT).show();
      }
    });
  }

  private class MedalTableListener implements ITableViewListener {
    @Override
    public void onCellClicked(@NonNull RecyclerView.ViewHolder cellView, int column, int row) {

    }

    @Override
    public void onCellLongPressed(@NonNull RecyclerView.ViewHolder cellView, int column, int row) {
    }

    @Override
    public void onColumnHeaderClicked(@NonNull RecyclerView.ViewHolder columnHeaderView, int column) {
    }

    @Override
    public void onColumnHeaderLongPressed(@NonNull RecyclerView.ViewHolder columnHeaderView, int column) {
    }

    @Override
    public void onRowHeaderClicked(@NonNull RecyclerView.ViewHolder rowHeaderView, int row) {

    }

    @Override
    public void onRowHeaderLongPressed(@NonNull RecyclerView.ViewHolder rowHeaderView, int row) {
    }
  }

}