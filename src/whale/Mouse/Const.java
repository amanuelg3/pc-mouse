package whale.Mouse;

import android.os.Environment;

public class Const {
	public static final int SERVER_PORT=3335;
	public static final int LOCAL_PORT=3334;
	public static final String PATH_ROOT=Environment.getExternalStorageDirectory().getPath();
	public static final String[] PATH_CREATE={"whale","mousekeyboard","config.xml"};
	public static final String CONFIG_FULLNAME=Environment.getExternalStorageDirectory().getPath()+"/whale/mousekeyboard/config.xml";
	public static final Config CONFIG=Config.GetConfig();
	
	public static boolean ISDOUBLEFINGER=false;
}
