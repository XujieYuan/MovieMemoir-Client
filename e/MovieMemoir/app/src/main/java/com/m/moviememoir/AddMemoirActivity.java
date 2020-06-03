package com.m.moviememoir;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.m.moviememoir.Adapter.CinemaAdapter;
import com.m.moviememoir.Bean.Cinema;
import com.m.moviememoir.Bean.MemoirSub;
import com.m.moviememoir.Bean.Movie;
import com.m.moviememoir.Utils.ArrayJson;
import com.m.moviememoir.Utils.HttpClient;
import com.m.moviememoir.Utils.Singleton;
import com.m.moviememoir.Utils.T;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

public class AddMemoirActivity extends AppCompatActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_release)
    TextView tvRelease;
    @BindView(R.id.tv_watch_date)
    TextView tvWatchDate;
    @BindView(R.id.btnDob)
    EditText btnDob;
    @BindView(R.id.rating)
    RatingBar rating;
    @BindView(R.id.tv_content)
    EditText tvContent;
    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.addMemoir)
    Button addMemoir;

    private List<Cinema> cinemaArrayList = new ArrayList<>();
    private CinemaAdapter cinemaAdapter;
    private int cid = 1;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private String datePicked = 2000 + "-" + 01 + "-" + 01;
    private DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int newYear, int newMonth, int newDate) {
            String displayDate = newDate + "/" + Integer.toString(newMonth + 1) + "/" + newYear;
            String strDay = "";
            String strMonth = "";
            strDay = (newDate > 10 ? "" : "0") + newDate;
            strMonth = (newMonth + 1 > 10 ? "" : "0") + (newMonth + 1);
            datePicked = newYear + "-" + strMonth + "-" + strDay;
            btnDob.setText(displayDate);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_memoir);
        ButterKnife.bind(this);
        cinemaAdapter = new CinemaAdapter(this, cinemaArrayList);
        spinner.setAdapter(cinemaAdapter);
        HttpClient.getInstance().asyncGet(Constant.cinemaAll, new HttpClient.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Request request, String result, Headers headers) {
                List<Cinema> cinemaList = ArrayJson.jsonToArrayList(result, Cinema.class);
                cinemaArrayList.clear();
                if (cinemaList.size() == 0) {
                    T.showShort("fetch error");
                } else {
                    cinemaArrayList.clear();
                    cinemaArrayList.addAll(cinemaList);
                    cinemaAdapter.notifyDataSetChanged();
                }
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cid = cinemaArrayList.get(position).getCinemaid();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final Movie movie = (Movie) getIntent().getSerializableExtra("Movie");
        tvTitle.setText(movie.getTitle());
        tvRelease.setText("Release Date: " + movie.getReleased());
        btnDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddMemoirActivity.this, dateListener, 2000, 1, 1).show();
            }
        });

        addMemoir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnDob.getText().toString().equals("Watch Date")) {
                    T.showShort("Please select watch date");
                    return;
                }

                if (tvContent.getText().toString().isEmpty()) {
                    T.showShort("Please input comment");
                    return;
                }

                MemoirSub memoir = new MemoirSub();
                memoir.setCid(cid);
                memoir.setComment(tvContent.getText().toString().trim());
                memoir.setMoviename(tvTitle.getText().toString());
                memoir.setMoviereleasedate(toTime(movie.getReleased()));
                memoir.setPid(Singleton.getInstance().getPerson().getPersonid());
                memoir.setRatingscore(rating.getNumStars());
                memoir.setWatchdate(datePicked);
                Log.i("xbw12138", new Gson().toJson(memoir));
                HttpClient.getInstance().asyncPost(Constant.API_MEMOIR, RequestBody.create(JSON, new Gson().toJson(memoir)), new HttpClient.HttpCallBack() {
                    @Override
                    public void onError(Request request, IOException e) {

                    }

                    @Override
                    public void onSuccess(Request request, String result, Headers headers) {
                        Log.d("xbw12138", result);
                        finish();
                    }
                });
            }
        });
    }

    public String toTime(String original) {
        String t[] = original.split(" ");
        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put("Jan", 1);
        hashMap.put("Feb", 2);
        hashMap.put("Mar", 3);
        hashMap.put("Apr", 4);
        hashMap.put("May", 5);
        hashMap.put("Jun", 6);
        hashMap.put("Jul", 7);
        hashMap.put("Aug", 8);
        hashMap.put("Sept", 9);
        hashMap.put("Oct", 10);
        hashMap.put("Nov", 11);
        hashMap.put("Dec", 12);

        return t[2] + "-" + hashMap.get(t[1]) + "-" + t[0];
    }
}
