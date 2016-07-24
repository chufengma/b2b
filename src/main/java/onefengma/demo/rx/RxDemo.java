package onefengma.demo.rx;

import com.alibaba.fastjson.JSON;

import com.tinify.Options;
import com.tinify.Source;
import com.tinify.Tinify;
import onefengma.demo.server.core.LogUtils;
import rx.Observable;
import rx.functions.Action1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author yfchu
 * @date 2016/5/19
 */

public class RxDemo {

    public static void main(String[] args) {
        Tinify.setKey("wMGvp7X-Sk02pI1vkouapzIKt3_6m84j");
        try {
            Source source = Tinify.fromBuffer(Files.readAllBytes(Paths.get("/Users/chufengma/projects/web/b2b/res/files/2016/7/24/Wkmvdz6NUJtm.png")));
            Options options = new Options()
                    .with("method", "scale")
                    .with("width", 450);
            Source resized = source.resize(options);
            resized.toFile("/Users/chufengma/projects/web/b2b/res/files/2016/7/24/Wkmvdz6NUJtm.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
