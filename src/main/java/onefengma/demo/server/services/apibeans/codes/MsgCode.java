package onefengma.demo.server.services.apibeans.codes;

/**
 * @author yfchu
 * @date 2016/5/30
 */
public class MsgCode {
    public static class MsgCodeRequest {

    }

    public static class MsgCodeResponse {
        public String code;

        public MsgCodeResponse(String code) {
            this.code = code;
        }
    }

}
