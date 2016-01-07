package com.emotion.playlist;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

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


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Color;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.content.Intent;
import java.io.IOException;

public class TouchScreen extends LinearLayout { 

	private List<PointF> pointsToDraw = new ArrayList<PointF>();
	
	public static Paint	point,touchPaint,line,circle,circle1,circle2,circle3,circle4,anno_1,anno_2,font,group,group_00;
	public static  PointF p;
	public static  float[] point_x,point_y;
	public emotion e;
	public static Canvas group_area;
	  private RectF Oval;
	  public static String[] anno_Valence,anno_Arousal,Valence,Arousal,Valence_1,Arousal_1,Valence_2,Arousal_2,Valence_3,Arousal_3,Valence_4,Arousal_4;
   public static String[] url;
   public static String anno_url;
   public static int point_number,c,range;
   public static int[] center;
   public static double[] d;
   public static double distance;
   public static String point_list,path;
   public static String[]  point_list_,anno_song_title,anno_song_title_ch,song_title,song_title_ch,song_title_1,song_title_2,song_title_3,song_title_4,anno_song_id,song_id,song_id_1,song_id_2,song_id_3,song_id_4,anno_song_url,song_url,song_url_1,song_url_2,song_url_3,song_url_4;
public static  MediaPlayer 	mPlayer;
public static  StreamingMediaPlayer audioStreamer;
public static boolean anno;
	public TouchScreen(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public TouchScreen(Context context) {
		super(context);
		init();
	}
	//initial all  component
	private void init() 
	{
		url=new String[4];
		touchPaint  = new Paint();
		touchPaint .setColor(Color.DKGRAY);
		touchPaint .setAntiAlias(true);
		touchPaint .setStyle(Style.STROKE);
		touchPaint .setStrokeWidth(2);
		line  = new Paint();
		line .setARGB(255, 0, 0, 255);
		line .setAntiAlias(true);
		line .setStyle(Style.STROKE);
		line .setTextSize(15);
		line .setStrokeWidth(1);
		anno_1  = new Paint();
		anno_1 .setColor(Color.BLACK);
		anno_1 .setAntiAlias(true);
		anno_1 .setStyle(Style.FILL);
		anno_1 .setTextSize(20);
		anno_1 .setStrokeWidth(1);
		anno_2  = new Paint();
		anno_2 .setColor(Color.BLACK);
		anno_2 .setAntiAlias(true);
		anno_2 .setStyle(Style.FILL);
		anno_2 .setTextSize(15);
		anno_2 .setStrokeWidth(1);
		point  = new Paint();
		point .setColor(Color.GREEN);
		point .setAntiAlias(true);
		point .setStyle(Style.FILL);
		point .setTextSize(10);
		point .setStrokeWidth(1);
		font  = new Paint();
		font .setColor(Color.GREEN);
		font .setAntiAlias(true);
		font .setStyle(Style.FILL);
		font .setTextSize(10);
		font .setStrokeWidth(1);
		group  = new Paint();
		group .setColor(Color.BLACK);
		group .setAntiAlias(true);
		group .setStyle(Style.STROKE);
		group .setTextSize(70);
		group .setStrokeWidth(1);
		group_00  = new Paint();
		group_00 .setColor(Color.LTGRAY);
		group_00 .setAntiAlias(true);
		group_00 .setStyle(Style.STROKE);
		group_00 .setTextSize(15);
		group_00 .setStrokeWidth(317/2);
		circle  = new Paint();
		circle .setARGB(255, 0, 0, 255);
		circle .setAntiAlias(true);
		circle .setStyle(Style.FILL);
		circle .setStrokeWidth(317);
		circle1  = new Paint();
		circle1 .setColor(Color.rgb(255, 255, 100));
		circle1 .setAntiAlias(true);
		circle1 .setStyle(Style.FILL);
		circle1 .setStrokeWidth(10);
		circle2  = new Paint();
		circle2 .setColor(Color.rgb(250, 100, 70));
		circle2 .setAntiAlias(true);
		circle2 .setStyle(Style.FILL);
		circle2 .setStrokeWidth(10);
		circle3  = new Paint();
		circle3 .setColor(Color.rgb(100, 250, 250));
		circle3 .setAntiAlias(true);
		circle3 .setStyle(Style.FILL);
		circle3 .setStrokeWidth(10);
		circle4  = new Paint();
		circle4 .setColor(Color.rgb(100, 250, 150));
		circle4 .setAntiAlias(true);
		circle4 .setStyle(Style.FILL);
		circle4 .setStrokeWidth(10);
		Oval = new RectF(0, 18, 317, 335);
		point_x=new float[100];
		point_y=new float[100];
		d=new double[100];
		c=(18+(335-18)/2);
		range=317/4;
	}

	@Override
    public boolean onTouchEvent(MotionEvent event) 
	{
		//clean the front circle
		pointsToDraw.clear();
		//draw new point
		pointsToDraw.add(new PointF(event.getX(), event.getY()));
		invalidate();
		return true;
	}
	
    @Override
    protected void onDraw(Canvas canvas) 
    {  	
		super.dispatchDraw(canvas);
		//diameter:317
		canvas.drawLine(0, 18+(335-18)/2,317, 18+(335-18)/2, line);
		canvas.drawLine(317/2,18,317/2, 335, line);

		//draw four quadrant and font
		canvas.drawArc(Oval,-90,90, true, circle1);
		canvas.drawArc(Oval,-180,90, true, circle2);
		canvas.drawArc(Oval,-270,90,true, circle3);
		canvas.drawArc(Oval,-360,90,true, circle4);
		canvas.drawText("情緒二極性", 210,165, anno_1);
		canvas.drawText("(正向)", 270,180, anno_2);
		canvas.drawText("激勵", 140, 40, anno_1);
		canvas.drawText("(負向)", 8,180, anno_2);
		canvas.drawText("(高)", 145,62, anno_2);
		canvas.drawText("(低)", 145,302, anno_2);
		canvas.drawText("喜",190, 122, group);
		canvas.drawText("怒",  317/2-317/4-20, 122, group);
		canvas.drawText("哀", 317/2-317/4-20, 275, group);
		canvas.drawText("樂",190, 275, group);
//when user login server will be see the point map into plane
	if(emotion.log==1){
		{
			point_number=0;
			touchPaint.setColor(Color.rgb(20, 80, 80));
					anno_url= "http://140.136.149.204/file/anno_map_"+user_login.user_name+".xml";	
						try {
						DefaultHttpClient client = new DefaultHttpClient();
						HttpUriRequest req = new HttpGet(anno_url);
						HttpResponse resp = client.execute(req);
						HttpEntity ent = resp.getEntity();
						InputStream stream = ent.getContent();
						DocumentBuilder b = DocumentBuilderFactory.newInstance().newDocumentBuilder();
						Document d = b.parse(new InputSource(stream));
						NodeList n = d.getElementsByTagName("mp3_conditions");
						anno_song_url=new String[n.getLength()];
						anno_song_id=new String[n.getLength()];
						anno_song_title=new String[n.getLength()];
						anno_song_title_ch=new String[n.getLength()];
						anno_Valence=new String[n.getLength()];
						anno_Arousal=new String[n.getLength()];
						for (int i = 0; i < n.getLength(); i++) {
							anno_song_url[i]="http://140.136.149.204/file/music/en_song/"+n.item(i).getChildNodes().item(1).getAttributes().item(0).getNodeValue()+".mp3";
							anno_song_id[i]=n.item(i).getChildNodes().item(0).getAttributes().item(0).getNodeValue()+"-";
							anno_song_title[i]=n.item(i).getChildNodes().item(1).getAttributes().item(0).getNodeValue()+"-";
							anno_Valence[i]=n.item(i).getChildNodes().item(2).getAttributes().item(0).getNodeValue();
							anno_Arousal[i]=n.item(i).getChildNodes().item(3).getAttributes().item(0).getNodeValue();
							anno_song_title_ch[i]=n.item(i).getChildNodes().item(4).getAttributes().item(0).getNodeValue();
							
					        	          				}
					}catch (Exception e) {
							e.printStackTrace();
						}	
					if(anno_song_id.length<=1){
						point_number=0;
						touchPaint.setColor(Color.RED);
						anno_url= "http://gemini612.no-ip.org/file/anno_map_geminihome.xml";	
						try {
						DefaultHttpClient client = new DefaultHttpClient();
						HttpUriRequest req = new HttpGet(anno_url);
						HttpResponse resp = client.execute(req);
						HttpEntity ent = resp.getEntity();
						InputStream stream = ent.getContent();
						DocumentBuilder b = DocumentBuilderFactory.newInstance().newDocumentBuilder();
						Document d = b.parse(new InputSource(stream));
						NodeList n = d.getElementsByTagName("mp3_conditions");
						anno_song_url=new String[n.getLength()];
						anno_song_id=new String[n.getLength()];
						anno_song_title=new String[n.getLength()];
						anno_song_title_ch=new String[n.getLength()];
						anno_Valence=new String[n.getLength()];
						anno_Arousal=new String[n.getLength()];
						for (int i = 0; i < n.getLength(); i++) {
							anno_song_url[i]="http://140.136.149.204/file/music/en_song/"+n.item(i).getChildNodes().item(1).getAttributes().item(0).getNodeValue()+".mp3";
							anno_song_id[i]=n.item(i).getChildNodes().item(0).getAttributes().item(0).getNodeValue()+"-";
							anno_song_title[i]=n.item(i).getChildNodes().item(1).getAttributes().item(0).getNodeValue()+"-";
							anno_Valence[i]=n.item(i).getChildNodes().item(2).getAttributes().item(0).getNodeValue();
							anno_Arousal[i]=n.item(i).getChildNodes().item(3).getAttributes().item(0).getNodeValue();
							anno_song_title_ch[i]=n.item(i).getChildNodes().item(4).getAttributes().item(0).getNodeValue();
							
					        	          				}
					}catch (Exception e) {
							e.printStackTrace();
						}	
					}
					for(int i=0;i<anno_song_id.length;i++){
						if(Integer.parseInt(anno_Valence[i])>0&&Integer.parseInt(anno_Arousal[i])>0){
					canvas.drawCircle(Integer.parseInt(anno_Valence[i]),Integer.parseInt(anno_Arousal[i]), 1, touchPaint);
						}
						else if(Integer.parseInt(anno_Valence[i])<0&&Integer.parseInt(anno_Arousal[i])>0){
							canvas.drawCircle(-Integer.parseInt(anno_Valence[i]),Integer.parseInt(anno_Arousal[i]), 1, touchPaint);
						}		
						else if(Integer.parseInt(anno_Valence[i])<0&&Integer.parseInt(anno_Arousal[i])<0){
		   					canvas.drawCircle(-Integer.parseInt(anno_Valence[i]),-Integer.parseInt(anno_Arousal[i]), 1, touchPaint);}
		   				else if(Integer.parseInt(anno_Valence[i])>0&&Integer.parseInt(anno_Arousal[i])<0){
		   					canvas.drawCircle(Integer.parseInt(anno_Valence[i]),-Integer.parseInt(anno_Arousal[i]), 1, touchPaint);}
		   				     	    }

				
		}
		 }
		
		Iterator<PointF> iterator = pointsToDraw.iterator();
		//start paint point and circle into plane
		while (iterator.hasNext()) {
		
			touchPaint.setColor(Color.WHITE);
			 point_list_=new String[10];
    		distance=0;
    		point_number=0;
    		point_list="";
    		p = iterator.next();
    		//when user touch the plane will be generation one white point
         canvas.drawCircle(p.x, p.y, 1, touchPaint);
    		

    		center=new int[anno_song_id.length];
    		point_x[0]=p.x;
    		point_y[0]=p.y;
    		//used user touch point's coordinate and every song's coordinate from playlist calculation Euclidean distance
    		for(int r=0;r<anno_song_id.length;r++){
				d[r]=Math.sqrt(Math.pow(point_x[0]-(value(anno_Valence[r])), 2)+Math.pow(point_y[0]-(value(anno_Arousal[r])), 2));
    		
    		}
    		//we have to compare the touch point between the first point and second point distance
			for(int m=0;m<2;m++){
				for(int n=m+1;n<2;n++){
			distance=Math.min(d[m],d[n]);
			if(distance==d[m]){
				canvas.drawCircle(((value(anno_Valence[m]))+p.x)/2, ((value(anno_Arousal[m]))+p.y)/2, 1, group_00);
	   	    			for(int k=0;k<anno_song_id.length;k++){
	   	    				d[k]=Math.sqrt(Math.pow(((value(anno_Valence[m]))+p.x)/2-(value(anno_Valence[k])), 2)+Math.pow(((value(anno_Arousal[m]))+p.y)/2-(value(anno_Arousal[k])), 2));
	   	   	    						   if(d[k]<range){ 
		    	    		point_number++;
		    	   		if(point_number>10){point_number=10;}
		    	    		song_url=new String[point_number];
		    	    		song_id=new String[point_number];
		    	    		song_title=new String[point_number];
		    	    		song_title_ch=new String[point_number];
		    	    		Valence=new String[point_number];
		    	    		Arousal=new String[point_number];
		    	    		song_url[0]=anno_song_url[k];
		    	    		song_id[0]=anno_song_id[k];
		    	    		song_title[0]=anno_song_title[k];
		    	    		song_title_ch[0]=anno_song_title_ch[k];
		    	    		Valence[0]=anno_Valence[k];
		    	    		Arousal[0]=anno_Arousal[k]; 
			       				   point_list+=String.valueOf(k)+"-";
			

				   
				  }
			   }
	   	    	
	   	    			if(point_number>0){
	   	    				emotion.playlist.setEnabled(true);
	   	    			}else{
	   	 				emotion.playlist.setEnabled(false);
	   	 			}
				point_list_=point_list.split("-");
			
			}else if(distance==d[n]){
				canvas.drawCircle(((value(anno_Valence[n]))+p.x)/2, ((value(anno_Arousal[n]))+p.y)/2, 1, group_00);
	    			for(int k=0;k<anno_song_id.length;k++){
	    				d[k]=Math.sqrt(Math.pow(((value(anno_Valence[n]))+p.x)/2-(value(anno_Valence[k])), 2)+Math.pow(((value(anno_Arousal[n]))+p.y)/2-(value(anno_Arousal[k])), 2));
	   	    						   if(d[k]<range){ 
    	    		point_number++;
    	    		if(point_number>10){point_number=10;}
    	    		song_url=new String[point_number];
    	    		song_id=new String[point_number];
    	    		song_title=new String[point_number];
    	    		song_title_ch=new String[point_number];
    	    		Valence=new String[point_number];
    	    		Arousal=new String[point_number];
    	    		song_url[0]=anno_song_url[k];
    	    		song_id[0]=anno_song_id[k];
    	    		song_title[0]=anno_song_title[k];
    	    		song_title_ch[0]=anno_song_title_ch[k];
    	    		Valence[0]=anno_Valence[k];
    	    		Arousal[0]=anno_Arousal[k]; 
	       				   point_list+=String.valueOf(k)+"-";
	

		   
		  }
	   }
	    			
		point_list_=point_list.split("-");
		
		if(point_number>0){
				emotion.playlist.setEnabled(true);
			}else{
				emotion.playlist.setEnabled(false);
			}
			}
				}
			}
    		
    	}
		 
    }
    //make sure all coordinate be positive number
    public  int value(String v){
    	if(Integer.parseInt(v)<0)
    		return -Integer.parseInt(v);
    	else
    		return Integer.parseInt(v);
    }
   
    }


