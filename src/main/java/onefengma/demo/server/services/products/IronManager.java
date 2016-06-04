package onefengma.demo.server.services.products;

import java.util.List;

import onefengma.demo.server.config.Config;
import onefengma.demo.server.core.BaseManager;
import onefengma.demo.server.model.apibeans.BaseBean;
import onefengma.demo.server.model.apibeans.product.IronPushRequest;
import onefengma.demo.server.model.apibeans.product.IronsRequest;
import onefengma.demo.server.model.apibeans.product.IronsResponse;
import onefengma.demo.server.model.metaData.IconDataCategory;
import onefengma.demo.server.model.product.IronProduct;
import onefengma.demo.server.services.funcs.CityDataHelper;

/**
 * @author yfchu
 * @date 2016/6/1
 */
public class IronManager extends BaseManager {

    private IronDataHelper ironDataHelper;

    public IronDataHelper getIronDataHelper() {
        if (ironDataHelper == null) {
            ironDataHelper = new IronDataHelper();
        }
        return ironDataHelper;
    }

    @Override
    public void init() {
        get("categories", BaseBean.class, ((request, response, requestBean) -> success(Config.getIconDataCategory()) ));

        get("irons", IronsRequest.class, ((request1, response1, requestBean) -> {
            IronsResponse ironsResponse = new IronsResponse();
            ironsResponse.currentPage = requestBean.currentPage;
            ironsResponse.maxCount = IronDataHelper.getIronDataHelper().getMaxCounts();
            ironsResponse.irons = IronDataHelper.getIronDataHelper().getIronProducts(requestBean.currentPage, requestBean.pageCount);
            ironsResponse.pageCount = requestBean.pageCount;
            return success(ironsResponse);
        }));

        multiPost("push", IronPushRequest.class, ((request, response, requestBean) -> {
            // 材料种类
            if (!IconDataCategory.get().materials.contains(requestBean.material)) {
                return error("材料种类填写不正确");
            }
            // 表面种类
            if (!IconDataCategory.get().surfaces.contains(requestBean.surface)) {
                return error("表面种类填写不正确");
            }
            // 不锈钢品类
            if (!IconDataCategory.get().types.contains(requestBean.ironType)) {
                return error("不锈钢品类填写不正确");
            }
            // 不锈钢产地
            if (!IconDataCategory.get().productPlaces.contains(requestBean.proPlace)) {
                return error("不锈钢产地填写不正确");
            }

            if (!CityDataHelper.instance().isCityExist(requestBean.sourceCityId)) {
                return error("货源产地不存在");
            }

            if (requestBean.price <= 0) {
                return error("发布价格不正确");
            }

            IronDataHelper.getIronDataHelper().pushIronProduct(requestBean.generateIconProduct());

            return success();
        }));
    }

    @Override
    public String getParentRoutePath() {
        return "iron";
    }

}
