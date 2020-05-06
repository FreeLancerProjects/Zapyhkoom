package com.creativeshare.zapyhakoom.Model;

import java.io.Serializable;

public class Data_Model implements Serializable{
    public InnerData getInnerData() {
        return innerData;
    }

    InnerData innerData;


    public class InnerData implements Serializable
    {

        private int id;
        private int product_id;
        private int user_id;
        private int sales_id;
        private String mobile;


        private String longitude;
        private String latitude;
        private String address;
        private String order_total;
        private String kersh_and_mosran;
        private String cutting;
        private String covering;
        private double quantity;
        private String created_at;
        private String updated_at;
        private String order_status;
        private String description;

        public int getSales_id() {
            return sales_id;
        }

        public String getDescription() {
            return description;
        }
        public int getId() {
            return id;
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

        public String getKersh_and_mosran() {
            return kersh_and_mosran;
        }

        public String getCutting() {
            return cutting;
        }

        public String getCovering() {
            return covering;
        }

        public double getQuantity() {
            return quantity;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public String getOrder_status() {
            return order_status;
        }





    }
}
