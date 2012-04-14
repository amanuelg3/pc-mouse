package whale.Communication;

/**
 * 连接工厂类
 */
public class CommunicationFactory {
	/**
	 * 根据给定的连接类型生成连接器
	 * @param connectionType
	 * @return
	 */
	public static ICommunication NewCommunication(whale.Mouse.Config.ConnectionType connectionType){
		if (connectionType==whale.Mouse.Config.ConnectionType.WIFI)
			return new Wifi();
		else if (connectionType==whale.Mouse.Config.ConnectionType.BLUETOOTH)
			return new BlueTooth(); 
			
		return null;
	}
}
