package com.m.moviememoir.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.m.moviememoir.Bean.Movie;
import com.m.moviememoir.Bean.Watch;
import com.m.moviememoir.Constant;
import com.m.moviememoir.Database.MovieDB;
import com.m.moviememoir.DetailActivity;
import com.m.moviememoir.R;
import com.m.moviememoir.Utils.HttpClient;
import com.m.moviememoir.Utils.T;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import okhttp3.Headers;
import okhttp3.Request;

public class WatchAdapter extends RecyclerView.Adapter<WatchAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private List<Watch> mDatas;
    private Context mContext;

    public WatchAdapter(Context context, List<Watch> datats) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mDatas = datats;
    }


    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        View view = mInflater.inflate(R.layout.item_movie_2,
                viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.title = (TextView) view.findViewById(R.id.title);
        viewHolder.time = (TextView) view.findViewById(R.id.release_time);
        viewHolder.add = (TextView) view.findViewById(R.id.add_time);
        viewHolder.image = (ImageView) view.findViewById(R.id.image);
        viewHolder.linearLayout = (LinearLayout) view.findViewById(R.id.linear_movie);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        viewHolder.title.setText(mDatas.get(i).getTitle());
        viewHolder.time.setText("Release Date: " + mDatas.get(i).getReleaseTime());
        viewHolder.add.setText("Add Time: " + mDatas.get(i).getAddTime());
        Picasso.with(mContext).load(mDatas.get(i).getImgUrl()).placeholder(R.drawable.default1).into(viewHolder.image);
        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpClient.getInstance().asyncGet(Constant.omdbapi + mDatas.get(i).getTitle(), new HttpClient.HttpCallBack() {
                    @Override
                    public void onError(Request request, IOException e) {
                        T.showShort("Network error");
                    }

                    @Override
                    public void onSuccess(Request request, String result, Headers headers) {
                        Movie movie = new Gson().fromJson(result, Movie.class);
                        if (movie.getResponse().equals("True")) {
                            Intent intent = new Intent(mContext, DetailActivity.class);
                            intent.putExtra("Movie", movie);
                            mContext.startActivity(intent);
                        } else {
                            T.showShort("Movie not found!");
                        }
                    }
                });
            }
        });
        viewHolder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(mContext)
                        .setTitle("Tips")
                        .setMessage("Are you sure you want to remove the movie from watch list?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final MovieDB movieDB = new MovieDB(mContext);
                                movieDB.deleteValue(mDatas.get(i).getTitle());
                                mDatas.remove(i);
                                notifyItemRemoved(i);
                                notifyItemRangeChanged(i, mDatas.size() - i);
                                T.showShort("successfully removed");
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .setCancelable(false)
                        .show();
                return true;
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView time;
        TextView add;
        ImageView image;
        LinearLayout linearLayout;

        public ViewHolder(View view) {
            super(view);
        }
    }
}