/**
 * File: ImageListAdapter.java
 * Created: 11/7/12
 * Author: Viacheslav Panasenko
 */
package com.slava.android.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.googlecode.flickrjandroid.photos.Photo;
import com.googlecode.flickrjandroid.photos.PhotoList;
import com.slava.android.R;
import com.slava.android.utils.ImageDownloader;

/**
 * ImageListAdapter
 * Implementation of the custom adapter for image list.
 */
public class ImageListAdapter extends BaseAdapter {

    private PhotoList mPhotos;
    private LayoutInflater mInflater;
    private ImageDownloader mImageDownloader;

    public ImageListAdapter(Context ctx, PhotoList photos) {
        mPhotos = photos;
        mInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mImageDownloader = new ImageDownloader(ctx.getApplicationContext());
    }

    @Override
    public int getCount() {
        if (mPhotos == null) {
            throw new IllegalStateException("Adapter is not initialized");
        }

        return mPhotos.size();
    }

    @Override
    public Object getItem(int i) {
        if (mPhotos == null || i >= mPhotos.size()) {
            throw new IndexOutOfBoundsException("Adapter doesn't have an item with requested" +
                    "index: " + i);
        }

        return mPhotos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        PhotoHolder holder = null;
        if (view == null) {
            view = mInflater.inflate(R.layout.list_item, viewGroup, false);
            holder = new PhotoHolder(view);
            view.setTag(holder);
        } else {
            holder = (PhotoHolder) view.getTag();
        }

        Photo photo = (Photo) getItem(i);
        mImageDownloader.download(photo.getSmallSquareUrl(), holder.thumbnail, 0);
        holder.name.setText(photo.getTitle());

        return view;
    }

    /**
     * PhotoHolder
     * Helper class for adapter. Represents list item view with its components.
     */
    private class PhotoHolder {

        protected ImageView thumbnail;
        protected TextView name;

        public PhotoHolder(View parent) {
            thumbnail = (ImageView) parent.findViewById(R.id.photo_thumbnail);
            name = (TextView) parent.findViewById(R.id.photo_name);
        }

    }
}
