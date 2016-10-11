package com.example.yemengshuai.avmoo.bean;

/**
 * Created by yemengshuai on 2016/9/25.
 */
public class StartsItem {
    private int id;
    private String startname;
    private String start_imge_url;
    private String start_detail;

    public int getId() {
        return id;
    }

    public String getStart_imge_url() {
        return start_imge_url;
    }

    public String getStartname() {
        return startname;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStart_imge_url(String start_imge_url) {
        this.start_imge_url = start_imge_url;
    }

    public void setStartname(String startname) {
        this.startname = startname;
    }

    public String getStart_detail() {
        return start_detail;
    }

    public void setStart_detail(String start_detail) {
        this.start_detail = start_detail;
    }

    @Override
    public String toString(){
        return "StartsItem=[id="+id+",startname="+startname+",start_image_url="+start_imge_url+",start_detail="+start_detail;
    }
}
