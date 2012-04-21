package whale.Mouse;

import java.io.File;
import java.io.FileOutputStream;
import com.thoughtworks.xstream.XStream;

public class Config {
	/*
	 *构造函数 
	 */
	public Config() {

	}
	/**
	 *  成员
	 */
	private ConnectionType connectionType;
	private String serverip;
	private String bluetoothName;

	//#region 访问器
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
	 * 通过xml取得一个配置文件
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
	 * 将配置写到SD中保存
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
			//如果文件不存在，创建文件
			
			FileOutputStream stream=new FileOutputStream(file);	
			XStream xStream=new XStream();
			xStream.alias("Config", Config.class);
			xStream.toXML(this, stream);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * 定义连接类型
	 */
	public enum ConnectionType{
		WIFI,
		BLUETOOTH;
	}
}
