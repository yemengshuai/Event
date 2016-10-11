package com.example.yemengshuai.avmoo.bean;

/**
 * Created by yemengshuai on 2016/8/23.
 */
public class StartItem {
    private int id;

    private String title;

    private String imgLink;

    private String link;

    private String product_id;

    private String product_data;

    private int newsType;

    public void setNewsType(int newsType) {
        this.newsType = newsType;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setProduct_data(String product_data) {
        this.product_data = product_data;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public String getTitle() {
        return title;
    }

    public String getImgLink() {
        return imgLink;
    }

    public int getId() {
        return id;
    }

    public String getLink() {
        return link;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProduct_data() {
        return product_data;
    }

    public String getProduct_id() {
        return product_id;
    }

    public int getNewsType() {
        return newsType;
    }

    @Override
    public String toString(){
        return "startItem[id="+id+",title="+title+",link="+link+",imgLink="+imgLink+
                ",product_id="+product_id+",product_data"+product_data+",newsType="+newsType;
    }
}
