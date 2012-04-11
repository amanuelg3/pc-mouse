package whale.utility;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


/**
 * UDP Socket������
 */
public class UDPSocket {
	private DatagramSocket socket=null; //����udp Э���Socket
	private int remotePort; //����Զ�̶˿ں�

	//#region Զ��IP��ַ
	private InetAddress remoteAddress=null; 
	public InetAddress getRemoteAddress() {
		return remoteAddress;
	}
	public void setRemoteAddress(InetAddress remoteAddress) {
		this.remoteAddress = remoteAddress;
	}
	//#endregion
	
	/**
	 * ���캯��
	 * @param ���ض˿ں�
	 */
	public UDPSocket(int port){
		try {
			remotePort=3334;
			socket=new DatagramSocket(port);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * �ر�Socket
	 */
	public void Close(){
		if (socket!=null && !socket.isClosed())
			socket.close();
	}

	/**
	 * ��������
	 */
	public void Send(byte[] data){
		if (data!=null && data.length>0 && socket!=null){
			DatagramPacket packet=new DatagramPacket(data, data.length, remoteAddress, remotePort);
			try {
				socket.send(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * ���͹㲥��Ϣ
	 */
	public void SendBroadCast(){
		try {
			InetAddress inetAddress=AndroidDevice.getLocalAddress();
			if (inetAddress!=null){
				byte[] ip=inetAddress.getAddress();
				ip[3]=(byte)255;
				remoteAddress=InetAddress.getByAddress(ip);
				
				byte[] data={100,0,0,0,0,0,0,0,100};
				System.arraycopy(inetAddress.getAddress(), 0, data, 1, 4);
				Send(data);
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * �ս�����
	 * @return 
	 */
	public byte[] Receive(){
		byte[] data=new byte[9];
		DatagramPacket pack=new DatagramPacket(data, data.length);
		try {
			socket.receive(pack);
			return pack.getData();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * ȡPc�˵�IP
	 */
	public InetAddress GetServerIP(){
		try {
			SendBroadCast();
			byte[] data=Receive();
			if (data!=null)
			{
				if (data.length==9 && data[0]==100 && data[8]==100){
					byte[] ip=new byte[4];
					System.arraycopy(data, 1, ip, 0, 4);
					return InetAddress.getByAddress(ip);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
