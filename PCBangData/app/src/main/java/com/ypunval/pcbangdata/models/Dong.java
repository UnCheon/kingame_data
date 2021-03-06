package com.ypunval.pcbangdata.models;



import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Dong extends RealmObject {
    @PrimaryKey
    private int id;
    private String name;
    private float latitude;
    private float longitude;
    private Doe doe;
    private Si si;


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

    public Doe getDoe() {
        return doe;
    }

    public void setDoe(Doe doe) {
        this.doe = doe;
    }

    public Si getSi() {
        return si;
    }

    public void setSi(Si si) {
        this.si = si;
    }
}
