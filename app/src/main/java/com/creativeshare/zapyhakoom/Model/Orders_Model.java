package com.creativeshare.zapyhakoom.Model;

import java.io.Serializable;
import java.util.List;

public class Orders_Model implements Serializable {




        List<InnerData> innerData;
        public List<InnerData> getData() {
            return innerData;
        }

        public class InnerData implements Serializable {
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

            public String getDescription() {
                return description;
            }

            product product;
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

            public int getSales_id() {
                return sales_id;
            }




            public product getproduct() {
                return product;
            }


            public class product implements Serializable {
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
                private String created_at;
                private String updated_at;

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



            }
        }

    }
