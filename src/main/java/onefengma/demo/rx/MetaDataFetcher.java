package onefengma.demo.rx;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chufengma on 16/7/22.
 */
public class MetaDataFetcher {

    public static List<Quotation> quotations = new ArrayList<>();

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, UnsupportedEncodingException {
        startFetch();
    }

    public static void startFetch() {
        try {
            Document doc = Jsoup.connect("http://www.51bxg.com").get();
            Elements items = doc.select(".ab_4 .unite_news_box_3 .unite_box_3 li");
            for(Element element : items) {
                String title = element.select(".title_right").html();
               String name = element.select(".price_right").html();
               System.out.println("-----" + title + ":" + name);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class Quotation {
        public String desc;
        public String proCity;
        public String price;
        public String title;
        public long time;
    }

}
