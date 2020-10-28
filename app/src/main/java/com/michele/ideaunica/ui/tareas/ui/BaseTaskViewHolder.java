package com.michele.ideaunica.ui.tareas.ui;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.michele.ideaunica.blog.TimelineClass;

public abstract class BaseTaskViewHolder extends RecyclerView.ViewHolder {

    abstract void setData(TimeTaskClass item);

    public BaseTaskViewHolder(@NonNull View itemView) {
        super(itemView);
    }
}
