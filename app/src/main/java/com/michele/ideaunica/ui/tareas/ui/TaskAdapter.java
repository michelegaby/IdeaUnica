package com.michele.ideaunica.ui.tareas.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.michele.ideaunica.R;
import com.michele.ideaunica.blog.BaseViewHolder;
import com.michele.ideaunica.blog.MySpannable;
import com.michele.ideaunica.blog.PostBlogClass;
import com.michele.ideaunica.blog.TimelineClass;
import com.michele.ideaunica.ui.tareas.AdaptadorTarea;
import com.michele.ideaunica.ui.tareas.TareaClass;

public class TaskAdapter extends BaseTaskViewHolder {

    private LinearLayout linearLayout;
    private CheckBox titulo;

    private Context context;

    private TaskAdapter.OnItemClickListener nListener;

    public interface OnItemClickListener{
        void onItenClick(TareaClass post);
    }

    public void setOnItemClickListener(TaskAdapter.OnItemClickListener listener){
        nListener = listener;
    }

    public TaskAdapter(@NonNull View itemView, Context nContext) {
        super(itemView);
        linearLayout = itemView.findViewById(R.id.item_task);
        titulo = itemView.findViewById(R.id.item_titulo_task);
        context =nContext;
    }

    @Override
    void setData(TimeTaskClass item) {
        final TareaClass post = item.getTaskClass();

        titulo.setText(post.getTitulo());

        if(post.getEstado() == 1)
        {
            titulo.setChecked(true);
            titulo.setTextColor(Color.GRAY);
        }else
        {
            titulo.setChecked(false);
            titulo.setTextColor(Color.BLACK);
        }

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(context,"CLICK",Toast.LENGTH_SHORT).show();

                if (nListener != null) {
                    nListener.onItenClick(post);
                }
            }
        });

    }
}

