package onefengma.demo.server.model.admin;

import onefengma.demo.server.model.qt.QtBrief;
import onefengma.demo.server.model.qt.QtDetail;
import onefengma.demo.server.services.user.AdminDataManager;

import java.util.List;

/**
 * Created by chufengma on 16/9/4.
 */
public class AdminQtResponse extends BaseAdminPageResponse {
    public List<AdminDataManager.QtItem> qtBriefList;
}
