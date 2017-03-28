package com.example.sads.mielera;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sads on 1/07/16.
 */
public class LlenarPersonal {
    String content,idPer;
    String[]persona;
    JSONArray json;
    JSONObject jsonObject;
    int id;
    String per;

    public LlenarPersonal(String content, String per){
    this.content= content;
        this.per= per;
    }

public String ObtenerIdPersona(int i){
    try {

        json = new JSONArray(content);
        jsonObject = json.getJSONObject(i);
        idPer=jsonObject.getString("idProduct");



    } catch (JSONException e) {
        e.printStackTrace();
    }
    return idPer;
}


    public String[] LlenarContenido(){
        try {
            json = new JSONArray(content);
            persona= new String[json.length()];
            for (int i=0; i < json.length(); i++) {
                jsonObject = json.getJSONObject(i);
                persona[i]=jsonObject.getString("Size");
                if(persona[i].equals(per)){
                    SetID(i);

                  }

             }

         } catch (JSONException e) {
            e.printStackTrace();
         }
            return persona;
        }


    public int GetID(){

        return id;
    }



    public void SetID(int position){
        this.id=position;
    }

}
