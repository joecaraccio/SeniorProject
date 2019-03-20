package com.sandbox.sandbox.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sandbox.sandbox.MainActivity;
import com.sandbox.sandbox.R;
import com.sandbox.sandbox.gallery.MainGallery;

import java.util.List;

/**
 * Created by joe on 2/28/19.
 */

public class ImageGalleryAdapter extends RecyclerView.Adapter<ImageGalleryAdapter.ViewHolder> {

    private List<Integer> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private  Context context;

    // data is passed into the constructor
    public ImageGalleryAdapter(Context context, List<Integer> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.cell_image, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Integer image = mData.get(position);
        //holder.myImageview.setImageResource(image);
        //resize image befre hand for better performance
        //holder.myImageview.setImageResource(resizeImage(image));

        Bitmap icon = BitmapFactory.decodeResource(context.getResources(), image);
        Bitmap m = getResizedBitmap(icon, 400, 400);
        holder.myImageview.setImageBitmap(m);

        //picture press
        holder.myImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("joe", "Image Position " + Integer.toString(position) + " Pressed");
                ((MainActivity) context).ImageDialogCallback(position);
            }
        });

    }

    public Bitmap getResizedBitmap(Bitmap image, int bitmapWidth,
                                   int bitmapHeight) {
        return Bitmap.createScaledBitmap(image, bitmapWidth, bitmapHeight,
                true);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
        public ImageView myImageview;
        ViewHolder(View itemView) {
            super(itemView);
            //myTextView = itemView.findViewById(R.id.tvAnimalName);
            myImageview = itemView.findViewById(R.id.img);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {



        }
    }

    // convenience method for getting data at click position
    Integer getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }




}
