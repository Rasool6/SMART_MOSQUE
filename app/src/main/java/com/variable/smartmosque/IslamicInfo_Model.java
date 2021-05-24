package com.variable.smartmosque;

import java.io.Serializable;

class IslamicInfo_Model implements Serializable {

         public  String islamic_Info_key;
         public  String mosq_key;
         public  String title;
         public  String detail;
         public  String infoSpinner;
         public  String infoImage;
         public  String current_date;


    public IslamicInfo_Model(String islamic_Info_key, String mosq_key, String title, String detail, String infoSpinner, String infoImage, String current_date) {
        this.islamic_Info_key = islamic_Info_key;
        this.mosq_key = mosq_key;
        this.title = title;
        this.detail = detail;
        this.infoSpinner = infoSpinner;
        this.infoImage = infoImage;
        this.current_date = current_date;
    }

    public IslamicInfo_Model(String mosq_key, String title, String detail, String infoSpinner, String infoImage, String current_date) {
        this.mosq_key = mosq_key;
        this.title = title;
        this.detail = detail;
        this.infoSpinner = infoSpinner;
        this.infoImage = infoImage;
        this.current_date = current_date;
    }
}
