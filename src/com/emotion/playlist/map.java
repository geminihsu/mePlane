package com.emotion.playlist;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List; 
import java.util.Locale; 

import android.content.Context; 
import android.content.Intent; 
import android.location.Address; 
import android.location.Criteria; 
import android.location.Geocoder; 
import android.location.Location; 
import android.location.LocationListener; 
import android.location.LocationManager; 
import android.net.Uri; 
import android.os.Bundle; 
import android.view.Menu;
import android.view.MenuItem;
import android.view.View; 
import android.widget.Button; 
import android.widget.EditText; 
import android.widget.TextView; 
import android.widget.Toast;

import com.google.android.maps.GeoPoint; 
import com.google.android.maps.MapActivity; 
import com.google.android.maps.MapController; 
import com.google.android.maps.MapView; 

public class map extends MapActivity implements LocationListener{
  public static Integer i;
  private TextView mTextView; 
  private LocationManager mLocationManager; 
  private String strLocationProvider = ""; 
  public static String login;
  private Location mLocation; 
  private MapController mapController; 
  private MapView mapview; 
  private Button large,small;  
  private int intZoomLevel=0; 
  private GeoPoint fromGeoPoint;  
  public static String located;
 protected static final int playlist = Menu.FIRST; 
 protected static final int main = Menu.FIRST+1; 
 private LocationManager mgr;
 String best;
  @Override 
  protected void onCreate(Bundle icicle) 
  { 
    // TODO Auto-generated method stub 
    super.onCreate(icicle); 
    
    setContentView(R.layout.map); 
    updateStat();
     setTitle("這是你目前所在位置");
     
    // create MapView object 
     mapview = (MapView)findViewById(R.id.myMapView); 
     mapController = mapview.getController(); 
     
    // set MapView show optional(street,state)
    mapview.setSatellite(true); 
    mapview.setStreetView(true); 
     
    // level to blow up
    intZoomLevel = 15; 
    mapController.setZoom(intZoomLevel); 
     
    // create LocationManager object for system LOCATION service 
    mLocationManager =  
    (LocationManager)getSystemService(Context.LOCATION_SERVICE); 
     
    getLocationProvider(); 
     
    // send Location object show in MapView  
    fromGeoPoint = getGeoByLocation(mLocation); 
    refreshMapViewByGeoPoint(fromGeoPoint, 
    		mapview, intZoomLevel); 
     
    // create LocationManager object for observation Location change event then update MapView 
    mLocationManager.requestLocationUpdates 
    (strLocationProvider, 2000, 10, mLocationListener); 
     
     
    //map to blow up 
    large = (Button)findViewById(R.id.big); 
    large.setOnClickListener(new Button.OnClickListener() 
    { 
      @Override 
      public void onClick(View v) 
      { 
        // TODO Auto-generated method stub 
        intZoomLevel++; 
        if(intZoomLevel>mapview.getMaxZoomLevel()) 
        { 
          intZoomLevel = mapview.getMaxZoomLevel(); 
        } 
        mapController.setZoom(intZoomLevel); 
      } 
    }); 
     
    //map to reduce
    small = (Button)findViewById(R.id.litte); 
    small.setOnClickListener(new Button.OnClickListener() 
    { 
      @Override 
      public void onClick(View v) 
      { 
        // TODO Auto-generated method stub 
        intZoomLevel--; 
        if(intZoomLevel<1) 
        { 
          intZoomLevel = 1; 
        } 
        mapController.setZoom(intZoomLevel); 
      } 
    }); 
  } 
  //update user location
  private void updateStat() {
	  login=user_login.user_name+"-";
      mgr = (LocationManager) getSystemService(LOCATION_SERVICE);
      Criteria criteria = new Criteria();
      best = mgr.getBestProvider(criteria, true);
      Location location = mgr.getLastKnownLocation(best);
      onLocationChanged(location);
      // pop up
      located=new String();
      StringBuffer msg = new StringBuffer();
      msg.append("Now you location: ");
      msg.append("Latitude: ");
      msg.append(Double.toString(location.getLatitude()));
      msg.append(", Longitude: ");
      msg.append(Double.toString(location.getLongitude()));
      located="2-"+login+Double.toString(location.getLatitude()-25)+"-"+Double.toString(location.getLongitude()-121);
      connection();
  }
  public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		//Toast.makeText(this, location.toString(), Toast.LENGTH_LONG).show();
	}
  public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	@Override
    protected void onResume() {
        super.onResume();
        mgr.requestLocationUpdates(best, 60000, 1, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mgr.removeUpdates(this);
    }
  // catch GPS coordinate by mobile device change event  
  public final LocationListener mLocationListener =  
  new LocationListener() 
  { 
    @Override 
    public void onLocationChanged(Location location) 
    { 
      // TODO Auto-generated method stub 
       
    	mLocation = location; 
      fromGeoPoint = getGeoByLocation(location); 
      refreshMapViewByGeoPoint(fromGeoPoint, 
    		  mapview, intZoomLevel); 
    } 
     
    @Override 
    public void onProviderDisabled(String provider) 
    { 
      // TODO Auto-generated method stub 
    	mLocation = null; 
    } 
     
    @Override 
    public void onProviderEnabled(String provider) 
    { 
      // TODO Auto-generated method stub 
       
    } 
     
    @Override 
    public void onStatusChanged(String provider, 
                int status, Bundle extras) 
    { 
      // TODO Auto-generated method stub 
       
    } 
  }; 
   
  // send Location object and get back GeoPoint object  
  private GeoPoint getGeoByLocation(Location location) 
  { 
    GeoPoint gp = null; 
    try 
    { 
      /* 當Location存在 */ 
      if (location != null) 
      { 
        double geoLatitude = location.getLatitude()*1E6; 
        double geoLongitude = location.getLongitude()*1E6; 
        gp = new GeoPoint((int) geoLatitude, (int) geoLongitude); 
      } 
    } 
    catch(Exception e) 
    { 
      e.printStackTrace(); 
    } 
    return gp; 
  } 
   
//send geoPoint change into MapView's Google Map  
  public static void refreshMapViewByGeoPoint 
  (GeoPoint gp, MapView mapview, int zoomLevel) 
  { 
    try 
    { 
      mapview.displayZoomControls(true); 
      MapController myMC = mapview.getController(); 
      myMC.animateTo(gp); 
      myMC.setZoom(zoomLevel); 
      mapview.setSatellite(false); 
    } 
    catch(Exception e) 
    { 
      e.printStackTrace(); 
    } 
  } 
   
  // send location change into MapView's Google Map  
  public static void refreshMapViewByCode 
  (double latitude, double longitude, 
      MapView mapview, int zoomLevel) 
  { 
    try 
    { 
      GeoPoint p = new GeoPoint((int) latitude, (int) longitude); 
      mapview.displayZoomControls(true); 
      MapController myMC = mapview.getController(); 
      myMC.animateTo(p); 
      myMC.setZoom(zoomLevel); 
      mapview.setSatellite(false); 
    } 
    catch(Exception e) 
    { 
      e.printStackTrace(); 
    } 
  } 
   
 
   
  // get LocationProvider  
  public void getLocationProvider() 
  { 
    try 
    { 
      Criteria mCriteria = new Criteria(); 
      mCriteria.setAccuracy(Criteria.ACCURACY_FINE); 
      mCriteria.setAltitudeRequired(false); 
      mCriteria.setBearingRequired(false); 
      mCriteria.setCostAllowed(true); 
      mCriteria.setPowerRequirement(Criteria.POWER_LOW); 
      strLocationProvider =  
    	  mLocationManager.getBestProvider(mCriteria, true); 
       
      mLocation = mLocationManager.getLastKnownLocation 
      (strLocationProvider); 
    } 
    catch(Exception e) 
    { 
    	mTextView.setText(e.toString()); 
      e.printStackTrace(); 
    } 
  } 
   
  @Override 
  protected boolean isRouteDisplayed() 
  { 
    // TODO Auto-generated method stub 
    return false; 
  } 
  //send user location to server
  public void connection(){
	     try{
	    	openDialog2(InetAddress.getLocalHost().getHostAddress());
	   		Socket socket = new Socket(InetAddress.getByName("140.136.149.204"),14741);
	   		//openDialog2("連線成功");

	   	BufferedWriter bf = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
	      bf.write(located);
	     	bf.flush();	// 這個動作粉重要, 千萬不要忘記
	   		socket.close();
	   		//openDialog2("傳送完畢");
	 

	   	}catch(IOException ie){openDialog2("連線失敗");}
	    }
	public void openDialog2(String msg){
		   Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
		   }
  @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, playlist, 0, "地圖-播放清單");
		menu.add(0, main, 0, "回到主畫面");
		return true;
	}
  public boolean onOptionsItemSelected(MenuItem item){
		super.onOptionsItemSelected(item);
		switch(item.getItemId()){
			case playlist:
				this.i=5;
				Intent playlist = new Intent();
    		    playlist.setClass(map.this, playlist.class);
    		    startActivity(playlist);
    			break;
			case main:
				Intent main = new Intent();
    		    main.setClass(map.this, emotion.class);
    		    startActivity(main);
    			break;
	}
		return true;
  }

}
