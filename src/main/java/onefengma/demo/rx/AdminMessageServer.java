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
public class AdminMessageServer extends WebSocketServer {

    public Map<Integer, List<WebSocket>> userSocketsMap = new HashMap<>();

    private static AdminMessageServer instance;

    public static AdminMessageServer getInstance() {
        if (instance == null) {
            try {
                instance = new AdminMessageServer();
            } catch (UnknownHostException e) {
                e.printStackTrace();
                LogUtils.e(e, "error when start user message server");
            }
        }
        return instance;
    }

    public AdminMessageServer() throws UnknownHostException {
        super(new InetSocketAddress(ConfigBean.ADMIN_MESSAGE_PORT));
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
        AdminParams userParams = JSON.parseObject(data, AdminParams.class);
        addConnect(userParams.adminId, conn);
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        LogUtils.i("web socket error: " + conn.getLocalSocketAddress().getHostName() + ":" + conn.getRemoteSocketAddress().getHostName());
        removeConnect(conn);
    }

    private void addConnect(int adminId, WebSocket webSocket) {
        List<WebSocket> userSockets = userSocketsMap.get(adminId);
        if (userSockets == null) {
            userSockets = new ArrayList<>();
            userSocketsMap.put(adminId, userSockets);
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

    public void sendUserMessage(int adminId, String title, String message) {
        List<WebSocket> webSockets = userSocketsMap.get(adminId);
        if (webSockets != null) {
            AdminMessage userMessage = new AdminMessage();
            userMessage.adminId = adminId;
            userMessage.content = message;
            userMessage.title = title;
            try {
                for(WebSocket webSocket : webSockets) {
                    webSocket.send(JSON.toJSONString(userMessage));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessageToAll(String title, String message) {
        try {
            AdminMessage userMessage = new AdminMessage();
            userMessage.title = title;
            userMessage.content = message;
            for(List<WebSocket> webSockets : userSocketsMap.values()) {
                for(WebSocket webSocket : webSockets) {
                    webSocket.send(JSON.toJSONString(userMessage));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class AdminMessage {
        public int adminId;
        public String content;
        public String title;
    }

    public static class DataParams {
        public String url;
        public String data;
    }

    public static class AdminParams {
        public int adminId;
    }

}
