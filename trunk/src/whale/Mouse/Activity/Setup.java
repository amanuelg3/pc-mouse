package whale.Mouse.Activity;

import whale.Communication.BlueTooth;
import whale.Mouse.AsyncBlueToothOperation;
import whale.Mouse.Const;
import whale.Mouse.R;
import whale.Mouse.Config.ConnectionType;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Setup extends Activity {
	EditText etServerIP;
	Button btnSave;
	RadioGroup rgConnectType,rgBlueToothList;
	Resources resource;  
	boolean isFirst=true;
	BlueTooth blt=new BlueTooth();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setup);
		resource = (Resources) getBaseContext().getResources(); 
		
		rgConnectType=(RadioGroup)findViewById(R.id.rgConnectType);
		rgBlueToothList=(RadioGroup)findViewById(R.id.rgBlueToothList);
		etServerIP=(EditText)findViewById(R.id.etServerIP);
		btnSave=(Button)findViewById(R.id.btnSave);
		btnSave.setOnClickListener(new onBtnSaveClick());
		
		Intent intent=getIntent();
		if (intent.getAction().equalsIgnoreCase("Setup")){
			isFirst=false;
		}else if (Const.CONFIG!=null){
			startActivity(new Intent().setClass(Setup.this, Main.class));
			finish();
			return;		
		}
		Init();
	}
	
	private void Init(){
		BlueToothInit();
		if (Const.CONFIG!=null){
			etServerIP.setText(Const.CONFIG.getServerip());
			rgConnectType.check(Const.CONFIG.getConnectionType()==ConnectionType.WIFI?android.R.id.button1:android.R.id.button2);
		}
	}
	
	private void BlueToothInit(){
		BluetoothAdapter adapter=blt.getAdapter();
		if (adapter.isEnabled()){
			ScanBlueToothDevice();
		}else
		{
			Intent intent=new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(intent, 1);
		}
	}
	
	/**
	 * 显示配对的蓝牙列表
	 * @param blt
	 */
	private void ScanBlueToothDevice() {
		AsyncBlueToothOperation async=new AsyncBlueToothOperation(Setup.this, blt);
		async.execute(1);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode==-1){
			ScanBlueToothDevice();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	/**
	 * 保存设置
	 */
	class onBtnSaveClick implements android.view.View.OnClickListener{
		@Override
		public void onClick(View v) {
			
			int n=rgConnectType.getChildCount();
			for (int i = 0; i < n; i++) {
				View vw = rgConnectType.getChildAt(i);
				if (vw instanceof RadioButton){
					RadioButton rb=(RadioButton)vw;
					if (rb.isChecked()){
						if (rb.getText().toString()==resource.getString(R.string.txt_setup_ConnectType_Wifi)){
							Const.CONFIG.setConnectionType(ConnectionType.WIFI);
						}else {
							Const.CONFIG.setConnectionType(ConnectionType.BLUETOOTH);
						}
					}
				}
			} //取用户选择的连接类型
			
			if (!etServerIP.getText().toString().equalsIgnoreCase("")){
				String ip=etServerIP.getText().toString();
				String expression="^(2[5][0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(2[5][0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(2[5][0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(2[5][0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})$";
				if (ip.matches(expression)){
					Const.CONFIG.setServerip(ip);
				}else{
					Toast.makeText(Setup.this, resource.getString(R.string.Err_Message_InvildIPFormat), Toast.LENGTH_LONG).show();
					return;
				}
			}//取用户输入的ip地址
			
			n= rgBlueToothList.getChildCount();
			for (int i = 0; i < n; i++) {
				View vw = rgBlueToothList.getChildAt(i);
				if (vw instanceof RadioButton){
					RadioButton rb=(RadioButton)vw;
					if (rb.isChecked()){
						Const.CONFIG.setBluetoothName(rb.getText().toString());
					}
				}
			} //取用户选择的蓝牙设备
			Const.CONFIG.SaveConfig();
			
			if(isFirst){
				Intent intent=new Intent();
				intent.setClass(Setup.this, Main.class);
				startActivity(intent);	
			}
			finish();
		}
	}
}
