package com.michele.ideaunica.Boarding;

public class BoardingClass {
    private int Images;
    private String Heading;
    private String Desc;

    public BoardingClass(String heading, String desc,int images) {
        Images = images;
        Heading = heading;
        Desc = desc;
    }

    public int getImages() {
        return Images;
    }

    public void setImages(int images) {
        Images = images;
    }

    public String getHeading() {
        return Heading;
    }

    public void setHeading(String heading) {
        Heading = heading;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }
}
