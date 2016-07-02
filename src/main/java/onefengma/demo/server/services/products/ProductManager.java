package onefengma.demo.server.services.products;

import onefengma.demo.server.core.BaseManager;
import onefengma.demo.server.core.PageBuilder;
import onefengma.demo.server.model.apibeans.product.*;
import onefengma.demo.server.model.product.ShopBrief;
import onefengma.demo.server.model.product.ShopDetail;
import onefengma.demo.server.services.funcs.CityDataHelper;
import onefengma.demo.server.services.user.SellerDataHelper;

import java.util.ArrayList;

/**
 * @author yfchu
 * @date 2016/5/23
 */
public class ProductManager extends BaseManager {
    @Override
    public void init() {

        post("search", SearchRequest.class, ((request, response, requestBean) -> {
            SearchRequest.SearchType searchType;
            try {
                searchType = requestBean.getType();
            } catch (Exception e) {
                searchType = null;
            }
            if (searchType == null) {
                return error("暂不支持的搜索类型");
            }
            SearchResponse searchResponse = new SearchResponse(requestBean.type, requestBean.currentPage, requestBean.pageCount);
            PageBuilder pageBuilder = new PageBuilder(requestBean.currentPage, requestBean.pageCount);
            switch (requestBean.getType()) {
                case IRON:
                    searchResponse.ironProducts = IronDataHelper.getIronDataHelper().searchIronProducts(requestBean.keyword, pageBuilder);
                    searchResponse.maxCount = IronDataHelper.getIronDataHelper().searchIronProductsMaxCount(requestBean.keyword);
                    break;
                case HANDING:
                    searchResponse.handingProducts = HandingDataHelper.getHandingDataHelper().searchHandingProduct(requestBean.keyword, pageBuilder);
                    searchResponse.maxCount = HandingDataHelper.getHandingDataHelper().searchHandingProductsMaxCount(requestBean.keyword);
                    break;
                case SHOP:
                    searchResponse.shops = SellerDataHelper.instance().searchShops(requestBean.keyword, pageBuilder);
                    searchResponse.maxCount = SellerDataHelper.instance().searchShopCount(requestBean.keyword);
                    break;
                default:
                    return error("暂不支持的搜索类型");
            }
            return success(searchResponse);
        }));

        get("shops", ShopRequest.class, ((request, response, requestBean) -> {
            ShopResponse shopResponse = new ShopResponse(requestBean.currentPage, requestBean.pageCount);

            PageBuilder pageBuilder = new PageBuilder(requestBean.currentPage, requestBean.pageCount)
                    .addInWhere("cityId", CityDataHelper.instance().getCitiesById(new ArrayList<>(), requestBean.cityId))
                    .setOrderByRequest(requestBean);

            shopResponse.shops = SellerDataHelper.instance().getShops(pageBuilder, requestBean.productType);
            shopResponse.maxCount = SellerDataHelper.instance().getShopCount(pageBuilder, requestBean.productType);
            return success(shopResponse);
        }));

        get("shopDetail", ShopDetailRequest.class, ((request, response, requestBean) -> {
            ShopDetail shopDetail = SellerDataHelper.instance().getShopDetail(requestBean.sellerId);
            if (shopDetail == null) {
                return error("没找到相关店铺");
            }
            return success(shopDetail);
        }));

        get("productShop", ProShopRequest.class, ((request, response, requestBean) -> {
            ShopBrief shopBrief = SellerDataHelper.instance().getShopByProId(requestBean.proId, requestBean.productType);
            if (shopBrief == null) {
                return error("未找到该商铺");
            }
            ProShopResponse shopResponse = new ProShopResponse();
            shopResponse.productType = requestBean.productType;
            shopResponse.shop = shopBrief;
            return success(shopResponse);
        }));
    }

    @Override
    public String getParentRoutePath() {
        return "product";
    }

}
