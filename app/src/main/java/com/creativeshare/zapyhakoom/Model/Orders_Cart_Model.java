package com.creativeshare.zapyhakoom.Model;


import java.io.Serializable;


public class Orders_Cart_Model implements Serializable {
    private String name;
    private String Image;
    private int product_id;
    private int user_id;
    private String mobile;
    private String longitude;
    private String latitude;
    private String address;
    private String order_total;
    private int kersh_and_mosran;
    private String cutting;
    private String covering;
    private String description;
    private int quantity;

    public String getName() {
        return name;
    }

    public String getImage() {
        return Image;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        Image = image;
    }


    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setOrder_total(String order_total) {
        this.order_total = order_total;
    }

    public void setKersh_and_mosran(int kersh_and_mosran) {
        this.kersh_and_mosran = kersh_and_mosran;
    }

    public void setCutting(String cutting) {
        this.cutting = cutting;
    }

    public void setCovering(String covering) {
        this.covering = covering;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getProduct_id() {
        return product_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getMobile() {
        return mobile;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getAddress() {
        return address;
    }

    public String getOrder_total() {
        return order_total;
    }

    public int getKersh_and_mosran() {
        return kersh_and_mosran;
    }

    public String getCutting() {
        return cutting;
    }

    public String getCovering() {
        return covering;
    }

    public String getDescription() {
        return description;
    }

    public int getQuantity() {
        return quantity;
    }


}

