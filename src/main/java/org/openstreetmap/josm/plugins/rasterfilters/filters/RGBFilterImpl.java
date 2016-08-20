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

import com.jhlabs.image.RGBAdjustFilter;
import org.openstreetmap.josm.Main;

import javax.json.Json;
import javax.json.JsonObject;
import java.awt.image.BufferedImage;
import java.rmi.server.UID;

public class RGBFilterImpl implements Filter {

	private RGBAdjustFilter mix = new RGBAdjustFilter();
	private float red, green, blue;
	private UID id;

	@Override
	public BufferedImage applyFilter(BufferedImage img) {
		mix.setRFactor(red);
		mix.setGFactor(green);
		mix.setBFactor(blue);
		return mix.filter(img, null);
	}

	@Override
	public String toString() {
		JsonObject json = Json
				.createObjectBuilder()
				.add("red",
						Json.createObjectBuilder()
						.add("value", red)
						.build())
						.add("green", Json.createObjectBuilder()
								.add("value", green)
								.build())
								.add("blue",Json.createObjectBuilder()
										.add("value", blue)
										.build())
										.build();
		return "from channelMix: \n" + json.toString();
	}

	@Override
	public JsonObject changeFilterState(JsonObject filterState) {

		if (filterState != null) {

			// new value of rgb params
			JsonObject redJson = filterState.getJsonObject("red");
			setRed((float) redJson.getJsonNumber("value").doubleValue());

			JsonObject greenJson = filterState.getJsonObject("green");
			setGreen((float) greenJson.getJsonNumber("value").doubleValue());

			JsonObject blueJson = filterState.getJsonObject("blue");
			setBlue((float) blueJson.getJsonNumber("value").doubleValue());

			Main.debug(id.toString() + " \n" + toString());

			return filterState;
		}

		return null;
	}

	@Override
	public void setId(UID id) {
		this.id = id;
	}

	@Override
	public UID getId() {
		return id;
	}

	public void setBlue(float blue) {
		this.blue = blue;
	}

	public void setGreen(float green) {
		this.green = green;
	}

	public void setRed(float red) {
		this.red = red;
	}

}
