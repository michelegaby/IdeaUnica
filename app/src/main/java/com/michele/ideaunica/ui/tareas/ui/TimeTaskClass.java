package com.michele.ideaunica.ui.tareas.ui;

import com.michele.ideaunica.blog.Constant;
import com.michele.ideaunica.blog.HeaderBlogClass;
import com.michele.ideaunica.blog.PostBlogClass;
import com.michele.ideaunica.ui.tareas.TareaClass;

public class TimeTaskClass {

    private HeaderTaskClass headerTaskClass;
    private TareaClass taskClass;
    private int viewType;

    public TimeTaskClass(HeaderTaskClass headerTaskClass) {
        this.headerTaskClass = headerTaskClass;
        viewType= Constant.ITEM_HEADER_VIEWTYPE;
    }

    public TimeTaskClass(TareaClass taskClass) {
        this.taskClass = taskClass;
        viewType=Constant.ITEM_POST_VIEWTYPE;
    }

    public HeaderTaskClass getHeaderTaskClass() {
        return headerTaskClass;
    }

    public void setHeaderTaskClass(HeaderTaskClass headerTaskClass) {
        this.headerTaskClass = headerTaskClass;
    }

    public TareaClass getTaskClass() {
        return taskClass;
    }

    public void setTaskClass(TareaClass taskClass) {
        this.taskClass = taskClass;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}
