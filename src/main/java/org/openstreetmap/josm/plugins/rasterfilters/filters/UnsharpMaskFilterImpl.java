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

import com.jhlabs.image.UnsharpFilter;
import org.openstreetmap.josm.Main;

import javax.json.Json;
import javax.json.JsonObject;
import java.awt.image.BufferedImage;
import java.rmi.server.UID;

public class UnsharpMaskFilterImpl implements Filter {

	private UnsharpFilter unsharp = new UnsharpFilter();
	private float amount;
	private int threshold;
	private int radius;
	private UID id;

	@Override
	public JsonObject changeFilterState(JsonObject newFilterState) {
		if (newFilterState != null) {

			// new value of amount
			JsonObject amountJson = newFilterState.getJsonObject("amount");
			setAmount((float) amountJson.getJsonNumber("value").doubleValue());

			JsonObject sizeJson = newFilterState.getJsonObject("radius");
			setRadius(sizeJson.getJsonNumber("value").intValue());

			JsonObject thresholdJson = newFilterState.getJsonObject("threshold");
			setThreshold(thresholdJson.getJsonNumber("value").intValue());

			Main.debug(id.toString() + " \n" + toString());

			return newFilterState;
		}

		return null;
	}

	@Override
	public BufferedImage applyFilter(BufferedImage img) {
		unsharp.setAmount(amount);
		unsharp.setRadius(radius);
		unsharp.setThreshold(threshold);
		return unsharp.filter(img, null);
	}

	@Override
	public void setId(UID id) {
		this.id = id;
	}

	@Override
	public UID getId() {
		return id;
	}

	@Override
	public String toString() {
		JsonObject json = Json
				.createObjectBuilder()
				.add("amount",
						Json.createObjectBuilder().add("value", amount).build())
				.add("radius",
						Json.createObjectBuilder().add("value", radius).build())
				.add("threshold",
						Json.createObjectBuilder().add("value", threshold).build())
				.build();
		return "from unsharp: \n" + json.toString();
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}


	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}

	public int getThreshold() {
		return threshold;
	}
}
