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
import android.widget.Toast;

public class user_login extends Activity  implements OnClickListener{
    /** Called when the activity is first created. */	
	public static String  message,u,user_name;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog);
        user_name="";
        Button textEntry = (Button) findViewById(R.id.dialog_button);
        textEntry.setOnClickListener(this); 
        }
	//send user infomation to log server
	 public void  onClick  (View v){
		   	try{
		   		EditText ip = (EditText)findViewById(R.id.ip_edit);
		   		EditText port = (EditText)findViewById(R.id.port_edit);
		   		 		openDialog2(InetAddress.getLocalHost().getHostAddress());
		   		Socket socket = new Socket(InetAddress.getByName(ip.getText().toString()),Integer.valueOf(port.getText().toString()));
		   		openDialog2("");

		   	BufferedWriter bf = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		   	EditText name = (EditText)findViewById(R.id.username_edit);
	   		EditText password = (EditText)findViewById(R.id.password_edit);   	
	   		user_name=name.getText().toString();
	  		   		bf.write(user_name);

		   		bf.flush();	
		   		socket.close();
		   		openDialog2("登入成功");
		   		emotion.log=1;
		   		Intent intent = new Intent();
    		    intent.setClass(user_login.this, emotion.class);
    		    startActivity(intent);
    		  
		   	}catch(IOException ie){openDialog2("登入失敗");}
	    	}
     //show log access success message
	   public void openDialog2(String msg){
		   Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
		   }
	}