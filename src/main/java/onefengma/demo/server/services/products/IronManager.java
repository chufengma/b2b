package onefengma.demo.server.services.products;

import java.util.ArrayList;
import java.util.List;

import onefengma.demo.common.StringUtils;
import onefengma.demo.server.config.Config;
import onefengma.demo.server.core.BaseManager;
import onefengma.demo.server.core.LogUtils;
import onefengma.demo.server.core.PageBuilder;
import onefengma.demo.server.model.apibeans.BaseAuthPageBean;
import onefengma.demo.server.model.apibeans.BaseBean;
import onefengma.demo.server.model.apibeans.product.DeleteIronProductRequest;
import onefengma.demo.server.model.apibeans.product.IronBuyRequest;
import onefengma.demo.server.model.apibeans.product.IronBuyResponse;
import onefengma.demo.server.model.apibeans.product.IronDetailRequest;
import onefengma.demo.server.model.apibeans.product.IronPushRequest;
import onefengma.demo.server.model.apibeans.product.IronsGetRequest;
import onefengma.demo.server.model.apibeans.product.IronsGetResponse;
import onefengma.demo.server.model.apibeans.product.MyIronBuyDetail;
import onefengma.demo.server.model.apibeans.product.MyIronBuyDetailResponse;
import onefengma.demo.server.model.apibeans.product.MyIronBuysResponse;
import onefengma.demo.server.model.apibeans.product.MyIronProductResponse;
import onefengma.demo.server.model.apibeans.product.SelectIronSupply;
import onefengma.demo.server.model.apibeans.product.UpdateIronProductRequest;
import onefengma.demo.server.model.metaData.IconDataCategory;
import onefengma.demo.server.model.product.IronBuy;
import onefengma.demo.server.model.product.IronBuyBrief;
import onefengma.demo.server.model.product.IronDetail;
import onefengma.demo.server.model.product.ShopBrief;
import onefengma.demo.server.model.product.SupplyBrief;
import onefengma.demo.server.services.funcs.CityDataHelper;
import onefengma.demo.server.services.order.OrderDataHelper;
import onefengma.demo.server.services.user.SellerDataHelper;
import onefengma.demo.server.services.user.UserDataHelper;

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
                    .addEqualWhere("isSpec", requestBean.isSpec)
                    .addEqualWhere("reviewed", true)
                    .addEqualWhere("deleteStatus", 0)
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
            if (!IconDataCategory.get().units.contains(requestBean.unit)) {
                return errorAndClear(requestBean, "单位选整卷油磨择有误");
            }
            if (requestBean.cover == null) {
                return errorAndClear(requestBean, "产品封面必须填写");
            }

            if (requestBean.price <= 0) {
                return errorAndClear(requestBean, "发布价格不正确");
            }

            IronDataHelper.getIronDataHelper().pushIronProduct(requestBean.generateIconProduct());
            SellerDataHelper.instance().addIronType(requestBean.getUserId(), requestBean.ironType);

            return success();
        }));

        post("buy", IronBuyRequest.class, ((request, response, requestBean) -> {

            if (!SellerDataHelper.instance().isSeller(requestBean.getUserId())) {
                return error("您不是企业用户，不能发布求购, 请前往后台点击成为商家上传公司三证等相关资料");
            }

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
            IronBuy ironBuy = requestBean.generateIronBug();
            ironBuy.salesmanId = UserDataHelper.instance().getSalesManId(requestBean.getUserId());
            IronDataHelper.getIronDataHelper().pushIronBuy(ironBuy);
            return success();
        }));

        get("buy", IronsGetRequest.class, ((request, response, requestBean) -> {
            IronDataHelper.getIronDataHelper().updateBuyStatus();

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
            List<ShopBrief> shopBriefs = SellerDataHelper.instance().getRecommendShopsByIron();
            return success(SellerDataHelper.filterShopRecommend(shopBriefs, SellerDataHelper.shopBlackListForIron, SellerDataHelper.shopWhiteListForIron));
        }));

        get("productRecommed", BaseBean.class, ((request, response, requestBean) -> {
            return success(IronDataHelper.getIronDataHelper().getIronProductRecommend());
        }));

        get("buyRecommend", BaseBean.class, ((request, response, requestBean) -> {
            return success(IronDataHelper.getIronDataHelper().getIronBuyRecommend());
        }));

        get("myBuy", BaseAuthPageBean.class, ((request, response, requestBean) -> {
            IronDataHelper.getIronDataHelper().updateBuyStatusByUserId(requestBean.getUserId());
            MyIronBuysResponse handingGetResponse = new MyIronBuysResponse(requestBean.currentPage, requestBean.pageCount);
            PageBuilder pageBuilder = new PageBuilder(requestBean.currentPage, requestBean.pageCount)
                    .addEqualWhere("userId", requestBean.getUserId())
                    .addOrderBy("pushTime", true);

            if (requestBean.status != -1) {
                pageBuilder.addEqualWhere("status", requestBean.status);
            }

            handingGetResponse.canceledCount = IronDataHelper.getIronDataHelper().getCancledCount(requestBean.getUserId());
            handingGetResponse.buys = IronDataHelper.getIronDataHelper().getIronsBuy(pageBuilder);
            handingGetResponse.maxCount = IronDataHelper.getIronDataHelper().getMaxIronBuyCounts(pageBuilder);
            handingGetResponse.lossRate = (float) handingGetResponse.canceledCount / (float) handingGetResponse.maxCount;
            handingGetResponse.lossRate = (float) handingGetResponse.canceledCount / (float) handingGetResponse.maxCount;
            handingGetResponse.newSupplyNums = IronDataHelper.getIronDataHelper().getMaxIronBuyNewSupplyNum(pageBuilder);
            return success(handingGetResponse);
        }));

        get("myBuyDetail", MyIronBuyDetail.class, ((request, response, requestBean) -> {
            IronDataHelper.getIronDataHelper().updateBuyStatusByUserId(requestBean.getUserId());

            MyIronBuyDetailResponse myIronBuyDetailResponse = new MyIronBuyDetailResponse();
            myIronBuyDetailResponse.buy = IronDataHelper.getIronDataHelper().getIronBuyBrief(requestBean.ironId);
            myIronBuyDetailResponse.supplies = IronDataHelper.getIronDataHelper().getIronBuySupplies(requestBean.ironId);

            if (myIronBuyDetailResponse.supplies!= null
                    && myIronBuyDetailResponse.buy != null) {
                for(SupplyBrief supplyBrief : myIronBuyDetailResponse.supplies) {
                    if (StringUtils.equals(myIronBuyDetailResponse.buy.supplyUserId, supplyBrief.sellerId)) {
                        supplyBrief.isWinner = true;
                    }
                    supplyBrief.mobile = UserDataHelper.instance().getUserMobile(supplyBrief.sellerId);
                }
            }

            myIronBuyDetailResponse.salesManPhone = UserDataHelper.instance().getSalesManTel(requestBean.getUserId());
            // reset iron new offer count
            IronDataHelper.getIronDataHelper().resetIronBuyNewOffersCount(requestBean.ironId);
            return success(myIronBuyDetailResponse);
        }));

        post("selectSupply", SelectIronSupply.class, ((request, response, requestBean) -> {
            IronDataHelper.getIronDataHelper().updateBuyStatusByUserId(requestBean.getUserId());

            IronBuyBrief ironBuyBrief = IronDataHelper.getIronDataHelper().getIronBuyBrief(requestBean.ironBuyId);
            if (ironBuyBrief == null || !StringUtils.equals(requestBean.getUserId(), ironBuyBrief.userId)) {
                return error("您没有相应求购");
            }
            if (IronDataHelper.getIronDataHelper().getIronBuyStatus(requestBean.ironBuyId) != 0) {
                return error("此次求购已经结束");
            }
            if (!IronDataHelper.getIronDataHelper().isUserIdInSupplyList(requestBean.ironBuyId, requestBean.supplyId)) {
                return error("该商家未参与报价");
            }
            if (!StringUtils.isEmpty(IronDataHelper.getIronDataHelper().getSupplyUserId(requestBean.ironBuyId))) {
                return error("此次求购已经结束");
            }
            LogUtils.i("--------selectSupply: userId:" + requestBean.getUserId() + "--ironBuyId:" + requestBean.ironBuyId + "--supplyId:" + requestBean.supplyId, true);
            IronDataHelper.getIronDataHelper().selectIronBuySupply(requestBean.getUserId(), requestBean.ironBuyId, requestBean.supplyId);
            return success();
        }));

        get("myProducts", BaseAuthPageBean.class, ((request, response, requestBean) -> {
            MyIronProductResponse myIronProductResponse = new MyIronProductResponse(requestBean.currentPage, requestBean.pageCount);
            myIronProductResponse.maxCount = IronDataHelper.getIronDataHelper().getMyIronProductCount(requestBean.getUserId());
            myIronProductResponse.irons = IronDataHelper.getIronDataHelper().getMyIronProduct(new PageBuilder(requestBean.currentPage, requestBean.pageCount), requestBean.getUserId());
            return success(myIronProductResponse);
        }));

        post("updateProduct", UpdateIronProductRequest.class, ((request, response, requestBean) -> {
            if(!IronDataHelper.getIronDataHelper().isUserIronRight(requestBean.getUserId(), requestBean.ironId)) {
                return error("用户权限错误");
            }
            IronDataHelper.getIronDataHelper().updateIronProduct(requestBean.ironId, requestBean.numbers, requestBean.price, requestBean.title);
            return success();
        }));

        post("deleteProduct", DeleteIronProductRequest.class, ((request, response, requestBean) -> {
            if(!IronDataHelper.getIronDataHelper().isUserIronRight(requestBean.getUserId(), requestBean.ironId)) {
                return error("用户权限错误");
            }
            if (OrderDataHelper.instance().isProductInOrdering(requestBean.ironId, 0)) {
                return error("该产品有相关订单,请处理后再删除");
            }
            IronDataHelper.getIronDataHelper().deleteIronProduct(requestBean.ironId);
            return success();
        }));

    }

    @Override
    public String getParentRoutePath() {
        return "iron";
    }

}
