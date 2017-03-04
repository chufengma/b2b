package onefengma.demo.rx;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yfchu
 * @date 2016/5/19
 */

public class RxDemo {

    public static void main(String[] args) {

        Map<String, List<String>> data = new HashMap<>();

        data.put("test", Arrays.asList("sss", "sdfsdf", "asdfasdf"));
        data.put("test2", Arrays.asList("sss1", "sdfsdf1", "asdfasdf1"));
        data.put("test3", Arrays.asList("sss3", "sdfsdf2", "asdfasdf2"));
        data.put("test4", Arrays.asList("sss4", "sdfsdf3", "asdfasdf3"));

        String jsonData = JSON.toJSONString(data);
        JSONObject jsonObject = JSONObject.parseObject(jsonData);
        Map<String, Object> map = JSON.parseObject(jsonObject.toJSONString(), new TypeReference<Map<String, Object>>() {});

        System.out.println(map.size());

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
