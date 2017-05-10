package onefengma.demo.server.services.specoffers;

import onefengma.demo.common.FileHelper;
import onefengma.demo.common.IdUtils;
import onefengma.demo.common.StringUtils;
import onefengma.demo.common.VerifyUtils;
import onefengma.demo.server.core.BaseManager;
import onefengma.demo.server.core.PageBuilder;
import onefengma.demo.server.model.metaData.HandingDataCategory;
import onefengma.demo.server.model.metaData.IconDataCategory;
import onefengma.demo.server.model.specoffer.*;
import onefengma.demo.server.services.funcs.CityDataHelper;

/**
 * Created by chufengma on 2017/5/1.
 */
public class SpecOfferManager extends BaseManager {

    @Override
    public void init() {
        multiPost("pushSpecOffer", SpecOfferRequest.class, ((request, response, requestBean) -> {
            SpecOffer specOffer = new SpecOffer();
            specOffer.offerId = IdUtils.id();
            specOffer.cover = FileHelper.generateRelativeInternetUri(requestBean.cover);

            if (!IconDataCategory.get().materials.contains(requestBean.material)) {
                return errorAndClear(requestBean, "材料填写有误");
            }
            if (!IconDataCategory.get().productPlaces.contains(requestBean.proPlace)) {
                return errorAndClear(requestBean, "产地填写有误");
            }
            if (!IconDataCategory.get().types.contains(requestBean.type)) {
                return errorAndClear(requestBean, "种类填写有误");
            }
            if (!IconDataCategory.get().surfaces.contains(requestBean.surface)) {
                return errorAndClear(requestBean, "表面填写有误");
            }
            if (!VerifyUtils.isPhone(requestBean.tel) && !VerifyUtils.isMobile(requestBean.tel)) {
                return errorAndClear(requestBean, "请填写正确的电话号码");
            }
            if (!VerifyUtils.isPhone(requestBean.hostTel) && !VerifyUtils.isMobile(requestBean.hostTel)) {
                return errorAndClear(requestBean, "请填写正确的电话号码");
            }
            if (requestBean.pic1 != null) {
                specOffer.pic1 = FileHelper.generateRelativeInternetUri(requestBean.pic1);
            }
            if (requestBean.pic2 != null) {
                specOffer.pic2 = FileHelper.generateRelativeInternetUri(requestBean.pic2);
            }
            if (requestBean.pic3 != null) {
                specOffer.pic3 = FileHelper.generateRelativeInternetUri(requestBean.pic3);
            }
            if (requestBean.pic4 != null) {
                specOffer.pic4 = FileHelper.generateRelativeInternetUri(requestBean.pic4);
            }
            if (requestBean.pic5 != null) {
                specOffer.pic5 = FileHelper.generateRelativeInternetUri(requestBean.pic5);
            }
            specOffer.title = requestBean.title;
            specOffer.des = requestBean.des;
            specOffer.hostName = requestBean.hostName;
            specOffer.hostTel = requestBean.hostTel;
            specOffer.material = requestBean.material;
            specOffer.price = requestBean.price;
            specOffer.proPlace = requestBean.proPlace;
            specOffer.surface = requestBean.surface;
            specOffer.type = requestBean.type;
            specOffer.tel = requestBean.tel;
            specOffer.tolerance = requestBean.tolerance;
            specOffer.unit = requestBean.unit;
            specOffer.spec = requestBean.spec;
            specOffer.city = requestBean.city;
            specOffer.cityDesc = requestBean.cityDesc;
            specOffer.province = requestBean.province;
            specOffer.count = requestBean.count;
            specOffer.priceDesc1 = requestBean.priceDesc1;
            specOffer.priceDesc2 = requestBean.priceDesc2;
            specOffer.pushTime = System.currentTimeMillis() + "";
            SpecOfferDataHelper.getHelper().insertSpecOffer(specOffer);
            return success();
        }));

        get("specoffers", SpecOfferFetchRequest.class, ((request, response, requestBean) -> {
            PageBuilder pageBuilder = new PageBuilder(requestBean.currentPage, requestBean.pageCount);
            if (!StringUtils.isEmpty(requestBean.material)) {
                pageBuilder.addEqualWhere("material", requestBean.material);
            }
            if (!StringUtils.isEmpty(requestBean.surface)) {
                pageBuilder.addEqualWhere("surface", requestBean.surface);
            }
            if (!StringUtils.isEmpty(requestBean.proPlace)) {
                pageBuilder.addEqualWhere("proPlace", requestBean.proPlace);
            }
            if (!StringUtils.isEmpty(requestBean.type)) {
                pageBuilder.addEqualWhere("type", requestBean.type);
            }
            if (!StringUtils.isEmpty(requestBean.key)) {
                pageBuilder.addLikeWhere("title", requestBean.key);
            }
            if (!StringUtils.isEmpty(requestBean.key)) {
                pageBuilder.addLikeWhere("title", requestBean.key);
            }
            if (requestBean.status != -1) {
                pageBuilder.addEqualWhere("status", requestBean.status);
            }
            return success(SpecOfferDataHelper.getHelper().fetchSpecOffers(pageBuilder));
        }));

        get("specoffer", SpecOfferQueryRequest.class, ((request, response, requestBean) -> {
            SpecOfferQueryResponse specOfferQueryResponse = new SpecOfferQueryResponse();
            specOfferQueryResponse.specOffer = SpecOfferDataHelper.getHelper().getSpecOffer(requestBean.offerId);
            if (specOfferQueryResponse.specOffer == null) {
                return error("该特殊报价不存在");
            }
            return success(specOfferQueryResponse);
        }));

        post("soldOut", SpecOfferQueryRequest.class, ((request, response, requestBean) -> {
            SpecOffer specOffer = SpecOfferDataHelper.getHelper().getSpecOffer(requestBean.offerId);
            if (specOffer == null) {
                return error("该特殊报价不存在");
            }
            SpecOfferDataHelper.getHelper().soldOutSpecOffer(requestBean.offerId);
            return success();
        }));

        post("updateSpecOffer", SpecOfferUpdateRequest.class, ((request, response, requestBean) -> {
            SpecOffer specOffer = SpecOfferDataHelper.getHelper().getSpecOffer(requestBean.offerId);
            if (specOffer == null) {
                return error("该特殊报价不存在");
            }
            boolean changed = false;
            if (!StringUtils.isEmpty(requestBean.city) && !StringUtils.equals(requestBean.city, specOffer.city)) {
                specOffer.city = requestBean.city;
                changed = true;
            }
            if (!StringUtils.isEmpty(requestBean.spec) && !StringUtils.equals(requestBean.spec, specOffer.spec)) {
                changed = true;
                specOffer.spec = requestBean.spec;
            }
            if (!StringUtils.equals(requestBean.tolerance, specOffer.tolerance)) {
                changed = true;
                specOffer.tolerance = requestBean.tolerance;
            }
            if (!StringUtils.isEmpty(requestBean.unit) && !StringUtils.equals(requestBean.unit, specOffer.unit)) {
                changed = true;
                specOffer.unit = requestBean.unit;
            }
            if (!StringUtils.isEmpty(requestBean.price) && !StringUtils.equals(requestBean.price, specOffer.price)) {
                changed = true;
                specOffer.price = requestBean.price;
            }
            if (!StringUtils.isEmpty(requestBean.title) && !StringUtils.equals(requestBean.title, specOffer.title)) {
                changed = true;
                specOffer.title = requestBean.title;
            }
            if (!StringUtils.isEmpty(requestBean.tel) && !StringUtils.equals(requestBean.tel, specOffer.tel)) {
                if (!VerifyUtils.isPhone(requestBean.tel) && !VerifyUtils.isMobile(requestBean.tel)) {
                    return errorAndClear(requestBean, "请填写正确的电话号码");
                }
                changed = true;
                specOffer.tel = requestBean.tel;
            }
            if (!StringUtils.isEmpty(requestBean.hostName) && !StringUtils.equals(requestBean.hostName, specOffer.hostName)) {
                changed = true;
                specOffer.hostName = requestBean.hostName;
            }
            if (!StringUtils.isEmpty(requestBean.hostTel) && !StringUtils.equals(requestBean.hostTel, specOffer.hostTel)) {
                if (!VerifyUtils.isPhone(requestBean.hostTel) && !VerifyUtils.isMobile(requestBean.hostTel)) {
                    return errorAndClear(requestBean, "请填写正确的电话号码");
                }
                changed = true;
                specOffer.hostTel = requestBean.hostTel;
            }
            if (!StringUtils.isEmpty(requestBean.province) && !StringUtils.equals(requestBean.province, specOffer.province)) {
                changed = true;
                specOffer.province = requestBean.province;
            }
            if (!StringUtils.isEmpty(requestBean.priceDesc1) && !StringUtils.equals(requestBean.priceDesc1, specOffer.priceDesc1)) {
                changed = true;
                specOffer.priceDesc1 = requestBean.priceDesc1;
            }
            if (!StringUtils.isEmpty(requestBean.priceDesc2) && !StringUtils.equals(requestBean.priceDesc2, specOffer.priceDesc2)) {
                changed = true;
                specOffer.priceDesc2 = requestBean.priceDesc2;
            }
            if (!StringUtils.isEmpty(requestBean.count) && !StringUtils.equals(requestBean.count, specOffer.count)) {
                changed = true;
                specOffer.count = requestBean.count;
            }
            if (!StringUtils.equals(requestBean.cityDesc, specOffer.cityDesc)) {
                changed = true;
                specOffer.cityDesc = requestBean.cityDesc;
            }
            if (!StringUtils.isEmpty(requestBean.des) && !StringUtils.equals(requestBean.des, specOffer.des)) {
                changed = true;
                specOffer.des = requestBean.des;
            }
            if (changed) {
                specOffer.updateTime = System.currentTimeMillis() + "";
                SpecOfferDataHelper.getHelper().updateSpecOffer(specOffer);
            }
            return success(specOffer);
        }));

    }



    @Override
    public String getParentRoutePath() {
        return "specoffer";
    }

}
