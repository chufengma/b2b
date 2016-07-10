package onefengma.demo.server.model.innermessage;

import onefengma.demo.server.model.apibeans.product.BasePageResponse;
import onefengma.demo.server.services.funcs.InnerMessageDataHelper;
import onefengma.demo.server.services.funcs.InnerMessageDataHelper.InnerMessageBrief;

import java.util.List;

/**
 * Created by chufengma on 16/7/10.
 */
public class InnerMessagesResponse extends BasePageResponse {

    public List<InnerMessageBrief> messages;

    public InnerMessagesResponse(int currentPage, int pageCount) {
        super(currentPage, pageCount);
    }
}
