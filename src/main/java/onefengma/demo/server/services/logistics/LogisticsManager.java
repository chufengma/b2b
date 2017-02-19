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

    }

    @Override
    public String getParentRoutePath() {
        return "logistics";
    }
}
