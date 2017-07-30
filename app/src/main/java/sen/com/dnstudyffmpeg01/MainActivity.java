package sen.com.dnstudyffmpeg01;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private NdkTest ndkTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ndkTest = new NdkTest();
    }

    /**
     * 拆分
     * @param view
     */
    public void diffFile(View view){

        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"Download"+File.separator;
        String inPath = path+"ffmpeg1.mp4";
        String outPath = path+"diffFile"+File.separator;
        String subfileName = outPath+"ffmpeg1_%d.mp4";
        File outFileDir = new File(outPath);
        if (!outFileDir.exists()){
            outFileDir.mkdir();
        }
        long start = System.currentTimeMillis();
       ndkTest.diffFile(inPath,outPath,subfileName,10);
        Log.e("sen","time:"+(System.currentTimeMillis()-start)/1000);
    }

    /**
     * 合并
     * @param view
     */
    public void patchFile(View view){
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"Download"+File.separator;

        String subPath = path+"diffFile"+File.separator;
        String inFilePath = subPath+"ffmpeg1_%d.mp4";
        String outPath = subPath+"Patchffmpeg.mp4";
        File subPathDir = new File(subPath);
        if (!subPathDir.exists()){
            subPathDir.mkdir();
        }
        long start = System.currentTimeMillis();
        ndkTest.patchFile(inFilePath,outPath,inFilePath,10);
        Log.e("sen","time:"+(System.currentTimeMillis()-start)/1000);
    }



}
