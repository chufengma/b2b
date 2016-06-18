package onefengma.demo.server.model.apibeans.order;

import onefengma.demo.server.model.apibeans.AuthSession;

/**
 * Created by chufengma on 16/6/18.
 */
public class OrderTmpRequest extends AuthSession {
    public int productType;
    public String productId;
    public String ironCount;
}
