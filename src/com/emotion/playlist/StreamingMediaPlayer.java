package com.emotion.playlist;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;


public class StreamingMediaPlayer {

    private static final int KB_BUFFER =  96*10/8;
    
	private TextView textStreamed,time;
	
	private ImageButton playButton;
	private ProgressBar	progressBar;
	public static playlist mp3path;
	private long mediaLengthInKb, mediaLengthInSeconds;
	private int totalKbRead = 0;
	
	//used handler to press every method
	private final Handler handler = new Handler();

	public static  MediaPlayer 	mediaPlayer;
	
	private File downloadingMediaFile; 
	//there were detected which is song state
	private boolean isInterrupted;
	
	private Context context;
	
	public static int counter = 0;
	//create minute and second to record time 
	public static int minute,second,p,t;
	
	//there will take UI argument from class streaming include Context,TextView,ImageButton,ProgressBar
 	public StreamingMediaPlayer(Context  context,TextView time,TextView textStreamed, ImageButton	playButton, ImageButton	streamButton,ProgressBar	progressBar) 
 	{   t=0;
 		p=0;
 		this.context = context;
 		this.time = time;
		this.textStreamed = textStreamed;
		this.playButton = playButton;
		this.progressBar = progressBar;

	}
	
      
     // prepare all song file size and create  space 
     
    public void startStreaming(final String mediaUrl, long	mediaLengthInKb, long	mediaLengthInSeconds) throws IOException {    	
    	minute=0;
 		second=0;
 		counter=0;
    	this.mediaLengthInKb = mediaLengthInKb;
    	this.mediaLengthInSeconds = mediaLengthInSeconds;
    	
		Runnable r = new Runnable() {   
	        public void run() {   
	            try {   
	        		downloadAudioIncrement(mediaUrl);
	            } catch (IOException e) {
	            	Log.e(getClass().getName(), "Unable to initialize the MediaPlayer for fileUrl=" + mediaUrl, e);
	            	return;
	            }   
	        }   
	    };   
	    new Thread(r).start();
    }
    
    //used the file output made song loading
    public void downloadAudioIncrement(String mediaUrl) throws IOException {

    	
    	URL url = new URL(mediaUrl);
    	
    	InputStream stream = url.openStream();
    	
        if (stream == null) {
        	Log.e(getClass().getName(), "Unable to create InputStream for mediaUrl:" + mediaUrl);
        }

	
        downloadingMediaFile = new File(context.getCacheDir(),"downloadingMedia_.dat");
        FileOutputStream out = new FileOutputStream(downloadingMediaFile);   
        byte buf[] = new byte[16384];

        int totalBytesRead = 0, incrementalBytesRead = 0;
        do {
        	int numread = stream.read(buf);   
            if (numread <= 0)
                break;   

            out.write(buf, 0, numread);
            totalBytesRead += numread;
            incrementalBytesRead += numread;
            totalKbRead = totalBytesRead/1000;
            testMediaBuffer();
          	fireDataLoadUpdate();
        } while (validateNotInterrupted());   

        if (validateNotInterrupted()) {
	       	fireDataFullyLoaded();
        }
       	stream.close();
    }  
// used this method to detect excited mediaPlayer object
    private boolean validateNotInterrupted() {
		if (isInterrupted) {
			if (mediaPlayer != null) {
				Log.e("StreamingMediaPlayer", "oh nos pausing!");
			mediaPlayer.pause(); 
		
			try{

				mediaPlayer.stop();
				
			}catch(Exception ex){
				Log.w("asda", ex.getMessage());
				
			}
			}
			return false;
		} else {
			return true;
		}
    }

    
   //there were process not yet completed played song
    private void  testMediaBuffer() {
     
	    Runnable updater = new Runnable() {
	        public void run() {
	            if (mediaPlayer == null) {
	            	if ( totalKbRead >= KB_BUFFER) {
	            		try {
	            			
	            	    	
		            		startMediaPlayer();
	            		} catch (Exception e) {
	            			Log.e(getClass().getName(), "Error copying buffered conent.", e);    			
	            		}
	            	}
	            } else if ( mediaPlayer.getDuration() - mediaPlayer.getCurrentPosition() <= 1000 ){ 

	            	transferBufferToMediaPlayer();
	            }
	        }
	    };
	    handler.post(updater);
    }
    //there were process played song event and let it playing continue loop in the playlist
    private void startMediaPlayer() {
    	
        try {   
        	p=0;
            streaming.listen.setEnabled(false);
        	File bufferedFile = new File(context.getCacheDir(),"playingMedia.dat");
        	moveFile(downloadingMediaFile,bufferedFile);
    		
        	Log.e("Player",bufferedFile.length()+"");
        	Log.e("Player",bufferedFile.getAbsolutePath());
        	
    		mediaPlayer = new MediaPlayer();
		  	counter++;
    		mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
    			
    			public void onCompletion(MediaPlayer mp) {
    			  if(counter==1){
    				  if(streaming.index!=0){
    					  p=streaming.index+1;
    				  }else{
    				p=streaming.i;
    				  }
   				 if(p+1==playlist.song_id_key.length){
   					 streaming.path=playlist.url_song_data[0];
					 streaming.id=playlist.song_id(playlist.song_id_key[0]);
					 streaming.title=playlist.song_title(playlist.song_title_key[0]);
					 streaming.title_ch=playlist.song_title(playlist.song_title_ch_key[0]);
   				    t=p;
   				 }else{
   					 streaming.path=playlist.url_song_data[p+1];
					 streaming.id=playlist.song_id(playlist.song_id_key[p+1]);
					 streaming.title=playlist.song_title(playlist.song_title_key[p+1]);
					 streaming.title_ch=playlist.song_title(playlist.song_title_ch_key[p+1]);
                    t=p++;
   				 }                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        
   				 
    			  }else{
    				  p=t;
    	   				 if(p+1==playlist.song_id_key.length){
    	   					 streaming.path=playlist.url_song_data[0];
    						 streaming.id=playlist.song_id(playlist.song_id_key[0]);
    						 streaming.title=playlist.song_title(playlist.song_title_key[0]);
    						 streaming.title_ch=playlist.song_title(playlist.song_title_ch_key[0]);
    	   				   t=0;
    	   				 }else{
    	   					streaming.path=playlist.url_song_data[p+1];
    						 streaming.id=playlist.song_id(playlist.song_id_key[p+1]);
    						 streaming.title=playlist.song_title(playlist.song_title_key[p+1]);
    						 streaming.title_ch=playlist.song_title(playlist.song_title_ch_key[p+1]);
    	                    t++;
    	   				 } 
    			  }
    				 try{
        			   startStreaming(streaming.path,1677, 214);
        			   // take every button enabled  for prepare streaming song
        				 streaming.playButton.setEnabled(false);
        				 streaming.stopButton.setEnabled(false);
        				 streaming.nextButton.setEnabled(false);
        				 streaming.backButton.setEnabled(false);
        				 streaming.love.setEnabled(false);
        				 streaming.deleteButton.setEnabled(false);
        				 streaming.group_00.setEnabled(false);
        				 streaming.group_01.setEnabled(false);
        				 streaming.group_10.setEnabled(false);
        				 streaming.reset.setEnabled(false);
        				 streaming.cancel.setEnabled(false);
        				 streaming.s1.setEnabled(false);
        				 streaming.s2.setEnabled(false);
        				 streaming.s3.setEnabled(false);
    				 }catch(IOException  e){
    						 Log.e(getClass().getName(), "Error starting to stream audio.", e);     
    					 }
    			}
    		}
    		);
    		FileInputStream fis = new FileInputStream(bufferedFile); 
    		FileDescriptor fd = fis.getFD(); 
    		mediaPlayer.reset();
    		mediaPlayer.setDataSource(fd);
        	mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    		mediaPlayer.prepare();
        } catch (IOException e) {
        	Log.e(getClass().getName(), "Error initializing the MediaPlaer.", e);
        	return;
        }   
    }
    
 //if song didn't competed played over but user selected the other song there were process the state
    private void transferBufferToMediaPlayer() {
	    try {

            Log.e("StreamingMediaPlayer", "before duration: " + mediaPlayer.getDuration() + 
            		" current pos: " + mediaPlayer.getCurrentPosition());
	    	// First determine if we need to restart the player after transferring data...e.g. perhaps the user pressed pause
	    	boolean wasPlaying = mediaPlayer.isPlaying();
	    	int curPosition = mediaPlayer.getCurrentPosition();
	    	mediaPlayer.reset();

	       	File bufferedFile = new File(context.getCacheDir(),"playingMedia.dat");
	    	moveFile(downloadingMediaFile,bufferedFile);
        	
		    		FileInputStream fis = new FileInputStream(bufferedFile);
      		FileDescriptor fd = fis.getFD(); 
    		mediaPlayer.setDataSource(fd);
    		fis.close();
      		mediaPlayer.prepare();
    		mediaPlayer.seekTo(curPosition);
    		

            Log.e("StreamingMediaPlayer", "after duration: " + mediaPlayer.getDuration() + 
            		" current pos: " + mediaPlayer.getCurrentPosition());
    	    		boolean atEndOfFile = mediaPlayer.getDuration() - mediaPlayer.getCurrentPosition() <= 1000;
        	if (wasPlaying || atEndOfFile){
        		Log.e("StreamingMediaPlayer", "Restarting media player");
        		mediaPlayer.stop();
        		}
        	else
        	{
        		Log.e("StreamingMediaPlayer", "Not restarting media player");
        	}
        	
		}catch (Exception e) {
	    	Log.e(getClass().getName(), "Error updating to newly loaded content.", e);            		
		}
    }
    //if all song didn't be played this method will find loading song size 
    private void fireDataLoadUpdate() {
		Runnable updater = new Runnable() {
	        public void run() {
	        	textStreamed.setText((CharSequence) ("歌曲已經讀取"+totalKbRead + "Kb"));
	    		float loadProgress = ((float)totalKbRead/(float)mediaLengthInKb);
	    		progressBar.setSecondaryProgress((int)(loadProgress*100));
	        }
	    };
	    handler.post(updater);
    }
    //if song had been loading completed then there will start playing song 
    public void fireDataFullyLoaded() {
    
		Runnable updater = new Runnable() { 
			public void run() {
   	        	transferBufferToMediaPlayer();
	        	textStreamed.setText((CharSequence) ("歌曲已經讀取完畢，總共讀取" + totalKbRead + "Kb"));
	        	mediaPlayer.start();
	        	playButton.setEnabled(true);
	        	streaming.play();
				streaming.song_id3.setText("播放歌曲:"+streaming.title_ch);
	   	startPlayProgressUpdater();
			}
	    };
	    handler.post(updater);
    }
   //create new player object
    public MediaPlayer getMediaPlayer() {
    	return mediaPlayer;
	}
	//there were return the loading progress and what time to play song
    public void startPlayProgressUpdater() {
    	float progress = (((float)mediaPlayer.getCurrentPosition()/1000)/(float)mediaLengthInSeconds);
 progressBar.setProgress((int)(progress*100));
		if (mediaPlayer.isPlaying()) {
			Runnable notification = new Runnable() {
		        public void run() {
		        	startPlayProgressUpdater();
		        	streaming.updateDisplay();
		        	second++;
		            if(second >= 60){
		         	   minute++;
		         	  streaming.updateDisplay();
		     	   second=0;}
		         	   if(second<10){
		            time.setText(String.valueOf(minute)+":"+"0"+String.valueOf(second));
		         		   }else{
		       	time.setText(String.valueOf(minute)+":"+String.valueOf(second));
		         			   }
				}
		    };
		    handler.postDelayed(notification,1000);
    	}
		else
		{
			Log.e("StreamingMediaPlayer", "Why is the mediaplayer not playing?");
		}
		
    }    
	//there were return the loading progress and what time to play song
    public void startPlayProgressUpdater1() {
    	float progress = (((float)mediaPlayer.getCurrentPosition()/10000)/(float)mediaLengthInSeconds);
 progressBar.setProgress((int)(progress*1000));
		if (mediaPlayer.isPlaying()) {
			Runnable notification = new Runnable() {
		        public void run() {
		        	startPlayProgressUpdater1();
		        	streaming.updateDisplay();
		        	second++;
		            if(second >= 60){
		         	   minute++;
		         	  streaming.updateDisplay();
		     	   second=0;}
		         	   if(second<10){
		            time.setText(String.valueOf(minute)+":"+"0"+String.valueOf(second));
		         		   }else{
		       	time.setText(String.valueOf(minute)+":"+String.valueOf(second));
		         			   }
				}
		    };
		    handler.postDelayed(notification,10000);
    	}
		else
		{
			Log.e("StreamingMediaPlayer", "Why is the mediaplayer not playing?");
		}
    }
    //if song was played there were process the event
    public void interrupt() {
    	playButton.setEnabled(false);
    	isInterrupted = true;
    	validateNotInterrupted();
    }
    //there were used buffered to received song data
	public void moveFile(File	oldLocation, File	newLocation)
	throws IOException {

		if ( oldLocation.exists( )) {
			BufferedInputStream  reader = new BufferedInputStream( new FileInputStream(oldLocation) );
			BufferedOutputStream  writer = new BufferedOutputStream( new FileOutputStream(newLocation, false));
            try {
		        byte[]  buff = new byte[8192];
		        int numChars;
		        while ( (numChars = reader.read(  buff, 0, buff.length ) ) != -1) {
		        	writer.write( buff, 0, numChars );
      		    }
            } catch( IOException ex ) {
				throw new IOException("IOException when transferring " + oldLocation.getPath() + " to " + newLocation.getPath());
            } finally {
                try {
                    if ( reader != null ){
                    	writer.close();
                        reader.close();
                    }
                } catch( IOException ex ){
				    Log.e(getClass().getName(),"Error closing files when transferring " + oldLocation.getPath() + " to " + newLocation.getPath() ); 
				}
            }
        } else {
			throw new IOException("Old location does not exist when transferring " + oldLocation.getPath() + " to " + newLocation.getPath() );
        }
	}
}
