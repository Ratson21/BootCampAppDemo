package com.example.test_android.model;

public class UserAddress {
    private String user_id;
    private String address;
    private Integer province_id;
    private Integer regencie_id;
    private Integer district_id;
    private Integer village_id;
    private String pos_code;

    @Override
    public String toString() {
        return "UserAddress{" +
                "user_id=" + user_id +
                ", address='" + address + '\'' +
                ", province_id=" + province_id +
                ", regencie_id=" + regencie_id +
                ", district_id=" + district_id +
                ", village_id=" + village_id +
                ", pos_code=" + pos_code +
                '}';
    }

    public UserAddress() {
    }

    public UserAddress(String user_id, String address, Integer province_id, Integer regencie_id, Integer district_id, Integer village_id, String pos_code) {
        this.user_id = user_id;
        this.address = address;
        this.province_id = province_id;
        this.regencie_id = regencie_id;
        this.district_id = district_id;
        this.village_id = village_id;
        this.pos_code = pos_code;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getProvince_id() {
        return province_id;
    }

    public void setProvince_id(Integer province_id) {
        this.province_id = province_id;
    }

    public Integer getRegencie_id() {
        return regencie_id;
    }

    public void setRegencie_id(Integer regencie_id) {
        this.regencie_id = regencie_id;
    }

    public Integer getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(Integer district_id) {
        this.district_id = district_id;
    }

    public Integer getVillage_id() {
        return village_id;
    }

    public void setVillage_id(Integer village_id) {
        this.village_id = village_id;
    }

    public String getPos_code() {
        return pos_code;
    }

    public void setPos_code(String pos_code) {
        this.pos_code = pos_code;
    }
}
