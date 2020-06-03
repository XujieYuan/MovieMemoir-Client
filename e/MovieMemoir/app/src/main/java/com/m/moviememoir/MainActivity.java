package com.m.moviememoir;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.m.moviememoir.Bean.Movie;
import com.m.moviememoir.Fragment.MainFragment;
import com.m.moviememoir.Fragment.MapFragment;
import com.m.moviememoir.Fragment.MovieMemoirFragment;
import com.m.moviememoir.Fragment.ReportFragment;
import com.m.moviememoir.Fragment.WatchListFragment;
import com.m.moviememoir.Utils.HttpClient;
import com.m.moviememoir.Utils.SPUtils;
import com.m.moviememoir.Utils.T;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Request;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Home");


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        MainFragment mainFragment = MainFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, mainFragment).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        initSearch(menu);
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.menu_home) {
            getSupportActionBar().setTitle("Home");
            MainFragment mainFragment = MainFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, mainFragment).commit();
        } else if (id == R.id.menu_movieMemoir) {
            getSupportActionBar().setTitle("Movie Memoir");
            MovieMemoirFragment movieMemoirFragment = MovieMemoirFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, movieMemoirFragment).commit();
        } else if (id == R.id.menu_watchlist) {
            getSupportActionBar().setTitle("Watch List");
            WatchListFragment watchListFragment = WatchListFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, watchListFragment).commit();
        } else if (id == R.id.menu_reports) {
            getSupportActionBar().setTitle("Reports");
            ReportFragment reportFragment = ReportFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, reportFragment).commit();
        } else if (id == R.id.menu_maps) {
            getSupportActionBar().setTitle("Maps");
            MapFragment mapFragment = MapFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, mapFragment).commit();
        } else if (id == R.id.nav_quit) {
            SPUtils.put(MainActivity.this, "IsLogin", "0");
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private SearchView mSearchView;

    private void initSearch(Menu menu) {
        MenuItem searchItem = menu.findItem(R.id.menu_search_view);
        mSearchView = (SearchView) searchItem.getActionView();
        final SearchView.SearchAutoComplete searchAutoComplete = (SearchView.SearchAutoComplete) mSearchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoComplete.setDropDownBackgroundResource(android.R.color.background_light);

        mSearchView.setQueryHint("Search");
        mSearchView.setSubmitButtonEnabled(true);

        searchAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long id) {
                String queryString = (String) adapterView.getItemAtPosition(itemIndex);
                searchAutoComplete.setText(queryString);
            }
        });

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (s.isEmpty()) {
                    Toast.makeText(MainActivity.this, "keyword is empty", Toast.LENGTH_SHORT).show();
                } else {
                    HttpClient.getInstance().asyncGet(Constant.omdbapi + s, new HttpClient.HttpCallBack() {
                        @Override
                        public void onError(Request request, IOException e) {
                            T.showShort("Network error");
                        }

                        @Override
                        public void onSuccess(Request request, String result, Headers headers) {
                            Movie movie = new Gson().fromJson(result, Movie.class);
                            if (movie.getResponse().equals("True")) {
                                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                                intent.putExtra("Movie", movie);
                                startActivity(intent);
                            } else {
                                T.showShort("Movie not found!");
                            }
                        }
                    });
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }
}
