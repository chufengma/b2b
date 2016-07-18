package onefengma.demo.server.model.news;

import java.util.List;

import onefengma.demo.server.model.apibeans.product.BasePageResponse;

/**
 * @author yfchu
 * @date 2016/7/18
 */
public class NewsResponse extends BasePageResponse {
    public List<NewsBrief> news;

    public NewsResponse(int currentPage, int pageCount) {
        super(currentPage, pageCount);
    }
}
