package onefengma.demo.server.model.apibeans.product;

import onefengma.demo.server.model.apibeans.AuthSession;

/**
 * Created by chufengma on 16/7/9.
 */
public class OfferHandingRequest extends AuthSession {
    public String handingId;
    public float price;
    public String msg;
}
