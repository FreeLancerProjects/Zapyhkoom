package com.creativeshare.zapyhakoom.Model;

import java.io.Serializable;

public class UserModel implements Serializable {
   InnerData innerData;


    public InnerData getData() {
        return innerData;
    }

    public class  InnerData implements Serializable{
        private int id;
        private String mobile;
        private String name;
        private String email;
        private String email_verified_at;
        private String created_at;
        private String updated_at;

        public void setRole(String role) {
            this.role = role;
        }

        private String role;

        public String getRole() {
            return role;
        }

        public int getId() {
            return id;
        }

        public String getMobile() {
            return mobile;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public String getEmail_verified_at() {
            return email_verified_at;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }





    }
}
