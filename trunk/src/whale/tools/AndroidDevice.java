package whale.tools;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import android.app.Activity;
import android.content.Context;
import android.view.Display;
import android.view.WindowManager;

/**
 * �豸������
 * @author whale 2012-4-10
 *
 */
public final class AndroidDevice {
	/**
	 * ȡ��ǰ��Ļ�Ǻ�����������
	 * @return 0 ����  1����
	 */
	public static int GetWindowOrientation(Activity content){
		Display display=((WindowManager)content.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		return display.getOrientation();
	}
	
	/**
	 * ȡ������IP��ַ
	 */
	public static InetAddress getLocalAddress(){
		try {
			for (Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();en.hasMoreElements();) {
				NetworkInterface ni=en.nextElement();
				for (Enumeration<InetAddress> ias=ni.getInetAddresses(); ias.hasMoreElements();) {
					InetAddress inetAddress=ias.nextElement();
					if (!inetAddress.isLoopbackAddress())
						return inetAddress;
				}	
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return null;
	}
}
