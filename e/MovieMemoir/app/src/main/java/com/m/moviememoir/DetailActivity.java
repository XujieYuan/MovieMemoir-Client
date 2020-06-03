package com.m.moviememoir;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.m.moviememoir.Bean.Movie;
import com.m.moviememoir.Bean.Watch;
import com.m.moviememoir.Database.MovieDB;
import com.m.moviememoir.Utils.T;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_actor)
    TextView tvActor;
    @BindView(R.id.tv_release)
    TextView tvRelease;
    @BindView(R.id.tv_country)
    TextView tvCountry;
    @BindView(R.id.tv_director)
    TextView tvDirector;
    @BindView(R.id.rating)
    RatingBar rating;
    @BindView(R.id.tv_overview)
    TextView tvOverview;
    @BindView(R.id.addList)
    Button addList;
    @BindView(R.id.addMemoir)
    Button addMemoir;
    @BindView(R.id.movie_image)
    ImageView movieImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        final Movie movie = (Movie) getIntent().getSerializableExtra("Movie");
        Picasso.with(this).load(movie.getPoster()).placeholder(R.drawable.default1).into(movieImage);
        tvTitle.setText(movie.getTitle());
        tvType.setText(movie.getGenre());
        tvActor.setText(movie.getActors());
        tvRelease.setText(movie.getReleased());
        tvCountry.setText(movie.getCountry());
        tvDirector.setText(movie.getDirector());
        rating.setRating(Float.parseFloat(movie.getImdbRating()) / 2);
        rating.setIsIndicator(true);
        tvOverview.setText(movie.getPlot());
        setTitle(movie.getTitle());

        addList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(DetailActivity.this)
                        .setTitle("Tips")
                        .setMessage("Are you sure you want to add the movie to watch list?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final MovieDB movieDB = new MovieDB(DetailActivity.this);
                                if(movieDB.isMark(movie.getTitle())){
                                    T.showShort("Add the watch list again");
                                }else{
                                    Watch watch = new Watch();
                                    watch.setTitle(movie.getTitle());
                                    watch.setReleaseTime(movie.getReleased());
                                    watch.setImgUrl(movie.getPoster());
                                    Calendar calendar = Calendar.getInstance();
                                    int year = calendar.get(Calendar.YEAR);
                                    final int month = calendar.get(Calendar.MONTH) + 1;
                                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                                    int hour = calendar.get(Calendar.HOUR_OF_DAY);
                                    int minute = calendar.get(Calendar.MINUTE);
                                    int second = calendar.get(Calendar.SECOND);
                                    watch.setAddTime(year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second);
                                    movieDB.insertValue(watch);
                                    T.showShort("add successfully");
                                }
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .setCancelable(false)
                        .show();
            }
        });

        addMemoir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, AddMemoirActivity.class);
                intent.putExtra("Movie", movie);
                startActivity(intent);
            }
        });
    }
}
