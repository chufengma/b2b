package onefengma.demo.server.model.apibeans.codes;

import onefengma.demo.server.model.apibeans.BaseBean;

/**
 * @author yfchu
 * @date 2016/5/30
 */
public class MsgCode {

    public static class MsgCodePostRequest extends BaseBean {
        public int code;
        public String mobile;
    }

    public static class MsgCodeGetRequest extends BaseBean {
        public String mobile;
    }

    public static class MsgCodeResponse {
        public int code;
        public String mobile;

        public MsgCodeResponse(String mobile, int code) {
            this.code = code;
            this.mobile = mobile;
        }
    }

}
