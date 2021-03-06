package onefengma.demo.server.model.apibeans;

import java.io.File;

import onefengma.demo.annotation.NotRequired;
import onefengma.demo.common.FileHelper;
import onefengma.demo.server.model.Seller;
import onefengma.demo.server.services.funcs.CityDataHelper;

/**
 * Created by chufengma on 16/6/2.
 */
public class SellerRequest extends AuthSession {
    public String companyName;
    public float regMoney;
    public String contact;
    public String cantactTel;
    public String fax;
    public String cityId;
    public String officeAddress;

    @NotRequired
    public File cover;
    @NotRequired
    public String qq;
    public String shopProfile;
    @NotRequired
    public File allCer;
    @NotRequired
    public File businessLic;
    @NotRequired
    public File codeLic;
    @NotRequired
    public File financeLic;

    public boolean isThreeInOne;


    public Seller generateSeller() {
        SellerRequest sellerRequest = this;
        if (sellerRequest == null) {
            return null;
        }
        Seller seller = new Seller();
        seller.userId = sellerRequest.getUserId();
        seller.companyName = sellerRequest.companyName;
        seller.regMoney = sellerRequest.regMoney;
        seller.contact = sellerRequest.contact;
        seller.cantactTel = sellerRequest.cantactTel;
        seller.fax = sellerRequest.fax;
        seller.cityId = sellerRequest.cityId;
        String cityDesc = "";
        try {
            cityDesc = CityDataHelper.instance().getCityDescById(cityId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        seller.officeAddress = cityDesc + sellerRequest.officeAddress;
        seller.qq = sellerRequest.qq;
        seller.shopProfile = sellerRequest.shopProfile;
        seller.cover = sellerRequest.cover == null ? "./files/2016/6/19/WdDfdnobGk7Y.jpg" : FileHelper.generateRelativeInternetUri(cover);
        seller.allCer = sellerRequest.allCer == null ? "" : FileHelper.generateRelativeInternetUri(sellerRequest.allCer.getPath());
        seller.businessLic = sellerRequest.businessLic == null ? "" : FileHelper.generateRelativeInternetUri(sellerRequest.businessLic.getPath());
        seller.codeLic = sellerRequest.codeLic == null ? "" : FileHelper.generateRelativeInternetUri(sellerRequest.codeLic.getPath());
        seller.financeLic = sellerRequest.financeLic == null ? "" : FileHelper.generateRelativeInternetUri(sellerRequest.financeLic.getPath());

        seller.applyTime = System.currentTimeMillis();

        return seller;
    }
}
