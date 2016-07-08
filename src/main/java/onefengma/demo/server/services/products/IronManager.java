package onefengma.demo.server.services.products;

import onefengma.demo.common.StringUtils;
import onefengma.demo.server.config.Config;
import onefengma.demo.server.core.BaseManager;
import onefengma.demo.server.core.PageBuilder;
import onefengma.demo.server.model.apibeans.BaseAuthPageBean;
import onefengma.demo.server.model.apibeans.BaseBean;
import onefengma.demo.server.model.apibeans.product.*;
import onefengma.demo.server.model.metaData.IconDataCategory;
import onefengma.demo.server.model.product.IronDetail;
import onefengma.demo.server.model.product.IronProduct;
import onefengma.demo.server.services.funcs.CityDataHelper;
import onefengma.demo.server.services.user.SellerDataHelper;
import onefengma.demo.server.services.user.UserDataHelper;

import java.util.ArrayList;

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
            ironsGetResponse.pageCount = requestBean.pageCount;

            PageBuilder pageBuilder = new PageBuilder(requestBean.currentPage, requestBean.pageCount)
                    .addEqualWhere("material", requestBean.material)
                    .addEqualWhere("ironType", requestBean.ironType)
                    .addEqualWhere("surface", requestBean.surface)
                    .addEqualWhere("proPlace", requestBean.proPlace)
                    .addEqualWhere("userId", requestBean.sellerId)
                    .addInWhere("sourceCityId", CityDataHelper.instance().getCitiesById(new ArrayList<>(), requestBean.cityId))
                    .setOrderByRequest(requestBean);

            ironsGetResponse.maxCount = IronDataHelper.getIronDataHelper().getMaxIronCounts(pageBuilder);
            ironsGetResponse.irons = IronDataHelper.getIronDataHelper()
                    .getIronProducts(pageBuilder);
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
                SellerDataHelper.instance().addIronType(requestBean.getUserId(), requestBean.ironType);
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

        get("buy", IronsGetRequest.class, ((request, response, requestBean) -> {
            IronBuyResponse ironBuyResponse = new IronBuyResponse(requestBean.currentPage, requestBean.pageCount);
            ironBuyResponse.currentPage = requestBean.currentPage;
            ironBuyResponse.pageCount = requestBean.pageCount;

            PageBuilder pageBuilder = new PageBuilder(requestBean.currentPage, requestBean.pageCount)
                    .addEqualWhere("material", requestBean.material)
                    .addEqualWhere("ironType", requestBean.ironType)
                    .addEqualWhere("surface", requestBean.surface)
                    .addEqualWhere("proPlace", requestBean.proPlace)
                    .addInWhere("locationCityId", CityDataHelper.instance().getCitiesById(new ArrayList<>(), requestBean.cityId))
                    .addEqualWhere("userId", requestBean.userId)
                    .addOrderBy("pushTime", true);

            ironBuyResponse.maxCount = IronDataHelper.getIronDataHelper().getMaxIronBuyCounts(pageBuilder);
            ironBuyResponse.buys = IronDataHelper.getIronDataHelper()
                    .getIronsBuy(pageBuilder);
            return success(ironBuyResponse);
        }));

        get("ironDetail", IronDetailRequest.class, ((request, response, requestBean) -> {
            IronDetail ironProduct = getIronDataHelper().getIronProductById(requestBean.ironId);
            if (ironProduct == null) {
                return error("未找到相关不锈钢产品");
            }
            ironProduct.setCityName(CityDataHelper.instance().getCityDescById(ironProduct.sourceCityId));
            return success(ironProduct);
        }));

        get("shopRecommend", BaseBean.class, ((request, response, requestBean) -> {
            return success(SellerDataHelper.instance().getRecommendShopsByIron());
        }));

        get("productRecommed", BaseBean.class, ((request, response, requestBean) -> {
            return success(IronDataHelper.getIronDataHelper().getIronProductRecommend());
        }));

        get("buyRecommend", BaseBean.class, ((request, response, requestBean) -> {
            return success(IronDataHelper.getIronDataHelper().getIronBuyRecommend());
        }));

        get("myBuy", BaseAuthPageBean.class, ((request, response, requestBean) -> {
            IronDataHelper.getIronDataHelper().updateCancledStatis(requestBean.getUserId());
            MyIronBuysResponse handingGetResponse = new MyIronBuysResponse(requestBean.currentPage, requestBean.pageCount);
            PageBuilder pageBuilder = new PageBuilder(requestBean.currentPage, requestBean.pageCount)
                    .addEqualWhere("userId", requestBean.getUserId())
                    .addOrderBy("pushTime", true);
            handingGetResponse.canceledCount = IronDataHelper.getIronDataHelper().getCancledCount(pageBuilder, requestBean.getUserId());
            handingGetResponse.buys = IronDataHelper.getIronDataHelper().getIronsBuy(pageBuilder);
            handingGetResponse.maxCount = IronDataHelper.getIronDataHelper().getMaxIronBuyCounts(pageBuilder);
            handingGetResponse.lossRate = (float) handingGetResponse.canceledCount / (float) handingGetResponse.maxCount;
            return success(handingGetResponse);
        }));

        get("myBuyDetail", MyIronBuyDetail.class, ((request, response, requestBean) -> {
            IronDataHelper.getIronDataHelper().updateCancledStatis(requestBean.getUserId());

            MyIronBuyDetailResponse myIronBuyDetailResponse = new MyIronBuyDetailResponse();
            myIronBuyDetailResponse.buy = IronDataHelper.getIronDataHelper().getIronBuyBrief(requestBean.ironId);
            myIronBuyDetailResponse.supplies = IronDataHelper.getIronDataHelper().getIronBuySupplies(requestBean.ironId);
            myIronBuyDetailResponse.salesManPhone = UserDataHelper.instance().getSalesManTel(requestBean.getUserId());
            return success(myIronBuyDetailResponse);
        }));

        post("selectSupply", SelectIronSupply.class, ((request, response, requestBean) -> {
            IronDataHelper.getIronDataHelper().updateCancledStatis(requestBean.getUserId());

            if (IronDataHelper.getIronDataHelper().getIronBuyStatus(requestBean.ironBuyId) != 0) {
                return error("此次求购已经结束");
            }
            if (!IronDataHelper.getIronDataHelper().isUserIdInSupplyList(requestBean.ironBuyId, requestBean.supplyId)) {
                return error("该商家未参与报价");
            }
            if (!StringUtils.isEmpty(IronDataHelper.getIronDataHelper().getSupplyUserId(requestBean.ironBuyId))) {
                return error("此次求购已经结束");
            }
            IronDataHelper.getIronDataHelper().selectIronBuySupply(requestBean.ironBuyId, requestBean.supplyId);
            return success();
        }));

    }

    @Override
    public String getParentRoutePath() {
        return "iron";
    }

}
