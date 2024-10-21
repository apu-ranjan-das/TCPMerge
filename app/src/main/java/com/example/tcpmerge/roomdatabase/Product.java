package com.example.tcpmerge.roomdatabase;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Product {
    @PrimaryKey
    @NonNull
    public String pid = "";

    @ColumnInfo(name = "pname")
    public String pname;

    @ColumnInfo(name = "price")
    public int price;

    @ColumnInfo(name = "qnt")
    public int qnt;

    public Product(String pid, String pname, int price, int qnt) {
        if (pid != null) this.pid = pid;
        else this.pid = "";
        this.pname = pname;
        this.price = price;
        this.qnt = qnt;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        if (pid != null) this.pid = pid;
        else this.pid = "";
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQnt() {
        return qnt;
    }

    public void setQnt(int qnt) {
        this.qnt = qnt;
    }
}
