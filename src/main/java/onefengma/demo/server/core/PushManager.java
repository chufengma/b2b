package onefengma.demo.server.core;

import com.alibaba.fastjson.JSON;
import com.xiaomi.xmpush.server.Constants;
import com.xiaomi.xmpush.server.Message;
import com.xiaomi.xmpush.server.Sender;

import onefengma.demo.common.StringUtils;
import onefengma.demo.common.ThreadUtils;
import onefengma.demo.server.config.Config;
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
    public static final String SECRET_KEY_IOS = "e103ftlN3WqpKA9N+I4c7w==";


    public static PushManager instance() {
        if (instance == null) {
            instance = new PushManager();
            Constants.useOfficial();
        }
        return instance;
    }

    public void init() {
//        if (Config.ENV == Config.ENVI.DEV) {
//            Constants.useSandbox();
//        } else {
            Constants.useOfficial();
//        }
    }

    public void pushData(BasePushData pushData) {
        LogUtils.saveToFiles("push data now ", true);
        pushAndroidMessage(pushData);
        pushIOSMessage(pushData);
    }

    private void pushIOSMessage(BasePushData basePushData) {
        JiGuangPushManager.pushIosMessage(basePushData);

//        if (StringUtils.isEmpty(basePushData.userId)) {
//            return;
//        }
//
//        String content = JSON.toJSONString(basePushData);
//
//        if (Config.ENV == Config.ENVI.DEV) {
//            LogUtils.i("push data for ios useSandbox " + content, true);
//        } else {
//            LogUtils.i("push data for ios useOfficial " + content, true);
//        }
//
//        ThreadUtils.instance().post(() -> {
//            String messagePayload = content;
//            Message.IOSBuilder build = new Message.IOSBuilder()
//                    .description(basePushData.desc)
//                    .soundURL("default")    // 消息铃声
//                    .badge(basePushData.bageCount)
//                    .extra("type", basePushData.type)
//                    .extra("id", basePushData.id)
//                    .extra("content", messagePayload)  // 自定义键值对
//                    .extra("flow_control", "4000");   // 设置平滑推送, 推送速度4000每秒(qps=4000)
//
//            if (basePushData.bageCount != -1) {
//                build.badge(basePushData.bageCount);
//            }
//
//            Sender sender = new Sender(SECRET_KEY_IOS);
//            try {
//                sender.sendToUserAccount(build.build(), ConfigBean.MOBILE_PUSH_PREFIX + basePushData.userId, 4);
//            } catch (Exception e) {
//                LogUtils.i("push data error:" + JSON.toJSONString(basePushData), true);
//                e.printStackTrace();
//            }
//        });
    }


    private void pushAndroidMessage(BasePushData basePushData) {
        LogUtils.saveToFiles("push data for android start : " + basePushData.userId, true);
        if (StringUtils.isEmpty(basePushData.userId)) {
            return;
        }

        String content = JSON.toJSONString(basePushData);
        LogUtils.saveToFiles("push data for android " + content, true);
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
                LogUtils.saveToFiles("push data " + ConfigBean.MOBILE_PUSH_PREFIX + ", " + content, false);
            } catch (Exception e) {
                LogUtils.saveToFiles("push data error:" + JSON.toJSONString(basePushData), true);
                e.printStackTrace();
            }
        });
    }

}
