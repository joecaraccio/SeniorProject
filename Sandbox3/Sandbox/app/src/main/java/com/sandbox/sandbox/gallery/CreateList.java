package com.sandbox.sandbox.gallery;

/**
 * Created by joe on 2/27/19.
 */

public class CreateList {
    private String image_title;
    private Integer image_id;
    private Integer listIndex;

    public String getImage_title() {
        return image_title;
    }

    public void setImage_title(String android_version_name) {
        this.image_title = android_version_name;
    }

    public Integer getImage_ID() {
        return image_id;
    }

    public void setImage_ID(Integer android_image_url) {
        this.image_id = android_image_url;
    }


    public void setImageIndex(Integer i){
        this.listIndex = i;
    }

    public Integer getImageIndex(){
        return this.listIndex;
    }
}
