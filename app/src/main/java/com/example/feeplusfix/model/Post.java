package com.example.feeplusfix.model;

public class Post {

    String namaBarang, deskripsiBarang, hargaBarang;

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
}
