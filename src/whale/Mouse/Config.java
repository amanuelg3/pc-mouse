package whale.Mouse;

import java.io.File;
import java.io.FileOutputStream;
import com.thoughtworks.xstream.XStream;

public class Config {
	/*
	 *���캯�� 
	 */
	public Config() {

	}
	/**
	 *  ��Ա
	 */
	private ConnectionType connectionType;
	private String serverip;
	private String bluetoothName;

	//#region ������
	/**
	 * @return the connectionType
	 */
	public ConnectionType getConnectionType() {
		return connectionType;
	}

	/**
	 * @param connectionType
	 *            the connectionType to set
	 */
	public void setConnectionType(ConnectionType connectionType) {
		this.connectionType = connectionType;
	}

	/**
	 * @return the serverip
	 */
	public String getServerip() {
		return serverip;
	}

	/**
	 * @param serverip
	 *            the serverip to set
	 */
	public void setServerip(String serverip) {
		this.serverip = serverip;
	}
	
	public String getBluetoothName() {
		return bluetoothName;
	}

	public void setBluetoothName(String bluetoothName) {
		this.bluetoothName = bluetoothName;
	}
	//#endregion

	@Override
	public String toString() {
		return "Config [bluetoothName=" + bluetoothName + ", connectionType="
				+ connectionType + ", serverip=" + serverip + "]";
	}
	

	/**
	 * ͨ��xmlȡ��һ�������ļ�
	 * @return
	 */
	public static Config GetConfig() {
		try {
			File file = new File(Const.CONFIG_FULLNAME);
			if (file.exists()) {
				XStream xStream = new XStream();
				xStream.alias("Config", Config.class);
				return (Config)xStream.fromXML(file);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

	/**
	 * ������д��SD�б���
	 * @param config
	 */
	public void SaveConfig() {
		try {
			File file=new File(Const.CONFIG_FULLNAME);
			if (!file.exists())
			{
				File dirs=new File(Const.PATH_ROOT+"/whale/mousekeyboard");
				dirs.mkdirs();
				file.createNewFile();
			}
			//����ļ������ڣ������ļ�
			
			FileOutputStream stream=new FileOutputStream(file);	
			XStream xStream=new XStream();
			xStream.alias("Config", Config.class);
			xStream.toXML(this, stream);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * ������������
	 */
	public enum ConnectionType{
		WIFI,
		BLUETOOTH;
	}
}
