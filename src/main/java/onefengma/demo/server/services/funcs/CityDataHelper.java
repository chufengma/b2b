package onefengma.demo.server.services.funcs;

import org.sql2o.Connection;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import onefengma.demo.common.StringUtils;
import onefengma.demo.server.core.BaseDataHelper;
import onefengma.demo.server.model.metaData.City;

/**
 * Created by chufengma on 16/6/3.
 */
public class CityDataHelper extends BaseDataHelper {

    private static CityDataHelper cityDataHelper;

    public static CityDataHelper instance() {
        if (cityDataHelper == null) {
            cityDataHelper = new CityDataHelper();
        }
        return cityDataHelper;
    }

    public void saveCities(List<City> cities) throws NoSuchFieldException, IllegalAccessException {
        String sql = createSql("insert into city(id, fatherId, name, type) values (:id, :fatherId, :name, :type)");
        try(Connection conn = getConn()) {
            for (City city : cities) {
                conn.createQuery(sql).addParameter("id", city.id)
                .addParameter("fatherId", city.fatherId)
                .addParameter("name", city.name)
                .addParameter("type", city.type)
                .executeUpdate();
            }
        }
    }

    public City getCityById(String cityId) throws NoSuchFieldException, IllegalAccessException {
        String sql = createSql("select * from city where id=:id");
        try(Connection connection = getConn()) {
            List<City> cities = connection.createQuery(sql).addParameter("id", cityId).executeAndFetch(City.class);
            if (cities.isEmpty()) {
                return null;
            } else {
                return cities.get(0);
            }
        }
    }

    public String getCityDescById(String cityID) throws NoSuchFieldException, IllegalAccessException {
        City currentCity = getCityById(cityID);
        Stack<String> stringStack = new Stack<>();
        stringStack.push(currentCity.name);
        while (!StringUtils.isEmpty(currentCity.fatherId)) {
            currentCity = getCityById(currentCity.fatherId);
            stringStack.push(currentCity.name);
        }
        StringBuffer cityDescBuilder = new StringBuffer();
        while(!stringStack.isEmpty()) {
            cityDescBuilder.append(stringStack.pop() + " ");
        }
        return cityDescBuilder.toString();
    }

    public List<String> getCitiesById(List<String> cities, String cityId) throws NoSuchFieldException, IllegalAccessException {
        if (StringUtils.isEmpty(cityId)) {
            return cities;
        }
        cities.add(cityId);
        City city = getCityById(cityId);
        List<City> subCityes = getSubCities(city.id);
        if (city != null &&  subCityes != null) {
            for(City subCity : subCityes) {
                getCitiesById(cities, subCity.id);
            }
        }
        return cities;
    }

    public List<City> getSubCities(String cityId) throws NoSuchFieldException, IllegalAccessException {
        String sql = createSql("select * from city where fatherId=:id");
        try(Connection connection = getConn()) {
            return connection.createQuery(sql).addParameter("id", cityId).executeAndFetch(City.class);
        }
    }

    public boolean isCityExist(String cityId) throws NoSuchFieldException, IllegalAccessException {
        return getCityById(cityId) != null;
    }

}
