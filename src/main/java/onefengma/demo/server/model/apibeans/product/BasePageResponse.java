package onefengma.demo.server.model.apibeans.product;

/**
 * Created by chufengma on 16/6/5.
 */
public class BasePageResponse {
    public int maxCount;
    public int currentPage;
    public int pageCount;

    public BasePageResponse(int currentPage, int pageCount) {
        this.currentPage = currentPage;
        this.pageCount = pageCount;
    }
}
