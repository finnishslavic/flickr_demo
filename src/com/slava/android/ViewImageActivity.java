/**
 * File: ViewImageActivity.java
 * Created: 11/7/12
 * Author: Viacheslav Panasenko
 */
package com.slava.android;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import com.slava.android.utils.ImageDownloader;

/**
 * ViewImageActivity
 * The screen that shows a full size image.
 */
public class ViewImageActivity extends Activity {

    public static final String EXTRA_URL = "com.slava.android.EXTRA_URL";

    private ImageDownloader mImageDownloader;
    private ImageView mPhotoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPhotoView = new ImageView(this);
        setContentView(mPhotoView);

        mImageDownloader = new ImageDownloader(getApplicationContext());

        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey(EXTRA_URL)) {
            String url = extras.getString(EXTRA_URL);
            mImageDownloader.download(url, mPhotoView, 0);
        }
    }

}
