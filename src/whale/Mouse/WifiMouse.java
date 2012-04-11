package whale.Mouse;

import java.net.InetAddress;

import whale.utility.Convert;
import whale.utility.UDPSocket;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class WifiMouse extends Activity {
	
	UDPSocket socket=null;
	GestureDetector mGestureDetector;
	int x,y;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wifimouse);
		mGestureDetector=new GestureDetector(new OnGestureDetectorListener()); 
		
		Intent intent=getIntent();
		String remoteIP=intent.getStringExtra("remoteIP");
		
		try {
			InetAddress remoteAddress = InetAddress.getByName(remoteIP);
			socket = new UDPSocket(3334);
			socket.setRemoteAddress(remoteAddress);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
    public boolean onTouchEvent(MotionEvent event) {
    	
    	if (event.getAction()==0){
    		x=(int)event.getX();
    		y=(int)event.getY();
    	}
    	if (event.getAction()==2){
    		int dx=(int)event.getX()-x;
    		int dy=(int)event.getY()-y;
    		
    		x=(int)event.getX();
    		y=(int)event.getY();
    		
    		try {
				byte[] data=new byte[9];
				System.arraycopy(Convert.intToByteArray(dx), 0, data, 1, 4);
				System.arraycopy(Convert.intToByteArray(dy), 0, data, 5, 4);
				socket.Send(data);
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    	return mGestureDetector.onTouchEvent(event); 
    }
    
    class OnGestureDetectorListener extends GestureDetector.SimpleOnGestureListener{

		@Override
		public boolean onDoubleTap(MotionEvent e) {
			try {
				byte[] data={3,0,0,0,0,0,0,0,0};
				socket.Send(data);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return super.onDoubleTap(e);
		}


		@Override
		public void onLongPress(MotionEvent e) {
			try {
				byte[] data={2,0,0,0,0,0,0,0,0};
				socket.Send(data);
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			super.onLongPress(e);
		}
		
		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			try {
				byte[] data={1,0,0,0,0,0,0,0,0};
				socket.Send(data);
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return super.onSingleTapUp(e);
		}
    }
    
    @Override
    protected void onDestroy() {
    	if (socket!=null) socket.Close();
    	super.onDestroy();
    }

    @Override
    protected void onPause() {
    	finish();
    	super.onPause();
    }
}
