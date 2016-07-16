package onefengma.demo.server.services.products;

import java.util.ArrayList;

import onefengma.demo.common.VerifyUtils;
import onefengma.demo.server.core.BaseManager;
import onefengma.demo.server.core.PageBuilder;
import onefengma.demo.server.model.apibeans.AuthSession;
import onefengma.demo.server.model.apibeans.BaseBean;
import onefengma.demo.server.model.apibeans.others.HelpFindProduct;
import onefengma.demo.server.model.apibeans.others.HelpFindProductRequest;
import onefengma.demo.server.model.apibeans.product.ProShopRequest;
import onefengma.demo.server.model.apibeans.product.ProShopResponse;
import onefengma.demo.server.model.apibeans.product.SearchRequest;
import onefengma.demo.server.model.apibeans.product.SearchResponse;
import onefengma.demo.server.model.apibeans.product.ShopDetailRequest;
import onefengma.demo.server.model.apibeans.product.ShopRequest;
import onefengma.demo.server.model.apibeans.product.ShopResponse;
import onefengma.demo.server.model.metaData.IconDataCategory;
import onefengma.demo.server.model.product.ShopBrief;
import onefengma.demo.server.model.product.ShopDetail;
import onefengma.demo.server.services.funcs.CityDataHelper;
import onefengma.demo.server.services.funcs.OthersDataHelper;
import onefengma.demo.server.services.user.SellerDataHelper;

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

        get("shopBrief", ShopDetailRequest.class, ((request, response, requestBean) -> {
            ShopBrief shopBrief = SellerDataHelper.instance().getShopBrief(requestBean.sellerId);
            if (shopBrief == null) {
                return error("没找到相关店铺");
            }
            return success(shopBrief);
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

        post("helpFindProduct", HelpFindProductRequest.class, ((request, response, requestBean) -> {
            HelpFindProduct helpFindProduct = requestBean.generateHelpFind();
            if(!VerifyUtils.isMobile(helpFindProduct.mobile)) {
                return error("手机号码格式不对");
            }
            // 不锈钢品类
            if (!IconDataCategory.get().types.contains(helpFindProduct.type)) {
                return errorAndClear(requestBean, "不锈钢品类填写不正确");
            }
            IronDataHelper.getIronDataHelper().insertFindHelpProduct(helpFindProduct);
            return success();
        }));
    }

    @Override
    public String getParentRoutePath() {
        return "product";
    }

}
