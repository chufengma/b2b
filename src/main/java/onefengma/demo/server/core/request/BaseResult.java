package onefengma.demo.server.core.request;

/**
 * @author yfchu
 * @date 2016/5/23
 */
public class BaseResult {
    public int status;
    public String errorMsg;
    public Object data;

    public BaseResult() {
    }

    public BaseResult(int status, String errorMsg, Object data) {
        this.status = status;
        this.errorMsg = errorMsg;
        this.data = data;
    }

}
