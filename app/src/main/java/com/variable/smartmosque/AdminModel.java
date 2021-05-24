package com.variable.smartmosque;

import java.io.Serializable;

class AdminModel implements Serializable {

 public    String admin_key;
    public   String mosqueName;
    public   String imamName;
    public   String email;
    public   String sect;
    public   String password;
    public   String mosqueLocation;
    public   String latitude;
    public   String longitude;
    public   String mosquImage;
    public   String mosque_city;



    public AdminModel(String admin_key, String mosqueName, String imamName, String email,String sect, String password,String mosqueLocation,String latitude,String longitude, String mosquImage,String mosque_city) {
        this.admin_key = admin_key;
        this.mosqueName = mosqueName;
        this.imamName = imamName;
        this.email = email;
        this.sect = sect;
        this.password = password;
        this.mosqueLocation = mosqueLocation;
        this.latitude = latitude;
        this.longitude = longitude;
        this.mosquImage =  mosquImage;
        this.mosque_city =  mosque_city;
    }

    public AdminModel(String mosqueName, String imamName, String email,String sect, String password,String mosqueLocation,String latitude,String longitude, String mosquImage,String mosque_city) {
        this.mosqueName = mosqueName;
        this.imamName = imamName;
        this.email = email;
        this.sect = sect;
        this.password = password;
        this.mosqueLocation = mosqueLocation;
        this.latitude = latitude;
        this.longitude = longitude;
        this.mosquImage =  mosquImage;
        this.mosque_city =  mosque_city;

    }
}
