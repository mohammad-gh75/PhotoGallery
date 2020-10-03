package org.maktab.photogallery.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JsonObj {
    String stat;
    Photos photos;

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public Photos getPhotos() {
        return photos;
    }

    public void setPhotos(Photos photos) {
        this.photos = photos;
    }

    public class Photos {
        int page;
        int pages;
        int perpage;
        int total;
        List<Photo> photo;

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public int getPerpage() {
            return perpage;
        }

        public void setPerpage(int perpage) {
            this.perpage = perpage;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }


        public List<Photo> getPhoto() {
            return photo;
        }

        public void setPhoto(List<Photo> photo) {
            this.photo = photo;
        }

        public class Photo {
            String id;
            @Expose
            String owner;
            @Expose
            String secret;
            @Expose
            String server;
            @Expose
            int farm;
            String title;
            @Expose
            int ispublic;
            @Expose
            int isfriend;
            @Expose
            int isfamily;
            String url_s;
            @Expose
            int height_s;
            @Expose
            int width_s;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getUrl_s() {
                return url_s;
            }

            public void setUrl_s(String url_s) {
                this.url_s = url_s;
            }
        }
    }
}
