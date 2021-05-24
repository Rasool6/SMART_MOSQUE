package com.variable.smartmosque;

import java.io.Serializable;

class PraytimeModel implements Serializable {
  public   String praytime_key;
    public  String mosq_key;
    public  String prayerName;
    public  String prayerTime;
    public  String aboutPrayer;
    public  String currentDate;

    public PraytimeModel(String prayerName, String prayerTime, String aboutPrayer, String currentDate) {
        this.prayerName = prayerName;
        this.prayerTime = prayerTime;
        this.aboutPrayer = aboutPrayer;
        this.currentDate = currentDate;
    }

    public PraytimeModel() {
    }
    public PraytimeModel(String praytime_key, String mosq_key, String prayerName, String prayerTime, String aboutPrayer, String currentDate) {
        this.praytime_key = praytime_key;
        this.mosq_key = mosq_key;
        this.prayerName = prayerName;
        this.prayerTime = prayerTime;
        this.currentDate = currentDate;
        this.aboutPrayer= aboutPrayer;
    }

    public PraytimeModel(String mosq_key, String prayerName, String prayerTime,String aboutPrayer, String currentDate) {
        this.mosq_key = mosq_key;
        this.prayerName = prayerName;
        this.prayerTime = prayerTime;
        this.currentDate = currentDate;
        this.aboutPrayer= aboutPrayer;

    }
}
