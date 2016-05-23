package onefengma.demo.common;

import retrofit2.Retrofit;

/**
 * @author yfchu
 * @date 2016/5/20
 */
public class HttpHelper {

    private static final String BASE_URL = "http://localhost:4567/";
    private static Retrofit retrofit;

    private static Retrofit getRetrofit() {
        if (retrofit == null) {
            synchronized (BASE_URL) {
                retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(new FastJsonConverters()).build();
        }
        }
        return retrofit;
    }

    public static <T> T getService(Class<T> serviceInterface) {
        return  getRetrofit().create(serviceInterface);
    }

}
