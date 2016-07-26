package onefengma.demo.rx;

import java.math.BigDecimal;

import onefengma.demo.common.NumberUtils;

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

//        DecimalFormat df = new DecimalFormat("0.0");
//        System.out.println("---" + df.format(new BigDecimal(112313.39323)));

//        try(Connection connection = OrderDataHelper.instance().getConn()) {
//            OrderDataHelper.instance().addIntegralByBuy(connection, "527cec6a380046b5b813537e10d065e9", "5afa98c48214438dad364113e3a82ce9", 1323);
//        }
        BigDecimal bd = new BigDecimal(1233.1129134 + "");
        System.out.println("---" + NumberUtils.round(bd, 3));
    }


}
