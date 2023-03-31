package com.example.monitoringsepedaapps;

public class EducationList {
    String judul,deskripsi,image;

    public EducationList(String judul, String deskripsi, String image) {
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.image = image;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
