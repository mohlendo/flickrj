package com.aetrion.flickr.photos;

public class GeoData {

	private float longitude;
	private float latitude;
	private int accuracy;
	
	public GeoData() {
		super();
	}
	
	public GeoData(String longitudeStr, String latitudeStr, String accuracyStr) {
		longitude = Float.parseFloat(longitudeStr);
		latitude = Float.parseFloat(latitudeStr);
		accuracy = Integer.parseInt(accuracyStr);
	}


	public int getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	
	public String toString() {
		return "GeoData[longitude=" + longitude + " latitude=" + latitude + " accuracy=" + accuracy + "]";
	}
	
	

}
