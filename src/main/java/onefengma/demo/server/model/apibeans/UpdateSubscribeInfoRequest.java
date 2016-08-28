package onefengma.demo.server.model.apibeans;

import onefengma.demo.annotation.NotRequired;

import java.io.File;

/**
 * Created by chufengma on 16/7/9.
 */
public class UpdateSubscribeInfoRequest extends AuthSession {
    @NotRequired
    public String types;
    @NotRequired
    public String surfaces;
    @NotRequired
    public String materials;
    @NotRequired
    public String proPlaces;
}
