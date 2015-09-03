package org.openstreetmap.josm.plugins.rasterfilters.filters;

import java.awt.image.BufferedImage;
import java.rmi.server.UID;

import javax.json.Json;
import javax.json.JsonObject;

import org.openstreetmap.josm.Main;

import com.jhlabs.image.RGBAdjustFilter;

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
