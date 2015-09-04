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

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.rmi.server.UID;

import javax.json.JsonObject;

import com.jhlabs.image.ChromaKeyFilter;

public class ColorRangeFilterImpl implements Filter {

	private ChromaKeyFilter chrKey = new ChromaKeyFilter();

	private double hTolerance = 0;
	private double sTolerance = 0;
	private double bTolerance = 0;
	private UID id;

	private int color;

	@Override
	public JsonObject changeFilterState(JsonObject filterState) {

		JsonObject hueJson = filterState.getJsonObject("hue");
		hTolerance = hueJson.getJsonNumber("value").doubleValue();

		JsonObject saturationJson = filterState.getJsonObject("saturation");
		sTolerance = saturationJson.getJsonNumber("value").doubleValue();

		JsonObject brightnessJson = filterState.getJsonObject("brightness");
		bTolerance = brightnessJson.getJsonNumber("value").doubleValue();

		JsonObject colorJson = filterState.getJsonObject("color").getJsonObject("value");
		int r = colorJson.getInt("red");
		int g = colorJson.getInt("green");
		int b = colorJson.getInt("blue");
		color = new Color(r, g, b).getRGB();

		return filterState;

	}

	@Override
	public BufferedImage applyFilter(BufferedImage img) {
		chrKey.setHTolerance((float) hTolerance);
		chrKey.setSTolerance((float) sTolerance);
		chrKey.setBTolerance((float) bTolerance);
		chrKey.setColor(color);

		return chrKey.filter(img, null);
	}

	@Override
	public void setId(UID id) {
		this.id = id;
	}

	@Override
	public UID getId() {
		return this.id;
	}
}
