package onefengma.demo.rx;

import com.alibaba.fastjson.JSON;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import onefengma.demo.common.StringUtils;
import onefengma.demo.server.config.ConfigBean;
import onefengma.demo.server.core.LogUtils;
import onefengma.demo.server.services.user.UserDataHelper;

/**
 * @author yfchu
 * @date 2016/7/22
 */
public class UserMessageServer extends WebSocketServer {

    public Map<String, List<WebSocket>> userSocketsMap = new HashMap<>();

    private static UserMessageServer instance;

    public static UserMessageServer getInstance() {
        if (instance == null) {
            try {
                instance = new UserMessageServer();
            } catch (UnknownHostException e) {
                e.printStackTrace();
                LogUtils.e(e, "error when start user message server");
            }
        }
        return instance;
    }

    public UserMessageServer() throws UnknownHostException {
        super(new InetSocketAddress(ConfigBean.USER_MESSAGE_PORT));
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        LogUtils.i("web socket open: " + conn.getRemoteSocketAddress().toString());
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        LogUtils.i("web socket close: " + conn.getRemoteSocketAddress().getHostName());
        removeConnect(conn);
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        try {
            DataParams dataParams = JSON.parseObject(message, DataParams.class);
            if (StringUtils.equals(dataParams.url, "user") && !StringUtils.isEmpty(dataParams.data)) {
                userDataHandler(dataParams.data, conn);
            }
        } catch (Exception e) {
            LogUtils.e(e, "error when parse dataParams ");
        }
    }

    private void userDataHandler(String data, WebSocket conn) {
        UserParams userParams = JSON.parseObject(data, UserParams.class);
        String userId = userParams.userId;
        if (!StringUtils.isEmpty(userId) && UserDataHelper.instance().isUserExisted(userId)) {
            addConnect(userId, conn);
        } else {
            LogUtils.i("web socket userId is null or user not exited: " + conn.getLocalSocketAddress().getHostName() + ":" + conn.getRemoteSocketAddress().getHostName());
            conn.close();
        }
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        LogUtils.i("web socket error: " + conn.getLocalSocketAddress().getHostName() + ":" + conn.getRemoteSocketAddress().getHostName());
        removeConnect(conn);
    }

    private void addConnect(String userId, WebSocket webSocket) {
        List<WebSocket> userSockets = userSocketsMap.get(userId);
        if (userSockets == null) {
            userSockets = new ArrayList<>();
            userSocketsMap.put(userId, userSockets);
        }
        userSockets.add(webSocket);
    }

    private void removeConnect(WebSocket webSocket) {
        for(List<WebSocket> sockets : userSocketsMap.values()) {
            if (sockets.contains(webSocket)) {
                sockets.remove(webSocket);
            }
        }
    }

    public void sendUserMessage(String userId, String message) {
        List<WebSocket> webSockets = userSocketsMap.get(userId);
        if (webSockets != null) {
            UserMessage userMessage = new UserMessage();
            userMessage.userId = userId;
            userMessage.message = message;
            try {
                for(WebSocket webSocket : webSockets) {
                    webSocket.send(JSON.toJSONString(userMessage));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static class UserMessage {
        public String userId;
        public String message;
    }

    public static class DataParams {
        public String url;
        public String data;
    }

    public static class UserParams {
        public String userId;
    }

}
