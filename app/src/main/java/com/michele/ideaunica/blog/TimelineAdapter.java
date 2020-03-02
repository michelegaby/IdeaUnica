package com.michele.ideaunica.blog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.michele.ideaunica.R;

import java.util.List;

public class TimelineAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private Context nContext;
    private List<TimelineClass> nData;

    public TimelineAdapter(Context nContext, List<TimelineClass> nData) {
        this.nContext = nContext;
        this.nData = nData;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType){
            case (Constant.ITEM_HEADER_VIEWTYPE):
                view = LayoutInflater.from(nContext).inflate(R.layout.item_header_blog,parent,false);
                return new HeaderBlogViewHolder(view);
            case(Constant.ITEM_POST_VIEWTYPE):
                view = LayoutInflater.from(nContext).inflate(R.layout.item_post_blog,parent,false);
                return new PostBlogViewHolder(view,nContext);
            default:throw new IllegalArgumentException();
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.setData(nData.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return nData.get(position).getViewType();
    }

    @Override
    public int getItemCount() {
        if(nData!=null) {
            return nData.size();
        }
        else{
            return 0;
        }
    }
}
