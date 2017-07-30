package sen.com.dnstudyffmpeg01;

/**
 * Created by Administrator on 2017/7/29.
 */

public class NdkTest {
    public native void diffFile(String inFilePath, String outPath,String subfileName,int count);

    public native void patchFile(String inFilePath, String outPath,String subfileName,int count);

    static {
        System.loadLibrary("native-lib");
    }
}
