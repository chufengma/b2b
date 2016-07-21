package onefengma.demo.server.model.metaData;

import java.util.Arrays;
import java.util.List;

/**
 * @author yfchu
 * @date 2016/6/1
 */
public class IconDataCategory {

    public List<String> surfaces = Arrays.asList("No.1", "2B", "BA", "2D", "彩色", "卫生级", "装饰", "酸白", "冷拉", "其他");

    public List<String> materials = Arrays.asList("201(L1,L2)", "202(L3,L4)", "304J1", "304(30408)", "304L(30403)", "321(32168)", "316L(31603)", "2205(S22053)", "309S", "310S", "904L", "409L", "430", "410S", "444", "301", "2507", "其他");

    public List<String> types = Arrays.asList("不锈钢卷", "不锈钢板", "不锈钢管", "矩形管", "角钢", "扁钢", "圆钢(光圆)", "圆钢(毛圆)", "“工”字钢", "钢带", "弯头", "法兰", "螺丝/螺母等配件",
            "焊条", "钢丝", "六角棒","管件（三通、四通、变径、大小头、接头", "阀门", "方钢", "槽钢", "复合板", "铸件", "锻件", "封头（管帽)", "其他");

    public List<String> productPlaces = Arrays.asList("太钢", "天管", "酒钢", "泰山钢铁", "宝钢", "东特", "广青", "福欣", "张浦", "联众",
            "诚德", "鼎信", "飞达", "上克", "青浦", "宝新", "甬金", "压延", "金汇", "宏旺", "新行健", "建恒", "山东澳星", "戴南", "远东", "江苏", "浙江", "广东", "其他");

    public List<String> units = Arrays.asList("kg", "个", "T", "平方", "片", "支", "千支", "条","捆");

    private static IconDataCategory iconDataCategory;

    public static IconDataCategory get() {
        if (iconDataCategory == null) {
            iconDataCategory = new IconDataCategory();
        }
        return iconDataCategory;
    }

}
