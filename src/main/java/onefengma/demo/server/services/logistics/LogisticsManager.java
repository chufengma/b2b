package onefengma.demo.server.services.logistics;

import com.alibaba.fastjson.JSON;
import onefengma.demo.common.StringUtils;
import onefengma.demo.common.VerifyUtils;
import onefengma.demo.rx.AdminMessageServer;
import onefengma.demo.server.core.BaseManager;
import onefengma.demo.server.core.PageBuilder;
import onefengma.demo.server.model.apibeans.BaseBean;
import onefengma.demo.server.model.apibeans.logistics.LogisticsPageRequst;
import onefengma.demo.server.model.apibeans.logistics.LogisticsRequest;
import onefengma.demo.server.model.logistics.LogisticsNormalBean;
import onefengma.demo.server.services.funcs.CityDataHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chufengma on 2017/2/18.
 */
public class LogisticsManager extends BaseManager {

    @Override
    public void init() {

        get("latestLogisticsRequests", BaseBean.class, ((request1, response1, requestBean1) -> {
            return success(LogisticsDataManager.instance().getNewestLogisticsRequests(20));
        }));

        post("logisticsRequest", LogisticsRequest.class, ((request, response, requestBean) -> {
            if (!CityDataHelper.instance().isCityExist(requestBean.startPoint)) {
                return error("起点城市选择错误");
            }
            if (!CityDataHelper.instance().isCityExist(requestBean.endPoint)) {
                return error("终点城市选择错误");
            }
            if (!VerifyUtils.isMobile(requestBean.tel)) {
                return error("请输入合法的电话号码");
            }
            LogisticsNormalBean logisticsNormalBean = new LogisticsNormalBean();
            logisticsNormalBean.startPoint = requestBean.startPoint;
            logisticsNormalBean.endPoint = requestBean.endPoint;
            logisticsNormalBean.tel = requestBean.tel;
            logisticsNormalBean.comment = requestBean.comment;
            logisticsNormalBean.specCommand = requestBean.sepcCommand;

            List<LogisticsDataManager.Good> goods = new ArrayList<LogisticsDataManager.Good>();
            StringBuilder pushContent = new StringBuilder();
            try {
                LogisticsDataManager.Good good = new LogisticsDataManager.Good();
                good.name = requestBean.goods1;
                good.count = Double.parseDouble(requestBean.goods1Count);
                goods.add(good);
                pushContent.append(good.name + " " + good.count + "吨");
                if (!StringUtils.isEmpty(requestBean.goods2) && !StringUtils.isEmpty(requestBean.goods2Count)) {
                    LogisticsDataManager.Good good2 = new LogisticsDataManager.Good();
                    good2.name = requestBean.goods2;
                    good2.count = Double.parseDouble(requestBean.goods2Count);
                    goods.add(good2);
                    pushContent.append(" | " + good2.name + " " + good2.count + "吨");
                }
                if (!StringUtils.isEmpty(requestBean.goods3) && !StringUtils.isEmpty(requestBean.goods3Count)) {
                    LogisticsDataManager.Good good3 = new LogisticsDataManager.Good();
                    good3.name = requestBean.goods3;
                    good3.count = Double.parseDouble(requestBean.goods3Count);
                    goods.add(good3);
                    pushContent.append(" | " + good3.name + " " + good3.count + "吨");
                }
                if (!StringUtils.isEmpty(requestBean.goods4) && !StringUtils.isEmpty(requestBean.goods4Count)) {
                    LogisticsDataManager.Good good4 = new LogisticsDataManager.Good();
                    good4.name = requestBean.goods4;
                    good4.count = Double.parseDouble(requestBean.goods4Count);
                    goods.add(good4);
                    pushContent.append(" | " + good4.name + " " + good4.count + "吨");
                }
            } catch (NumberFormatException e) {
                return error("请输入合法的数量");
            }
            logisticsNormalBean.goods = JSON.toJSONString(goods);
            logisticsNormalBean.pushTime = System.currentTimeMillis();
            LogisticsDataManager.instance().insertLogisticsRequest(logisticsNormalBean);
            String content = logisticsNormalBean.tel + "询价找车：" + CityDataHelper.instance().getCityDescById(logisticsNormalBean.startPoint) +
                    "-->" + CityDataHelper.instance().getCityDescById(logisticsNormalBean.endPoint) + ", "
                    + pushContent.toString() + "";
            if (!StringUtils.isEmpty(logisticsNormalBean.specCommand)) {
                content += "(" + logisticsNormalBean.specCommand + ")";
            }
            if (!StringUtils.isEmpty(logisticsNormalBean.comment)) {
                content += " , 备注：" + logisticsNormalBean.comment;
            }
            AdminMessageServer.getInstance().sendMessageToAll("有新的物流询价", content);
            return success();
        }));
    }

    @Override
    public String getParentRoutePath() {
        return "logistics";
    }
}
