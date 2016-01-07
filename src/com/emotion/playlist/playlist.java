package com.emotion.playlist;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.widget.ListView;

public class playlist extends ListActivity {
	 ArrayList<Map<String, Object>> coll;
	 Map<String, Object> item; 
	   public emotion group;
	   public map point;
	   public search artist;
	   String url,link,url_s,link_s;
	   public static String url_path,q;
	    public static String song_id,song_title,song_title_ch;
	    public static String[] url_song_data,Valence,Arousal;
	    public static String[] song_id_key,song_title_key,song_title_ch_key;
	    public static int k,j;
	 
	    public static  StreamingMediaPlayer audioStreamer;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		k=0;
		j=0;
		//detect user press what kind button access what kind playlist 
		if((group.playlist_id)==8){
			url = "http://gemini612.no-ip.org/file/search_artist.xml";	
			link="http://140.136.149.204/file/music/en_song/";
		}
		else
			if((group.playlist_id)==6){
			url = "http://gemini612.no-ip.org/file/emotion_00.xml";	
			link="http://140.136.149.204/file/music/CAL500/";
			
			}
			else if((group.playlist_id)==1){
				url = "http://gemini612.no-ip.org/file/emotion_01.xml";	
				link="http://140.136.149.204/file/music/CAL500/";
				}else if((group.playlist_id)==2){
				url = "http://gemini612.no-ip.org/file/emotion_11.xml";	
				link="http://140.136.149.204/file/music/CAL500/";
			}
			else if((group.playlist_id)==3){
			url = "http://gemini612.no-ip.org/file/emotion_10.xml";	
			link="http://140.136.149.204/file/music/CAL500/";
		}else if((group.playlist_id)==4){
			url = "http://gemini612.no-ip.org/file/playlist_"+user_login.user_name+".xml";	
			link="http://140.136.149.204/file/music/en_song/";
		}
		else if((point.i)==5){
			url = "http://gemini612.no-ip.org/file/location_"+user_login.user_name+".xml";	
			link="http://140.136.149.204/file/music/en_song/";
		}
		
	//the function will be parse the emotion playlist xml file
    coll= new ArrayList<Map<String, Object>>();
		super.onCreate(savedInstanceState);
		try {
			
			if(TouchScreen.point_number>0){
				url = "http://gemini612.no-ip.org/file/playlist_"+user_login.user_name+".xml";	
				link="http://140.136.149.204/file/music/en_song/";
			this.k=0;
			this.j=0;
	    	this.url_path="";
	    	 this.song_id="";
	         this.song_title="";
	         this.song_title_ch="";
			url_song_data=new String[TouchScreen.point_number];
          song_id_key=new String[TouchScreen.point_number];
          song_title_key=new String[TouchScreen.point_number];
          song_title_ch_key=new String[TouchScreen.point_number];
          Valence=new String[TouchScreen.point_number];
          Arousal=new String[TouchScreen.point_number];
		for (int i = 0; i < TouchScreen.point_number; i++) {
	    url_song_data[i]=TouchScreen.anno_song_url[Integer.valueOf((TouchScreen.point_list_[i]))];
	          	song_id_key[i]=TouchScreen.anno_song_id[Integer.valueOf((TouchScreen.point_list_[i]))];
	          	song_title_key[i]=TouchScreen.anno_song_title[Integer.valueOf((TouchScreen.point_list_[i]))];
	           	song_title_ch_key[i]=TouchScreen.anno_song_title_ch[Integer.valueOf((TouchScreen.point_list_[i]))];
	          	Valence[i]=TouchScreen.anno_Valence[Integer.valueOf((TouchScreen.point_list_[i]))];
	          	Arousal[i]=TouchScreen.anno_Arousal[Integer.valueOf((TouchScreen.point_list_[i]))];
	          	item = new HashMap<String, Object>();
	          item.put("c1",song_title_ch_key[i]);
	          coll.add(item);   }
			}else{
				song_playlist(url);
			}
	
		}catch(Exception e) {
				e.printStackTrace();
			}	
		this.setListAdapter(new SimpleAdapter(this, coll,
	                android.R.layout.simple_list_item_1, new String[] { "c1" },
	                new int[] {android.R.id.text1}));
	}
	//set menu  color
	@Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
		if((group.playlist_id)==6){
		v.setSelected(true);
    	v.setBackgroundColor(Color.rgb(255, 255, 100));
		}
		else if((group.playlist_id)==1){
			v.setSelected(true);
	    	v.setBackgroundColor(Color.rgb(250, 100, 70));
		}else if((group.playlist_id)==2){
			v.setSelected(true);
	    	v.setBackgroundColor(Color.rgb(100, 250, 250));
		}else if((group.playlist_id)==3){
			v.setSelected(true);
	    	v.setBackgroundColor(Color.rgb(100, 250, 150));
		}
    	this.k=position;
    	this.url_path=url_path(url_song_data[k]);
    	 this.song_id=song_id(song_id_key[k]);
         this.song_title=song_title(song_title_key[k]);
         this.song_title_ch=song_title(song_title_ch_key[k]);
         this.j=position;
         
    		listsong();
    	};
    	//send all parameter to the class StreamingMediaPlayer
    	public void listsong(){
    		 Intent listsong=new Intent(this,streaming.class);
    		 startActivity(listsong);
    	 }
    	//take song id
    	public  static String song_id(String song_id){
    
    		return song_id;
   	 }
    	//take next song path
    	public  static String next_song_path(int pos){
    		j=pos+1;
    		if(j==song_id_key.length) j=0;
    		url_path=url_path(url_song_data[j]);
       	    song_id=song_id(song_id_key[j]);
            song_title=song_title(song_title_key[j]);
            song_title_ch=song_title(song_title_ch_key[j]);
            return url_path;
   	 }
    	//take next song id
    	public  static String next_song_id(int pos){
    		j=pos+1;
    		if(j==song_id_key.length) j=0;
       	    song_id=song_id(song_id_key[j]);
           return song_id;
   	 }
    	//take next song title
    	public  static String next_song_title(int pos){
    		j=pos+1;
    		if(j==song_id_key.length) j=0;
            song_title=song_title(song_title_key[j]);
            return song_title;
   	 }
    	//take next song Chinese title
    	public  static String next_song_title_ch(int pos){
    		j=pos+1;
    		if(j==song_id_key.length) j=0;
            song_title_ch=song_title(song_title_ch_key[j]);
            return song_title_ch;
   	 }
    	//take front song path
    	public  static String back_song_path(int pos){
    		j=pos-1;
    		if(j==0) j=song_id_key.length;
    		url_path=url_path(url_song_data[j]);
       	    song_id=song_id(song_id_key[j]);
            song_title=song_title(song_title_key[j]);
            song_title_ch=song_title(song_title_ch_key[j]);
            return url_path;
   	 }
    	//take front song id
    	public  static String back_song_id(int pos){
    		j=pos-1;
    		if(j==0) j=song_id_key.length;
       	    song_id=song_id(song_id_key[j]);
           return song_id;
   	 }
    	//take front song title
    	public  static String back_song_title(int pos){
    		j=pos-1;
    		if(j==0) j=song_id_key.length;
            song_title=song_title(song_title_key[j]);
            return song_title;
   	 }
    	//take front song Chinese title
    	public  static String back_song_title_ch(int pos){
    		j=pos-1;
    		if(j==0) j=song_id_key.length;
            song_title_ch=song_title(song_title_ch_key[j]);
            return song_title_ch;
   	 }
    	//take song path
    	public  static String url_path(String path){
    	    
    		return path;
   	 }
    	//take song title
    	public  static String song_title(String song_title){
    	    
    		return song_title;
   	 }
    	//the function will be parse the myplaylist,map and search playlist xml file
      public  void song_playlist(String url){
    	  this.k=0;
			this.j=0;
	    	this.url_path="";
	    	 this.song_id="";
	         this.song_title="";
	         this.song_title_ch="";
	     	try {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpUriRequest req = new HttpGet(url);
		HttpResponse resp = client.execute(req);
		HttpEntity ent = resp.getEntity();
		InputStream stream = ent.getContent();
		DocumentBuilder b = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document d = b.parse(new InputSource(stream));
		NodeList n = d.getElementsByTagName("mp3_conditions");
		
		url_song_data=new String[n.getLength()];
        song_id_key=new String[n.getLength()];
        song_title_key=new String[n.getLength()];
        song_title_ch_key=new String[n.getLength()];
        Valence=new String[n.getLength()];
        Arousal=new String[n.getLength()];
		for (int i = 0; i < n.getLength(); i++) {
	    url_song_data[i]=link+n.item(i).getChildNodes().item(1).getAttributes().item(0).getNodeValue()+".mp3";
	          	song_id_key[i]=n.item(i).getChildNodes().item(0).getAttributes().item(0).getNodeValue()+"-";
	          	song_title_key[i]=n.item(i).getChildNodes().item(1).getAttributes().item(0).getNodeValue()+"-";
	          	Valence[i]=n.item(i).getChildNodes().item(2).getAttributes().item(0).getNodeValue();
	          	Arousal[i]=n.item(i).getChildNodes().item(3).getAttributes().item(0).getNodeValue();
	        	song_title_ch_key[i]=n.item(i).getChildNodes().item(4).getAttributes().item(0).getNodeValue();
	          	item = new HashMap<String, Object>();
	          item.put("c1",n.item(i).getChildNodes().item(4).getAttributes().item(0).getNodeValue());
	          coll.add(item);   
	          			}
		
   	 	}catch (Exception e) {
			e.printStackTrace();
		}
    	}}


    	
