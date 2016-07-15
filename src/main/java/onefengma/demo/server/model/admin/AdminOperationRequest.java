package onefengma.demo.server.model.admin;

import onefengma.demo.annotation.NotRequired;
import onefengma.demo.server.model.apibeans.AdminAuthSession;

/**
 * Created by chufengma on 16/7/9.
 */
public class AdminOperationRequest extends AdminAuthSession {
    public String id;
    public int operation;
    @NotRequired
    public String message;
}
