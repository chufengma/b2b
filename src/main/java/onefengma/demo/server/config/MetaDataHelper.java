package onefengma.demo.server.config;

import com.alibaba.fastjson.JSON;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import onefengma.demo.server.model.metaData.IconDataCategory;
import onefengma.demo.server.model.metaData.City;
import onefengma.demo.server.core.LogUtils;
import spark.Spark;

/**
 * @author yfchu
 * @date 2016/6/1
 */
public class MetaDataHelper {

    private static final IconDataCategory ICON_DATA_CATEGORY = new IconDataCategory();
    private static List<City> CITIES = initCities();

    public static IconDataCategory getIconDataCategory() {
        return ICON_DATA_CATEGORY;
    }

    private static List<City> initCities() {
        try {
            String cityFiles = FileUtils.readFileToString(new File(Config.getBaseMetaPath() + "citys.json"));
            return JSON.parseArray(cityFiles, City.class);
        } catch (Exception e) {
            LogUtils.e(e, "error when load cities");
            Spark.stop();
            return Arrays.asList();
        }
    }

    public static List<City> getCities() {
        return CITIES;
    }
}
