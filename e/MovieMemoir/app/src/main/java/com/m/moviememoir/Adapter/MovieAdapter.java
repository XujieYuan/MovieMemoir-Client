package com.m.moviememoir.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.m.moviememoir.Bean.Memoir;
import com.m.moviememoir.DetailActivity;
import com.m.moviememoir.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private List<Memoir> mDatas;
    private Context mContext;

    public MovieAdapter(Context context, List<Memoir> datats) {
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
        View view = mInflater.inflate(R.layout.item_movie_1,
                viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.title = (TextView) view.findViewById(R.id.title);
        viewHolder.time = (TextView) view.findViewById(R.id.time);
        viewHolder.rate = (TextView) view.findViewById(R.id.rate);
        viewHolder.image = (ImageView) view.findViewById(R.id.image);
        viewHolder.linearLayout = (LinearLayout) view.findViewById(R.id.linear_movie);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        viewHolder.title.setText(mDatas.get(i).getMoviename());
        viewHolder.time.setText("Release Date: " + mDatas.get(i).getMoviereleasedate());
        viewHolder.rate.setText("rate: " + mDatas.get(i).getRatingscore());
        Picasso.with(mContext).load(mDatas.get(i).getMovie().getPoster()).placeholder(R.drawable.default1).into(viewHolder.image);
        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra("Movie", mDatas.get(i).getMovie());
                mContext.startActivity(intent);
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView time;
        TextView rate;
        ImageView image;
        LinearLayout linearLayout;

        public ViewHolder(View view) {
            super(view);
        }
    }
}