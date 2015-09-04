/*
Copyright 2015 Nipel-Crumple

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

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
