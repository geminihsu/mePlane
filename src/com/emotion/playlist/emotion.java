package com.emotion.playlist;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class emotion extends Activity {
		public static ImageButton myplaylist,search,playlist,map;
	public	com.emotion.playlist.TouchScreen t;
    public static int i,playlist_id,log,g;
    public static String reset,logout;
	protected static final int list = Menu.FIRST;
	protected static final int exit = Menu.FIRST+1;
	public static TextView point;
	public static LinearLayout l;
    //Called when the activity is first created. 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //used log to find user authentication
        i=0;
        setContentView(R.layout.emotion);
        setTitle("請先點選menu登入您的使用者資訊");
        if(log==1){
        initControls();
        setTitle("開始使用個人化專屬音樂-情緒平面");
       }
       
    }
    private void initControls() {
        t=(com.emotion.playlist.TouchScreen) findViewById(R.id.popup_window); 
    	t.setVisibility(0);
    	TouchScreen.point_number=0;
		g=0;
    	 point = (TextView) findViewById(R.id.point);  			
    	
    	 playlist =(ImageButton) findViewById(R.id.emo);
    	 playlist.setEnabled(false);
        //defined access point playlist(emotion playlist)
    	 playlist.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				if((TouchScreen.p.x>317/2)&&(TouchScreen.p.y<(18+(335-18)/2))){
					group_00();
					}else if((TouchScreen.p.x<317/2)&&(TouchScreen.p.y<(18+(335-18)/2))){
					group_01();
				}else if((TouchScreen.p.x<317/2)&&(TouchScreen.p.y>(18+(335-18)/2))){
					group_11();
				}else if((TouchScreen.p.x>317/2)&&(TouchScreen.p.y>(18+(335-18)/2))){
					group_10();
					
				}
				
        }});
    	//defined access search artist-album playlist
    	 search = (ImageButton) findViewById(R.id.search);
    	 search.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				TouchScreen.point_number=0;
				i=7;
				playlist_id=playlist_id(i);
				Intent intent = new Intent();
    		    intent.setClass(emotion.this, search.class);
    		    startActivity(intent);
        }});
    	//defined access  myplaylist playlist
    	 myplaylist = (ImageButton) findViewById(R.id.myplaylist);
    	 myplaylist.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				TouchScreen.point_number=0;
				i=4;
				playlist_id=playlist_id(i);
				Intent intent = new Intent();
    		    intent.setClass(emotion.this, playlist.class);
    		    startActivity(intent);
        }});
    	//defined access map playlist
    	 map = (ImageButton) findViewById(R.id.location);
    	 map.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				TouchScreen.point_number=0;
				i=5;
				playlist_id=playlist_id(i);
				Intent intent = new Intent();
    		    intent.setClass(emotion.this, map.class);
    		    startActivity(intent);
        }});
    		if(TouchScreen.point_number>0){
        		playlist.setEnabled(true);
        	}
    }
  //the menu option
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, list, 0, "登入");
		menu.add(0, exit, 0, "離開");
		return true;
	}
  //defined different menu function
    public boolean onOptionsItemSelected(MenuItem item){
		super.onOptionsItemSelected(item);
		switch(item.getItemId()){
			case list:
				Intent listsong=new Intent(this,user_login.class);
	    		 startActivity(listsong);
	    		 log=1;
				break;
			case exit:
				finish();
				log=0;
				break;
				}
		return true;	
	}
   
    //return song id
    public  static Integer playlist_id(Integer i){
	    
		return i;
	 }
    //defined access different emotion playlist function
    public void group_00(){
    	i=6;
		playlist_id=playlist_id(i);
		Intent intent = new Intent();
	    intent.setClass(emotion.this, playlist.class);
	    startActivity(intent);
    }
    public void group_01(){
    	i=1;
		playlist_id=playlist_id(i);
		Intent intent = new Intent();
	    intent.setClass(emotion.this, playlist.class);
	    startActivity(intent);
    }
    public void group_11(){
    	i=2;
		playlist_id=playlist_id(i);
		Intent intent = new Intent();
	    intent.setClass(emotion.this, playlist.class);
	    startActivity(intent);
    }
    public void group_10(){
    	i=3;
		playlist_id=playlist_id(i);
		Intent intent = new Intent();
	    intent.setClass(emotion.this, playlist.class);
	    startActivity(intent);
    }
  //user logout emPlane send number seven to the server known delete the user log
    public void logout(){
    	logout="7-";
    	 try{
    	       	openDialog2(InetAddress.getLocalHost().getHostAddress());
    	      		Socket socket = new Socket(InetAddress.getByName("140.136.149.204"),14741);
    	          	BufferedWriter bf = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    	  	      	
    	      	bf.write(logout);
    	        	bf.flush();	
    	      		socket.close();
    	     
    	      		logout="";
    	      	}catch(IOException ie){openDialog2("連線失敗");}
			
}
   public void openDialog2(String msg){
   	   Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
   	   }
       	
}