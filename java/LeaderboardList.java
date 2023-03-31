package com.example.monitoringsepedaapps;

public class LeaderboardList {
    String nama,poin,level,email,no_telfon,uid;

    public LeaderboardList(){}

    public LeaderboardList(String nama, String poin, String level, String email, String no_telfon, String uid)
    {
        this.nama = nama;
        this.poin = poin;
        this.level = level;
        this.email = email;
        this.no_telfon = no_telfon;
        this.uid = uid;
    }
    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getPoin() {
        return poin;
    }

    public void setPoin(String poin) {
        this.poin = poin;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNo_telfon() {
        return no_telfon;
    }

    public void setNo_telfon(String no_telfon) {
        this.no_telfon = no_telfon;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
