package onefengma.demo.server.core;

import com.xiaomi.xmpush.server.*;
import onefengma.demo.common.ThreadUtils;
import onefengma.demo.server.config.ConfigBean;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    public void pushAndroidMessage(String userId) {
        ThreadUtils.instance().post(new Runnable() {
            @Override
            public void run() {
                String messagePayload = "哈哈哈哈哈哈哈哈啊哈哈哈哈哈哈哈哈哈";
                String title = "欢迎登陆淘不锈";
                String description = "淘不锈是一个什么什么什么什么什么什么什么什么什么什么";
                Message message = new Message.Builder()
                        .title(title)
                        .description(description).payload(messagePayload)
                        .restrictedPackageName(ANDROID_PAKAGE)
                        .passThrough(1)
                        .notifyType(1)
                        .build();

                Sender sender = new Sender(SECRET_KEY);
                try {
                    sender.sendToUserAccount(message, ConfigBean.MOBILE_PUSH_PREFIX + userId, 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
