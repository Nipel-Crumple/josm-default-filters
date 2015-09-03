package org.openstreetmap.josm.plugins.rasterfilters.filters;

import java.awt.image.BufferedImage;
import java.rmi.server.UID;

import javax.json.JsonObject;

import com.jhlabs.image.SaturationFilter;

public class SaturateFilterImpl implements Filter {

	private SaturationFilter saturate = new SaturationFilter();
	private float amount;
	private UID id;

	@Override
	public JsonObject changeFilterState(JsonObject filterState) {

		JsonObject jsonDesaturate = filterState.getJsonObject("saturate");
		amount = (float) jsonDesaturate.getJsonNumber("value").doubleValue();

		return filterState;
	}

	@Override
	public BufferedImage applyFilter(BufferedImage img) {

		saturate.setAmount(amount);
		img = saturate.filter(img, null);

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
