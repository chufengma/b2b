package onefengma.demo.server.services.products;

import onefengma.demo.server.config.Config;
import onefengma.demo.server.core.BaseManager;
import onefengma.demo.server.core.PageBuilder;
import onefengma.demo.server.model.apibeans.BaseBean;
import onefengma.demo.server.model.apibeans.product.IronBuyRequest;
import onefengma.demo.server.model.apibeans.product.IronDetailRequest;
import onefengma.demo.server.model.apibeans.product.IronPushRequest;
import onefengma.demo.server.model.apibeans.product.IronsGetRequest;
import onefengma.demo.server.model.apibeans.product.IronsGetResponse;
import onefengma.demo.server.model.metaData.IconDataCategory;
import onefengma.demo.server.model.product.IronProduct;
import onefengma.demo.server.services.funcs.CityDataHelper;
import onefengma.demo.server.services.user.SellerDataHelper;

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

        get("irons", IronsGetRequest.class, ((request1, response1, requestBean) -> {
            IronsGetResponse ironsGetResponse = new IronsGetResponse(requestBean.currentPage, requestBean.pageCount);
            ironsGetResponse.currentPage = requestBean.currentPage;
            ironsGetResponse.maxCount = IronDataHelper.getIronDataHelper().getMaxCounts();
            ironsGetResponse.pageCount = requestBean.pageCount;
            ironsGetResponse.irons = IronDataHelper.getIronDataHelper()
                    .getIronProducts(new PageBuilder(requestBean.currentPage, requestBean.pageCount)
                            .addEqualWhere("material", requestBean.material)
                            .addEqualWhere("ironType", requestBean.ironType)
                            .addEqualWhere("surface", requestBean.surface)
                            .addEqualWhere("proPlace", requestBean.proPlace));
            return success(ironsGetResponse);
        }));

        multiPost("push", IronPushRequest.class, ((request, response, requestBean) -> {
            // 材料种类
            if (!IconDataCategory.get().materials.contains(requestBean.material)) {
                return errorAndClear(requestBean, "材料种类填写不正确");
            }
            // 表面种类
            if (!IconDataCategory.get().surfaces.contains(requestBean.surface)) {
                return errorAndClear(requestBean, "表面种类填写不正确");
            }
            // 不锈钢品类
            if (!IconDataCategory.get().types.contains(requestBean.ironType)) {
                return errorAndClear(requestBean, "不锈钢品类填写不正确");
            }
            // 不锈钢产地
            if (!IconDataCategory.get().productPlaces.contains(requestBean.proPlace)) {
                return errorAndClear(requestBean, "不锈钢产地填写不正确");
            }

            if (!CityDataHelper.instance().isCityExist(requestBean.sourceCityId)) {
                return errorAndClear(requestBean, "货源产地不存在");
            }

            if (requestBean.cover == null) {
                return errorAndClear(requestBean, "产品封面必须填写");
            }

            if (requestBean.price <= 0) {
                return errorAndClear(requestBean, "发布价格不正确");
            }
            try {
                IronDataHelper.getIronDataHelper().pushIronProduct(requestBean.generateIconProduct());
            } finally {
                cleanTmpFiles(requestBean.extra);
            }

            return success();
        }));

        post("buy", IronBuyRequest.class, ((request, response, requestBean) -> {
            // 材料种类
            if (!IconDataCategory.get().materials.contains(requestBean.material)) {
                return errorAndClear(requestBean, "材料种类填写不正确");
            }
            // 表面种类
            if (!IconDataCategory.get().surfaces.contains(requestBean.surface)) {
                return errorAndClear(requestBean, "表面种类填写不正确");
            }
            // 不锈钢品类
            if (!IconDataCategory.get().types.contains(requestBean.ironType)) {
                return errorAndClear(requestBean, "不锈钢品类填写不正确");
            }
            // 不锈钢产地
            if (!IconDataCategory.get().productPlaces.contains(requestBean.proPlace)) {
                return errorAndClear(requestBean, "不锈钢产地填写不正确");
            }

            if (!CityDataHelper.instance().isCityExist(requestBean.locationCityId)) {
                return errorAndClear(requestBean, "交货地点不存在");
            }
            IronDataHelper.getIronDataHelper().pushIronBuy(requestBean.generateIronBug());
            return success();
        }));

        get("ironDetail", IronDetailRequest.class, ((request, response, requestBean) -> {
            IronProduct ironProduct = getIronDataHelper().getIronProductById(requestBean.ironId);
            if (ironProduct == null) {
                return error("未找到相关不锈钢产品");
            }
            return success(ironProduct);
        }));

        get("shopRecommend", BaseBean.class, ((request, response, requestBean) -> {
            return success(SellerDataHelper.instance().getRecommendShopsByIron());
        }));
    }

    @Override
    public String getParentRoutePath() {
        return "iron";
    }

}
