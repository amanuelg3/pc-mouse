package whale.Mouse.Activity;

import whale.Mouse.Const;
import whale.Mouse.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Setup extends Activity {
	EditText etServerIP;
	Button btn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setup);
		
		etServerIP=(EditText)findViewById(R.id.etServerIP);
		btn=(Button)findViewById(R.id.btn);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Const.CONFIG.setServerip(etServerIP.getText().toString());
				Const.CONFIG.SaveConfig();
				
				Intent intent=new Intent();
				intent.setClass(Setup.this, Main.class);
				startActivity(intent);
			}
		});
	}
	
	@Override
	protected void onPause() {
		Const.CONFIG.setServerip(etServerIP.getText().toString());
		Const.CONFIG.SaveConfig();
		super.onPause();
	}
}
