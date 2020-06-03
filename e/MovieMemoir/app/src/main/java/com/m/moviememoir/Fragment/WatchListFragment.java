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

import com.m.moviememoir.Adapter.WatchAdapter;
import com.m.moviememoir.Bean.Watch;
import com.m.moviememoir.Database.MovieDB;
import com.m.moviememoir.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class WatchListFragment extends Fragment {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    Unbinder unbinder;
    private Context mContext;
    private List<Watch> watchArrayList = new ArrayList<>();
    private WatchAdapter watchAdapter;

    public WatchListFragment() {

    }

    public static WatchListFragment newInstance() {
        WatchListFragment fragment = new WatchListFragment();
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
        View view = inflater.inflate(R.layout.fragment_watchlist, container, false);
        unbinder = ButterKnife.bind(this, view);
        mContext = getContext();
        initView();
        return view;
    }

    private void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        watchAdapter = new WatchAdapter(mContext, watchArrayList);
        recyclerView.setAdapter(watchAdapter);
        final MovieDB movieDB = new MovieDB(mContext);
        watchArrayList.clear();
        watchArrayList.addAll(movieDB.getValue());
        watchAdapter.notifyDataSetChanged();
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
