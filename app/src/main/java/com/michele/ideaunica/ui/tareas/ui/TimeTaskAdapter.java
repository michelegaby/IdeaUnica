package com.michele.ideaunica.ui.tareas.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.michele.ideaunica.R;
import com.michele.ideaunica.blog.BaseViewHolder;
import com.michele.ideaunica.blog.Constant;
import com.michele.ideaunica.blog.HeaderBlogViewHolder;
import com.michele.ideaunica.blog.PostBlogViewHolder;
import com.michele.ideaunica.blog.TimelineClass;
import com.michele.ideaunica.ui.tareas.AdaptadorTarea;
import com.michele.ideaunica.ui.tareas.TareaClass;

import java.util.List;

public class TimeTaskAdapter extends RecyclerView.Adapter<BaseTaskViewHolder> {

    private Context nContext;

    private List<TimeTaskClass> nData;

    private TimeTaskAdapter.OnItemClickListener nListener;

    public interface OnItemClickListener{
        void onItenClick(TareaClass post);
    }

    public void setOnItemClickListener(TimeTaskAdapter.OnItemClickListener listener){
        nListener = listener;
    }

    public TimeTaskAdapter(Context nContext, List<TimeTaskClass> nData) {
        this.nContext = nContext;
        this.nData = nData;
    }

    @NonNull
    @Override
    public BaseTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType){
            case (Constant.ITEM_HEADER_VIEWTYPE):
                view = LayoutInflater.from(nContext).inflate(R.layout.item_header_task,parent,false);
                return new HeaderTaskViewHolder(view);
            case(Constant.ITEM_POST_VIEWTYPE):
                view = LayoutInflater.from(nContext).inflate(R.layout.item_body_task,parent,false);
                TaskAdapter taskAdapter = new TaskAdapter(view,nContext);

                taskAdapter.setOnItemClickListener(new TaskAdapter.OnItemClickListener() {
                    @Override
                    public void onItenClick(TareaClass post) {

                        if (nListener != null) {
                            nListener.onItenClick(post);
                        }
                    }
                });

                return taskAdapter;

            default:throw new IllegalArgumentException();
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseTaskViewHolder holder, int position) {
        holder.setData(nData.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return nData.get(position).getViewType();
    }

    @Override
    public int getItemCount() {
        if(nData != null) {
            return nData.size();
        }
        else{
            return 0;
        }
    }
}
