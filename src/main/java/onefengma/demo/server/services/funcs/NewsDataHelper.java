package onefengma.demo.server.services.funcs;

import org.sql2o.Connection;

import java.util.List;

import onefengma.demo.common.IdUtils;
import onefengma.demo.server.core.BaseDataHelper;
import onefengma.demo.server.core.PageBuilder;
import onefengma.demo.server.model.news.InnerNewsBrief;
import onefengma.demo.server.model.news.InnerNewsDetail;
import onefengma.demo.server.model.news.InnerNewsResponse;
import onefengma.demo.server.model.news.NewsBrief;
import onefengma.demo.server.model.news.NewsDetail;
import onefengma.demo.server.model.news.NewsResponse;
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

    public List<InnerNewsBrief> getIndexInnerNews() {
        String sql = "select " + generateFiledString(InnerNewsBrief.class) + " from inner_news order by pushTime des limit 0,10 ";
        try (Connection connection = getConn()) {
            return connection.createQuery(sql).executeAndFetch(InnerNewsBrief.class);
        }
    }

    public InnerNewsResponse getInnerNews(PageBuilder pageBuilder) {
        String sql = "select " + generateFiledString(InnerNewsBrief.class) + " from inner_news order by pushTime des " + pageBuilder.generateLimit();
        String countSql = "select count(*) from inner_news";
        InnerNewsResponse newsResponse = new InnerNewsResponse(pageBuilder.currentPage, pageBuilder.pageCount);
        try (Connection connection = getConn()) {
            newsResponse.news = connection.createQuery(sql).executeAndFetch(InnerNewsBrief.class);
            Integer count = connection.createQuery(countSql).executeScalar(Integer.class);
            newsResponse.maxCount = count == null ? 0 : count;
        }
        return newsResponse;
    }

    public void editInnerNews(String id, String title, String content) {
        String sql = "update inner_news set title=:title, content=:content, pushTime=:time where id=:id";
        try (Connection conn = getConn()) {
            conn.createQuery(sql).addParameter("id", id)
                    .addParameter("title", title)
                    .addParameter("content", content)
                    .addParameter("time", System.currentTimeMillis()).executeUpdate();
        }
    }

    public void pushInnerNews(String title, String content) {
        String sql = "insert into inner_news set id=:id, title=:title, content=:content, pushTime=:time ";
        try (Connection conn = getConn()) {
            conn.createQuery(sql).addParameter("id", IdUtils.id())
                    .addParameter("title", title)
                    .addParameter("content", content)
                    .addParameter("time", System.currentTimeMillis()).executeUpdate();
        }
    }

    public void deleteInnerNews(String id) {
        String sql = "delete from inner_news where id=:id";
        try (Connection conn = getConn()) {
            conn.createQuery(sql).addParameter("id", id).executeUpdate();
        }
    }

    public InnerNewsDetail getInnerNewsDetail(String id) {
        String sql = "select " + generateFiledString(InnerNewsDetail.class) + " from inner_news where id=:id";
        try (Connection conn = getConn()) {
            return conn.createQuery(sql).addParameter("id", id).executeAndFetchFirst(InnerNewsDetail.class);
        }
    }

    public List<NewsBrief> getIndexNews() {
        String sql = "select " + generateFiledString(NewsBrief.class) + " from news order by pushTime des limit 0,10 ";
        try (Connection connection = getConn()) {
            return connection.createQuery(sql).executeAndFetch(NewsBrief.class);
        }
    }

    public NewsResponse getNews(PageBuilder pageBuilder) {
        String sql = "select " + generateFiledString(NewsBrief.class) + " from news order by pushTime des " + pageBuilder.generateLimit();
        String countSql = "select count(*) from news";
        NewsResponse newsResponse = new NewsResponse(pageBuilder.currentPage, pageBuilder.pageCount);
        try (Connection connection = getConn()) {
            newsResponse.news = connection.createQuery(sql).executeAndFetch(NewsBrief.class);
            Integer count = connection.createQuery(countSql).executeScalar(Integer.class);
            newsResponse.maxCount = count == null ? 0 : count;
        }
        return newsResponse;
    }

    public void editNews(String id, String title, String content) {
        String sql = "update news set title=:title, content=:content, pushTime=:time where id=:id";
        try (Connection conn = getConn()) {
            conn.createQuery(sql).addParameter("id", id)
                    .addParameter("title", title)
                    .addParameter("content", content)
                    .addParameter("time", System.currentTimeMillis()).executeUpdate();
        }
    }

    public void pushNews(String title, String content) {
        String sql = "insert into news set id=:id, title=:title, content=:content, pushTime=:time ";
        try (Connection conn = getConn()) {
            conn.createQuery(sql).addParameter("id", IdUtils.id())
                    .addParameter("title", title)
                    .addParameter("content", content)
                    .addParameter("time", System.currentTimeMillis()).executeUpdate();
        }
    }

    public void deleteNews(String id) {
        String sql = "delete from news where id=:id";
        try (Connection conn = getConn()) {
            conn.createQuery(sql).addParameter("id", id).executeUpdate();
        }
    }

    public NewsDetail getNewsDetail(String id) {
        String sql = "select " + generateFiledString(NewsDetail.class) + " from news where id=:id";
        try (Connection conn = getConn()) {
            return conn.createQuery(sql).addParameter("id", id).executeAndFetchFirst(NewsDetail.class);
        }
    }


    public List<TheySay> getTheySay() {
        String sql = "select " + generateFiledString(TheySay.class) + " from they_say order by pushTime des limit 0,10 ";
        try (Connection connection = getConn()) {
            return connection.createQuery(sql).executeAndFetch(TheySay.class);
        }
    }

}
