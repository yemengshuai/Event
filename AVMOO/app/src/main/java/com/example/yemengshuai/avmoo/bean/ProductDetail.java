package com.example.yemengshuai.avmoo.bean;

/**
 * Created by yemengshuai on 2016/9/21.
 */
public class ProductDetail {
    private String title;
    private String productcover_url;
    private String product_id;
    private String product_time;
    private String product_long;
    private String product_company1;
    private String product_company2;
    private String product_series;

    public String getTitle() {
        return title;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_company1() {
        return product_company1;
    }

    public String getProduct_series() {
        return product_series;
    }

    public String getProduct_time() {
        return product_time;
    }

    public String getProductcover_url() {
        return productcover_url;
    }

    public void setProduct_company1(String product_company1) {
        this.product_company1 = product_company1;
    }

    public void setProduct_series(String product_series) {
        this.product_series = product_series;
    }

    public void setProduct_time(String product_time) {
        this.product_time = product_time;
    }

    public void setProductcover_url(String productcover_url) {
        this.productcover_url = productcover_url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProduct_long() {
        return product_long;
    }

    public void setProduct_long(String product_long) {
        this.product_long = product_long;
    }

    public String getProduct_company2() {
        return product_company2;
    }

    public void setProduct_company2(String product_company2) {
        this.product_company2 = product_company2;
    }
}
