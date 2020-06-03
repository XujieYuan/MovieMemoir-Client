package com.m.moviememoir.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.m.moviememoir.Adapter.MovieAdapter;
import com.m.moviememoir.Bean.Memoir;
import com.m.moviememoir.Bean.Movie;
import com.m.moviememoir.Constant;
import com.m.moviememoir.R;
import com.m.moviememoir.Utils.ArrayJson;
import com.m.moviememoir.Utils.HttpClient;
import com.m.moviememoir.Utils.SPUtils;
import com.m.moviememoir.Utils.Singleton;
import com.m.moviememoir.Utils.T;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Headers;
import okhttp3.Request;

public class MovieMemoirFragment extends Fragment {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    Unbinder unbinder;

    String email = "";
    private Context mContext;
    private List<Memoir> memoirArrayList = new ArrayList<>();
    private MovieAdapter movieAdapter;

    public MovieMemoirFragment() {

    }

    public static MovieMemoirFragment newInstance() {
        MovieMemoirFragment fragment = new MovieMemoirFragment();
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
        View view = inflater.inflate(R.layout.fragment_moviememoir, container, false);
        mContext = getContext();
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        movieAdapter = new MovieAdapter(mContext, memoirArrayList);
        recyclerView.setAdapter(movieAdapter);

        email = (String) SPUtils.get(mContext, "username", "");
        HttpClient.getInstance().asyncGet(Constant.memoirAll + Singleton.getInstance().getPerson().getPersonid(), new HttpClient.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Request request, String result, Headers headers) {
                List<Memoir> memoirList = ArrayJson.jsonToArrayList(result, Memoir.class);
                memoirArrayList.clear();
                if (memoirList.size() == 0) {
                    T.showShort("Username error");
                } else {
                    for (final Memoir memoir : memoirList) {
                        HttpClient.getInstance().asyncGet(Constant.omdbapi + memoir.getMoviename(), new HttpClient.HttpCallBack() {
                            @Override
                            public void onError(Request request, IOException e) {

                            }

                            @Override
                            public void onSuccess(Request request, String result, Headers headers) {
                                Movie movie = new Gson().fromJson(result, Movie.class);
                                if (movie.getResponse().equals("True")) {
                                    memoir.setMovie(movie);
                                    memoirArrayList.add(memoir);
                                    movieAdapter.notifyDataSetChanged();
                                } else {
                                    T.showShort("Movie not found!");
                                }
                            }
                        });
                    }
                }
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
}
