package onefengma.demo.server.services.logistics;

import com.alibaba.fastjson.JSON;
import onefengma.demo.common.StringUtils;
import onefengma.demo.common.VerifyUtils;
import onefengma.demo.server.core.BaseManager;
import onefengma.demo.server.core.PageBuilder;
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

        get("logistics_requests", LogisticsPageRequst.class, ((request1, response1, requestBean1) -> {
            PageBuilder pageBuilder = new PageBuilder(requestBean1.currentPage, requestBean1.pageCount);
            if (requestBean1.status != -1) {
                pageBuilder.addEqualWhere("status", requestBean1.status);
            }
            return success(LogisticsDataManager.instance().getLogisticsRequests(pageBuilder));
        }));

        post("logistics_request", LogisticsRequest.class, ((request, response, requestBean) -> {
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

            List<String> goods = new ArrayList<String>();
            goods.add(requestBean.goods1);
            if (!StringUtils.isEmpty(requestBean.goods2)) {
                goods.add(requestBean.goods2);
            }
            if (!StringUtils.isEmpty(requestBean.goods3)) {
                goods.add(requestBean.goods3);
            }
            if (!StringUtils.isEmpty(requestBean.goods4)) {
                goods.add(requestBean.goods4);
            }
            logisticsNormalBean.goods = JSON.toJSONString(goods);
            logisticsNormalBean.pushTime = System.currentTimeMillis();
            LogisticsDataManager.instance().insertLogisticsRequest(logisticsNormalBean);
            return success();
        }));
    }

    @Override
    public String getParentRoutePath() {
        return "logistics";
    }
}
