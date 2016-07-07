package onefengma.demo.server.services.order;

import onefengma.demo.server.core.BaseDataHelper;
import onefengma.demo.server.core.PageBuilder;
import onefengma.demo.server.model.apibeans.LastRecords;
import onefengma.demo.server.model.apibeans.order.MyCommingOrdersResponse;
import onefengma.demo.server.model.apibeans.order.MyOrdersResponse;
import onefengma.demo.server.model.order.OrderBrief;
import org.sql2o.Connection;
import org.sql2o.data.Row;
import org.sql2o.data.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chufengma on 16/7/7.
 */
public class SellerDataHelper extends BaseDataHelper {

    private static SellerDataHelper orderDataHelper;

    public static SellerDataHelper instance() {
        if (orderDataHelper == null) {
            orderDataHelper = new SellerDataHelper();
        }
        return orderDataHelper;
    }

}
