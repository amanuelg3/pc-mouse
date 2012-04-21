package whale.Communication;

import java.net.InetAddress;
import java.net.UnknownHostException;

import whale.Mouse.Const;
import whale.tools.UDPSocket;

public class Wifi implements ICommunication {

	private UDPSocket socket;
	
	public Wifi() {
		Start();
	}

	@Override
	public void Start() {
		socket=new UDPSocket(Const.LOCAL_PORT);
		try {
			socket.setRemoteAddress(InetAddress.getByName(Const.CONFIG.getServerip()));
			socket.setRemotePort(Const.SERVER_PORT);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void Send(byte[] data) {
		if (socket!=null){
			socket.Send(data);
		}
	}
	
	@Override
	public void Close() {
		// TODO Auto-generated method stub
		socket.Close();
	}
}
