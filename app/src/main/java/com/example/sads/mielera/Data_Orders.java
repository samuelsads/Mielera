package com.example.sads.mielera;

/**
 * Created by sads on 22/07/16.
 */
public class Data_Orders {
    public String nombre;
    public int IconId;
    public String fecha;
    public String cantidad;
    public String descuento;
    public String size;



    public Data_Orders(String nombre, int IconId, String fecha, String cantidad, String descuento, String size) {
        this.nombre = nombre;
        this.IconId = IconId;
        this.fecha = fecha;
        this.cantidad=cantidad;
        this.descuento=descuento;
        this.size = size;

    }
    public String GetSize() {
        return size;
    }

    public String getNombre() {
        return nombre;
    }

    public int getIconId() {
        return IconId;
    }

    public String getFecha() {
        return fecha;
    }

    public String getCantidad() {
        return cantidad;
    }

    public String getDescuento() {
        return descuento;
    }
}
