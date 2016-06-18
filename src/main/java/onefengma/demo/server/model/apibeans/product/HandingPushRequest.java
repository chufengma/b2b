package onefengma.demo.server.model.apibeans.product;

import java.io.File;

import onefengma.demo.annotation.NotRequired;
import onefengma.demo.common.FileHelper;
import onefengma.demo.common.IdUtils;
import onefengma.demo.server.model.apibeans.AuthSession;
import onefengma.demo.server.model.product.HandingProduct;

/**
 * Created by chufengma on 16/6/5.
 */
public class HandingPushRequest extends AuthSession {
    public String type;
    public String souCityId;
    public String title;
    public float price;
    public String unit;
    public File cover;
    @NotRequired
    public File image1;
    @NotRequired
    public File image2;
    @NotRequired
    public File image3;

    public HandingProduct generateHandingProduct() {
        HandingProduct handingProduct = new HandingProduct();
        handingProduct.id = IdUtils.id();
        handingProduct.type = type;
        handingProduct.souCityId = souCityId;
        handingProduct.title = title;
        handingProduct.price = price;
        handingProduct.unit = unit;
        handingProduct.cover = FileHelper.generateRelativeInternetUri(cover);
        handingProduct.image1 = FileHelper.generateRelativeInternetUri(image1);
        handingProduct.image2 = FileHelper.generateRelativeInternetUri(image2);
        handingProduct.image3 = FileHelper.generateRelativeInternetUri(image3);
        handingProduct.userId = getUserId();
        return handingProduct;
    }
}
