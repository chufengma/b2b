package onefengma.demo.server.model.apibeans.product;

import org.apache.commons.io.FileUtils;

import java.io.File;

import onefengma.demo.annotation.NotRequired;
import onefengma.demo.common.FileHelper;
import onefengma.demo.common.IdUtils;
import onefengma.demo.server.model.apibeans.AuthSession;
import onefengma.demo.server.model.product.IronProduct;

/**
 * Created by chufengma on 16/6/4.
 */
public class IronPushRequest extends AuthSession {

    public String surface;
    public String ironType;
    public String proPlace;
    public String material;
    public String sourceCityId;
    public String title;
    public float price;
    public File cover;
    @NotRequired
    public File image1;
    @NotRequired
    public File image2;
    @NotRequired
    public File image3;
    public boolean isSpec;

    public IronProduct generateIconProduct() {
        IronProduct ironProduct = new IronProduct();
        ironProduct.userId = getUserId();
        ironProduct.proId = IdUtils.id();
        ironProduct.ironType = ironType;
        ironProduct.proPlace = proPlace;
        ironProduct.material = material;
        ironProduct.sourceCityId = sourceCityId;
        ironProduct.title = title;
        ironProduct.price = price;
        ironProduct.cover = FileHelper.generateRelativeInternetUri(cover.getPath());
        ironProduct.surface = surface;
        ironProduct.isSpec = isSpec;
        if (image1 != null) {
            ironProduct.image1 = FileHelper.generateRelativeInternetUri(image1.getPath());
        }
        if (image2 != null) {
            ironProduct.image2 = FileHelper.generateRelativeInternetUri(image2.getPath());
        }
        if (image3 != null) {
            ironProduct.image3 = FileHelper.generateRelativeInternetUri(image3.getPath());
        }
        return ironProduct;
    }

}