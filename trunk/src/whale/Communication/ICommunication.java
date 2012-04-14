package whale.Communication;

public interface ICommunication {
	void Send(byte[] data);
	
	void Close();
}
