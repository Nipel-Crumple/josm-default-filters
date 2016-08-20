package org.openstreetmap.josm.plugins.rasterfilters.filters;

import com.jhlabs.image.InvertFilter;

import javax.json.JsonObject;
import java.awt.image.BufferedImage;
import java.rmi.server.UID;

/**
 * Created by Nipel-Crumple on 19.08.2016.
 */
public class InvertFilterImpl implements Filter {

    private InvertFilter invertFilter = new InvertFilter();

    private UID id;
    private boolean isInvertedColor;

    @Override
    public JsonObject changeFilterState(JsonObject filterState) {
        JsonObject invJson = filterState.getJsonObject("invert");
        isInvertedColor = invJson.getBoolean("value");
        return null;
    }

    @Override
    public BufferedImage applyFilter(BufferedImage img) {
        if (isInvertedColor)
            return invertFilter.filter(img, null);
        return img;
    }

    @Override
    public void setId(UID id) {
        this.id = id;
    }

    @Override
    public UID getId() {
        return id;
    }
}
