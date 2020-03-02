package com.michele.ideaunica.blog;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.michele.ideaunica.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HeaderBlogViewHolder extends BaseViewHolder{

    private TextView tvHeader;

    public HeaderBlogViewHolder(@NonNull View itemView) {
        super(itemView);
        tvHeader=itemView.findViewById(R.id.item_header_text_blog);
    }

    @Override
    void setData(TimelineClass item) {
        HeaderBlogClass headerBlogClass = item.getHeaderBlogClass();

        try {
            SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat formateador = new SimpleDateFormat("MMMM d, yyyy");
            Date date = parseador.parse(headerBlogClass.getHeaderblog());
            tvHeader.setText(formateador.format(date));

        } catch (ParseException e) {
            tvHeader.setText(headerBlogClass.getHeaderblog());
        };
    }
}
