package onefengma.demo.server.model.mobile;

/**
 * @author yfchu
 * @date 2016/8/19
 */
public class NewIronBuyPushData extends BasePushData {
    public NewIronBuyPushData(String userId) {
        super(userId, PUSH_TYPE_NEW_IRON_BUY);
    }
}
