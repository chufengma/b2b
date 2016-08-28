package onefengma.demo.server.model;

import onefengma.demo.common.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by chufengma on 16/8/28.
 */
public class SubscribeInfo {
    public String userId;
    public String[] types;
    public String[] surfaces;
    public String[] materials;
    public String[] proPlaces;

    public void parse(String types, String surfaces, String materials, String proPlaces) {
        if (!StringUtils.isEmpty(types)) {
            this.types = types.split(",");
        }
        if (!StringUtils.isEmpty(surfaces)) {
            this.surfaces = surfaces.split(",");
        }
        if (!StringUtils.isEmpty(materials)) {
            this.materials = materials.split(",");
        }
        if (!StringUtils.isEmpty(proPlaces)) {
            this.proPlaces = proPlaces.split(",");
        }
    }
}
