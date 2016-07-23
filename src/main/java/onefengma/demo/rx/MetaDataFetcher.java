package onefengma.demo.rx;

import com.alibaba.fastjson.JSON;
import onefengma.demo.common.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
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
                fetchGraph();
            }
        }, 0, 1000 * 60 * 60 * 12);
    }

    public static List<Quotation> getQuotations() {
        if (quotations.size() == 0) {
            fetch();
        }
        return quotations;
    }

    public static void fetchGraph() {
        try {
            Document doc = Jsoup.parse(new URL("http://www.gangg.cn/ji/showw.php"), 20000);
            Elements elements = doc.select("script");
            for (Element element : elements) {
                if (StringUtils.equals(element.attr("src"), "jquery.js")) {
                    element.attr("src", "/admin/js/jquery.js");
                }
                if (StringUtils.equals(element.attr("src"), "highcharts.js")) {
                    element.attr("src", "/data/highcharts.js");
                }
                if (StringUtils.equals(element.attr("src"), "geett.js")) {
                    element.attr("src", "/data/geett.js");
                }
            }
            doc.select("#container").attr("style", "width:100%;height:120px;");
            Writer out = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream("./res/B2BPlatformFront/data/graph.html"), "UTF-8"));
            out.write(doc.toString());
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

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
