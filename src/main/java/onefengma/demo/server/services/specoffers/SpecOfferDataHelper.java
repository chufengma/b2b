package onefengma.demo.server.services.specoffers;

import onefengma.demo.common.StringUtils;
import onefengma.demo.server.core.BaseDataHelper;
import onefengma.demo.server.core.PageBuilder;
import onefengma.demo.server.model.specoffer.SpecOffer;
import onefengma.demo.server.model.specoffer.SpecOfferFetchResponse;
import org.sql2o.Connection;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by chufengma on 2017/5/1.
 */
public class SpecOfferDataHelper extends BaseDataHelper {


    private static SpecOfferDataHelper specOfferDataHelper;

    public static SpecOfferDataHelper getHelper() {
        if (specOfferDataHelper == null) {
            specOfferDataHelper = new SpecOfferDataHelper();
        }
        return specOfferDataHelper;
    }

    public void insertSpecOffer(SpecOffer specOffer) throws InvocationTargetException, NoSuchMethodException, UnsupportedEncodingException, IllegalAccessException {
        try (Connection conn = getConn()) {
            createInsertQuery(conn, "special_offers", specOffer).executeUpdate();
        }
    }

    public SpecOfferFetchResponse fetchSpecOffers(PageBuilder pageBuilder) {
        try (Connection conn = getConn()) {
            SpecOfferFetchResponse specOfferFetchResponse = new SpecOfferFetchResponse(pageBuilder.currentPage, pageBuilder.pageCount);
            String where = pageBuilder.generateWhere();
            specOfferFetchResponse.specOffers = conn.createQuery("select * from special_offers" + (StringUtils.isEmpty(where) ? " " : " where " + where) + " " + pageBuilder.generateLimit()).executeAndFetch(SpecOffer.class);
            Integer integer = conn.createQuery("select count(*) from special_offers " + (StringUtils.isEmpty(where) ? " " : " where " + where)).executeScalar(Integer.class);
            specOfferFetchResponse.maxCount = integer == null ? 0 : integer;
            return specOfferFetchResponse;
        }
    }

    public SpecOffer getSpecOffer(String offerId) {
        try(Connection conn = getConn()) {
            return conn.createQuery("select * from special_offers where offerId=:offerId").addParameter("offerId", offerId).executeAndFetchFirst(SpecOffer.class);
        }
    }

    public void soldOutSpecOffer(String offerId) {
        try(Connection conn = getConn()) {
            conn.createQuery("update special_offers set status=1 where offerId=:offerId").addParameter("offerId", offerId).executeUpdate();
        }
    }

//    public void updateSpecOffer(SpecOffer specOffer) {
//        try(Connection conn = getConn()) {
//            String sql = "insert into special_offers " +
//                    "( offerId,type,material,surface,proPlace,spec,tolerance,price,unit,title,des,cover,pic1,pic2,pic3,pic4,pic5,tel,hostName,hostTel,province,city,cityDesc,priceDesc1,priceDesc2,count,pushTime,updateTime,status) " +
//                    "values (  :offerId, :type, :material, :surface, :proPlace, :spec, :tolerance, :price, :unit, :title, :des, :cover, :pic1, :pic2, :pic3, :pic4, :pic5, :tel, :hostName, :hostTel, :province, :city, :cityDesc, :priceDesc1, :priceDesc2, :count, :pushTime, :updateTime, :status)"
//            conn.createQuery("update special_offers set status=1 where offerId=:offerId").addParameter("offerId", specOffer.offerId).executeUpdate();
//        }
//    }
}
