package onefengma.demo.rx;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author yfchu
 * @date 2016/5/19
 */

public class RxDemo {

    public static void main(String[] args) {
//        Tinify.setKey("wMGvp7X-Sk02pI1vkouapzIKt3_6m84j");
//        try {
//            Source source = Tinify.fromBuffer(Files.readAllBytes(Paths.get("/Users/chufengma/projects/web/b2b/res/files/2016/7/24/Wkmvdz6NUJtm.png")));
//            Options options = new Options()
//                    .with("method", "scale")
//                    .with("width", 450);
//            Source resized = source.resize(options);
//            resized.toFile("/Users/chufengma/projects/web/b2b/res/files/2016/7/24/Wkmvdz6NUJtm.png");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        DecimalFormat df = new DecimalFormat("0.0");
        System.out.println("---" + df.format(new BigDecimal(112313.39323)));
    }


}
