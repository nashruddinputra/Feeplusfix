package com.example.feeplusfix.model;

import java.io.Serializable;

public class Post implements Serializable {

    String namaBarang, deskripsiBarang, hargaBarang;

    String userId;

    public Post(){

    }

    public Post(String namaBarang, String deskripsiBarang, String hargaBarang) {
        this.namaBarang = namaBarang;
        this.deskripsiBarang = deskripsiBarang;
        this.hargaBarang = hargaBarang;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public String getDeskripsiBarang() {
        return deskripsiBarang;
    }

    public void setDeskripsiBarang(String deskripsiBarang) {
        this.deskripsiBarang = deskripsiBarang;
    }

    public String getHargaBarang() {
        return hargaBarang;
    }

    public void setHargaBarang(String hargaBarang) {
        this.hargaBarang = hargaBarang;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
