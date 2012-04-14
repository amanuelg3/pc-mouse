package whale.Mouse;

import whale.Communication.ICommunication;
import whale.tools.Convert;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class OnTouchListener implements View.OnTouchListener {

	private GestureDetector gestureDetector;
	private ICommunication communication;
	private int x, y;

	public OnTouchListener(ICommunication communication) {
		this.communication=communication;
		gestureDetector =new GestureDetector(new OnGestDetectorListener(communication));
	}
	
	@Override
	protected void finalize() throws Throwable {
		communication.Close();
		super.finalize();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		int action = event.getAction();
		if (action == 0) {
			x = (int)event.getX();
			y = (int)event.getY();
		}
		if (action==261){
			x = (int)event.getX(1);
			y = (int)event.getY(1);
		}
		//��ʼ������
		
		if (event.getPointerCount() > 1) { //����Ƕ�㣬��ִ��������������Ĺ���
			if (!Const.ISDOUBLEFINGER) {
				Const.ISDOUBLEFINGER=true;
				communication.Send(new byte[]{4,0,0,0,0,0,0,0,0});
			}else {
				if (action == MotionEvent.ACTION_MOVE)
					MouseMove(event.getX(1), event.getY(1));
			}
		} else { //����ǵ��㣬��ִ��δ��������깦��
			if (Const.ISDOUBLEFINGER && action==MotionEvent.ACTION_UP){
				Const.ISDOUBLEFINGER=false;
				communication.Send(new byte[]{5,0,0,0,0,0,0,0,0});
			}else {
				if (action == MotionEvent.ACTION_MOVE)
					MouseMove(event.getX(), event.getY());
			}
		}
		//�������ƣ��ж��ǵ��������˫�������ǳ�����Ļ�������Ҽ�����
		return gestureDetector.onTouchEvent(event);
	}

	/**
	 * ����ƶ�����
	 */
	private void MouseMove(float nx, float ny) {
		byte[] data = new byte[9];
		System.arraycopy(Convert.intToByteArray((int) nx - x), 0, data, 1,4);
		System.arraycopy(Convert.intToByteArray((int) ny - y), 0, data, 5,4);
		
		x = (int) nx;
		y = (int) ny;
		communication.Send(data);
	}
}
