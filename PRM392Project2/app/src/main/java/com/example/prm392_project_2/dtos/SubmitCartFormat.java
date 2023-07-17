package com.example.prm392_project_2.dtos;

import java.util.List;

public class SubmitCartFormat {
    public  int  accountId;
    public  int  shipFee;
    public  int totalPrice ;
    public  String address;
    public  boolean isPayed = true;
    public List<DetailsFormat> details;

    public int getAccountId() {
        return accountId;
    }

    public SubmitCartFormat() {
    }

    public SubmitCartFormat(int accountId, int shipFee, int totalPrice, String address, boolean isPayed, List<DetailsFormat> details) {
        this.accountId = accountId;
        this.shipFee = shipFee;
        this.totalPrice = totalPrice;
        this.address = address;
        this.isPayed = isPayed;
        this.details = details;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getShipFee() {
        return shipFee;
    }

    public void setShipFee(int shipFee) {
        this.shipFee = shipFee;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isPayed() {
        return isPayed;
    }

    public void setPayed(boolean payed) {
        isPayed = payed;
    }

    public List<DetailsFormat> getDetails() {
        return details;
    }

    public void setDetails(List<DetailsFormat> details) {
        this.details = details;
    }
}
