package sen.com.bsdiff;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final int MSG_PATCH_END = 0;
    private String newApkPath,oldApkPath,pacthPath;
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_PATCH_END:
                    // 跳转到系统安装页面
                    //去看对应目录有没生成apk 就行了
//                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                    intent.addCategory(Intent.CATEGORY_DEFAULT);
//                    intent.setDataAndType(Uri.parse(newApkPath),
//                            "application/vnd.android.package-archive");
//                    startActivityForResult(intent, 0);// 如果用户取消安装的话,
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this,"new app ",Toast.LENGTH_LONG).show();
         oldApkPath = getApkInstallDir("sen.com.bsdiff");
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+"Download"+File.separator;
         pacthPath = path+"newApk.pacth";
         newApkPath = path+"newApk.apk";
        if(!new File(newApkPath).exists()){
            try {
                new File(newApkPath).createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void updatePatch(View view){
        Toast.makeText(this,"update ",Toast.LENGTH_LONG).show();
        update();
    }

    public void update(){
       new Thread(){
           @Override
           public void run() {
               BsPatch.patch("pacth",oldApkPath,newApkPath,pacthPath);
               mHandler.sendEmptyMessage(MSG_PATCH_END);
           }
       }.start();



    }

    public String getApkInstallDir(String packeName){
        PackageManager packageManager = this.getPackageManager();
        try {
            ApplicationInfo applicationInfo= packageManager.getApplicationInfo(packeName,0);
            return applicationInfo.sourceDir;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packeName;
    }
}
