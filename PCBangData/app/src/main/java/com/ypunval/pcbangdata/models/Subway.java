package com.ypunval.pcbangdata.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Subway extends RealmObject {
    @PrimaryKey
    private int id;
    private String name;
    private String line;
    private String region;
    private int pcBangCount;
    private float latitude;
    private float longitude;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getPcBangCount() {
        return pcBangCount;
    }

    public void setPcBangCount(int pcBangCount) {
        this.pcBangCount = pcBangCount;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}
