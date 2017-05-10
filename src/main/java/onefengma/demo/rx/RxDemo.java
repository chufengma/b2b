package onefengma.demo.rx;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import onefengma.demo.common.VerifyUtils;

import java.math.BigDecimal;
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
//        String data = "9999.98";
//        BigDecimal bg = new BigDecimal(data);
//        System.out.println("========" + bg.setScale(1, BigDecimal.ROUND_DOWN).toString());
                System.out.println("========" + VerifyUtils.isPhone("051081812186"));
    }

}
