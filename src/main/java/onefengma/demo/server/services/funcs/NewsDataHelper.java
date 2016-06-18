package onefengma.demo.server.services.funcs;

import org.sql2o.Connection;

import java.util.List;

import onefengma.demo.server.core.BaseDataHelper;
import onefengma.demo.server.model.news.InnerNews;
import onefengma.demo.server.model.news.News;
import onefengma.demo.server.model.news.TheySay;

/**
 * Created by chufengma on 16/6/18.
 */
public class NewsDataHelper extends BaseDataHelper {
    private static NewsDataHelper newsDataHelper;

    public static NewsDataHelper instance() {
        if (newsDataHelper == null) {
            newsDataHelper = new NewsDataHelper();
        }
        return newsDataHelper;
    }

    public List<InnerNews>  getIndexInnerNews() {
        String sql = "select " + generateFiledString(InnerNews.class) + " from innerNews order by pushTime desc limit 0,10 ";
        try(Connection connection = getConn()) {
            return connection.createQuery(sql).executeAndFetch(InnerNews.class);
        }
    }

    public List<News>  getIndexNews() {
        String sql = "select " + generateFiledString(News.class) + " from news order by pushTime desc limit 0,10 ";
        try(Connection connection = getConn()) {
            return connection.createQuery(sql).executeAndFetch(News.class);
        }
    }

    public List<TheySay>  getTheySay() {
        String sql = "select " + generateFiledString(TheySay.class) + " from they_say order by pushTime desc limit 0,10 ";
        try(Connection connection = getConn()) {
            return connection.createQuery(sql).executeAndFetch(TheySay.class);
        }
    }

}
