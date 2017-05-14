package com.module.geolocalisation;

import java.util.Observable;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class GeoLocationListener extends Observable implements LocationListener{
	static final String UPDATE_LOCATION = "location_updated";
	static final String UPDATE_PROVIDER_DISABLED = "provider_disabled";
	
	private Context context_ = null;
	
	private LocationManager locationManager_ = null;
	private Criteria criteria_ = null;
	private String provider_ = "";
	long minTimeUpdate_ = 400;
	float minDistanceUpdate_ = 1;
	
	private Location location_ = null; 
	
	public GeoLocationListener(Context context){
		context_ = context;
		locationManager_ = (LocationManager) context_.getSystemService(Context.LOCATION_SERVICE);
		refreshCriteria();
		refreshProvider();
	}
	
	public void pauseGeolocalisation()
	{
		locationManager_.removeUpdates(this);
	}
	
	public void resumeGeolocalisation()
	{
		locationManager_.requestLocationUpdates(provider_, minTimeUpdate_, minDistanceUpdate_, this);
	}
	
	public Location getLocation()
	{
		if (location_ == null)
		{
			location_ = locationManager_.getLastKnownLocation(provider_);
		}
		return location_;
	}
	
	public double getLatitude()
	{
		if (location_ == null)
		{
			getLocation();
		}
		
		if (location_ != null)
		{
			return location_.getLatitude();
		}
		
		return -1;
	}
	
	public double getLongitude()
	{
		if (location_ == null)
		{
			getLocation();
		}
		
		if (location_ != null)
		{
			return location_.getLongitude();
		}
		
		return -1;
	}

	@Override
	public void onLocationChanged(Location loc) {
		// TODO Auto-generated method stub
		location_ = loc;
		setChanged();
		notifyObservers(UPDATE_LOCATION);
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		setChanged();
		notifyObservers(UPDATE_PROVIDER_DISABLED);
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	
	public void refreshProvider()
	{
		provider_ = locationManager_.getBestProvider(criteria_, true);
	}
	
	public void refreshCriteria()
	{
		criteria_ = getPreferenceCriteria();
	}
	
	private Criteria getPreferenceCriteria()
	{
		Criteria crit = new Criteria();

		// Quelle precision avons-nous besoin?
		crit.setAccuracy(Criteria.ACCURACY_FINE);
		
		// Doit-on connaitre l'altitude?
		crit.setAltitudeRequired(false);
		
		// Doit-on fournir une direction?
		crit.setBearingRequired(false);
		
		// Doit-on fournir une vitesse?
		crit.setSpeedRequired(false);
		
		// Le provider peut-il etre payant
		crit.setCostAllowed(false);
		
		// Quelle consommation d'energie on prefere
		crit.setPowerRequirement(Criteria.POWER_HIGH);
		
		return crit;
	}


}