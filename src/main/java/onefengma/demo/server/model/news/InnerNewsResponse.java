package onefengma.demo.server.model.news;

import java.util.List;

import onefengma.demo.server.model.apibeans.product.BasePageResponse;

/**
 * @author yfchu
 * @date 2016/7/18
 */
public class InnerNewsResponse extends BasePageResponse {
    public List<InnerNewsBrief> news;

    public InnerNewsResponse(int currentPage, int pageCount) {
        super(currentPage, pageCount);
    }
}
