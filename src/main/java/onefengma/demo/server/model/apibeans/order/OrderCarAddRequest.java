package onefengma.demo.server.model.apibeans.order;

import onefengma.demo.server.model.apibeans.AuthSession;

/**
 * Created by chufengma on 16/7/5.
 */
public class OrderCarAddRequest extends AuthSession {
    public int productType;
    public String proId;
}
