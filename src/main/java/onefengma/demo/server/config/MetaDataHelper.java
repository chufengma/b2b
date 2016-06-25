package onefengma.demo.server.config;

import com.alibaba.fastjson.JSON;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import onefengma.demo.server.model.metaData.IconDataCategory;
import onefengma.demo.server.model.metaData.City;
import onefengma.demo.server.core.LogUtils;
import onefengma.demo.server.services.funcs.CityDataHelper;
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
            String cityFiles = FileUtils.readFileToString(new File(Config.getBaseMetaPath() + "citys.json"), "utf-8");
            List<City> cities = JSON.parseArray(cityFiles, City.class);
            return cities;
        } catch (Exception e) {
            LogUtils.e(e, "error when load cities");
            return Arrays.asList();
        }
    }

    public static void sa(List<City> cities) throws NoSuchFieldException, IllegalAccessException {
        if (cities == null) {
            return;
        }
        CityDataHelper.instance().saveCities(cities);
        for(City city1 : cities) {
            sa(city1.sub);
        }
    }

    public static void rt(City city) {
        if (city.sub == null) {
            return;
        }
        for(City city1 : city.sub) {
            city1.fatherId = city.id;
            rt(city1);
        }
    }

    public static List<City> getCities() {
        return CITIES;
    }
}
