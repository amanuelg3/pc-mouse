package whale.Mouse;

import java.net.InetAddress;
import java.util.regex.Pattern;

import whale.tools.*;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class WifiConnect extends Activity {

	UDPSocket socket = null;
	EditText ettIP;
	Resources resources;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wificonnect);
		resources=getResources();
		
		//自动获取PC的ip
		Thread thread=new Thread(new GetServerIP());
		thread.start();
		
		
		//手动获取PC的ip
		ettIP=(EditText)findViewById(R.id.ettRemoteIP);
		Button btnConnect=(Button)findViewById(R.id.btnConnect);
		btnConnect.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String ipString=ettIP.getText().toString();
				if (Pattern.matches("^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}$", ipString)){
					Go(ipString);
				}else{
					Toast.makeText(WifiConnect.this, resources.getString(R.string.Err_Message_InvildIPFormat), Toast.LENGTH_LONG).show();
				}
			}
		});
	}
	
	/**
	 * 开始起动wifi鼠标
	 */
	private void Go(String remoteip){
		Intent intent=new Intent();
		intent.setClass(WifiConnect.this, WifiMouse.class);
		intent.putExtra("remoteIP", remoteip);
		startActivity(intent);
	}

	/**
	 * 取PC端ip的线程 
	 */
	class GetServerIP implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			socket = new UDPSocket(3335);
			InetAddress serverip = socket.GetServerIP();
			if (serverip!=null){
				Go(serverip.getHostAddress());
			}
		}
	}

	@Override
	protected void onPause() {
		if (socket != null)
			socket.Close();
		finish();
		super.onPause();
	}
}
