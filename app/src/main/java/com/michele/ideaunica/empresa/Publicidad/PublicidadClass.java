package com.michele.ideaunica.empresa.Publicidad;

public class PublicidadClass {
    private String Images;
    private String Heading;
    private String Desc;

    public PublicidadClass(String heading, String desc,String images) {
        Images = images;
        Heading = heading;
        Desc = desc;
    }

    public String getImages() {
        return Images;
    }

    public void setImages(String images) {
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
