package onefengma.demo.server.model.apibeans.admin;

import java.util.List;

import onefengma.demo.server.model.admin.BaseAdminPageResponse;
import onefengma.demo.server.services.user.AdminDataManager.ProductVerify;

/**
 * Created by chufengma on 16/7/10.
 */
public class AdminHandingVerifyResponse extends BaseAdminPageResponse {
    public List<ProductVerify> products;
}
