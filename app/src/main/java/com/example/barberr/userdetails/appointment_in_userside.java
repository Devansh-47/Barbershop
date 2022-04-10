package com.example.barberr.userdetails;


import java.util.ArrayList;

public class appointment_in_userside {
    String services;
    String shopname;
    String appointment_date;
    String slot;
    String amount;

    public appointment_in_userside(String shopname, String appointment_date, String services , String slot,String amount){
       this.shopname=shopname;
       this.services=services;
       this.appointment_date=appointment_date;
       this.slot=slot;
       this.amount=amount;
    }

    appointment_in_userside(){}

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getAppointment_date() {
        return appointment_date;
    }

    public void setAppointment_date(String appointment_date) {
        this.appointment_date = appointment_date;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
