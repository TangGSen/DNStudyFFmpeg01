package sen.com.bsdiff;

/**
 * Created by Administrator on 2017/7/30.
 */

public class BsPatch {
    static {
        System.loadLibrary("native-lib");
    }

    public static native void patch(String agr,String oldPath, String newApkPath,String patchFilePath );
}
