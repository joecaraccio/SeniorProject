package com.sandbox.sandbox.adapters;

import android.content.Context;
import android.media.Image;
import android.opengl.GLSurfaceView;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;


import com.sandbox.sandbox.MainActivity;
import com.sandbox.sandbox.R;
import com.sandbox.sandbox.gallery.MainGallery;

import java.util.List;


public class ModelGalleryAdapter extends RecyclerView.Adapter<ModelGalleryAdapter.ViewHolder> {

    private List<Integer> mData;
    private LayoutInflater mInflater;
    private ModelGalleryAdapter.ItemClickListener mClickListener;
    private  Context context;

    // constructor
    public ModelGalleryAdapter(Context context, List<Integer> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.cell_image, viewGroup, false );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Integer gL = mData.get(i);
        viewHolder.modelView.setBackgroundResource(gL);

        viewHolder.modelView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ((MainActivity) context).ModelDialogCallback(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // GLSurfaceView objects
        private ImageView modelView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            modelView = itemView.findViewById(R.id.model);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

        }
    }


    Integer getItem(int id) {
        return mData.get(id);
    }


    void setClickListener(ModelGalleryAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }


    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
