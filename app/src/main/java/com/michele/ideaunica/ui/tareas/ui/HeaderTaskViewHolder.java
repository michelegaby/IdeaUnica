package com.michele.ideaunica.ui.tareas.ui;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.michele.ideaunica.R;
import com.michele.ideaunica.blog.BaseViewHolder;
import com.michele.ideaunica.blog.HeaderBlogClass;
import com.michele.ideaunica.blog.TimelineClass;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HeaderTaskViewHolder extends BaseTaskViewHolder{

    private TextView tvHeader;

    public HeaderTaskViewHolder(@NonNull View itemView) {
        super(itemView);
        tvHeader=itemView.findViewById(R.id.item_header_text_task);
    }

    @Override
    void setData(TimeTaskClass item) {
        HeaderTaskClass headerTaskClass = item.getHeaderTaskClass();

        tvHeader.setText(headerTaskClass.getHeadertask());

    }
}
