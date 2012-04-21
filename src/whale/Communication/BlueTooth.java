package whale.Communication;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;
import whale.Mouse.Const;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;


public class BlueTooth implements ICommunication {
	
	private BluetoothAdapter adapter;
	private BluetoothDevice device;
	private BluetoothSocket socket;
	private OutputStream os;
	
	public BluetoothAdapter getAdapter() {
		return adapter;
	}

	public void setAdapter(BluetoothAdapter adapter) {
		this.adapter = adapter;
	}

	public BlueTooth(){
		Start();
	}

	/**
	 * 取得配对的蓝牙列表
	 */
	public Set<BluetoothDevice> GetPairList(){
		if (adapter==null)
			adapter=BluetoothAdapter.getDefaultAdapter();
		if (adapter!=null){
			Set<BluetoothDevice> bluetoothDevices=adapter.getBondedDevices();
			if (bluetoothDevices.size()>0){
				return bluetoothDevices;	
			}
		}
		return null;
	}
	
	@Override
	public void Start() {
		String message=null;
		adapter=BluetoothAdapter.getDefaultAdapter();
		if (adapter.isEnabled())
		{
			Set<BluetoothDevice> bluetoothDevices=adapter.getBondedDevices();
			if (bluetoothDevices.size()>0){
				for (Iterator<BluetoothDevice> iterator = bluetoothDevices.iterator(); iterator.hasNext();) {
					BluetoothDevice bluetoothDevice = (BluetoothDevice) iterator.next();
					if (bluetoothDevice.getName().equals(Const.CONFIG.getBluetoothName()))
					{
						device=bluetoothDevice;
						break;
					}
				}
			}
			//设置远程蓝牙设备
		}else{
			message="OpenBlueTooth";
		}
		
		try {
			if (device!=null){
				socket=device.createRfcommSocketToServiceRecord(UUID.fromString("3887895d-6bd2-4411-88af-d7156010cf79"));
				socket.connect();
				os=socket.getOutputStream();
				//连接远程蓝牙，并建输出流通道
			}else {
				message="没有配对的蓝牙设备";
			}
		} catch (IOException e) {
			message="连接电脑的蓝牙失败";
			e.printStackTrace();
		}
	}

	@Override
	public void Send(byte[] data) {
		if (os!=null){
			try {
				os.write(data);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void Close() {
		// TODO Auto-generated method stub
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
