package onefengma.demo.server.core;

import com.alibaba.fastjson.JSON;
import com.xiaomi.xmpush.server.Constants;
import com.xiaomi.xmpush.server.Message;
import com.xiaomi.xmpush.server.Sender;

import onefengma.demo.common.StringUtils;
import onefengma.demo.common.ThreadUtils;
import onefengma.demo.server.config.ConfigBean;
import onefengma.demo.server.model.mobile.BasePushData;

/**
 * Created by chufengma on 16/8/13.
 */
public class PushManager {

    private static PushManager instance;
    public static final String ANDROID_PAKAGE = "com.onefengma.taobuxiu";
    public static final String APP_ID = "2882303761517500719";
    public static final String SECRET_KEY = "d6JcRax80B2SqHQfQEpYwQ==";


    public static PushManager instance() {
        if (instance == null) {
            instance = new PushManager();
        }
        return instance;
    }

    public void init() {
        Constants.useOfficial();
    }

    public void pushData(BasePushData pushData) {
        pushAndroidMessage(pushData);
    }

    private void pushAndroidMessage(BasePushData basePushData) {
        if (StringUtils.isEmpty(basePushData.userId)) {
            return;
        }

        String content = JSON.toJSONString(basePushData);
        LogUtils.i("push data " + content, false);

        ThreadUtils.instance().post(() -> {
            String messagePayload = content;
            String title = basePushData.title;
            Message message = new Message.Builder()
                    .title(title)
                    .description(basePushData.desc)
                    .extra("type", basePushData.type)
                    .payload(messagePayload)
                    .restrictedPackageName(ANDROID_PAKAGE)
                    .passThrough(1)
                    .notifyType(1)
                    .build();

            Sender sender = new Sender(SECRET_KEY);
            try {
                sender.sendToUserAccount(message, ConfigBean.MOBILE_PUSH_PREFIX + basePushData.userId, 4);
            } catch (Exception e) {
                LogUtils.i("push data error:" + JSON.toJSONString(basePushData), true);
                e.printStackTrace();
            }
        });
    }

}
