package onefengma.demo.server.model.mobile;

/**
 * @author yfchu
 * @date 2016/8/19
 */
public class BasePushData {
    public String title;
    public String desc;
    public String userId;
    public String type;

    public BasePushData(String userId, String type) {
        this.userId = userId;
        this.type = type;
    }

    public static final String PUSH_TYPE_BUY = "buy";
}
