package com.myapp.businessnews.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.evrencoskun.tableview.adapter.AbstractTableAdapter;
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder;
import com.myapp.businessnews.table.CountryOlympicMedals;
import com.myapp.businessnews.R;

import java.util.ArrayList;
import java.util.List;

public class CountryMedalsTableAdapter extends AbstractTableAdapter {

  private final Context context;

  private List<CountryOlympicMedals> products;

  public CountryMedalsTableAdapter(Context context) {
    this.context = context;
  }

  @NonNull
  @Override
  public AbstractViewHolder onCreateCellViewHolder(@NonNull ViewGroup parent, int viewType) {
    View cell = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewmedal_tablelayout_cell, parent, false);

    return new CellViewHolder(context, cell);
  }

  @Override
  public int getColumnHeaderItemViewType(int position) {
    return position;
  }

  @Override
  public int getRowHeaderItemViewType(int position) {
    return position;
  }

  @Override
  public int getCellItemViewType(int position) {
    return position;
  }

  @Override
  public void onBindCellViewHolder(@NonNull AbstractViewHolder holder, @Nullable Object cellItemModel, int columnPosition, int rowPosition) {
    Cell cell = (Cell) cellItemModel;

    CellViewHolder viewHolder = (CellViewHolder) holder;
    viewHolder.setText(cell.getData());
    viewHolder.setProductString(cell.getProductString());

    // It is necessary to remeasure itself.
    viewHolder.getLayoutParams().width = LinearLayout.LayoutParams.WRAP_CONTENT;
    viewHolder.requestLayout();
  }

  @NonNull
  @Override
  public AbstractViewHolder onCreateColumnHeaderViewHolder(@NonNull ViewGroup parent, int viewType) {
    View columnHeader = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewmedal_tablelayout_column, parent, false);

    return new ColumnHeaderViewHolder(columnHeader);
  }

  @Override
  public void onBindColumnHeaderViewHolder(@NonNull AbstractViewHolder holder, @Nullable Object columnHeaderItemModel, int columnPosition) {
    String columnHeader = (String) columnHeaderItemModel;

    ColumnHeaderViewHolder viewHolder = (ColumnHeaderViewHolder) holder;
    viewHolder.setText(columnHeader);

    // It is necessary to remeasure itself.
    viewHolder.getLayoutParams().width = LinearLayout.LayoutParams.WRAP_CONTENT;
    viewHolder.requestLayout();
  }

  @NonNull
  @Override
  public AbstractViewHolder onCreateRowHeaderViewHolder(@NonNull ViewGroup parent, int viewType) {
    View rowHeader = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewmedal_tablelayout_row, parent, false);

    return new RowHeaderViewHolder(rowHeader);
  }

  @Override
  public void onBindRowHeaderViewHolder(@NonNull AbstractViewHolder holder, @Nullable Object rowHeaderItemModel, int rowPosition) {
    String rowHeader = (String) rowHeaderItemModel;

    RowHeaderViewHolder viewHolder = (RowHeaderViewHolder) holder;
    viewHolder.setText(rowHeader);
  }

  @NonNull
  @Override
  public View onCreateCornerView(@NonNull ViewGroup parent) {
    return LayoutInflater.from(parent.getContext()).inflate(R.layout.viewmedal_tablelayout_corner, parent, false);
  }

  public int getCount() {
    if (products == null)
      return 0;

    return products.size();
  }

  public void setMedals(List<CountryOlympicMedals> products) {
    this.products = products;
    notifyDatasetChanged();
  }

  private ArrayList<String> getColumns() {
    ArrayList<String> columns = new ArrayList<>();

    columns.add("Country");
    columns.add("Rank");
    columns.add("Gold");
    columns.add("Silver");
    columns.add("Bronze");
    columns.add("Total Medals");

    return columns;
  }

  private ArrayList<String> getRows(int count) {
    ArrayList<String> rows = new ArrayList<>();

    for (int i = 1; i <= count; i++)
      rows.add(String.valueOf(i));

    return rows;
  }

  private ArrayList<List> getCells() {
    ArrayList<List> cells = new ArrayList<>();

    for (CountryOlympicMedals product : products) {
      ArrayList<Cell> cell = getCell(product);

      if (cell != null) cells.add(cell);
    }

    return cells;
  }

  private ArrayList<Cell> getCell(CountryOlympicMedals country) {
    ArrayList<Cell> cell = new ArrayList<>();

    if (!validateFilters(country)) return null;

    cell.add(new Cell(country.toString(), country.getCountryName()));
    cell.add(new Cell(country.toString(), String.valueOf(country.getRank())));

    cell.add(new Cell(country.toString(), String.valueOf(country.getGold())));
    cell.add(new Cell(country.toString(), String.valueOf((country.getSilver()))));
    cell.add(new Cell(country.toString(), String.valueOf((country.getBronze()))));
    cell.add(new Cell(country.toString(), String.valueOf((country.getTotal()))));

    return cell;
  }

  private void notifyDatasetChanged() {
    ArrayList<String> columns = getColumns();
    ArrayList<List> cells = getCells();
    ArrayList<String> rows = getRows(cells.size());

    setAllItems(columns, rows, cells);
  }

  private boolean validateFilters(CountryOlympicMedals product) {
    return true;
  }

  public static class CellViewHolder extends AbstractViewHolder {

    private final Context context;
    private final View view;
    private final TextView textView;
    private String productString;

    public CellViewHolder(Context context, @NonNull View itemView) {
      super(itemView);

      this.context = context;
      this.view = itemView;
      this.textView = itemView.findViewById(R.id.tv);
    }

    @Override
    public void setSelected(@NonNull SelectionState selectionState) {
      super.setSelected(selectionState);
      setBackgroundColor(Color.WHITE);
    }

    public void setText(String text) {
      textView.setText(text);
    }

    public String getProductString() {
      return productString;
    }

    public void setProductString(String productString) {
      this.productString = productString;
    }

    public ViewGroup.LayoutParams getLayoutParams() {
      return view.getLayoutParams();
    }

    public void requestLayout() {
      view.requestLayout();
    }
  }

  class Cell {
    private final String productString;
    private final String data;

    public Cell(String productString, String data) {
      this.productString = productString;
      this.data = data;
    }

    public String getData() {
      return data;
    }

    public String getProductString() {
      return productString;
    }
  }

  class ColumnHeaderViewHolder extends AbstractViewHolder {

    public final TextView textView;

    public ColumnHeaderViewHolder(View itemView) {
      super(itemView);

      this.textView = (TextView) itemView;

    }

    @Override
    public void setSelected(@NonNull SelectionState selectionState) {
      super.setSelected(selectionState);
      setBackgroundColor(context.getResources().getColor(R.color.brand_color_secondary));
    }

    public void setText(String text) {
      textView.setText(text);
    }

    public ViewGroup.LayoutParams getLayoutParams() {
      return textView.getLayoutParams();
    }

    public void requestLayout() {
      textView.requestLayout();
    }
  }

  class RowHeaderViewHolder extends AbstractViewHolder {
    public final TextView textView;

    public RowHeaderViewHolder(View itemView) {
      super(itemView);

      this.textView = (TextView) itemView;
    }

    @Override
    public void setSelected(@NonNull SelectionState selectionState) {
      super.setSelected(selectionState);
      setBackgroundColor(context.getResources().getColor(R.color.brand_color_secondary));
    }

    public void setText(String text) {
      textView.setText(text);
    }
  }
}
