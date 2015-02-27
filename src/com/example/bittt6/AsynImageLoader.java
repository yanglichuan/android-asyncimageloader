
package com.example.bittt6;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

public class AsynImageLoader {
    
    // public HashMap<String, SoftReference<Bitmap>> caches = new
    // HashMap<String, SoftReference<Bitmap>>();
    LruCacheUtils temp = new LruCacheUtils();
    public HashMap<String, MyAsync> tasks = new HashMap<String, MyAsync>();
    Bitmap bbb = null;

    public void setBitmap(final ImageView iv, final String path, final int resId) {
        if ((bbb = temp.getBitmapFromMemCache(path)) != null) {
            iv.setImageBitmap(bbb);
        } else {
            Bitmap t = null;
            if ((t = getBitmapFromYlcDisk(path)) != null) {
                iv.setImageBitmap(t);
                temp.addBitmapToMemCache(path, t);
                return;
            }
            iv.setImageResource(resId);
            if (tasks.containsKey(path)) {
                MyAsync async = tasks.get(path);
                async.imageViews.add(iv);
                return;
            } else {
                MyAsync a = new MyAsync();
                a.path = path;
                a.restId = resId;
                a.imageViews.add(iv);
                a.executett();
            }
        }
    }

    public static Bitmap getBitmapFromYlcDisk(String string) {
        // 去disk中去查找
        FileInputStream fis = null;
        Bitmap bitmap = null;
        try {
            File f = new File(Environment.getExternalStorageDirectory(), "ylc"
                    + string.substring(string.lastIndexOf("/")));
            if (f.exists()) {
                fis = new FileInputStream(f);
                bitmap = BitmapFactory.decodeStream(fis);
                return bitmap;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    public static Bitmap getNetWorkBitmap(String urlString) {
        URL imgUrl = null;
        Bitmap bitmap = null;
        try {
            imgUrl = new URL(urlString);
            // 使用HttpURLConnection打开连接
            HttpURLConnection urlConn = (HttpURLConnection) imgUrl
                    .openConnection();
            urlConn.setDoInput(true);
            urlConn.connect();
            // 将得到的数据转化成InputStream
            InputStream is = urlConn.getInputStream();
            // 将InputStream转换成Bitmap

            File directory = Environment.getExternalStorageDirectory();
            File dst = new File(directory, "ylc" + urlString.substring(urlString.lastIndexOf("/")));
            FileOutputStream fos = new FileOutputStream(dst);
            byte[] bs = new byte[1024];
            int ch = 0;
            while ((ch = is.read(bs, 0, 1024)) > 0) {
                fos.write(bs, 0, ch);
            }
            fos.close();

            is.close();
            bitmap = BitmapFactory.decodeStream(new FileInputStream(dst));
            Log.i("mini", dst.getAbsolutePath());
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            System.out.println("[getNetWorkBitmap->]MalformedURLException");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("[getNetWorkBitmap->]IOException");
            e.printStackTrace();
        }
        return bitmap;
    }

    class MyAsync extends AsyncTask<Void, Void, Bitmap> {
        public int restId;
        public String path;
        public ArrayList<ImageView> imageViews = new ArrayList<ImageView>();

        @Override
        protected Bitmap doInBackground(Void... params) {
            return getNetWorkBitmap(path);
        }

        protected void onPostExecute(Bitmap result) {
            if (result == null) {
                for (ImageView iv : imageViews) {
                    iv.setImageResource(restId);
                }
            } else {
                for (ImageView iv : imageViews) {
                    iv.setImageBitmap(result);
                }
                // caches.put(path, new SoftReference<Bitmap>(result));
                temp.addBitmapToMemCache(path, result);
            }
        };

        public void executett() {
            this.execute();
            tasks.put(path, this);
        }
    }
}
