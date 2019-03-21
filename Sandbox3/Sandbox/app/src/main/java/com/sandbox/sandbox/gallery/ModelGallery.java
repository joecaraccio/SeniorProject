package com.sandbox.sandbox.gallery;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.sandbox.sandbox.MainActivity;
import com.sandbox.sandbox.R;

import java.util.ArrayList;

public class ModelGallery extends AppCompatActivity {

    public final String image_titles[] = {
            "temple_lion",

    };

    public final Integer image_ids[] = {
            R.mipmap.model
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);





        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.imagegallery);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),3);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<CreateList> createLists = prepareData();
        MGalleryAdapter adapter = new MGalleryAdapter(getApplicationContext(), createLists);
        adapter.mContext = this;
        recyclerView.setAdapter(adapter);


    }

    public void Callback(Integer i){
        Log.i("joe", "Picture Selected");
        Intent resultIntent = new Intent();
        resultIntent.putExtra("Selected Picture Index", i);
        setResult(MainActivity.ImagePickResult, resultIntent);
        finish();
    }



    private ArrayList<CreateList> prepareData(){

        ArrayList<CreateList> theimage = new ArrayList<>();
        for(int i = 0; i< image_titles.length; i++){
            CreateList createList = new CreateList();
            createList.setImage_title(image_titles[i]);
            createList.setImage_ID(image_ids[i]);
            createList.setImageIndex(i);
            theimage.add(createList);
        }
        return theimage;
    }

}
