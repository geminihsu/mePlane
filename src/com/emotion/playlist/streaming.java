package com.emotion.playlist;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;


import android.app.AlertDialog;
import android.widget.Toast;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.widget.LinearLayout;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipInputStream;

public class streaming extends Activity {
	public static TextView mTimeDisplay;
	
	public static int mYear,mMonth,mDay,mHour,mMinute;

	static final int TIME_DIALOG_ID = 0;

	public static Button reset,cancel;
	
	public static ImageButton listen,love,playButton,stopButton,backButton,nextButton,deleteButton,group_00,group_01,group_10,group_11;
	
	public static TextView time_run,textStreamed,song_id3,problem;

	public static ProgressBar progressBar;
	
	public static String login,add,annoted,delete,path,id,title,title_ch,emo_v1,emo_v2,emo_v3,time,located;
	
	public static Integer i,count_00,count_01,count_10; 
 
	public static boolean c;
	public static int s,index,n=0;
	public static  StreamingMediaPlayer audioStreamer;
	public static playlist mp3path;
	protected static final int list = Menu.FIRST;
	protected static final int search_art= Menu.FIRST+1;
	protected static final int main  = Menu.FIRST+2;

	public static Spinner s1,s2,s3;
	 public static Integer[] av_value;
	 private LocationManager mgr;
	  String best;
		public static LinearLayout l;
		 public emotion group;
	 
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.streaming);
        login=user_login.user_name+"-";
        l= (LinearLayout) findViewById(R.id.wrap);
    	mTimeDisplay = (TextView) findViewById(R.id.timeDisplay);
    	textStreamed = (TextView) findViewById(R.id.text_kb_streamed);
    	time_run = (TextView) findViewById(R.id.time);
    	song_id3 = (TextView) findViewById(R.id.song_id3);
    	problem= (TextView) findViewById(R.id.problem);
    	 mTimeDisplay.setText("");
		 textStreamed.setText("");
		 time_run.setText("");
		 song_id3.setText("");
		 problem.setTextColor(Color.BLACK);
		 mTimeDisplay.setTextSize(16);
		 textStreamed.setTextSize(16);
		 time_run.setTextSize(16);
		 song_id3.setTextSize(18);
		 problem.setTextSize(16);
		 //we detect user touch plane color change background color and font color
   	 if((group.playlist_id)==6){
			l.setBackgroundColor(Color.rgb(255, 255, 100));		
   	 }
   	 
			else if((group.playlist_id)==1){
			l.setBackgroundColor(Color.rgb(250, 100, 70));
			
			}else if((group.playlist_id)==2){
				l.setBackgroundColor(Color.rgb(100, 250, 250));
			}else if((group.playlist_id)==3){
			l.setBackgroundColor(Color.rgb(100, 250, 150));
			}else{
				 mTimeDisplay.setTextColor(Color.WHITE);
				 textStreamed.setTextColor(Color.WHITE);
				 time_run.setTextColor(Color.WHITE);
				 song_id3.setTextColor(Color.WHITE);
				 problem.setTextColor(Color.WHITE);
				 mTimeDisplay.setTextSize(16);
				 textStreamed.setTextSize(16);
				 time_run.setTextSize(16);
				 song_id3.setTextSize(18);
				 problem.setTextSize(18);
			}
  
        initControls();
   
    } 
    
    public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		//Toast.makeText(this, location.toString(), Toast.LENGTH_LONG).show();
	}
 //initial all  variable
    private void initControls() {
    	
    	delete=new String();
    	delete="";
    	annoted=new String();
    	annoted="";
    	located=new String();
    	located="";
    	add=new String();
    	add="";
    	i=mp3path.k;
    	id=mp3path.song_id;
    	title=mp3path.song_title;
    	title_ch=mp3path.song_title_ch;
    	count_00=0;
    	count_01=0;
    	count_10=0;
    	group_00 = (ImageButton) findViewById(R.id.group00);
    	group_01 = (ImageButton) findViewById(R.id.group01);
    	group_10 = (ImageButton) findViewById(R.id.group10);
    	reset = (Button) findViewById(R.id.reset);
    	cancel=(Button) findViewById(R.id.cancel);
    	s1=(Spinner)findViewById(R.id.emotion_00);
		ArrayAdapter<String> adapter1=new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,emotion_00);
		s1.setAdapter(adapter1);
		s2=(Spinner)findViewById(R.id.emotion_01);
		ArrayAdapter<String> adapter2=new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,emotion_01);
		s2.setAdapter(adapter2);
		s3=(Spinner)findViewById(R.id.emotion_10);
		ArrayAdapter<String> adapter3=new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,emotion_10);
		s3.setAdapter(adapter3);
		
		// get the current time
		 group_00.setEnabled(false);
		 group_01.setEnabled(false);
		 group_10.setEnabled(false);
		 reset.setEnabled(false);
		 cancel.setEnabled(false);
		 s1.setEnabled(false);
		 s2.setEnabled(false);
		 s3.setEnabled(false);
		   progressBar = (ProgressBar) findViewById(R.id.progress_bar);
     	listen=(ImageButton) findViewById(R.id.listening);
        playButton = (ImageButton) findViewById(R.id.button_play);
        playButton.setEnabled(false);
        stopButton = (ImageButton) findViewById(R.id.button_stop);
        stopButton.setEnabled(false);
    	nextButton = (ImageButton) findViewById(R.id.button_next);
    	nextButton.setEnabled(false);
    	backButton = (ImageButton) findViewById(R.id.button_back);
    	backButton.setEnabled(false);
    	deleteButton = (ImageButton) findViewById(R.id.button_delete);
    	deleteButton.setEnabled(false);
    	problem = (TextView) findViewById(R.id.problem);
   
     	love=(ImageButton) findViewById(R.id.love);
		
     	//start streaming song
		listen.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				StreamingMediaPlayer.p=0;
				StreamingMediaPlayer.t=0;
				StreamingMediaPlayer.counter=0;
				index=0;
				mTimeDisplay.setText("");
				song_id3.setText("播放歌曲:"+title_ch);
				startStreamingAudio();
			
        }});
	
	
	   //play and pause song
		playButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				if (audioStreamer.getMediaPlayer().isPlaying()) {

				audioStreamer.getMediaPlayer().pause();
				playButton.setImageResource(R.drawable.button_play);
				 group_00.setEnabled(false);
				 group_01.setEnabled(false);
				 group_10.setEnabled(false);
				s1.setEnabled(false);
				s2.setEnabled(false);
				s3.setEnabled(false);  
				reset.setEnabled(false);
				cancel.setEnabled(false);
				nextButton.setEnabled(false);
				backButton.setEnabled(false);
				deleteButton.setEnabled(false);
				stopButton.setEnabled(false);
				// button.setEnabled(false);
				love.setEnabled(false);
				
			} else {
				
					audioStreamer.getMediaPlayer().start();
				audioStreamer.startPlayProgressUpdater1();
					playButton.setImageResource(R.drawable.button_pause);
				    play();
				   // setTitle("正在播放"+title_ch);
				}
				
        }});
	
		//next song
		nextButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				if(StreamingMediaPlayer.counter!=0){
				if(StreamingMediaPlayer.p==playlist.url_song_data.length) StreamingMediaPlayer.p=0;
				path=mp3path.next_song_path(StreamingMediaPlayer.p);
				 id=mp3path.next_song_id(StreamingMediaPlayer.p);
				 title=mp3path.next_song_title(StreamingMediaPlayer.p);	
				 title_ch=mp3path.next_song_title_ch(StreamingMediaPlayer.p);
				index=StreamingMediaPlayer.p++;
				}else{
					if(i==playlist.url_song_data.length) i=0;
					path=mp3path.next_song_path(i);
					 id=mp3path.next_song_id(i);
					 title=mp3path.next_song_title(i);	
					 title_ch=mp3path.next_song_title_ch(i);
					index=i++;
				}
				 StreamingMediaPlayer.minute=0;
				 StreamingMediaPlayer.second=0;
					if ( audioStreamer != null) {
				    	//audioStreamer.interrupt();
				    	audioStreamer.getMediaPlayer().reset();
				    	mTimeDisplay.setText("");
				    	try{
				    		play();
				    	audioStreamer.getMediaPlayer().setDataSource(path);
				    	audioStreamer.getMediaPlayer().prepare();
				    	updateDisplay();
						song_id3.setText("播放歌曲:"+title_ch);
				    	audioStreamer.getMediaPlayer().start();
				    	audioStreamer.startPlayProgressUpdater1();
	
				    	}catch (IOException e) {
				    		Log.e(getClass().getName(), "Error initializing the MediaPlaer.", e);
				    	}
				    	}
        }});
		//front song
		backButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				n++;
				if(n==1){
				StreamingMediaPlayer.p=playlist.k;}
				if(StreamingMediaPlayer.p==1) StreamingMediaPlayer.p=playlist.url_song_data.length;
				 path=mp3path.back_song_path(StreamingMediaPlayer.p);
				 id=mp3path.back_song_id(StreamingMediaPlayer.p);
				 title=mp3path.back_song_title(StreamingMediaPlayer.p);	
				 title_ch=mp3path.back_song_title_ch(StreamingMediaPlayer.p);
				 index=StreamingMediaPlayer.p--;
				 i--;
				 StreamingMediaPlayer.minute=0;
				 StreamingMediaPlayer.second=0;
				 if ( audioStreamer != null) {
					 audioStreamer.getMediaPlayer().reset();
				    	mTimeDisplay.setText("");
				    	try{
				    		play();
				    	audioStreamer.getMediaPlayer().setDataSource(path);
				    	audioStreamer.getMediaPlayer().prepare();
				    	updateDisplay();
						song_id3.setText("播放歌曲:"+title_ch);
				    	audioStreamer.getMediaPlayer().start();
				    	audioStreamer.startPlayProgressUpdater1();
	
				    	}catch (IOException e) {
				    		Log.e(getClass().getName(), "Error initializing the MediaPlaer.", e);
				    	}
				    	}
			    		
        }});
		//stop song and turn on menu
		stopButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				
				if ( audioStreamer.getMediaPlayer().isPlaying()) {
					audioStreamer.getMediaPlayer().pause(); 
				
	    	}
				ma();
	
			    		
        }});
		//delete song form playlist
		deleteButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				deletedata();
        }});
	    //the first class score
		group_00.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view1) {
				count_00++;
				 if (count_00%5==0){ 
					 count_00=5;
				 group_00.setEnabled(false);
				 }else{ count_00=count_00%5;
					}
				emo_v1="-"+emotion_00[s1.getSelectedItemPosition()]+"-"+emo1_v[s1.getSelectedItemPosition()]+"-"+emo1_a[s1.getSelectedItemPosition()]+"-"+count_00.toString()+"-";
				 setTitle("這首歌曲你覺得"+emotion_00[s1.getSelectedItemPosition()]+"有"+count_00.toString()+"分");
					s1.setEnabled(false);
   
			}});
		//the second class score
		group_01.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view2) {	
					count_01++;
					 if (count_01%5==0){ 
						 count_01=5;
					 group_01.setEnabled(false);
					 }else{ count_01=count_01%5;
						}
					 emo_v2=emotion_01[s2.getSelectedItemPosition()]+"-"+emo2_v[s2.getSelectedItemPosition()]+"-"+emo2_a[s2.getSelectedItemPosition()]+"-"+count_01.toString()+"-";
					 setTitle("這首歌曲你覺得"+emotion_01[s2.getSelectedItemPosition()]+"有"+count_01.toString()+"分");
						s2.setEnabled(false);
        }});
		//the four class score
			group_10.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view3) {
				count_10++;
				 if (count_10%5==0){ 
					 count_10=5;
				 group_10.setEnabled(false);
				 }else{ count_10=count_10%5;
					}
      emo_v3=emotion_10[s3.getSelectedItemPosition()]+"-"+emo3_v[s3.getSelectedItemPosition()]+"-"+emo3_a[s3.getSelectedItemPosition()]+"-"+count_10.toString();
				setTitle("這首歌曲你覺得"+emotion_10[s3.getSelectedItemPosition()]+"有"+count_10.toString()+"分");
				s3.setEnabled(false);
			}});
		
		//take the song add in to myplaylist
		 love.setEnabled(false);
		 love.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view4) {
				Calendar t = Calendar.getInstance();
				i=mp3path.k;
		    	id=mp3path.song_id;
		    	title=mp3path.song_title;
		    	title_ch=mp3path.song_title_ch;
				mYear=t.get(Calendar.YEAR);
				mMonth = t.get(Calendar.MONTH);
				mDay = t.get(Calendar.DAY_OF_MONTH);
				mHour = t.get(Calendar.HOUR_OF_DAY);
			    mMinute = t.get(Calendar.MINUTE);
			    time=String.valueOf(mYear)+"-"+String.valueOf(mMonth+1)+"-"+String.valueOf(mDay)+"-"+String.valueOf(pad(mHour))+"-"+String.valueOf(mMinute+StreamingMediaPlayer.minute)+"-";
				updateDisplay();
				updateStat();
				add();
				c=true;
				connection(count_00,count_01,count_10,c);
        }});
		//send song emotion score to the server
		 reset.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view5) {
				i=mp3path.k;
		    	id=mp3path.song_id;
		    	title=mp3path.song_title;
		    	title_ch=mp3path.song_title_ch;
			Calendar t = Calendar.getInstance();
				mYear=t.get(Calendar.YEAR);
				mMonth = t.get(Calendar.MONTH);
				mDay = t.get(Calendar.DAY_OF_MONTH);
				mHour = t.get(Calendar.HOUR_OF_DAY);
			    mMinute = t.get(Calendar.MINUTE);
			    time=String.valueOf(mYear)+"-"+String.valueOf(mMonth+1)+"-"+String.valueOf(mDay)+"-"+String.valueOf(pad(mHour))+"-"+String.valueOf(mMinute+StreamingMediaPlayer.minute)+"-";
				updateDisplay();
				updateStat();
				annotation();
				count_00=0;
		    	count_01=0;
		    	count_10=0;
				 group_00.setEnabled(true);
				 group_01.setEnabled(true);
				 group_10.setEnabled(true);
				s1.setEnabled(true);
				s2.setEnabled(true);
				s3.setEnabled(true);   	 
				
        }});
		 //cancel song emotion score
		 cancel.setOnClickListener(new View.OnClickListener() {
				public void onClick(View view5) {
					i=mp3path.k;
			    	id=mp3path.song_id;
			    	title=mp3path.song_title;
			    	title_ch=mp3path.song_title_ch;
					final Calendar t = Calendar.getInstance();
					mYear=t.get(Calendar.YEAR);
					mMonth = t.get(Calendar.MONTH);
					mDay = t.get(Calendar.DAY_OF_MONTH);
					mHour = t.get(Calendar.HOUR_OF_DAY);
				    mMinute = t.get(Calendar.MINUTE);
				    time=String.valueOf(mYear)+"-"+String.valueOf(mMonth+1)+"-"+String.valueOf(mDay)+"-"+String.valueOf(pad(mHour))+"-"+String.valueOf(mMinute+StreamingMediaPlayer.minute)+"-";
					updateDisplay();
					updateStat();
					count_00=0;
			    	count_01=0;
			    	count_10=0;
					 group_00.setEnabled(true);
					 group_01.setEnabled(true);
					 group_10.setEnabled(true);
					s1.setEnabled(true);
					s2.setEnabled(true);
					s3.setEnabled(true);   	 
					
	        }});
    }
    //enabled  all button and take play song time
    public static void play(){
    	mTimeDisplay.setText("");
    	Calendar t = Calendar.getInstance();
		located="";
		time="";
		mYear=t.get(Calendar.YEAR);
		mMonth = t.get(Calendar.MONTH);
		mDay = t.get(Calendar.DAY_OF_MONTH);
		mHour = t.get(Calendar.HOUR_OF_DAY);
	    mMinute = t.get(Calendar.MINUTE);
	    time=String.valueOf(mYear)+"-"+String.valueOf(mMonth+1)+"-"+String.valueOf(mDay)+"-"+String.valueOf(pad(mHour))+"-"+String.valueOf(mMinute+StreamingMediaPlayer.minute)+"-";
		//updateDisplay();
		count_00=0;
    	count_01=0;
    	count_10=0;
		 group_00.setEnabled(true);
		 group_01.setEnabled(true);
		 group_10.setEnabled(true);
		 listen.setEnabled(false);
		 stopButton.setEnabled(true);
		 nextButton.setEnabled(true);
		backButton.setEnabled(true);
		playButton.setEnabled(true);
		 reset.setEnabled(true);
		 cancel.setEnabled(true);
		 //button.setEnabled(true);
		 love.setEnabled(true);
		if((emotion.playlist_id)==4||TouchScreen.point_number>0){
			 deleteButton.setEnabled(true);	
		}
		s1.setEnabled(true);
		s2.setEnabled(true);
		s3.setEnabled(true);
    }
 // updates the time we display in the TextView
	public static void updateDisplay() {
	    mTimeDisplay.setText(
	        new StringBuilder()
	                .append("今天是 ")
	                .append(pad(mYear)).append("年")
	                .append(pad(mMonth+1)).append("月")
	                .append(pad(mDay)).append("日，                         ")
	                .append("現在時間是:")
	                .append(pad(mHour)).append(":")
	                .append(pad(mMinute+StreamingMediaPlayer.minute)));
	}
	 public  static String pad(int c) {
	        if (c >= 10){
	            return String.valueOf(c);
	        }else if(c==60){
	        	 return "00";
	        }
	        else
	            return "0" + String.valueOf(c);
	    }
      //updates the location and send to server
	 	 private void updateStat() {
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
	        if(emotion.i==4){
	        	emotion.i=3;
	        }
	 
	        located=Double.toString(location.getLatitude()-25)+"-"+Double.toString(location.getLongitude()-121)+"-"+String.valueOf(emotion.i+1)+"-";
	    }
	 	 //add song function
	 public void add(){ 
  	   add="4-";
  		setTitle("這首歌曲已經加入您的播放清單中");
  		c=true;
			connection(count_00,count_01,count_10,c);
  }
	  //delete song function
	 public void deletedata(){ 
   if(TouchScreen.point_number>0){
	   delete="5-";
	   setTitle("這首歌曲已經從您的情緒平面中刪除");
   }else{
	   delete="1-";
	   setTitle("這首歌曲已經從您的播放清單中刪除");
   }
	c=true;
	connection(count_00,count_01,count_10,c);    
    		
    }
	 //score emotion  song function
    public void annotation(){
    	annoted="3-";
 		setTitle("你已經對這首歌曲加上情緒評分");
 		c=true;
			connection(count_00,count_01,count_10,c);
			
 }
    //send song path be parameter to the class StreamingMediaPlayer
    public void startStreamingAudio() {
    	    	try { 
    		if ( audioStreamer != null) {
    			audioStreamer.interrupt();
   
    		}
    		 
    		path=mp3path.url_path;
    		audioStreamer = new StreamingMediaPlayer(this,time_run,textStreamed, playButton,listen,progressBar);
    		audioStreamer.startStreaming(path,1677, 214);
    		//setTitle("正在播放"+title_ch);
    
    		
    		//streamButton.setEnabled(false);
    	} catch (IOException e) {
	    	Log.e(getClass().getName(), "Error starting to stream audio.", e);            		
    	}
    	    	
    }
    //return to the menu
    public void ma(){
    	Intent listsong=new Intent(this,emotion.class);
		 startActivity(listsong);
   }
    //defined emotion keyword
    public static final String[] emotion_00={
    	"激勵","熱鬧","情緒化","熱情","刺激","高興","愉悅","強烈","渾厚","積極","浪漫","深情","敏感","奇特"
    };
    public static final String[] emo1_v={
    	"160","238","230","230","251","259","232","198","198","224","251","251","219","195"
    };
    public static final String[] emo1_a={
    	"174","143","157","157","125","137","184","163","163","207","122","122","189","169"
    };
    public static final String[] emotion_01={
    	"生氣","活潑","嬉戲","難過","振作"
    };
    public static final String[] emo2_v={
    	"123","123","134","82","160"
      	   };
    public static final String[] emo2_a={
    	"143","143","166","178","174"
      	   };
   
    public static final String[] emotion_10={
    	"輕鬆","舒適","明亮","悠閒","溫柔","圓潤","慰藉","平靜","感動"
    };
    public static final String[] emo3_v={
     	"238","232","207","213","219","213","224","224","181"
     	     	   };
    public static final String[] emo3_a={
    	"143","184","198","135","189","135","207","207","157"
     	    };
    //the menu option
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, list, 0, "回到播放清單");
		menu.add(0, search_art, 0, "搜尋此歌手");
		menu.add(0, main, 0, "回到主畫面");
	
		return true;
	}
    //used different number send to the server known what user want to do
    public void connection(int count_00,int count_01,int count_10,boolean c){
     try{
    	openDialog2(InetAddress.getLocalHost().getHostAddress());
   		Socket socket = new Socket(InetAddress.getByName("140.136.149.204"),14741);
   		openDialog2("連線成功");

   	BufferedWriter bf = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
   		
   	if(delete.equals("1-")||delete.equals("5-")){
   		bf.write(delete+login+mp3path.song_id);
   		delete="";
   	}else if(annoted.equals("3-")){
   		bf.write(annoted+login+time+mp3path.song_id+mp3path.song_title+mp3path.song_title_ch+emo_v1+emo_v2+emo_v3);
   		annoted="";
   		located="";
		time="";
   	}else if(add.equals("4-")){
   	bf.write(add+login+time+located+mp3path.song_id+mp3path.song_title+mp3path.song_title_ch);
   add="";
   	located="";
	time="";
   	}
  
     	bf.flush();	
   		socket.close();
   		openDialog2("操作成功");
 

   	}catch(IOException ie){openDialog2("連線失敗");}
    }
public void openDialog2(String msg){
	   Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	   }
    	//defined different menu function
	public boolean onOptionsItemSelected(MenuItem item){
		super.onOptionsItemSelected(item);
		switch(item.getItemId()){
			case list:
				Intent intent1 = new Intent();
			    intent1.setClass(this, playlist.class);
			    startActivity(intent1);
				break;
			case search_art:
				Intent sea_art = new Intent();
				sea_art.setClass(this, search.class);
			    startActivity(sea_art);
				break;
			case main:
				Intent intent2 = new Intent();
			    intent2.setClass(this, emotion.class);
			    startActivity(intent2);
			    emotion.g=0;
				break;
		
		}
		return true;	
	}
}

