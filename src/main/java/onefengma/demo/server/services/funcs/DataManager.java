package onefengma.demo.server.services.funcs;

import org.sql2o.Connection;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import onefengma.demo.common.IdUtils;
import onefengma.demo.common.NumberUtils;
import onefengma.demo.server.core.BaseDataHelper;

/**
 * @author yfchu
 * @date 2016/11/2
 */

public class DataManager extends BaseDataHelper {

    private static DataManager instance;
    private static boolean userHasChanged;

    private Timer timer;

    public static DataManager instance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    public void justCopyIt() {
        if (!userHasChanged) {
//            changeUserSellerData();
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
                "FROM `seller` where userId=:oldUserId limit 1";

        String fetchSellerSql = "select companyName from seller where userId=:userId limit 1";

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

            int count = 1;

            for (String userId : userIdList) {
                String[] realUserIdArray = userId.split("_");
                if (realUserIdArray.length <= 1) {
                    continue;
                }

                String companyName = conn.createQuery(fetchSellerSql)
                        .addParameter("userId", realUserIdArray[0])
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
                        .addParameter("oldUserId", realUserIdArray[0]).executeUpdate();

                System.out.println("-------system count:" + count);
                count++;
            }
        }
    }

    public void changeProductOrdersMockData() {
        String deleteAllData = "delete from product_orders_mock";
        String fetchAllSellerNameSql = "select userId,companyName,cantactTel from seller where isMock=0 and companyName <> '' and cantactTel <> ''";
        String fetchIronProductSql = "select proId,price from iron_product limit 0,1000";

        String fetchOrdersSql = "select id from product_orders";
        String productOrdersSql = "insert into " +
                "product_orders_mock(`id`,  `buyerId`,  `sellerId`,  `productId`,  `productType`, `count`,  `salesmanId`,  `sellTime`,  `timeLimit`,  `status`,  `ironCount`,  `ironPrice`,  `singleScore`, `totalMoney`,  `deleteBy`,  `cancelBy`,  `finishTime`, `message`, `productPrice`)" +
                "select `id`,  :buyerId,  :sellerId,  :productId,  0,  :count,  :salesmanId,  `sellTime`,  `timeLimit`,  `status`,  `ironCount`,  `ironPrice`,  `singleScore`, :totalMoney,  `deleteBy`,  `cancelBy`,  `finishTime`, `message`, `productPrice`" +
                "from product_orders where id=:id";

        List<CompanyBriefInfo> companyBriefInfos = getCompanyInfos();
        List<IronProductInfo> ironProductInfos;
        List<String> ordersIdList;
        try (Connection conn = getConn()) {
            conn.createQuery(deleteAllData).executeUpdate();

            ordersIdList = conn.createQuery(fetchOrdersSql).executeAndFetch(String.class);
            ironProductInfos = conn.createQuery(fetchIronProductSql).executeAndFetch(IronProductInfo.class);
            Random random = new Random(10086);

            for (String id : ordersIdList) {
                int index = 0;
                int indexAnother = 0;
                while (index == indexAnother) {
                    index = random.nextInt(companyBriefInfos.size());
                    indexAnother = random.nextInt(companyBriefInfos.size());
                }
                IronProductInfo ironProductInfo = ironProductInfos.get(new Random().nextInt(ironProductInfos.size()));
                float count = (float) (new Random().nextInt(187)) + (new Random().nextBoolean() ? 0.5f : 0f);
                conn.createQuery(productOrdersSql)
                        .addParameter("buyerId", companyBriefInfos.get(index).userId)
                        .addParameter("productId", ironProductInfo.proId)
                        .addParameter("salesmanId", new Random().nextInt(12) + 4)
                        .addParameter("count", count)
                        .addParameter("totalMoney", NumberUtils.round(count * ironProductInfo.price, 2))
                        .addParameter("id", id)
                        .addParameter("sellerId", companyBriefInfos.get(indexAnother).userId).executeUpdate();
            }
        }


        String productOrdersSqlForTime = "insert into " +
                "product_orders_mock(`id`,  `buyerId`,  `sellerId`,  `productId`,  `productType`, `count`,  `salesmanId`,  `sellTime`,  `timeLimit`,  `status`,  `ironCount`,  `ironPrice`,  `singleScore`, `totalMoney`,  `deleteBy`,  `cancelBy`,  `finishTime`, `message`, `productPrice`)" +
                "select :newId,  :buyerId,  :sellerId,  :productId,  0,  :count,  :salesmanId,  :sellTime,  `timeLimit`,  `status`,  `ironCount`,  `ironPrice`,  `singleScore`, :totalMoney,  `deleteBy`,  `cancelBy`,  :finishTime, `message`, `productPrice`" +
                "from product_orders where id=:oldId";
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try(Connection conn = getConn()) {
                    Calendar calendar = Calendar.getInstance();
                    if (calendar.get(Calendar.HOUR_OF_DAY) < 19 && calendar.get(Calendar.HOUR_OF_DAY) > 8) {
                        int count = new Random().nextInt(5) + 10;
                        for (int i = 0; i < count; i++) {
                            IronProductInfo info = ironProductInfos.get(new Random().nextInt(ironProductInfos.size()));
                            String proId = ordersIdList.get(new Random().nextInt(ordersIdList.size()));
                            int index = 0;
                            int indexAnother = 0;
                            while (index == indexAnother) {
                                index = new Random().nextInt(companyBriefInfos.size());
                                indexAnother = new Random().nextInt(companyBriefInfos.size());
                            }

                            Calendar thisCalendar = Calendar.getInstance();
                            thisCalendar.set(Calendar.HOUR_OF_DAY, thisCalendar.get(Calendar.HOUR_OF_DAY) - 1);
                            thisCalendar.set(Calendar.MINUTE, new Random().nextInt(thisCalendar.get(Calendar.MINUTE)));
                            thisCalendar.set(Calendar.SECOND, new Random().nextInt(thisCalendar.get(Calendar.SECOND)));

                            float totalMoney = new Random().nextInt(380000) + 20000;
                            float proCount = (float) (NumberUtils.round(totalMoney / info.price, 0) + (new Random().nextInt(3) == 0 ? 0.5 : 0));

                            conn.createQuery(productOrdersSqlForTime)
                                    .addParameter("buyerId", companyBriefInfos.get(index).userId)
                                    .addParameter("productId", info.proId)
                                    .addParameter("salesmanId", new Random().nextInt(12) + 4)
                                    .addParameter("count", proCount)
                                    .addParameter("totalMoney", NumberUtils.round(proCount * info.price, 2))
                                    .addParameter("newId", proId + new Random().nextInt(10000))
                                    .addParameter("oldId", proId)
                                    .addParameter("finishTime", thisCalendar.getTimeInMillis() + new Random().nextInt(1000 * 60 * 55) + 1000 * 60 * 5)
                                    .addParameter("sellTime", thisCalendar.getTimeInMillis())
                                    .addParameter("sellerId", companyBriefInfos.get(indexAnother).userId).executeUpdate();

                            System.out.println("------------" + proId + "," + thisCalendar.getTimeInMillis());
                        }
                    }
                }
            }
        }, 0, 1000 * 60 * 20);
    }

    public void changeIronBuyQtData() {
        String deleteAllData = "delete from iron_buy_qt_mock";

        String ironBuySql = "select id from iron_buy where status=1";
        String fetchOrdersSql = "select qtId from iron_buy_qt";
        String productOrdersSql = "insert into " +
                "iron_buy_qt_mock (`qtId`, `salesmanId`, `ironBuyId`, `status`, `pushTime`, `finishTime`, `userId`, `startTime`)" +
                "select :newId, :salesManId, :ironBuyId, :status, :pushTime, :finishTime, `userId`, :startTime " +
                "from iron_buy_qt where qtId=:id";

        try (Connection conn = getConn()) {
            List<String> ordersIdList = conn.createQuery(fetchOrdersSql).executeAndFetch(String.class);
            conn.createQuery(deleteAllData).executeUpdate();

            Random random = new Random(8686239);
            List<String> ironBuys = conn.createQuery(ironBuySql).executeAndFetch(String.class);

            Calendar startCalendar = Calendar.getInstance();
            startCalendar.set(2016, 8, 26);
            long startTime = startCalendar.getTimeInMillis();
            long endTime = new Date().getTime();


            for (int i = 0; i < 582; i++) {
                int index = random.nextInt(ironBuys.size());
                String qtId = ordersIdList.get(i % ordersIdList.size());
                long pushTime = -1;
                long qtStartTime = -1;
                long qtFinishTime = -1;

                pushTime = (new Random().nextLong()) % (endTime - startTime) + startTime;
                Calendar thisCalendar = Calendar.getInstance();
                thisCalendar.setTimeInMillis(pushTime);
                if (thisCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || thisCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                    pushTime = pushTime - 1000 * 60 * 60 * 24 * 2;
                    thisCalendar.setTimeInMillis(pushTime);
                }
                thisCalendar.set(Calendar.HOUR_OF_DAY, new Random().nextInt(6) + 9);
                thisCalendar.set(Calendar.MINUTE, new Random().nextInt(60) + 1);
                pushTime = thisCalendar.getTimeInMillis();

                qtStartTime = pushTime + new Random().nextInt(1000 * 60 * 60 * 2) + 1000 * 60 * 5;
                qtFinishTime = qtStartTime + new Random().nextInt(1000 * 60 * 50) + 1000 * 60 * 10;
                thisCalendar.setTimeInMillis(qtFinishTime);
                thisCalendar.set(Calendar.HOUR_OF_DAY, new Random().nextInt(6) + 9);
                thisCalendar.set(Calendar.MINUTE, new Random().nextInt(60) + 1);
                qtFinishTime = thisCalendar.getTimeInMillis();

                int status = new Random().nextInt(100) == 8 ? 2 : 1;
                if (status == 2) {
                    qtFinishTime = 0;
                }

                conn.createQuery(productOrdersSql)
                        .addParameter("newId", IdUtils.id())
                        .addParameter("pushTime", pushTime)
                        .addParameter("status", status)
                        .addParameter("startTime", qtStartTime)
                        .addParameter("finishTime", qtFinishTime)
                        .addParameter("salesManId", new Random().nextInt(21) + 7)
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

    public static class IronProductInfo {
        public String proId;
        public float price;
    }

}