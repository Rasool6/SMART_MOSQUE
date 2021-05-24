package com.variable.smartmosque;

import java.io.Serializable;

class MosqueProfileModel implements Serializable {

   public   String mosqueProfile_key;
   public  String email;
   public  String password;
    public  String mosque_name;
    public   String mosque_imam;
    public  String mosque_location;
    public  String mosque_city;
    public  String mosque_image;

    public MosqueProfileModel(String mosqueProfile_key, String mosque_city) {
        this.mosqueProfile_key = mosqueProfile_key;
        this.mosque_city = mosque_city;
    }

    public MosqueProfileModel(String mosque_name, String mosque_imam, String mosque_city, String mosque_location, String mosque_image) {
        this.mosque_name = mosque_name;
        this.mosque_imam = mosque_imam;
        this.mosque_city = mosque_city;
        this.mosque_location = mosque_location;
        this.mosque_image = mosque_image;
    }

    public MosqueProfileModel() {
    }

    public MosqueProfileModel(String mosqueProfile_key, String mosque_name, String mosque_imam, String mosque_location, String mosque_city, String mosque_image) {
        this.mosqueProfile_key = mosqueProfile_key;
        this.mosque_name = mosque_name;
        this.mosque_imam = mosque_imam;
        this.mosque_location = mosque_location;
        this.mosque_city = mosque_city;
        this.mosque_image = mosque_image;
    }

    public MosqueProfileModel(String email, String password, String mosque_name, String mosque_imam, String mosque_location, String mosque_city, String mosque_image) {
        this.email = email;
        this.password = password;
        this.mosque_name = mosque_name;
        this.mosque_imam = mosque_imam;
        this.mosque_location = mosque_location;
        this.mosque_city = mosque_city;
        this.mosque_image = mosque_image;
    }

    public MosqueProfileModel(String mosqueProfile_key, String email, String password, String mosque_name, String mosque_imam, String mosque_location, String mosque_city, String mosque_image) {
        this.mosqueProfile_key = mosqueProfile_key;
        this.email = email;
        this.password = password;
        this.mosque_name = mosque_name;
        this.mosque_imam = mosque_imam;
        this.mosque_location = mosque_location;
        this.mosque_city = mosque_city;
        this.mosque_image = mosque_image;
    }
}
