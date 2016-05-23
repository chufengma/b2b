package onefengma.demo.rx;

import onefengma.demo.server.core.LogUtils;
import onefengma.demo.model.SimpleStr;
import retrofit2.Call;
import retrofit2.http.POST;
import rx.Observable;
import rx.functions.Action1;

/**
 * @author yfchu
 * @date 2016/5/19
 */

public class RxDemo {

    public static void main(String[] args) {
        Observable.just(1,2,3,4,5).toBlocking().forEach(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                LogUtils.i(integer + 12 + "");
            }
        });
    }

    public interface ServerService {
        @POST("file")
        public Call<SimpleStr> getDemoStr();
    }


}
