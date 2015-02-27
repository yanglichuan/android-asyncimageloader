
package com.example.bittt6;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;

public class LruCacheUtils {

    // Default memory cache size in kilobytes
    private static final int DEFAULT_MEM_CACHE_SIZE = 1024 * 1024 * 5; // 5MB
    private LruCache<String, Bitmap> mMemoryCache;

    // Default disk cache size in bytes
    private static final int DEFAULT_DISK_CACHE_SIZE = 1024 * 1024 * 20; // 10MB

    public LruCacheUtils() {
        init();
    }

    private void init() {
        // Set up memory cache
        mMemoryCache = new LruCache<String, Bitmap>(DEFAULT_MEM_CACHE_SIZE) {
            /**
             * Measure item size in kilobytes rather than units which is more
             * practical for a bitmap cache
             */
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                final int bitmapSize = getBitmapSize(bitmap) / 1024;
                return bitmapSize == 0 ? 1 : bitmapSize;
            }
        };
    }

    public void addBitmapToMemCache(String data, Bitmap bitmap) {
        if (data == null || bitmap == null || bitmap.isRecycled()) {
            return;
        }
        // Add to memory cache
        if (mMemoryCache != null && mMemoryCache.get(data) == null) {
            mMemoryCache.put(data, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String data) {
        if (mMemoryCache != null) {
            final Bitmap memBitmap = mMemoryCache.get(data);
            if (memBitmap != null) {
                if (BuildConfig.DEBUG) {
                }

                Log.i("mini", "调用lrucache了");
                return memBitmap;
            }
        }
        return null;
    }

    public static int getBitmapSize(Bitmap bitmap) {
        if (Utils.hasHoneycombMR1()) {
            return bitmap.getByteCount();
        }
        // Pre HC-MR1
        return bitmap.getRowBytes() * bitmap.getHeight();
    }

}
