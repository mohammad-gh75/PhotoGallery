package org.maktab.photogallery.repository;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.maktab.photogallery.controller.fragment.PhotoGalleryFragment;
import org.maktab.photogallery.model.GalleryItem;
import org.maktab.photogallery.model.JsonObj;
import org.maktab.photogallery.network.FlickrFetcher;

import java.util.ArrayList;
import java.util.List;

public class PhotoRepository {

    private static PhotoRepository sInstance;
    private List<GalleryItem> mItems = new ArrayList<>();
    private int mCurrentPage=1;
    private int mPages=1;

    public static PhotoRepository getInstance() {
        if (sInstance == null)
            sInstance = new PhotoRepository();

        return sInstance;
    }

    public int getPages() {
        return mPages;
    }

    public int getCurrentPage() {
        return mCurrentPage;
    }

    public void setCurrentPage(int currentPage) {
        mCurrentPage = currentPage;
    }

    public List<GalleryItem> getItems(int page) {

        String url = FlickrFetcher.generateUrl(page);

        FlickrFetcher flickrFetcher = new FlickrFetcher();
        String jsonBodyString = null;
        try {
            jsonBodyString = flickrFetcher.getString(url);
            Log.d(PhotoGalleryFragment.TAG, jsonBodyString);
//            JSONObject jsonBody = new JSONObject(jsonBodyString);
//            mItems = parseJson(jsonBody);
            mItems.addAll(parseJson(jsonBodyString));
//            mItems = parseJson(jsonBodyString);
        } catch (Exception e) {
            Log.e(PhotoGalleryFragment.TAG, e.getMessage(), e);
        }
        return mItems;
    }

    public void setItems(List<GalleryItem> items) {
        mItems = items;
    }

    private PhotoRepository() {

    }

    private List<GalleryItem> parseJson(/*JSONObject jsonBody*/String jsonString) throws JSONException {
        List<GalleryItem> items = new ArrayList<>();

        Gson gson = new Gson();
        JsonObj jsonObj = gson.fromJson(jsonString, JsonObj.class);
        JsonObj.Photos photos = jsonObj.getPhotos();
        mPages=photos.getPages();
        List<JsonObj.Photos.Photo> photoArray = photos.getPhoto();
        for (int i = 0; i < photoArray.size(); i++) {
            JsonObj.Photos.Photo photoObject = photoArray.get(i);
            String id = photoObject.getId();
            String caption = photoObject.getTitle();
            String url = photoObject.getUrl_s();

            GalleryItem item = new GalleryItem(id, caption, url);
            items.add(item);
        }
        return items;
        /*JSONObject photosObject = jsonBody.getJSONObject("photos");
        JSONArray photoArray = photosObject.getJSONArray("photo");
        for (int i = 0; i < photoArray.length(); i++) {
            JSONObject photoObject = photoArray.getJSONObject(i);
            if (!photoObject.has("url_s"))
                continue;

            String id = photoObject.getString("id");
            String caption = photoObject.getString("title");
            String url = photoObject.getString("url_s");*/

    }
}
