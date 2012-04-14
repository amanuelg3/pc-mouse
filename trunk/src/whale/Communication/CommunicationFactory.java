package whale.Communication;

/**
 * ���ӹ�����
 */
public class CommunicationFactory {
	/**
	 * ���ݸ�����������������������
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
