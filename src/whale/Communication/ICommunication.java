package whale.Communication;

public interface ICommunication {
	void Start();
	void Close();
	void Send(byte[] data);
}
