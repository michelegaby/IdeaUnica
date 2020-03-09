package com.michele.ideaunica.blog;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.michele.ideaunica.R;

public class PostBlogViewHolder extends BaseViewHolder{

    private TextView empresa,tiempo,contenido;
    private ImageView imgEmpresa,img;
    private Button leer;
    private Context context;

    public PostBlogViewHolder(@NonNull View itemView, Context nContext) {
        super(itemView);
        empresa=itemView.findViewById(R.id.item_post_blog_empresa);
        tiempo=itemView.findViewById(R.id.item_post_blog_time);
        contenido=itemView.findViewById(R.id.item_post_blog_text);
        imgEmpresa=itemView.findViewById(R.id.item_post_blog_img_empresa);
        img=itemView.findViewById(R.id.item_post_blog_img);
        leer=itemView.findViewById(R.id.item_post_blog_leer_mas);
        context=nContext;
    }

    @Override
    void setData(TimelineClass item) {
        final PostBlogClass post = item.getPostBlogClass();
        //contenido.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/toruslight.ttf"));
        empresa.setText(post.getEmpresa());
        tiempo.setText(post.getTime());
        contenido.setText(post.getContenido());
        makeTextViewResizable(contenido, 10, " Ver más ", true);
        if(post.getUrlphoto().isEmpty() || post.getUrlphoto().equals("") || post.getUrlphoto().equals("null")){
            img.setVisibility(View.GONE);
        }else{
            img.setVisibility(View.VISIBLE);
            Glide.with(itemView.getContext())
                    .load(post.getUrlphoto())
                    .placeholder(R.drawable.cargando)
                    .error(R.drawable.fondorosa)
                    .into(img);
        }
        Glide.with(itemView.getContext())
                .load("https://ideaunicabolivia.com/"+post.getUrlempresa())
                .placeholder(R.drawable.cargando)
                .error(R.drawable.fondorosa)
                .into(imgEmpresa);
        if(post.getUrlphoto().isEmpty() || post.getUrlphoto().equals("") || post.getUrlphoto().equals("null")){
            leer.setVisibility(View.GONE);
        }else
        {
            leer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse(post.getUrl());
                    Intent intent =new Intent(Intent.ACTION_VIEW,uri);
                    context.startActivity(intent);
                }
            });
        }

    }

    public static void makeTextViewResizable(final TextView tv, final int maxLine, final String expandText, final boolean viewMore) {
        if (tv.getTag() == null) {
            tv.setTag(tv.getText());
        }
        ViewTreeObserver vto = tv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {
                String text;
                int lineEndIndex;
                ViewTreeObserver obs = tv.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                if (maxLine == 0) {
                    lineEndIndex = tv.getLayout().getLineEnd(0);
                    text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                } else if (maxLine > 0 && tv.getLineCount() >= maxLine) {
                    lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
                    text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                } else {
                    lineEndIndex = tv.getLayout().getLineEnd(tv.getLayout().getLineCount() - 1);
                    text = tv.getText().subSequence(0, lineEndIndex) + " " + expandText;
                }
                tv.setText(text);
                tv.setMovementMethod(LinkMovementMethod.getInstance());
                tv.setText(
                        addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, lineEndIndex, expandText,
                                viewMore), TextView.BufferType.SPANNABLE);
            }
        });
    }

    private static SpannableStringBuilder addClickablePartTextViewResizable(final Spanned strSpanned, final TextView tv,
                                                                            final int maxLine, final String spanableText, final boolean viewMore) {
        String str = strSpanned.toString();
        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);
        if (str.contains(spanableText)) {
            ssb.setSpan(new MySpannable(false){
                @Override
                public void onClick(View widget) {
                    tv.setLayoutParams(tv.getLayoutParams());
                    tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                    tv.invalidate();
                    if (viewMore) {
                        makeTextViewResizable(tv, -1, " Leer menos ", false);
                    } else {
                        makeTextViewResizable(tv, 10, " Leer más ", true);
                    }
                }
            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);
        }
        return ssb;
    }
}
