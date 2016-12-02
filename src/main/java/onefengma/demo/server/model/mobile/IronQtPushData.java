package onefengma.demo.server.model.mobile;

/**
 * @author yfchu
 * @date 2016/8/19
 */
public class IronQtPushData extends BasePushData {

    public IronQtPushData(String userId, String qtId) {
        super(userId, PUSH_TYPE_IRON_QT);
        this.id = qtId;
    }
}
