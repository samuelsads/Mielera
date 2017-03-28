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
 * Created by sads on 15/06/16.
 */
public class ViewAdapter extends  RecyclerView.Adapter <ViewAdapter.MyViewHolder> implements View.OnLongClickListener {//implement Onlong for add in main class

private Context ctx;
    private View.OnLongClickListener listener;//add a listener long
int pos;
    ArrayList<Information> data ;

    int posicion;
    public ViewAdapter(ArrayList<Information> data, Context context) {
        this.data = data;
        this.ctx= context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_row,parent,false);
        MyViewHolder contenido = new MyViewHolder(view,ctx,data);
        view.setOnLongClickListener(this);//call de event listener of type long
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
        holder.text.setText(data.get(position).getTexto());
        holder.name.setText(data.get(position).getNombre());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }



    public void setOnLongClickListener(View.OnLongClickListener listener) {//create method setOnLong for listener
        this.listener = listener;
    }

    @Override
    public boolean onLongClick(View view) {//call method with override for de method setOnLong
        if(listener != null)
            listener.onLongClick(view);



        return false;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView text;
        TextView name;
        ImageView image;
        ArrayList<Information> da = new ArrayList<Information>();
        Context ctx;

        public MyViewHolder(View itemView, Context ctx, ArrayList<Information> da) {

            super(itemView);
            this.da = da;
            this.ctx = ctx;
            itemView.setOnClickListener(this);

            text = (TextView) itemView.findViewById(R.id.ListText);
            image = (ImageView) itemView.findViewById(R.id.ListIcon);
            name = (TextView) itemView.findViewById(R.id.ListNombre);
        }



        @Override
        public void onClick(View view) {

            posicion = getAdapterPosition();

            Information infor = this.da.get(posicion);
        }






    }


}
