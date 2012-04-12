package whale.train;

import whale.Mouse.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class mutitouch extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wificonnect);
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		int n=event.getPointerCount();
		if (n==2)
		{
			float x=event.getX(1);
			
		}

		return true;
	}
	
	
	
    
}
