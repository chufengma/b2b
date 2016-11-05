package onefengma.demo.server.services.funcs;

import org.sql2o.Connection;

import java.util.List;
import java.util.Random;

import onefengma.demo.common.IdUtils;
import onefengma.demo.server.core.BaseDataHelper;

/**
 * @author yfchu
 * @date 2016/11/2
 */

public class DataManager extends BaseDataHelper {

    private static DataManager instance;
    private static boolean userHasChanged;

    public static DataManager instance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    public void justCopyIt() {
        if (!userHasChanged) {
            changeUserSellerData();
            userHasChanged = true;
        }
        changeProductOrdersMockData();
        changeIronBuyQtData();
    }

    public void changeUserSellerData() {
        String userSql = "insert into " +
                "user(`name`,  `password`,  `mobile`,  `userId`,  `isSeller`,  `monthBuyMoney`,  `buySellerId`,  `registerTime`,  `salesManId`,  `avator`, `integral`,  `salesBindTime`, `isMock`)" +
                "select " +
                "`name`,  `password`,  mobile + (100000000000 * FLOOR((RAND() * 1000))) ,  CONCAT(userId, '_', id, '_', FLOOR((RAND() * 1000))) ,  `isSeller`,  `monthBuyMoney`,  `buySellerId`,  1469349892277,  `salesManId`,  `avator`, `integral`,  `salesBindTime`, 1 " +
                "from user where userId not like '%\\_%'";

        String fetchSql = "select userId from user";

        String sellerSql = "insert into " +
                "seller(`userId`,  `companyName`,  `regMoney`,  `contact`,  `cantactTel`,  `fax`,  `cityId`,  `officeAddress`,  `qq`, `shopProfile`,  `allCer`,  `businessLic`,  `codeLic`,  `financeLic`,  `cover`,  `ironTypeDesc`, `handingTypeDesc`,  `salesmanId`,  `productCount`,  `monthSellCount`,  `monthSellMoney`,  `score`,  `passed`,  `passTime`,  `winningTimes`,`integral`,  `reviewed`,  `applyTime`, `refuseMessage`, isMock )" +
                "select :newUserID,  :companyName,  `regMoney`,  `contact`,  `cantactTel`,  `fax`,  `cityId`,  LEFT(`officeAddress`, 256),  `qq`,  LEFT(`shopProfile`, 256),  `allCer`,  `businessLic`,  `codeLic`,  `financeLic`,  `cover`,  LEFT(`ironTypeDesc`, 256),  LEFT(`handingTypeDesc`, 256),  `salesmanId`,  `productCount`,  `monthSellCount`,  `monthSellMoney`,  `score`,  `passed`,  `passTime`,  `winningTimes`,  LEFT(`integral`, 256),  `reviewed`, 1469349892277,  LEFT(`refuseMessage`, 256), 1 " +
                "FROM `b2b_dev`.`seller` where userId like :oldUserId limit 1";

        String fetchSellerSql = "select companyName from seller where userId like :oldUserId limit 1";

        String delteMockUserSql = "delete from user where isMock=1";
        String delteMockSellerSql = "delete from seller where isMock=1";
        try (Connection conn = getConn()) {
            conn.createQuery(delteMockUserSql).executeUpdate();
            conn.createQuery(delteMockSellerSql).executeUpdate();

            // 1. 首先扩充user * 5
            for (int i = 0; i < 4; i++) {
                conn.createQuery(userSql).executeUpdate();
            }

            // 2. 根据扩充的userId扩充Seller
            List<String> userIdList = conn.createQuery(fetchSql).executeScalarList(String.class);

            for (String userId : userIdList) {
                String[] realUserIdArray = userId.split("_");
                if (realUserIdArray.length <= 1) {
                    continue;
                }

                String companyName = conn.createQuery(fetchSellerSql)
                        .addParameter("oldUserId", "%" + realUserIdArray[0] + "%")
                        .executeScalar(String.class);

                if (companyName == null) {
                    continue;
                }

                StringBuilder newCompayName = new StringBuilder();
                int cpCount = companyName.codePointCount(0, companyName.length());
                for (int i = 0; i < cpCount; i++) {
                    int index = companyName.offsetByCodePoints(0, i);
                    int cp = companyName.codePointAt(index);
                    if (!Character.isSupplementaryCodePoint(cp)) {
                        newCompayName.append((char) cp);
                        newCompayName.append("*");
                    }
                }
                conn.createQuery(sellerSql)
                        .addParameter("newUserID", userId)
                        .addParameter("companyName", newCompayName.toString())
                        .addParameter("oldUserId", "%" + realUserIdArray[0] + "%").executeUpdate();
            }
        }
    }

    public void changeProductOrdersMockData() {
        String deleteAllData = "delete from product_orders_mock";
        String fetchAllSellerNameSql = "select userId,companyName,cantactTel from seller where isMock=0 and companyName <> '' and cantactTel <> ''";

        String fetchOrdersSql = "select id from product_orders";
        String productOrdersSql = "insert into " +
                "product_orders_mock(`id`,  `buyerId`,  `sellerId`,  `productId`,  `productType`, `count`,  `salesmanId`,  `sellTime`,  `timeLimit`,  `status`,  `ironCount`,  `ironPrice`,  `singleScore`, `totalMoney`,  `deleteBy`,  `cancelBy`,  `finishTime`, `message`, `productPrice`)" +
                "select `id`,  :buyerId,  :sellerId,  `productId`,  `productType`, `count`,  `salesmanId`,  `sellTime`,  `timeLimit`,  `status`,  `ironCount`,  `ironPrice`,  `singleScore`, `totalMoney`,  `deleteBy`,  `cancelBy`,  `finishTime`, `message`, `productPrice`" +
                "from product_orders where id=:id";

        try (Connection conn = getConn()) {
            conn.createQuery(deleteAllData).executeUpdate();

            List<String> ordersIdList = conn.createQuery(fetchOrdersSql).executeAndFetch(String.class);
            Random random = new Random(10086);
            List<CompanyBriefInfo> companyBriefInfos = getCompanyInfos();

            for (String id : ordersIdList) {
                int index = 0;
                int indexAnother = 0;
                while (index == indexAnother) {
                    index = random.nextInt(companyBriefInfos.size());
                    indexAnother = random.nextInt(companyBriefInfos.size());
                }
                conn.createQuery(productOrdersSql)
                        .addParameter("buyerId", companyBriefInfos.get(index).userId)
                        .addParameter("id", id)
                        .addParameter("sellerId", companyBriefInfos.get(companyBriefInfos.size() - index).userId).executeUpdate();
            }
        }
    }

    public void changeIronBuyQtData() {
        String deleteAllData = "delete from iron_buy_qt_mock";

        String ironBuySql = "select id from iron_buy";
        String fetchOrdersSql = "select qtId from iron_buy_qt";
        String productOrdersSql = "insert into " +
                "iron_buy_qt_mock (`qtId`, `salesmanId`, `ironBuyId`, `status`, `pushTime`, `finishTime`, `userId`, `startTime`)" +
                "select :newId, `salesmanId`, :ironBuyId, `status`, `pushTime`, `finishTime`, `userId`, `startTime`" +
                "from iron_buy_qt where qtId=:id";

        try (Connection conn = getConn()) {
            List<String> ordersIdList = conn.createQuery(fetchOrdersSql).executeAndFetch(String.class);
            conn.createQuery(deleteAllData).executeUpdate();

            Random random = new Random(8686239);
            List<String> ironBuys = conn.createQuery(ironBuySql).executeAndFetch(String.class);
            for (int i = 0; i < 234 + 11; i++) {
                int index = random.nextInt(ironBuys.size());
                String qtId = ordersIdList.get(i % ordersIdList.size());

                conn.createQuery(productOrdersSql)
                        .addParameter("newId", IdUtils.id())
                        .addParameter("ironBuyId", ironBuys.get(index))
                        .addParameter("id", ordersIdList.get(i % ordersIdList.size())).executeUpdate();
            }
        }

    }

    public List<CompanyBriefInfo> getCompanyInfos() {
        String fetchAllSellerNameSql = "select userId,companyName,cantactTel from seller where isMock=0 and companyName <> '' and cantactTel <> ''";
        try (Connection conn = getConn()) {
            return conn.createQuery(fetchAllSellerNameSql).executeAndFetch(CompanyBriefInfo.class);
        }
    }

    public static class CompanyBriefInfo {
        public String companyName;
        public String cantactTel;
        public String userId;
    }

}
