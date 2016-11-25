package onefengma.demo.server.core;

import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.alibaba.fastjson.JSON;
import onefengma.demo.server.config.Config;
import onefengma.demo.server.config.ConfigBean;
import onefengma.demo.server.model.mobile.BasePushData;

/**
 * Created by chufengma on 2016/11/25.
 */
public class JiGuangPushManager {

    public static void pushIosMessage(BasePushData basePushData) {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.alias(ConfigBean.MOBILE_PUSH_PREFIX + basePushData.userId))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert(basePushData.title)
                                .setBadge(basePushData.bageCount)
                                .addExtra("type", basePushData.type)
                                .addExtra("id", basePushData.id)
                                .addExtra("content", JSON.toJSONString(basePushData))
                                .build())
                        .build())
                .setMessage(Message.content(basePushData.title))
                .setOptions(Options.newBuilder()
                        .setApnsProduction(true)
                        .build())
                .build();
        JPushClient jpushClient = new JPushClient("69c92369d5b5ecb40ca687e0", "6dd079ea3c1a1e22e8527e27", 3);
        try {
            PushResult result = jpushClient.sendPush(payload);
            LogUtils.saveToFiles("Got result - " + result.getOriginalContent(), true);
        } catch (Exception e) {
            LogUtils.saveToFiles("Got result - " + e.getMessage(), true);
            e.printStackTrace();
        }
    }

}
