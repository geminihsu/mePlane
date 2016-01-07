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

public class playlist_album extends ListActivity {
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
	    public static int i,j;
	 
	    public static  StreamingMediaPlayer audioStreamer;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		if((group.playlist_id)==7){
				url = "http://gemini612.no-ip.org/file/search_artist_album.xml";	
				link="http://140.136.149.204/file/music/en_song/";
			}
		
    coll= new ArrayList<Map<String, Object>>();
		super.onCreate(savedInstanceState);
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
		this.setListAdapter(new SimpleAdapter(this, coll,
	                android.R.layout.simple_list_item_2, new String[] { "c1" },
	                new int[] {android.R.id.text1}));
	}
	@Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
		
    	
    	this.i=position;
    	this.url_path=url_path(url_song_data[i]);
        this.song_id=song_id(song_id_key[i]);
         this.song_title=song_title(song_title_key[i]);
         this.song_title_ch=song_title(song_title_ch_key[i]);
        
    			query_song();
    		
    	//mPlayer.stop();
    	// finish();
    	};
    
    	public  static String song_id(String song_id){
    
    		return song_id;
   	 }
    	public  static String next_song_path(int pos){
    		j=pos+1;
    		if(j==song_id_key.length) j=0;
    		url_path=url_path(url_song_data[j]);
       	    song_id=song_id(song_id_key[j]);
            song_title=song_title(song_title_key[j]);
            song_title_ch=song_title(song_title_ch_key[j]);
            return url_path;
   	 }
    	public  static String next_song_id(int pos){
       	    song_id=song_id(song_id_key[j]);
           return song_id;
   	 }
    	public  static String next_song_title(int pos){
            song_title=song_title(song_title_key[j]);
            return song_title;
   	 }
    	public  static String next_song_title_ch(int pos){
            song_title_ch=song_title(song_title_ch_key[j]);
            return song_title_ch;
   	 }
    	public  static String back_song_path(int pos){
    		j=pos-1;
    		if(j==0) j=song_id_key.length;
    		url_path=url_path(url_song_data[j]);
       	    song_id=song_id(song_id_key[j]);
            song_title=song_title(song_title_key[j]);
            song_title_ch=song_title(song_title_ch_key[j]);
            return url_path;
   	 }
    	public  static String back_song_id(int pos){
       	    song_id=song_id(song_id_key[j]);
           return song_id;
   	 }
    	public  static String back_song_title(int pos){
            song_title=song_title(song_title_key[j]);
            return song_title;
   	 }
    	public  static String back_song_title_ch(int pos){
            song_title_ch=song_title(song_title_ch_key[j]);
            return song_title_ch;
   	 }
    	public  static String url_path(String path){
    	    
    		return path;
   	 }
    	public  static String song_title(String song_title){
    	    
    		return song_title;
   	 }
    	
    	public  void query_song(){
    		 q="8-";
 		   	try{
 		   	  
 		   	   		Socket socket = new Socket(InetAddress.getByName("140.136.149.204"),14741);
 		
 		   	BufferedWriter bf = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8"));
 		  
 	  		   	bf.write(q+song_title);
 		   		bf.flush();	
 		   		socket.close();
 		   	
 		   		emotion.i=8;
 		   		emotion.playlist_id=emotion.playlist_id(emotion.i);
 		   		Intent intent = new Intent();
 		   	Intent intent1 = new Intent();
	   	    intent1.setClass(this,playlist.class);
		    startActivity(intent1);
 		   	}catch(IOException ie){}
 	    	}

    	}


    	
