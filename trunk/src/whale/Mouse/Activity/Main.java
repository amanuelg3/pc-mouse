package whale.Mouse.Activity;

import org.apache.http.util.EncodingUtils;

import whale.Communication.CommunicationFactory;
import whale.Communication.ICommunication;
import whale.Mouse.Const;
import whale.Mouse.OnTouchListener;
import whale.Mouse.R;
import whale.Mouse.Config.ConnectionType;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

/**
 * @author zgq
 *
 */
public class Main extends Activity {
	
	private ICommunication communication;
	private InputMethodManager im;
	
	private LinearLayout lltMain,lltInputArea;
	private EditText etKey;
	private ImageButton imbWifi,imbBlueTooth,imbKeyboard,imbAbout,imbSetup,imbExit;
	private Button btnSend,btnBackspace;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); //禁用系统休眠
		im=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		
		lltInputArea=(LinearLayout)findViewById(R.id.lltInputArea);
		lltMain=(LinearLayout)findViewById(R.id.lltMain);
		lltMain.setLongClickable(true); //这句很重要，不然 这个linearlayout不识别GestureDetector
		
		etKey=(EditText)findViewById(R.id.etKey);
		
		imbWifi=(ImageButton)findViewById(R.id.imbWifi);
		imbWifi.setOnClickListener(new OnClickListener());
		imbBlueTooth=(ImageButton)findViewById(R.id.imbBluetooth);
		imbBlueTooth.setOnClickListener(new OnClickListener());
		imbKeyboard=(ImageButton)findViewById(R.id.imbKeyboard);
		imbKeyboard.setOnClickListener(new OnClickListener());
		imbAbout=(ImageButton)findViewById(R.id.imbAbout);
		imbAbout.setOnClickListener(new OnClickListener());
		imbSetup=(ImageButton)findViewById(R.id.imbSetup);
		imbSetup.setOnClickListener(new OnClickListener());
		imbExit=(ImageButton)findViewById(R.id.imbExit);
		imbExit.setOnClickListener(new OnClickListener());
		btnSend=(Button)findViewById(R.id.btnSend);
		btnSend.setOnClickListener(new OnClickListener());
		btnBackspace=(Button)findViewById(R.id.btnBackspace);
		btnBackspace.setOnClickListener(new OnClickListener());
		
		CommunicationSwitch(Const.CONFIG.getConnectionType());
	}
	
	/**
	 *顶部按钮事件 
	 */
	class OnClickListener implements android.view.View.OnClickListener{
		@Override
		public void onClick(View v) {
			
			int vid=v.getId();
			switch (vid) {
			case R.id.imbAbout:
				Dialog dialog=new Dialog(Main.this);
				dialog.setTitle(R.string.txt_Dialog_About_Title);
				dialog.setContentView(R.layout.about);
				dialog.show();
				break;
			case R.id.imbSetup:
				Intent intent=new Intent();
				intent.setAction("Setup");
				intent.setClass(Main.this, Setup.class);
				startActivity(intent);
				break;
			case R.id.imbKeyboard:
				if (lltInputArea.getVisibility()==View.VISIBLE){
					lltInputArea.setVisibility(View.INVISIBLE);
					im.hideSoftInputFromWindow(etKey.getWindowToken(), 0);
				}else{
					lltInputArea.setVisibility(View.VISIBLE);
					im.showSoftInput(etKey, 0);	
				}
				break;
			case R.id.imbWifi:
				CommunicationSwitch(ConnectionType.WIFI);
				break;
			case R.id.imbBluetooth:
				CommunicationSwitch(ConnectionType.BLUETOOTH);
				break;
			case R.id.btnSend:
				String str = etKey.getText().toString();
				byte[] data = EncodingUtils.getBytes(str, "utf-8");
				byte[] buffer = new byte[data.length + 1];
				buffer[0] = 100;
				System.arraycopy(data, 0, buffer, 1, data.length);
				communication.Send(buffer);
				etKey.setText("");
				break;
			case R.id.btnBackspace:
				communication.Send(new byte[]{101});
				break;
			default:
				finish();
				break;
			}
		}
	}
	
	/**
	 * 连接类型切换
	 */
	private void CommunicationSwitch(ConnectionType connectionType){
		if (connectionType==ConnectionType.BLUETOOTH)
		{
			imbWifi.setBackgroundResource(R.drawable.wifi);
			imbBlueTooth.setBackgroundResource(R.drawable.bluetoothlight);
			Const.CONFIG.setConnectionType(ConnectionType.BLUETOOTH);
		}else{
			imbWifi.setBackgroundResource(R.drawable.wifilight);
			imbBlueTooth.setBackgroundResource(R.drawable.bluetooth);
			Const.CONFIG.setConnectionType(ConnectionType.WIFI);
		}

		if (connectionType!=Const.CONFIG.getConnectionType()) Const.CONFIG.SaveConfig();
	}
	
	@Override
	protected void onResume() {
		if (communication!=null) communication.Close();
		communication= CommunicationFactory.NewCommunication(Const.CONFIG.getConnectionType());
		communication.Start();
		lltMain.setOnTouchListener(new OnTouchListener(communication));
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		communication.Close();
		super.onPause();
	}
}
