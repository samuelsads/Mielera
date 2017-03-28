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
 * Created by sads on 22/07/16.
 */
public class ViewAdapterOrders extends RecyclerView.Adapter<ViewAdapterOrders.MyViewHolder> implements View.OnLongClickListener,View.OnClickListener {
    private Context ctx;
    private View.OnLongClickListener listener;//add a listener long
    private View.OnClickListener listener1;
    int pos;
    ArrayList<Data_Orders> data ;

    int posicion;
    public ViewAdapterOrders(ArrayList<Data_Orders> data, Context context) {
        this.data = data;
        this.ctx= context;
    }




    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_orders,parent,false);
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
        holder.name.setText(data.get(position).getNombre());
        holder.fecha.setText(data.get(position).getFecha());
        holder.cantidad.setText(data.get(position).getCantidad());
        holder.descuento.setText(data.get(position).getDescuento());
        holder.tamaño.setText(data.get(position).GetSize());
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
        TextView fecha;
        TextView name;
        TextView cantidad;
        TextView descuento;
        TextView tamaño ;
        ImageView image;
        ArrayList<Data_Orders> da = new ArrayList<Data_Orders>();
        Context ctx;

        public MyViewHolder(View itemView, Context ctx, ArrayList<Data_Orders> da) {

            super(itemView);
            this.da = da;
            this.ctx = ctx;
            itemView.setOnClickListener(this);

            name = (TextView) itemView.findViewById(R.id.ListUsuario);
            image = (ImageView) itemView.findViewById(R.id.ListIcon);
            fecha = (TextView) itemView.findViewById(R.id.ListFecha);
            cantidad= (TextView)itemView.findViewById(R.id.ListCantidad);
            descuento= (TextView)itemView.findViewById(R.id.ListDescuento);
            tamaño = (TextView)itemView.findViewById(R.id.txttamaño);
        }


        @Override
        public void onClick(View view) {

            posicion = getAdapterPosition();


        }

    }
}
