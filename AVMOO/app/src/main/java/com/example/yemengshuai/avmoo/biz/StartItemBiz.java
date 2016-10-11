package com.example.yemengshuai.avmoo.biz;



import com.example.yemengshuai.avmoo.bean.CommonException;
import com.example.yemengshuai.avmoo.bean.ProductDetail;
import com.example.yemengshuai.avmoo.bean.StartItem;
import com.example.yemengshuai.avmoo.bean.StartsItem;
import com.example.yemengshuai.avmoo.util.Constant;
import com.example.yemengshuai.avmoo.util.DataUtil;
import com.example.yemengshuai.avmoo.util.UriUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yemengshuai on 2016/8/23.
 */
public class StartItemBiz {
    public List<StartItem> getStartItem(int newsType, int curPage)
            throws CommonException {
        List<StartItem> startItems = new ArrayList<>();
        String url = UriUtil.getUri(newsType, curPage);
        String htmlStr = DataUtil.doGet(url);
        StartItem item = null;
        Document doc = Jsoup.parse(htmlStr);
        Elements elements_items = doc.getElementsByClass("item");

        if (newsType==Constant.AVMOO_TYPE_NEW||newsType==Constant.AVMOO_TYPE_ALL){
            for (int i = 0; i < elements_items.size(); i++) {
                item = new StartItem();
                item.setNewsType(newsType);
                Element product = elements_items.get(i);
                Element link = product.child(0);
                item.setLink(link.attr("href"));
                Element image = link.getElementsByTag("img").get(0);
                item.setImgLink(image.attr("src"));
                Element title = product.getElementsByTag("span").get(0);
                item.setTitle(title.text());
                Element product_id = title.getElementsByTag("date").get(0);
                item.setProduct_id(product_id.text());
                Element product_data = title.child(2);
                item.setProduct_data(product_data.text());
                startItems.add(item);

            }
            return startItems;
        }else if (newsType==Constant.AVMOO_TYPE_HOT){
            for (int i = 0; i < elements_items.size(); i++) {
                item = new StartItem();
                item.setNewsType(newsType);
                Element product = elements_items.get(i);
                Element link = product.child(0);
                item.setLink(link.attr("href"));
                Element image = link.getElementsByTag("img").get(0);
                item.setImgLink(image.attr("src"));
                Element title = product.getElementsByTag("span").get(0);
                item.setTitle(title.text());
                Element product_id = title.child(2);
                item.setProduct_id(product_id.text());
                Element product_data = title.child(3);
                item.setProduct_data(product_data.text());
                startItems.add(item);

            }
            return startItems;
        }
        return null;

    }

    public ProductDetail getProductDetial(String html){
        ProductDetail product=new ProductDetail();
        Document doc=Jsoup.parse(html);
        Element detail=doc.select("[class=col-md-9 screencap]").get(0);
        Element product_cover=detail.child(0);
        product.setProductcover_url(product_cover.attr("href"));
        product.setTitle(product_cover.attr("title"));
        Element info=doc.select("[class=col-md-3 info").get(0);
        Element product_id=info.child(0).child(1);
        product.setProduct_id(product_id.text());
        Element product_time=info.child(1);
        product.setProduct_time(product_time.text());
        Element product_long=info.child(2);
        product.setProduct_long(product_long.text());
        Element product_company1=info.child(3);
        product.setProduct_company1(product_company1.text());
        Element product_company2=info.child(4);
        product.setProduct_company2(product_company2.text());
        Element product_series=info.child(5);
        product.setProduct_series(product_series.text());
        return product;
    }
    public List<String> getImageUrl(String html){
        Document doc=Jsoup.parse(html);
        List<String> ImageUrls=new ArrayList<>();
        Element product_Image=doc.getElementById("sample-waterfall");
        if (null!=product_Image){
            Elements Images=product_Image.getElementsByClass("sample-box");
            for (Element Image_element:Images){
                String image_url;
                image_url=Image_element.attr("href");
                ImageUrls.add(image_url);
         }
        return ImageUrls;
        }else {
            return null;
        }
    }

    public List<StartsItem> getStartsItem(int cupage) throws CommonException {
        List<StartsItem> startsItems = new ArrayList<>();
        String url = "https://avmo.pw/cn/actresses/page/" + cupage;
        String htmlStr = DataUtil.doGet(url);
        StartsItem item = null;
        Document doc = Jsoup.parse(htmlStr);
        Elements elements_items = doc.getElementsByClass("item");
        for (int i = 0; i < elements_items.size(); i++) {
            item = new StartsItem();
            Element starts_detail = elements_items.get(i).child(0);
            item.setStart_detail(starts_detail.attr("href"));
            Element start_image = starts_detail.child(0).child(0);
            item.setStart_imge_url(start_image.attr("src"));
            Element start_name = starts_detail.child(1).child(0);
            item.setStartname(start_name.text());

            startsItems.add(item);

        }
        return startsItems;
    }

    public List<StartItem> getStartItemS(String startlink, int cupage)throws CommonException{
        List<StartItem> list=new ArrayList<>();
        String a="/page/";
        String url=startlink+a+cupage;
        String htmlStr = DataUtil.doGet(url);
        StartItem item = null;
        Document doc = Jsoup.parse(htmlStr);
        Elements elements_items = doc.getElementsByClass("movie-box");
        for (int i = 0; i < elements_items.size(); i++) {
            item = new StartItem();
            Element product = elements_items.get(i);
            Element link = product;
            item.setLink(link.attr("href"));
            Element image = link.getElementsByTag("img").get(0);
            item.setImgLink(image.attr("src"));
            Element title = product.getElementsByTag("span").get(0);
            item.setTitle(title.text());
            Elements date=title.getElementsByTag("date");
            Element product_id=date.get(0);
            Element product_data=date.get(1);
            item.setProduct_id(product_id.text());
            item.setProduct_data(product_data.text());
            list.add(item);

        }
        return list;
    }


}