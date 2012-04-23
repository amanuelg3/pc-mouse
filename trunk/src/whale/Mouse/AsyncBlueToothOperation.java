package whale.Mouse;

import java.util.Iterator;
import java.util.Set;

import whale.Communication.BlueTooth;
import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class AsyncBlueToothOperation extends AsyncTask<Integer, Void, Void> {

	Set<BluetoothDevice> bluetoothDevices;
	BlueTooth blueTooth;
	Activity context;
	
	public AsyncBlueToothOperation(Activity context,BlueTooth blueTooth){
		this.blueTooth=blueTooth;
		this.context=context;
		
	}
	
	@Override
	protected Void doInBackground(Integer... params) {
		bluetoothDevices = blueTooth.GetPairList();
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		Resources resource= (Resources) context.getBaseContext().getResources(); 
		if (bluetoothDevices!=null && bluetoothDevices.size()>0){
			int i=0;
			RadioGroup rg=(RadioGroup)context.findViewById(R.id.rgBlueToothList);
			
			for (Iterator<BluetoothDevice> iterator = bluetoothDevices.iterator(); iterator.hasNext();) {
				BluetoothDevice bluetoothDevice = (BluetoothDevice) iterator.next();
				
				//添加分隔线
				if (i!=0){
					LinearLayout llt1=new LinearLayout(context);
					llt1.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 1));
					llt1.setBackgroundResource(R.color.split_line_Black);
					LinearLayout llt2=new LinearLayout(context);
					llt2.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 1));
					llt2.setBackgroundResource(R.color.split_line_White);
					rg.addView(llt1);
					rg.addView(llt2);
				}
				
				//添加一个蓝牙设备到列表
				RadioButton rb=new RadioButton(context);
				rb.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
				rb.setTextColor((ColorStateList) resource.getColorStateList(R.color.setup_list_text));
				rb.setText(bluetoothDevice.getName());
				if (Const.CONFIG!=null && Const.CONFIG.getBluetoothName().equalsIgnoreCase(bluetoothDevice.getName()))
						rb.setChecked(true);
				rg.addView(rb);
				
				i++;
			}
			
			
		}
		super.onPostExecute(result);
	}
}
