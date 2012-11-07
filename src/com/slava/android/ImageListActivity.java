/**
 * File: ImageListActivity.java
 * Created: 11/7/12
 * Author: Viacheslav Panasenko
 */
package com.slava.android;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.googlecode.flickrjandroid.Flickr;
import com.googlecode.flickrjandroid.FlickrException;
import com.googlecode.flickrjandroid.photos.Photo;
import com.googlecode.flickrjandroid.photos.PhotoList;
import com.googlecode.flickrjandroid.photos.PhotosInterface;
import com.slava.android.ui.ImageListAdapter;
import org.json.JSONException;

import java.io.IOException;

/**
 * ImageListActivity
 * Screen with list of recent images from Flickr.
 */
public class ImageListActivity extends Activity {

    private static final String API_KEY = "58c90530270ab5592857d04e50e4eadf";

    private ProgressDialog mProgressDialog;

    private ListView mList;
    private Flickr mFlickr;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_list);

        mList = (ListView) findViewById(android.R.id.list);

        mFlickr = new Flickr(API_KEY);
        RequestRecentPhotosTask requestRecentPhotos = new RequestRecentPhotosTask();
        requestRecentPhotos.execute();
    }

    /**
     * Shows progress dialog with waiting message.
     */
    public void showProgressDialog() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage(getString(R.string.wait_message));
        mProgressDialog.show();
    }

    /**
     * Hides progress dialog.
     */
    public void hideProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    /**
     * RequestRecentPhotosTask
     * Asynchronous recent photos request to the Flickr API.
     */
    private class RequestRecentPhotosTask extends AsyncTask<Void, Void, PhotoList> {

        @Override
        protected PhotoList doInBackground(Void... voids) {
            PhotosInterface photos = mFlickr.getPhotosInterface();
            PhotoList list = null;
            try {
                list = photos.getRecent(null, 50, 0);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (FlickrException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return list;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
        }

        @Override
        protected void onPostExecute(PhotoList list) {
            super.onPostExecute(list);
            hideProgressDialog();
            if (list != null) {
                ImageListAdapter adapter = new ImageListAdapter(ImageListActivity.this, list);
                mList.setAdapter(adapter);

                // Set onItemClickListener to show full size image
                mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Photo clickedPhoto = (Photo) adapterView.getItemAtPosition(i);
                        Intent showPhoto = new Intent(ImageListActivity.this, ViewImageActivity.class);
                        showPhoto.putExtra(ViewImageActivity.EXTRA_URL, clickedPhoto.getLargeUrl());
                        startActivity(showPhoto);
                    }
                });
            }
        }
    }
}
