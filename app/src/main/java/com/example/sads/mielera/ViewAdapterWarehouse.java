package com.example.sads.mielera;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sads on 6/08/16.
 */
public class ViewAdapterWarehouse  extends RecyclerView.Adapter<ViewAdapterWarehouse.MyViewHolder> implements View.OnLongClickListener,View.OnClickListener {
    private Context ctx;
    private View.OnLongClickListener listener;//add a listener long
    private View.OnClickListener listener1;
    int pos;
    ArrayList<Data_Payment> data ;
    int posicion;


    public ViewAdapterWarehouse(ArrayList<Data_Payment> data, Context context) {
        this.data = data;
        this.ctx= context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_bodega,parent,false);
        MyViewHolder contenido = new MyViewHolder(view,ctx,data);
        view.setOnLongClickListener(this);//call de event listener of type long
        view.setOnClickListener(this);
        return contenido;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Drawable drawable;//circle imagen of here
        Bitmap originalBitmap;
        drawable = ctx.getResources().getDrawable(data.get(position).getIconId());
        RoundedBitmapDrawable roundedDrawable;
        originalBitmap = ((BitmapDrawable) drawable).getBitmap();
        roundedDrawable = RoundedBitmapDrawableFactory.create(ctx.getResources(), originalBitmap);
        roundedDrawable.setCornerRadius(originalBitmap.getHeight());
        holder.image.setImageDrawable(roundedDrawable);// here
        //holder.image.setImageResource(data.get(position).getIconId());
        holder.cantidad.setText(data.get(position).getQuantity_payment());
        holder.tamaño.setText(data.get(position).getQuantity_received());
        holder.Date.setText(data.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setOnLongClickListener(View.OnLongClickListener listener) {//create method setOnLong for listener
        this.listener = listener;
    }
    public void setOnClickListener(View.OnClickListener listener) {//create method setOnLong for listener
        this.listener1 = listener;
    }


    @Override
    public boolean onLongClick(View view) {
        if(listener != null)
            listener.onLongClick(view);

        return false;
    }

    @Override
    public void onClick(View view) {
        if(listener1 != null)
            listener1.onClick(view);

    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView Date;
        TextView cantidad;
        TextView tamaño;
        ImageView image;
        ArrayList<Data_Payment> da = new ArrayList<Data_Payment>();
        Context ctx;

        public MyViewHolder(View itemView, Context ctx, ArrayList<Data_Payment> da) {

            super(itemView);
            this.da = da;
            this.ctx = ctx;
            itemView.setOnClickListener(this);

            cantidad = (TextView) itemView.findViewById(R.id.List_Quantity_Payment);
            image = (ImageView) itemView.findViewById(R.id.ListIcon);
            Date = (TextView) itemView.findViewById(R.id.ListFecha);
            tamaño= (TextView)itemView.findViewById(R.id.List_Quantity_received);
        }


        @Override
        public void onClick(View view) {

            posicion = getAdapterPosition();


        }

    }
}
