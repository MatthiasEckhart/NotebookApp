package at.fhj.moappdev.n073b00k.helpers;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import at.fhj.moappdev.n073b00k.R;

/**
 * This class is used to track the GPS position of the user's phone.
 */
public class LocationTracker extends Service implements LocationListener {

    /**
     * The application's environment.
     */
    private final Context mContext;

    /**
     * Indicates if we can get the user's position.
     */
    private boolean isLocationAvailable = false;

    /**
     * The user's geographical location.
     */
    private Location location;

    /**
     * The latitude of the user's location.
     */
    private double latitude;

    /**
     * The longitude of the user's location.
     */
    private double longitude;

    /**
     * The minimum distance to change for updates in meters.
     * This constant is set to 15 meters.
     */
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 15;

    /**
     * The minimum time between updates in milliseconds.
     * This constant is set to 1 minute.
     */
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60;

    /**
     * This object provides access to the system location services.
     */
    protected LocationManager locationManager;

    /**
     * Calls {@code getLocation} to retrieve the user's location.
     *
     * @param context the application's environment
     */
    public LocationTracker(Context context) {
        this.mContext = context;
        getLocation();
    }

    /**
     * This method is used to retrieve the user's location.
     *
     * @return the user's geographical location
     */
    public Location getLocation() {
        try {
            locationManager = (LocationManager) mContext
                    .getSystemService(LOCATION_SERVICE);

            /* Check if the user has GPS enabled. */
            boolean isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            /* Check if the user has network enabled. */
            boolean isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            /* Check if the user has GPS or network enabled. */
            if (isGPSEnabled || isNetworkEnabled) {
                this.isLocationAvailable = true;
                /* If the user has network enabled, we request location updates through the network provider. */
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.i(LocationTracker.class.getName(), "Retrieving the user's geographical location through the network provider.");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        /* If location is not null, set latitude and longitude. */
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                /* If GPS is enabled, we retrieve the latitude and longitude by using the phone's GPS services. */
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.i(LocationTracker.class.getName(), "Retrieving the user's geographical location through the GPS provider.");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            /* If location is not null, set latitude and longitude. */
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(LocationTracker.class.getName(), "Cannot get the user's geographical location.");
        }
        return location;
    }


    /**
     * This method stops the GPS listener.
     * If you call this method, the {@code LocationTracker} will stop using GPS to track the user's geographical location.
     */
    public void stopUsingGPS() {
        if (locationManager != null) locationManager.removeUpdates(LocationTracker.this);
    }


    /**
     * Used to get the location's latitude.
     *
     * @return the location's latitude
     */
    public double getLatitude() {
        if (location != null) latitude = location.getLatitude();
        return latitude;
    }


    /**
     * Used to get the location's longitude.
     *
     * @return the location's longitude
     */
    public double getLongitude() {
        if (location != null) longitude = location.getLongitude();
        return longitude;
    }

    /**
     * Used to check if the user has GPS or network enabled.
     *
     * @return boolean {@code true} if either GPS or network is enabled
     */
    public boolean canGetLocation() {
        return this.isLocationAvailable;
    }


    /**
     * This method displays an alert dialog asking the user if she wants to go to the settings
     * in order to activate the location services.
     */
    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        /* Set the settings dialog title. */
        alertDialog.setTitle(getResources().getString(R.string.location_tracker_alert_dialog_settings_title));
        /* Set the settings dialog message. */
        alertDialog.setMessage(getResources().getString(R.string.location_tracker_alert_dialog_settings_content));
        /* On pressing the settings button, start the respective activity. */
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(settingsIntent);
            }
        });
        /* Exit this alert dialog if the cancel button has been pressed. */
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        /* Show the alert dialog. */
        alertDialog.show();
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}