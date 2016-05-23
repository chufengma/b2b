package onefengma.demo.common;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * @author yfchu
 * @date 2016/5/20
 */
public class FastJsonConverters extends Converter.Factory {

    public FastJsonConverters() {
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new FastJsonResponseConverter<>(type);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return new FastJsonRequestConverter();
    }

    @Override
    public Converter<?, String> stringConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return StringConverter.INSTANCE;
    }

    static class FastJsonResponseConverter<T> implements Converter<ResponseBody, T> {

        Type type;

        FastJsonResponseConverter(Type type) {
            this.type = type;
        }

        @Override
        public T convert(ResponseBody value) {
            try {
                return JSON.parseObject(value.bytes(), type);
            } catch (IOException e) {
                return null;
            }
        }
    }

    static class FastJsonRequestConverter implements Converter<RequestBody, RequestBody> {
        @Override
        public RequestBody convert(RequestBody value) throws IOException {
            return value;
        }
    }

    static final class StringConverter implements Converter<String, String> {
        static final StringConverter INSTANCE = new StringConverter();

        @Override public String convert(String value) throws IOException {
            return value;
        }
    }
}
