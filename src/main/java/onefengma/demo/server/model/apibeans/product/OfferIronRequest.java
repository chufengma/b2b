package onefengma.demo.server.model.apibeans.product;

import onefengma.demo.annotation.NotRequired;
import onefengma.demo.server.model.apibeans.AuthSession;

import java.lang.annotation.Native;

/**
 * Created by chufengma on 16/7/9.
 */
public class OfferIronRequest extends AuthSession {
    public String ironId;
    public float price;
    @NotRequired
    public String msg;
    public String unit;
}
