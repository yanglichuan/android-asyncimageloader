
package com.example.bittt6;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;

public class MainActivity extends Activity {

    public ImageView iv1;
    public ImageView iv2;
    public ImageView iv3;
    public ImageView iv4;
    public ImageView iv5;
    public ImageView iv6;
    public ImageView iv7;
    public ImageView iv8;
    public ImageView iv9;
    public ImageView iv10;
    
    
    AsynImageLoader bbb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        iv1 = (ImageView) this.findViewById(R.id.iv1);
        iv2 = (ImageView) this.findViewById(R.id.iv2);
        iv3 = (ImageView) this.findViewById(R.id.iv3);
        iv4 = (ImageView) this.findViewById(R.id.iv4);
        iv5 = (ImageView) this.findViewById(R.id.iv5);
        iv6 = (ImageView) this.findViewById(R.id.iv6);
        iv7 = (ImageView) this.findViewById(R.id.iv7);
        iv8 = (ImageView) this.findViewById(R.id.iv8);
        iv9 = (ImageView) this.findViewById(R.id.iv9);
        iv10 = (ImageView) this.findViewById(R.id.iv10);
        
        bbb =new AsynImageLoader();
        String path = "http://pic56.nipic.com/file/20141227/13663111_194028253000_2.jpg";
        bbb.setBitmap(iv1, path, R.drawable.ic_launcher);
        bbb.setBitmap(iv3, "http://pic57.nipic.com/file/20141227/2249845_094001145210_2.jpg", R.drawable.ic_launcher);
        bbb.setBitmap(iv4, path, R.drawable.ic_launcher);
        bbb.setBitmap(iv5, path, R.drawable.ic_launcher);
        bbb.setBitmap(iv6, path, R.drawable.ic_launcher);
        bbb.setBitmap(iv7, path, R.drawable.ic_launcher);
        bbb.setBitmap(iv8, path, R.drawable.ic_launcher);
        bbb.setBitmap(iv9, path, R.drawable.ic_launcher);
        bbb.setBitmap(iv10, path, R.drawable.ic_launcher);
        
        
        handler.sendEmptyMessageDelayed(100, 4000);
    }
    
    //
    public Handler handler = new Handler(){
      public void handleMessage(android.os.Message msg) {
          String path = "http://pic56.nipic.com/file/20141227/13663111_194028253000_2.jpg";
          bbb.setBitmap(iv2, path, R.drawable.ic_launcher);
          
      };
    };
    
    
    
}
