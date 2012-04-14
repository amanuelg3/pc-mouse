package whale.Mouse.Activity;

import whale.Communication.CommunicationFactory;
import whale.Communication.ICommunication;
import whale.Mouse.Const;
import whale.Mouse.OnTouchListener;
import whale.Mouse.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

/**
 * @author zgq
 *
 */
public class Main extends Activity {
	
	private ICommunication communication;
	LinearLayout lltMain;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		communication = CommunicationFactory.NewCommunication(Const.CONFIG.getConnectionType());
		
		lltMain=(LinearLayout)findViewById(R.id.lltMain);
		lltMain.setOnTouchListener(new OnTouchListener(communication));
		lltMain.setLongClickable(true); //这句很重要，不然 这个linearlayout不识别GestureDetector
	}
	
	@Override
	protected void onResume() {
		if (communication==null)
			communication = CommunicationFactory.NewCommunication(Const.CONFIG.getConnectionType());
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		communication.Close();
		super.onPause();
	}
}
