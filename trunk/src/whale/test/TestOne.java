package whale.test;

import whale.Mouse.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;

public class TestOne extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wificonnect);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		return super.onTouchEvent(event);
	}
}
