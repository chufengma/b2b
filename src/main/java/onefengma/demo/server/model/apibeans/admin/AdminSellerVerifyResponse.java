package onefengma.demo.server.model.apibeans.admin;

import java.util.List;

import onefengma.demo.server.model.admin.BaseAdminPageResponse;
import onefengma.demo.server.services.user.AdminDataManager.SellerVerify;

/**
 * Created by chufengma on 16/7/10.
 */
public class AdminSellerVerifyResponse extends BaseAdminPageResponse {
    public List<SellerVerify> sellers;
}
