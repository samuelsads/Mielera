package com.example.sads.mielera;

/**
 * Created by sads on 3/08/16.
 */
public class Data_Payment {
    public String quantity_payment;
    public int IconId;
    public String Date;
    public String quantity_received;
    public Data_Payment(String quantity_payment, int IconId, String Date, String quantity_received) {
        this.quantity_payment = quantity_payment;
        this.IconId = IconId;
        this.Date = Date;
        this.quantity_received=quantity_received;


    }
    public String getQuantity_payment() {
        return quantity_payment;
    }

    public int getIconId() {
        return IconId;
    }

    public String getDate() {
        return Date;
    }

    public String getQuantity_received() {
        return quantity_received;
    }


}
