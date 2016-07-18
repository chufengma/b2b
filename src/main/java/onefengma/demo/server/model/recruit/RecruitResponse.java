package onefengma.demo.server.model.recruit;

import java.util.List;

import onefengma.demo.server.model.apibeans.product.BasePageResponse;
import onefengma.demo.server.services.funcs.RecruitDataManager.RecruitBrief;

/**
 * @author yfchu
 * @date 2016/7/18
 */
public class RecruitResponse extends BasePageResponse {
    public List<RecruitBrief> recruits;

    public RecruitResponse(int currentPage, int pageCount) {
        super(currentPage, pageCount);
    }
}
