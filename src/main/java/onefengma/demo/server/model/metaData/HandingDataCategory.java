package onefengma.demo.server.model.metaData;

import java.util.Arrays;
import java.util.List;

/**
 * Created by chufengma on 16/6/5.
 */
public class HandingDataCategory {
    
    public List<String> handingTypes = Arrays.asList("整卷油磨",
            "中厚板镜面抛光",
            "中厚板油磨",
            "冲花板",
            "压花板",
            "抗指纹",
            "平板油磨拉丝",
            "平板干磨短丝",
            "普通拉丝",
            "普通镜面",
            "卷圆焊接",
            "剪折弯",
            "激光切割",
            "水刀切割",
            "超宽板拉丝",
            "不锈钢非标准件定制",
            "镀色板",
            "和纹板");

    public List<String> units = Arrays.asList("吨", "平方米", "米");

    private static HandingDataCategory handingDataCategory;

    public static HandingDataCategory get() {
        if (handingDataCategory == null) {
            handingDataCategory = new HandingDataCategory();
        }
        return handingDataCategory;
    }

}
