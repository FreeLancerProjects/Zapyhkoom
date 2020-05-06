package com.creativeshare.zapyhakoom.Model;

import java.io.Serializable;

public class Setting_Model implements Serializable {

private InnerData innerData;

    public InnerData getInnerData() {
        return innerData;
    }

    public class InnerData implements Serializable {
        private String terms_and_conditions_en;

        private String terms_and_conditions;
        private String bank_details;
        private String created_at;
        private String updated_at;
        private int id;

        public int getId() {
            return id;
        }

        public String getTerms_and_conditions_en() {
            return terms_and_conditions_en;
        }

        public String getTerms_and_conditions() {
            return terms_and_conditions;
        }

        public String getBank_details() {
            return bank_details;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }


    }
}