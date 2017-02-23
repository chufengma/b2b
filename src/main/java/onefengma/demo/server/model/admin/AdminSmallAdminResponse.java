package onefengma.demo.server.model.admin;

import onefengma.demo.server.services.user.AdminDataManager;

import java.util.List;

/**
 * Created by chufengma on 16/9/4.
 */
public class AdminSmallAdminResponse extends BaseAdminPageResponse {
    public List<AdminDataManager.SmallAdmin> smallAdmins;
}
