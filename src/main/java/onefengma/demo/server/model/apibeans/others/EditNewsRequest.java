package onefengma.demo.server.model.apibeans.others;

import onefengma.demo.server.model.apibeans.AdminAuthSession;

/**
 * @author yfchu
 * @date 2016/7/15
 */
public class EditNewsRequest extends AdminAuthSession {
    public String title;
    public String content;
    public String id;
}

