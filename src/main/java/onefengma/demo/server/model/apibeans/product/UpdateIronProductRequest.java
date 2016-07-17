package onefengma.demo.server.model.apibeans.product;

import onefengma.demo.annotation.NotRequired;
import onefengma.demo.server.model.apibeans.AuthSession;

/**
 * Created by chufengma on 16/7/9.
 */
public class UpdateIronProductRequest extends AuthSession {
    public String ironId;
    public long numbers;
    public float price;
    public String title;
}
