package com.michele.ideaunica.blog;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    abstract void setData(TimelineClass item);
    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
    }
}
