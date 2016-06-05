package onefengma.demo.server.services.products;

import onefengma.demo.server.core.BaseManager;
import onefengma.demo.server.core.PageBuilder;
import onefengma.demo.server.model.apibeans.BaseBean;
import onefengma.demo.server.model.apibeans.product.HandingGetRequest;
import onefengma.demo.server.model.apibeans.product.HandingGetResponse;
import onefengma.demo.server.model.apibeans.product.HandingPushRequest;
import onefengma.demo.server.model.metaData.HandingDataCategory;
import onefengma.demo.server.services.funcs.CityDataHelper;

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
                return errorAndClear(requestBean, "单位选择有误");
            }
            HandingDataHelper.getHandingDataHelper().insertHandingProduct(requestBean.generateHandingProduct());
            return success(requestBean);
        }));

        get("handing", HandingGetRequest.class, ((request, response, requestBean) -> {
            HandingGetResponse handingGetResponse = new HandingGetResponse(requestBean.currentPage, requestBean.pageCount);
            handingGetResponse.maxCount = HandingDataHelper.getHandingDataHelper().getMaxCount();
            handingGetResponse.handingProducts = HandingDataHelper.getHandingDataHelper().getHandingProducts(new PageBuilder(requestBean.currentPage, requestBean.pageCount)
                .addEqualWhere("type", requestBean.handingType)
                .addEqualWhere("souCityId", requestBean.souCityId));
            return success(handingGetResponse);
        }));

    }

    @Override
    public String getParentRoutePath() {
        return "handing";
    }
}
