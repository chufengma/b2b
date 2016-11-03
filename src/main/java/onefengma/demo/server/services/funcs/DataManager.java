package onefengma.demo.server.services.funcs;

import org.sql2o.Connection;

import java.util.List;

import onefengma.demo.server.core.BaseDataHelper;

/**
 * @author yfchu
 * @date 2016/11/2
 */

public class DataManager extends BaseDataHelper {

    private static DataManager instance;

    public static DataManager instance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    public void justCopyIt() {

        String userSql = "insert into " +
                         "user(`name`,  `password`,  `mobile`,  `userId`,  `isSeller`,  `monthBuyMoney`,  `buySellerId`,  `registerTime`,  `salesManId`,  `avator`, `integral`,  `salesBindTime`, `isMock`)" +
                         "select " +
                         "`name`,  `password`,  `mobile`,  CONCAT(id, userId, '_', FLOOR((RAND() * 1000))) ,  `isSeller`,  `monthBuyMoney`,  `buySellerId`,  `registerTime`,  `salesManId`,  `avator`, `integral`,  `salesBindTime`, 1 " +
                         "from user";

        String fetchSql = "select userId from user  ";

        String sellerSql = "insert into " +
                "seller(`userId`,  `companyName`,  `regMoney`,  `contact`,  `cantactTel`,  `fax`,  `cityId`,  `officeAddress`,  `qq`, `shopProfile`,  `allCer`,  `businessLic`,  `codeLic`,  `financeLic`,  `cover`,  `ironTypeDesc`, `handingTypeDesc`,  `salesmanId`,  `productCount`,  `monthSellCount`,  `monthSellMoney`,  `score`,  `passed`,  `passTime`,  `winningTimes`,`integral`,  `reviewed`,  `applyTime`, `refuseMessage`, isMock )" +
                "select :newUserID,  `companyName`,  `regMoney`,  `contact`,  `cantactTel`,  `fax`,  `cityId`,  LEFT(`officeAddress`, 256),  `qq`,  LEFT(`shopProfile`, 256),  `allCer`,  `businessLic`,  `codeLic`,  `financeLic`,  `cover`,  LEFT(`ironTypeDesc`, 256),  LEFT(`handingTypeDesc`, 256),  `salesmanId`,  `productCount`,  `monthSellCount`,  `monthSellMoney`,  `score`,  `passed`,  `passTime`,  `winningTimes`,  LEFT(`integral`, 256),  `reviewed`,  `applyTime`,  LEFT(`refuseMessage`, 256), 1 " +
                "FROM `b2b_dev`.`seller` where userId like :oldUserId limit 1";

        try(Connection conn = getConn()) {
            // 1. 首先扩充user
            conn.createQuery(userSql).executeUpdate();

            // 2. 根据扩充的userId扩充Seller
            List<String> userIdList = conn.createQuery(fetchSql).executeScalarList(String.class);
            for(String userId : userIdList) {
                conn.createQuery(sellerSql)
                        .addParameter("newUserID", userId)
                        .addParameter("oldUserId", "%" + userId + "%").executeUpdate();
            }
        }

    }

}
