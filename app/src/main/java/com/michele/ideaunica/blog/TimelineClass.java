package com.michele.ideaunica.blog;

import android.content.Context;

public class TimelineClass {

    private HeaderBlogClass headerBlogClass;
    private PostBlogClass postBlogClass;
    private int viewType;

    public TimelineClass(HeaderBlogClass headerBlogClass) {
        this.headerBlogClass = headerBlogClass;
        viewType= Constant.ITEM_HEADER_VIEWTYPE;
    }

    public TimelineClass(PostBlogClass postBlogClass) {
        this.postBlogClass = postBlogClass;
        viewType=Constant.ITEM_POST_VIEWTYPE;
    }

    public HeaderBlogClass getHeaderBlogClass() {
        return headerBlogClass;
    }

    public void setHeaderBlogClass(HeaderBlogClass headerBlogClass) {
        this.headerBlogClass = headerBlogClass;
    }

    public PostBlogClass getPostBlogClass() {
        return postBlogClass;
    }

    public void setPostBlogClass(PostBlogClass postBlogClass) {
        this.postBlogClass = postBlogClass;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}
