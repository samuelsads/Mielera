package com.example.sads.mielera;

/**
 * Created by sads on 16/06/16.
 */
public class Information {
   public String Texto;
   public int IconId;
   public String Nombre;

   public Information(String texto, int iconId, String nombre) {
      Texto = texto;
      IconId = iconId;
      Nombre = nombre;
   }



   public String getTexto() {
      return Texto;
   }

   public int getIconId() {
      return IconId;
   }

   public String getNombre() {
      return Nombre;
   }
}