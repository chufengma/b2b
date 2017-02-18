package onefengma.demo.rx;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.http.*;
import rx.Observable;
import rx.functions.Action1;

import java.io.File;


/**
 * @author yfchu
 * @date 2016/5/19
 */

public class RxDemo {

    public static void main(String[] args) {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://localhost:9090")
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        retrofit.create(DemoService.class).test("Test").subscribe(new Action1<Data>() {
//            @Override
//            public void call(Data s) {
//                System.out.println("-----" + s.data);
//            }
//        });
//
//        // 创建 RequestBody，用于封装 请求RequestBody
//        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), "test.png");
//        MultipartBody.Part body = MultipartBody.Part.createFormData("image", "test.png", requestFile);
//        retrofit.create(DemoService.class).update(body).subscribe(new Action1<Data>() {
//            @Override
//            public void call(Data data) {
//                System.out.println("-----" + data.data);
//            }
//        });
    }

//    public interface DemoService {
//        @GET("/test")
//        Observable<Data> test(@Query("id") String id);
//
//        @Multipart
//        @POST("imageUpload")
//        Observable<Data> update(@Part MultipartBody.Part image);
//    }
//
//    public static class Data {
//        public String errorMsg;
//        public int status;
//        public String data;
//    }

}
