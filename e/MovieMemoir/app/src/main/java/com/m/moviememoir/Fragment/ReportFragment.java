package com.m.moviememoir.Fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.m.moviememoir.Bean.Bar;
import com.m.moviememoir.Bean.Pie;
import com.m.moviememoir.Constant;
import com.m.moviememoir.R;
import com.m.moviememoir.Utils.ArrayJson;
import com.m.moviememoir.Utils.HttpClient;
import com.m.moviememoir.Utils.Singleton;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Headers;
import okhttp3.Request;

public class ReportFragment extends Fragment {
    @BindView(R.id.watchStartDate)
    EditText watchStartDate;
    @BindView(R.id.watchEndDate)
    EditText watchEndDate;
    @BindView(R.id.showPie)
    Button showPie;
    @BindView(R.id.pieChart)
    PieChart pieChart;
    @BindView(R.id.spinnerYear)
    Spinner spinnerYear;
    @BindView(R.id.showBar)
    Button showBar;
    @BindView(R.id.barChart)
    BarChart barChart;
    Unbinder unbinder;

    PieData pieData;
    PieDataSet pieDataSet;
    ArrayList pieEntries;
    String personid = "1";

    public ReportFragment() {

    }

    public static ReportFragment newInstance() {
        ReportFragment fragment = new ReportFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        personid = Singleton.getInstance().getPerson().getPersonid() + "";
        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener dateStartListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month,
                                  int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                String myFormat = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                watchStartDate.setText(sdf.format(myCalendar.getTime()));

            }
        };
        final DatePickerDialog.OnDateSetListener dateEndListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month,
                                  int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                String myFormat = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                watchEndDate.setText(sdf.format(myCalendar.getTime()));
            }
        };
        watchStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), dateStartListener, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        watchEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), dateEndListener, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        pieChart.setNoDataText("Please pick the date to fill this pie chart");
        showPie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String from = watchStartDate.getText().toString().trim();
                String to = watchEndDate.getText().toString().trim();
                if (!(from.isEmpty() || to.isEmpty())) {
                    setPieChart(from, to);
                } else {
                    Toast.makeText(getActivity(), "Please Enter the Date first", Toast.LENGTH_SHORT).show();
                }

            }
        });
        Calendar forYears = Calendar.getInstance();
        ArrayList<String> years = new ArrayList();
        years.add(String.valueOf(forYears.get(Calendar.YEAR)));
        years.add(String.valueOf(forYears.get(Calendar.YEAR) - 1));
        years.add(String.valueOf(forYears.get(Calendar.YEAR) - 2));
        years.add(String.valueOf(forYears.get(Calendar.YEAR) - 3));
        years.add(String.valueOf(forYears.get(Calendar.YEAR) - 4));
        years.add(String.valueOf(forYears.get(Calendar.YEAR) - 5));
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, years);
        spinnerYear.setAdapter(arrayAdapter);


        barChart.setNoDataText("Please select the year to fill this bar chart");
        showBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedYear = spinnerYear.getSelectedItem().toString();
                setBarChart(selectedYear);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void setPieChart(String from, String to) {
        HttpClient.getInstance().asyncGet(String.format(Constant.pieChart, from, to), new HttpClient.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Request request, String result, Headers headers) {
                List<Pie> pieList = ArrayJson.jsonToArrayList(result, Pie.class);
                if (pieList.size() != 0) {
                    pieEntries = new ArrayList<PieEntry>();
                    for (Pie pie : pieList) {
                        pieEntries.add(new PieEntry(pie.getCount(), pie.getPostcode()));
                    }
                    pieDataSet = new PieDataSet(pieEntries, "");
                    pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
                    pieDataSet.setValueTextColor(Color.BLUE);
                    pieDataSet.setValueTextSize(12f);
                    pieDataSet.setSliceSpace(5f);

                    pieData = new PieData(pieDataSet);
                    pieChart.setUsePercentValues(true);
                    pieChart.setData(pieData);
                    pieChart.setEntryLabelColor(Color.BLACK);
                    pieChart.setEntryLabelTextSize(10f);
                    pieChart.setHoleRadius(5f);
                    pieChart.setTransparentCircleRadius(10f);
                    pieChart.getLegend().setEnabled(true);
                    pieChart.getDescription().setText("Watch Times and Percentage(%) per Suburb");
                    pieChart.setDrawEntryLabels(true);
                    pieChart.animateXY(1000, 1000);
                }
            }
        });
    }

    public void setBarChart(String year) {
        HttpClient.getInstance().asyncGet(String.format(Constant.barChart, year), new HttpClient.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Request request, String result, Headers headers) {
                List<Bar> barList = ArrayJson.jsonToArrayList(result, Bar.class);
                if (barList.size() != 0) {
                    HashMap<Integer, Integer> hashMap = new HashMap<>();
                    for (Bar bar : barList) {
                        hashMap.put(bar.getMonth(), bar.getCount());
                    }
                    ArrayList<BarEntry> barEntries = new ArrayList<>();
                    ArrayList<String> labels = new ArrayList<>();
                    for (int i = 1; i < 13; i++) {
                        if (hashMap.containsKey(i)) {
                            barEntries.add(new BarEntry(i, hashMap.get(i)));
                        } else {
                            barEntries.add(new BarEntry(i, 0));
                        }
                        labels.add(i + "");
                    }
                    BarDataSet barDataSet = new BarDataSet(barEntries, "Watch Times");
                    barDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
                    barDataSet.setValueTextColor(Color.BLACK);
                    BarData data = new BarData(barDataSet);
                    data.setBarWidth(0.5f);

                    barChart.setData(data);
                    barChart.getLegend().setEnabled(true);
                    barChart.getDescription().setText("Watch Times Per Month");

                    //barChart.set
                    XAxis xAxis = barChart.getXAxis();
                    YAxis yAxis = barChart.getAxisRight();
                    YAxis yAxis1 = barChart.getAxisLeft();

                    yAxis1.setAxisMinimum(0);
                    yAxis.setEnabled(false);
                    Log.i("label", labels.toString());
                    xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
                    xAxis.setDrawAxisLine(false);
                    //xAxis.setL
                    xAxis.setPosition(XAxis.XAxisPosition.TOP);
                    barChart.setDoubleTapToZoomEnabled(false);
                    xAxis.setGranularity(1f);
                    barChart.animateXY(1000, 1000);
                }
            }
        });
    }
}
