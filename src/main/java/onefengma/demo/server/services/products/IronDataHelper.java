package onefengma.demo.server.services.products;

import org.sql2o.Connection;

import java.util.List;

import onefengma.demo.server.core.BaseDataHelper;
import onefengma.demo.server.model.product.IronProduct;

/**
 * @author yfchu
 * @date 2016/6/1
 */
public class IronDataHelper extends BaseDataHelper {

    private static IronDataHelper ironDataHelper;

    public static IronDataHelper getIronDataHelper() {
        if (ironDataHelper == null) {
            ironDataHelper = new IronDataHelper();
        }
        return ironDataHelper;
    }

    public int getMaxCounts() {
        String sql = "select count(id) from iron_product;";
        try(Connection conn = getConn()) {
            int count = conn.createQuery(sql).executeScalar(Integer.class);
            return count;
        }
    }

    public List<IronProduct>  getIronProducts(int currentPage, int pageCount) {
        if (pageCount <=0 || pageCount >= 300) {
            pageCount = 300;
        }
        String sql = "select * from iron_product limit :start, :count";
        try (Connection conn = getConn()){
            return conn.createQuery(sql).addParameter("start", currentPage * pageCount)
                                 .addParameter("count", pageCount)
            .executeAndFetch(IronProduct.class);
        }
    }

    public void pushIronProduct(IronProduct ironProduct) {
        String sql = "insert into iron_product(userId, proId, surface, ironType, proPlace, material, sourceCityId, title, price, cover, image1, image2, image3, isSpec) " +
                "values (:userId, :proId,:surface,:ironType,:proPlace,:material,:sourceCityId,:title,:price,:cover,:image1,:image2,:image3,:isSpec)";
        try(Connection conn = getConn()) {
            conn.createQuery(sql).addParameter("proId", ironProduct.proId)
                    .addParameter("userId", ironProduct.userId)
                    .addParameter("surface", ironProduct.surface)
                    .addParameter("ironType", ironProduct.ironType)
                    .addParameter("proPlace", ironProduct.proPlace)
                    .addParameter("material", ironProduct.material)
                    .addParameter("sourceCityId", ironProduct.sourceCityId)
                    .addParameter("title", ironProduct.title)
                    .addParameter("price", ironProduct.price)
                    .addParameter("cover", ironProduct.cover)
                    .addParameter("image1", ironProduct.image1)
                    .addParameter("image2", ironProduct.image2)
                    .addParameter("image3", ironProduct.image3)
                    .addParameter("isSpec", ironProduct.isSpec)
                    .executeUpdate();
        }
    }

}
