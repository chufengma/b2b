package onefengma.demo.server.model.apibeans.order;

import onefengma.demo.server.model.apibeans.AuthSession;

/**
 * Created by chufengma on 16/7/3.
 */
public class VoteOrderRequest extends AuthSession {
    public String orderId;
    public float vote;
}
