package com.example.test_android.model;

public class Village {

    private int id ;
    private int district_id;
    private String name;
    private String alt_name ;
    private String latitude;
    private String longitude;

    @Override
    public String toString() {
        return "Village{" +
                "id=" + id +
                ", district_id=" + district_id +
                ", name='" + name + '\'' +
                ", alt_name='" + alt_name + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }

    public Village() {
    }

    public Village(int id, int district_id, String name, String alt_name, String latitude, String longitude) {
        this.id = id;
        this.district_id = district_id;
        this.name = name;
        this.alt_name = alt_name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(int district_id) {
        this.district_id = district_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlt_name() {
        return alt_name;
    }

    public void setAlt_name(String alt_name) {
        this.alt_name = alt_name;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
