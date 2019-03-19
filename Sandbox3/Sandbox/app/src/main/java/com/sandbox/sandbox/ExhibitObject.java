package com.sandbox.sandbox;

import android.media.Image;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.Node;

/**
 * Contains all ExhibitObjects
 * allows us to make exhibit objects editable
 */

public class ExhibitObject {
    private Node ObjectNode = null;
    private AnchorNode ObjectAnchorNode = null;


    private String objectType = null;

    private boolean Edittable = false;
    private boolean EditMode = false;
    public ImageView image = null;

    //Control panels
    private View view;
    public FloatingActionButton deleteButton;
    public FloatingActionButton downButton;
    public FloatingActionButton upButton;


    public ExhibitObject(AnchorNode an, Node n){
        ObjectAnchorNode = an;
        ObjectNode = n;
    }


    public void SetupImageTouch(){
        if(this.image != null){
            this.image.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent event) {

                    int x = (int) event.getX();
                    int y = (int) event.getY();

                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            //press on image
                            ToggleEditMode();



                            Log.i("joe", "touched down");
                            break;

                    }
                    return true;
                }
            });
        }
    }

    public void ToggleEditMode(){
        if(EditMode == true){
            //Turn off Edit Mode
            EditMode = false;
            this.deleteButton.hide();
            this.downButton.hide();
            this.upButton.hide();
        }else{
            //Turn on Edit Mode
            EditMode = true;
            this.deleteButton.show();
            this.downButton.show();
            this.upButton.show();

        }
    }


    public void SetupControlPanel(View v){
        this.view = v;

        this.deleteButton = v.findViewById(R.id.control_delete);
        this.deleteButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){ delete(); };
        });

        this.downButton = v.findViewById(R.id.control_sizedown);
        this.downButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){ downSize(); };
        });

        this.upButton = v.findViewById(R.id.control_sizeup);
        this.upButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){ upSize(); };
        });

        //Hide all these
        this.deleteButton.hide();
        this.downButton.hide();
        this.upButton.hide();

    }


    //delete the object
    public void delete(){
        Log.i("joe","Object delete press");
        this.ObjectAnchorNode.removeChild(ObjectNode);
    }

    //size up the object
    public void upSize(){
        Log.i("joe", "Sizing Up");
        LinearLayout ll = (LinearLayout) this.view;
        ViewGroup.LayoutParams params = ll.getLayoutParams();
        params.width += 100;
        ll.setLayoutParams(params);
    }

    //size down the object
    public void downSize(){

    }


}
