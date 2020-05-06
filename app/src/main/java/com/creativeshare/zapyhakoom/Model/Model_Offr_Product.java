package com.creativeshare.zapyhakoom.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Model_Offr_Product implements Serializable {

    public InnerData getInnerData() {
        return innerData;
    }

    InnerData innerData;

    public class InnerData implements Serializable{
        private int id;
        private String name;

        private String image;
        private String created_at;
        private String updated_at;
        ArrayList<Prods> prods;

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

        public ArrayList<Prods> getData() {
            return prods;
        }
        public class Prods implements Serializable {

            private int id;
            private int category_id;
            private String name;
            private String image;
            private String description;
            private double price;
            private double quantity;
            private String fridge_cutting_price;
            private String quarter_cutting_price;
            private String half_cutting_price;
            private String alife_price;
            private String stand_price;
            private String plastic_price;
            private String plates_price;
            private String kersh_and_mosran_price;
            private  String created_at;
            private String updated_at;
            Pivot pivot;
            public int getId() {
                return id;
            }

            public int getCategory_id() {
                return category_id;
            }

            public String getName() {
                return name;
            }

            public String getImage() {
                return image;
            }

            public String getDescription() {
                return description;
            }

            public double getPrice() {
                return price;
            }

            public double getQuantity() {
                return quantity;
            }

            public String getFridge_cutting_price() {
                return fridge_cutting_price;
            }

            public String getQuarter_cutting_price() {
                return quarter_cutting_price;
            }

            public String getHalf_cutting_price() {
                return half_cutting_price;
            }

            public String getAlife_price() {
                return alife_price;
            }

            public String getStand_price() {
                return stand_price;
            }

            public String getPlastic_price() {
                return plastic_price;
            }

            public String getPlates_price() {
                return plates_price;
            }

            public String getKersh_and_mosran_price() {
                return kersh_and_mosran_price;
            }

            public String getCreated_at() {
                return created_at;
            }

            public String getUpdated_at() {
                return updated_at;
            }

            public Pivot getPivot(){
                return pivot;
            }
            public class Pivot implements Serializable{

                public int getOffer_id() {
                    return offer_id;
                }

                public int getProduct_id() {
                    return product_id;
                }

                int offer_id;
                int product_id;
            }











        }}
}
