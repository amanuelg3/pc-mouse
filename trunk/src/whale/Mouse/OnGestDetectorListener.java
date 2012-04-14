package whale.Mouse;

import whale.Communication.ICommunication;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class OnGestDetectorListener extends GestureDetector.SimpleOnGestureListener {
	
	private ICommunication communication=null;
	
	public OnGestDetectorListener(ICommunication communication) {
		this.communication = communication;
	}

	@Override
	public boolean onDoubleTap(MotionEvent e) {
		communication.Send(new byte[]{3,0,0,0,0,0,0,0,0});
		return super.onDoubleTap(e);
	}

	@Override
	public void onLongPress(MotionEvent e) {
		if (!Const.ISDOUBLEFINGER)
			communication.Send(new byte[]{2,0,0,0,0,0,0,0,0});
		super.onLongPress(e);
	}

	@Override
	public boolean onSingleTapConfirmed(MotionEvent e) {
		communication.Send(new byte[]{1,0,0,0,0,0,0,0,0});
		return super.onSingleTapConfirmed(e);
	}
}
