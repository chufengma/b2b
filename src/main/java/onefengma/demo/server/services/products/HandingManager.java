package onefengma.demo.server.services.products;

import onefengma.demo.common.StringUtils;
import onefengma.demo.server.core.BaseManager;
import onefengma.demo.server.core.PageBuilder;
import onefengma.demo.server.model.apibeans.BaseAuthPageBean;
import onefengma.demo.server.model.apibeans.product.*;
import onefengma.demo.server.model.apibeans.BaseBean;
import onefengma.demo.server.model.metaData.HandingDataCategory;
import onefengma.demo.server.model.product.HandingDetail;
import onefengma.demo.server.model.product.HandingProduct;
import onefengma.demo.server.services.funcs.CityDataHelper;
import onefengma.demo.server.services.order.OrderDataHelper;
import onefengma.demo.server.services.user.SellerDataHelper;
import onefengma.demo.server.services.user.UserDataHelper;

import java.util.ArrayList;

/**
 * Created by chufengma on 16/6/5.
 */
public class HandingManager extends BaseManager{

    @Override
    public void init() {

        get("categories", BaseBean.class, ((request1, response1, requestBean1) -> success(HandingDataCategory.get())));

        multiPost("push", HandingPushRequest.class, ((request, response, requestBean) -> {
            if (!HandingDataCategory.get().handingTypes.contains(requestBean.type)) {
                return errorAndClear(requestBean, "加工种类选择有误");
            }
            if (!CityDataHelper.instance().isCityExist(requestBean.souCityId)) {
                return errorAndClear(requestBean, "加工所在城市选择有误");
            }
            if (!HandingDataCategory.get().units.contains(requestBean.unit)) {
                return errorAndClear(requestBean, "单位选整卷油磨择有误");
            }
            HandingDataHelper.getHandingDataHelper().insertHandingProduct(requestBean.generateHandingProduct());
            SellerDataHelper.instance().addHandingType(requestBean.getUserId(), requestBean.type);
            return success();
        }));

        get("handing", HandingGetRequest.class, ((request, response, requestBean) -> {
            HandingGetResponse handingGetResponse = new HandingGetResponse(requestBean.currentPage, requestBean.pageCount);
            PageBuilder pageBuilder = new PageBuilder(requestBean.currentPage, requestBean.pageCount)
                    .addEqualWhere("type", requestBean.handingType)
                    .addEqualWhere("souCityId", requestBean.cityId)
                    .addEqualWhere("reviewed", true)
                    .addEqualWhere("userId", requestBean.sellerId)
                    .setOrderByRequest(requestBean);
            handingGetResponse.handingProducts = HandingDataHelper.getHandingDataHelper().getHandingProducts(pageBuilder);
            handingGetResponse.maxCount = HandingDataHelper.getHandingDataHelper().getMaxCount(pageBuilder);
            return success(handingGetResponse);
        }));

        multiPost("buy", HandingBuyRequest.class, ((request, response, requestBean) -> {
            if (!HandingDataCategory.get().handingTypes.contains(requestBean.handingType)) {
                return errorAndClear(requestBean, "加工种类选择有误");
            }
            if (!CityDataHelper.instance().isCityExist(requestBean.souCityId)) {
                return errorAndClear(requestBean, "加工所在城市选择有误");
            }
            HandingDataHelper.getHandingDataHelper().pushHandingBuy(requestBean.generateBuy());
            return success();
        }));

        get("buy", HandingGetRequest.class, ((request, response, requestBean) -> {
            HandingBuysResponse handingGetResponse = new HandingBuysResponse(requestBean.currentPage, requestBean.pageCount);
            PageBuilder pageBuilder = new PageBuilder(requestBean.currentPage, requestBean.pageCount)
                    .addEqualWhere("handingType", requestBean.handingType)
                    .addInWhere("souCityId", CityDataHelper.instance().getCitiesById(new ArrayList<>(), requestBean.cityId))
                    .addEqualWhere("userId", requestBean.userId)
                    .addOrderBy("pushTime", true);
            handingGetResponse.handings = HandingDataHelper.getHandingDataHelper().getHandingBuys(pageBuilder);
            handingGetResponse.maxCount = HandingDataHelper.getHandingDataHelper().getMaxBuyCount(pageBuilder);
            return success(handingGetResponse);
        }));

        get("myBuy", BaseAuthPageBean.class, ((request, response, requestBean) -> {
            HandingDataHelper.getHandingDataHelper().updateCancledStatis(requestBean.getUserId());
            MyHandingBuysResponse handingGetResponse = new MyHandingBuysResponse(requestBean.currentPage, requestBean.pageCount);
            PageBuilder pageBuilder = new PageBuilder(requestBean.currentPage, requestBean.pageCount)
                    .addEqualWhere("userId", requestBean.getUserId())
                    .addOrderBy("pushTime", true);
            handingGetResponse.canceledCount = HandingDataHelper.getHandingDataHelper().getCancledCount(requestBean.getUserId());
            handingGetResponse.handings = HandingDataHelper.getHandingDataHelper().getHandingBuys(pageBuilder);
            handingGetResponse.maxCount = HandingDataHelper.getHandingDataHelper().getMaxBuyCount(pageBuilder);
            handingGetResponse.lossRate = (float) handingGetResponse.canceledCount / (float) handingGetResponse.maxCount;
            return success(handingGetResponse);
        }));

        get("myBuyDetail", MyHandingBuyDetail.class, ((request, response, requestBean) -> {
            HandingDataHelper.getHandingDataHelper().updateCancledStatis(requestBean.getUserId());

            MyHandingDetailResponse myHandingDetailResponse = new MyHandingDetailResponse();
            myHandingDetailResponse.buy = HandingDataHelper.getHandingDataHelper().getHandingBrief(requestBean.handingId);
            myHandingDetailResponse.supplies = HandingDataHelper.getHandingDataHelper().getHandingBuySupplies(requestBean.handingId);
            myHandingDetailResponse.salesManPhone = UserDataHelper.instance().getSalesManTel(requestBean.getUserId());
            return success(myHandingDetailResponse);
        }));

        get("handingDetail", HandingDetailRequest.class, ((request, response, requestBean) -> {
            HandingDetail handingProduct = HandingDataHelper.getHandingDataHelper().getHandingProductById(requestBean.handingId);
            handingProduct.setCityName(CityDataHelper.instance().getCityDescById(handingProduct.souCityId));
            if (handingProduct == null) {
                return error("未找到相关加工信息");
            }
            return success(handingProduct);
        }));

        post("selectSupply", SelectHandingSupply.class, ((request, response, requestBean) -> {
            HandingDataHelper.getHandingDataHelper().updateCancledStatis(requestBean.getUserId());

            if (HandingDataHelper.getHandingDataHelper().getHandingBuyStatus(requestBean.handingBuyId) != 0) {
                return error("此次求购已经结束");
            }
            if (!HandingDataHelper.getHandingDataHelper().isUserIdInSupplyList(requestBean.handingBuyId, requestBean.supplyId)) {
                return error("该商家未参与报价");
            }
            if (!StringUtils.isEmpty(HandingDataHelper.getHandingDataHelper().getSupplyUserId(requestBean.handingBuyId))) {
                return error("此次求购已经结束");
            }
            HandingDataHelper.getHandingDataHelper().selectHandingBuySupply(requestBean.getUserId(), requestBean.handingBuyId, requestBean.supplyId);
            return success();
        }));

        get("shopRecommend", BaseBean.class, ((request, response, requestBean) -> {
            return success(SellerDataHelper.instance().getRecommendShopsByHanding());
        }));

        get("myProducts", BaseAuthPageBean.class, ((request, response, requestBean) -> {
            MyHandingProductResponse myHandingProductResponse = new MyHandingProductResponse(requestBean.currentPage, requestBean.pageCount);
            myHandingProductResponse.maxCount = HandingDataHelper.getHandingDataHelper().getMyHandingProductCount(requestBean.getUserId());
            myHandingProductResponse.handings = HandingDataHelper.getHandingDataHelper().getMyHandingProduct(new PageBuilder(requestBean.currentPage, requestBean.pageCount), requestBean.getUserId());
            return success(myHandingProductResponse);
        }));

        post("updateProduct", UpdateHandingProductRequest.class, ((request, response, requestBean) -> {
            if(!HandingDataHelper.getHandingDataHelper().isUserHandingRight(requestBean.getUserId(), requestBean.handingId)) {
                return error("用户权限错误");
            }
            HandingDataHelper.getHandingDataHelper().updateHandingProduct(requestBean.handingId, requestBean.price);
            return success();
        }));

        post("deleteProduct", DeleteHandingProductRequest.class, ((request, response, requestBean) -> {
            if(!HandingDataHelper.getHandingDataHelper().isUserHandingRight(requestBean.getUserId(), requestBean.handingId)) {
                return error("用户权限错误");
            }
            if (OrderDataHelper.instance().isProductInOrdering(requestBean.handingId, 1)) {
                return error("该产品有相关订单,请处理后再删除");
            }
            HandingDataHelper.getHandingDataHelper().deleteHandingProduct(requestBean.handingId);
            return success();
        }));


    }

    @Override
    public String getParentRoutePath() {
        return "handing";
    }
}
