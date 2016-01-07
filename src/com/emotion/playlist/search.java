package com.emotion.playlist;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class search extends Activity  implements OnClickListener{
    /** Called when the activity is first created. */	
	public static String  message,u;
	 public static Integer i;
	 public static EditText artist;
	 
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        u="";
        TouchScreen.point_number=0;
        Button search = (Button) findViewById(R.id.search_button);
        artist = (EditText)findViewById(R.id.artist_edit);
	   	   artist.setText(streaming.title_ch);
        search.setOnClickListener(this);     
        }
	 public void  onClick  (View v){
		 this.i=1;
		 u="6-";
		   	try{
		   	  
		   		openDialog2(InetAddress.getLocalHost().getHostAddress());
		   		Socket socket = new Socket(InetAddress.getByName("140.136.149.204"),14741);
		   	openDialog2("");

		   	BufferedWriter bf = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8"));
		  
	  		   	bf.write(u+artist.getText().toString());
		   		bf.flush();	
		   		socket.close();
		   		openDialog2("");
		   	 	u="";
		   		emotion.i=7;
		   		emotion.playlist_id=emotion.playlist_id(emotion.i);
		   		Intent intent = new Intent();
		   	    intent.setClass(search.this,playlist_album.class);
    		    startActivity(intent);
    		    artist.setText("");

		   	}catch(IOException ie){openDialog2("");}
	    	}

	   public void openDialog2(String msg){
		   Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
		   }
	public String log(String message){
	return message;
	}
	
	}