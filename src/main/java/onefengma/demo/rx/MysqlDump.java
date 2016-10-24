package onefengma.demo.rx;

import com.alibaba.fastjson.JSON;

import onefengma.demo.common.StringUtils;
import onefengma.demo.server.config.ConfigBean;
import onefengma.demo.server.model.mobile.LoseOfferPushData;
import onefengma.demo.server.model.mobile.WinOfferPushData;
import onefengma.demo.server.model.product.IronBuyBrief;
import onefengma.demo.server.model.product.SupplyBrief;
import onefengma.demo.server.services.products.IronDataHelper;
import onefengma.demo.server.services.user.UserDataHelper;

/**
 * Created by chufengma on 16/7/24.
 */
public class MysqlDump {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        ConfigBean.configDev();

        IronBuyBrief ironBuyBrief = IronDataHelper.getIronDataHelper().getIronBuyBrief("YlPK62ZsQ1Pm");

//        BuyPushData pushData = new BuyPushData("sdfa33234231", BasePushData.PUSH_TYPE_BUY);
//        pushData.title = "您的求购有新报价";
//        pushData.desc = "风马牛" + "公司 已对您的ABC求购进行报价，点击查看";
//        pushData.ironBuyBrief = ironBuyBrief;
//        pushData.bageCount = pushData.newSupplyNums;
//        pushData.newSupplyNums = 12;

//        BuyPushData pushData = new BuyPushData("sdfa33234231", BasePushData.PUSH_TYPE_OFFER_MISS);
//        pushData.title = "有商家已放弃您的求购";
//        pushData.desc = "风马公司 已放弃对您的XXX求购报价.";
//        pushData.ironBuyBrief = ironBuyBrief;
//        pushData.newSupplyNums = 12;


//        IronQtPushData pushData = new IronQtPushData("sdfa33234231", "gNViZLPR6nDP");
//        pushData.title = "您的求购开始质检";
//        pushData.desc = "您的求购" + IronDataHelper.getIronDataHelper().generateIronBuyMessage(ironBuyBrief);

//        NewIronBuyPushData pushData = new NewIronBuyPushData("sdfa33234231");
//        pushData.title = "有您感兴趣的求购";
//        pushData.desc = "有人求购XXXXXXX";


//        WinOfferPushData pushData = new WinOfferPushData("sdfa33234231");
//        pushData.title = "恭喜您成功中标！";
//        pushData.desc = "恭喜您！您报价的XXXX已中标，请联系对方吧 : ";
//        pushData.ironBuyBrief = ironBuyBrief;


        LoseOfferPushData pushData = new LoseOfferPushData("sdfa33234231");
        pushData.title = "很遗憾您竞标失败";
        pushData.desc = "很遗憾！您报价的XXX未中标.";
        pushData.ironBuyBrief = ironBuyBrief;

        System.out.println(JSON.toJSONString(pushData));
    }

}
