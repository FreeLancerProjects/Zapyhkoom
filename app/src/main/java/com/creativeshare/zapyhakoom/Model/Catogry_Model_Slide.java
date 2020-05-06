package com.creativeshare.zapyhakoom.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Catogry_Model_Slide


  implements Serializable {


        ArrayList<InnerData> innerData;

        public ArrayList<InnerData> getData() {
            return innerData;
        }

        public class InnerData implements Serializable {
            private int id;
            private String name;

            private String image;
            private String created_at;
            private String updated_at;

            public int getId() {
                return id;
            }

            public String getName() {
                return name;
            }

            public String getImage() {
                return image;
            }

            public String getCreated_at() {
                return created_at;
            }

            public String getUpdated_at() {
                return updated_at;
            }
        }
    }
