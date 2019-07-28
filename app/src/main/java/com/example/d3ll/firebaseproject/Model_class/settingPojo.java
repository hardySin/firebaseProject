package com.example.d3ll.firebaseproject.Model_class;

public class settingPojo {
    private String status;
    private String user_info;
    private String Image;
    private String Thumbnail;
    private String titleKey;



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUser_info() {
        return user_info;
    }

    public void setUser_info(String user_info) {
        this.user_info = user_info;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        Thumbnail = thumbnail;
    }

    public String gettitleKey() {
        return titleKey;
    }

    public void settitleKey(String titleKey) {
        this.titleKey= titleKey;
    }
}
