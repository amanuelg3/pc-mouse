package whale.utility;


/**
 * 类型转换
 * @author whale
 *
 */
public class Convert {
	/**
	 *4字凶整型转字节数组 
	 * @param i
	 * @return
	 */
	public static byte[] intToByteArray(int i) {
		byte[] result = new byte[4];
		result[0] = (byte) ((i >> 24) & 0xFF);
		result[1] = (byte) ((i >> 16) & 0xFF);
		result[2] = (byte) ((i >> 8) & 0xFF);
		result[3] = (byte) (i & 0xFF);
		return result;
	}
}
