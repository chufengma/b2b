package onefengma.demo.server.core;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

import onefengma.demo.common.IdUtils;
import onefengma.demo.common.StringUtils;
import spark.Request;

/**
 * @author yfchu
 * @date 2016/5/30
 */
public class MsgCodeHelper {

    private static final String MOBILE = "msgMobile";
    private static final String MSG_CODE = "msgCode";
    private static final String MSG_TIME = "msgTime";

    public static boolean isMsgCodeRight(Request request, int msgCode, String mobile) {
        String serverMobile = request.session().attribute(MOBILE);
        Integer serverMsgCode = request.session().attribute(MSG_CODE);
        if (serverMobile == null || serverMsgCode == null) {
            return false;
        }
        return StringUtils.equals(mobile, serverMobile) && msgCode == serverMsgCode;
    }

    public static int generateMsgCode(Request request, String number) throws ApiException {
        TaobaoClient client = new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest", "23382355", "e3800fe279b9109060ba97c527a86b7a");
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        req.setExtend("123456");
        req.setSmsType("normal");
        req.setSmsFreeSignName("淘不锈");
        int code = IdUtils.intId(6);
        req.setSmsParam("{\"code\":\"" + code + "\",\"name\":\"" + number + "\"}");
        req.setRecNum(number);
        req.setSmsTemplateCode("SMS_10400379");
        AlibabaAliqinFcSmsNumSendResponse response = client.execute(req);
        if (response.isSuccess()) {
            request.session().attribute(MOBILE, number);
            request.session().attribute(MSG_CODE, code);
            request.session().attribute(MSG_TIME, System.currentTimeMillis());
            return code;
        } else {
            throw new ApiException("Response error!");
        }
    }

    public static boolean hasSendLateMinitue(Request request) {
        Long lastTime =  request.session().attribute(MSG_TIME);
        if (lastTime != null) {
            return (System.currentTimeMillis() - lastTime) < 1000 * 55;
        }
        return false;
    }

}
