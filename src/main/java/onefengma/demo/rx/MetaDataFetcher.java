package onefengma.demo.rx;

import com.alibaba.fastjson.JSON;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by chufengma on 16/7/22.
 */
public class MetaDataFetcher {

    public static List<Quotation> quotations = new ArrayList<>();

    public static void startFetch() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                fetch();
            }
        }, 0, 1000 * 60 * 60 * 12);
    }

    public static List<Quotation> getQuotations() {
        if (quotations.size() == 0) {
            fetch();
        }
        return quotations;
    }

    public static void fetch() {
        List<Quotation> quotationsTmp = new ArrayList<>();
        try {
            Document doc = Jsoup.parse(new URL("http://www.51bxg.com"), 20000);
            Elements items = doc.select(".ab_4 .unite_news_box_3 .unite_box_3 li");
            for(int i=0;i<3;i++) {
                Element element = items.get(i);
                String title = element.select(".title_right").html();
                String price = element.select(".price_right").html();
                Quotation quotation = new Quotation();
                quotation.time = System.currentTimeMillis();
                quotation.price = price;
                String descs[] = title.split(" ", 3);
                quotation.desc = descs[2];
                quotation.proCity = descs[1];
                quotation.market = descs[0];
                quotationsTmp.add(quotation);
            }
            if (quotationsTmp.size() != 0) {
                quotations.clear();
                quotations.addAll(quotationsTmp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class Quotation {
        public String desc;
        public String proCity;
        public String price;
        public String market;
        public long time;
    }

}
