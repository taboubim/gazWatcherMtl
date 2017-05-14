package com.module.geolocalisation;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

public class PlaceLocator { 
	
	private GoogleMap map_ = null;
	private Geocoder geocoder_ = null;
	
	private Context context_;
	
	
	public PlaceLocator(Context context, GoogleMap map)
	{
		context_ = context;
		map_ = map;
		geocoder_ = new Geocoder(context_, Locale.getDefault());
	}
	
	public LatLng getLatLngFromAddress(String adresse)
	{
		LatLng loc = null;
		Address addresse = getAddressFromString(adresse);
		if (addresse != null)
		{
			loc = new LatLng(addresse.getLatitude(), addresse.getLongitude());
		}		
		return loc;
	}
	
	private Address getAddressFromString(String adresse){
		List<Address> foundAdresses;
		Address adresseTrouvee = null;
		try {
			foundAdresses = geocoder_.getFromLocationName(adresse, 5);
			adresseTrouvee = foundAdresses.get(0);			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}
		return adresseTrouvee;
	}
	
	// Mettre un marker sur la maps a partir d'une adresse
	public boolean putMarker(String adresse)
	{
		LatLng position = getLatLngFromAddress(adresse);
		
		if (position == null)
		{
			return false;
		}
		
		// Marker avec la position trouver a partir d'une adresse
	    map_.addMarker(new MarkerOptions()
	        .position(position)
	        .title("Addresse Marker")
	        .snippet(adresse));
		
		return true;
	}
	
	public boolean putMarker(LatLng position)
	{
		String title = "Location Marker";
		String snippet = String.format("(%f , %f)", position.latitude, position.longitude);
		map_.addMarker(new MarkerOptions()
				.position(position)
				.title(title)
				.snippet(snippet));
		return true;
	}
	
	public boolean putMarker(double latitude, double longitude)
	{
		LatLng position = new LatLng(latitude, longitude);
		String title = "Latitude/Longitude Marker";
		String snippet = String.format("(%f , %f)", latitude, longitude);
		map_.addMarker(new MarkerOptions()
				.position(position)
				.title(title)
				.snippet(snippet));
		return true;
	}
}
