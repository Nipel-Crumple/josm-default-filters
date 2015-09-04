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

import javax.json.Json;
import javax.json.JsonObject;

import com.jhlabs.image.ContrastFilter;

public class ContrastFilterImpl implements Filter {
	private ContrastFilter contrastBrightness = new ContrastFilter();
	private float contrast = 1.0f, brightness = 1.0f;
	private UID id;

	@Override
	public JsonObject changeFilterState(JsonObject filterState){

		JsonObject contrastJson = filterState.getJsonObject("contrast");
		setContrast((float) contrastJson.getJsonNumber("value").doubleValue());

		JsonObject brightnessJson = filterState.getJsonObject("brightness");
		setBrightness((float) brightnessJson.getJsonNumber("value").doubleValue());

		return filterState;
	}

	@Override
	public BufferedImage applyFilter(BufferedImage img) {

		contrastBrightness.setBrightness(brightness);
		contrastBrightness.setContrast(contrast);

		img = contrastBrightness.filter(img, null);

		return img;
	}

	@Override
	public String toString() {
		JsonObject json = Json
				.createObjectBuilder()
				.add("contrast",
						Json.createObjectBuilder().add("value", contrast)
						.build())
						.add("brightness",
								Json.createObjectBuilder().add("value", brightness)
								.build())
								.build();
		return "from contrast: \n" + json.toString();
	}

	@Override
	public void setId(UID id) {
		this.id = id;
	}

	@Override
	public UID getId() {
		return id;
	}

	public float getContrast() {
		return contrast;
	}

	public void setContrast(float contrast) {
		this.contrast = contrast;
	}

	public float getBrightness() {
		return brightness;
	}

	public void setBrightness(float brightness) {
		this.brightness = brightness;
	}

}
