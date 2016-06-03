package onefengma.demo.server.services.funcs;

import org.sql2o.Connection;

import java.util.List;

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

}
