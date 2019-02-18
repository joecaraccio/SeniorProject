package com.sandbox.sandbox;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.google.ar.core.Anchor;
import com.google.ar.core.Config;
import com.google.ar.core.Frame;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.core.Pose;
import com.google.ar.core.Session;
import com.google.ar.core.Trackable;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.HitTestResult;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

//https://github.com/google-ar/arcore-android-sdk/issues/110
/*
    you can create an anchor at any pose

 */

/**
 * This is an example activity that uses the Sceneform UX package to make common AR tasks easier.
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final double MIN_OPENGL_VERSION = 3.0;

    private ArFragment arFragment;
    private ModelRenderable andyRenderable;
    private GestureDetector gestureDetector;
    private Session session;

    private FloatingActionButton fab;

    //Toolbox Variables
    private RecyclerView tb_recyclerView;
    private RecyclerView.Adapter tb_mAdapter;
    private RecyclerView.LayoutManager tb_layoutManager;

    @Override
    @SuppressWarnings({"AndroidApiChecker", "FutureReturnValueIgnored"})
    // CompletableFuture requires api level 24
    // FutureReturnValueIgnored is not valid
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("joe","Main Camera View Started");
        if (!checkIsSupportedDeviceOrFinish(this)) {
            return;
        }

        setContentView(R.layout.activity_ux);
        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment);
        try{
            Log.i("joe", "Create New Session");
            session = new Session(this);
            Config c = new Config(session);
            c.setUpdateMode(Config.UpdateMode.LATEST_CAMERA_IMAGE);
            session.configure(c);
            Log.i("joe", "Supposibly Succesful");
            //arFragment.upd

        }catch(Exception e){
            Log.i("joe", "Unable to Create Session");
        }
        arFragment.getArSceneView().setupSession(session);

        //floating action button used for opening toolbar
        /*
        fab = (FloatingActionButton) findViewById(R.id.toolbarbutton);
        fab.setAlpha(0.30f);
        fab.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               FloatingActionPress();
           }
       });
       */

        SlidingPaneLayout spl = (SlidingPaneLayout) findViewById(R.id.slidingplanelay);
        spl.setEnabled(false); //disable slider


        //toolbar
        tb_recyclerView = (RecyclerView) findViewById(R.id.toolbarlist);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        tb_recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        tb_layoutManager = new LinearLayoutManager(this);
        tb_recyclerView.setLayoutManager(tb_layoutManager);

        // specify an adapter (see also next example)
        String[] t = new String[10];
        t[0] = "j";
        t[1] = "k";
        t[2] = "k";
        t[3] = "k";
        t[4] = "k";
        t[5] = "k";
        t[6] = "k";
        t[7] = "k";
        t[8] = "k";
        t[9] = "k";

        tb_mAdapter = new MyAdapter(t);
        tb_recyclerView.setAdapter(tb_mAdapter);


        // When you build a Renderable, Sceneform loads its resources in the background while returning
        // a CompletableFuture. Call thenAccept(), handle(), or check isDone() before calling get().
        ModelRenderable.builder()
                .setSource(this, R.raw.andy)
                .build()
                .thenAccept(renderable -> andyRenderable = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });


        //Node infoNode = new Node();
        //infoNode.setParent(this);

        //Detects Gestures on Screen
        gestureDetector = new GestureDetector(
                this,
        new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                onSingleTap(e);
                return true;
            }

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }
        });



        arFragment.getArSceneView().getScene().setOnTouchListener(
                        (HitTestResult hitTestResult, MotionEvent event) -> {


                            //detect tap on the scene
                            return gestureDetector.onTouchEvent(event);
                        });

        //https://heartbeat.fritz.ai/build-you-first-android-ar-app-with-arcore-and-sceneform-in-5-minutes-af02dc56efd6
        arFragment.getArSceneView().getScene().addOnUpdateListener( frameTime -> {
            arFragment.onUpdate(frameTime);
            onUpdate();
        });

        arFragment.setOnTapArPlaneListener(
                (HitResult hitResult, Plane plane, MotionEvent motionEvent) -> {
                    if (andyRenderable == null) {
                        Log.i("joe","Andy Renderable == null");
                        return;
                    }

                    CreateObject(hitResult, plane, motionEvent);

                    // Create the Anchor.
                    Anchor anchor = hitResult.createAnchor();
                    AnchorNode anchorNode = new AnchorNode(anchor);
                    anchorNode.setParent(arFragment.getArSceneView().getScene());

                    // Create the transformable andy and add it to the anchor.
                    TransformableNode andy = new TransformableNode(arFragment.getTransformationSystem());
                    andy.setParent(anchorNode);
                    andy.setRenderable(andyRenderable);
                    andy.select();
                });
    }


    //Tap on Screen
    private void onSingleTap(MotionEvent tap) {
        //tap.
        Log.i("joe","onSingleTap");
        //arFragment.getArSceneView().getScene().getCamera().get


        //Create a Pose inview of the camera

        Pose currentPose = arFragment.getArSceneView().getArFrame().getAndroidSensorPose().compose(Pose.makeTranslation(0,0,-1.0f)).extractTranslation();
        Anchor anchor = session.createAnchor(currentPose);
        AnchorNode anchorNode = new AnchorNode(anchor);
        anchorNode.setParent(arFragment.getArSceneView().getScene());
        Node n1 = new Node();
        n1.setEnabled(true);

        ViewRenderable.builder().setView(this, R.layout.sandboxus_test).build()
                .thenAccept(
                        (renderable) -> {
                            n1.setRenderable(renderable);

                            TextView textView = (TextView) renderable.getView();
                            textView.setText("bron");

                        })
                .exceptionally(
                        (throwable) -> {
                            throw new AssertionError("Could not load plane card view.", throwable);
                        });

        anchorNode.addChild(n1);

        //Enable our Adjuster Toolbar

        /*
        Frame frame = arFragment.getArSceneView().getArFrame();
        if(frame != null){
            Log.i("joe", "Lets try this frame stuff");
            for( HitResult hit : frame.hitTest(tap)){
                Log.i("joe", "-- For HitResult");
                Trackable trackable = hit.getTrackable();
                Anchor anchor = hit.createAnchor();
                AnchorNode anchorNode = new AnchorNode(anchor);
                anchorNode.setParent(arFragment.getArSceneView().getScene());

                Node n1 = new Node();
                n1.setEnabled(true);

                ViewRenderable.builder().setView(this, R.layout.sandboxus_test).build()
                        .thenAccept(
                        (renderable) -> {
                            n1.setRenderable(renderable);

                            TextView textView = (TextView) renderable.getView();
                            textView.setText("bron");

                        })
                        .exceptionally(
                                (throwable) -> {
                                    throw new AssertionError("Could not load plane card view.", throwable);
                                });

                anchorNode.addChild(n1);
                Log.i("joe","CREATED ANCHOR \n\n\n");

            }


        }
        */

    } //onTap end

        //called when the user creates an object
    public void CreateObject(HitResult hitResult, Plane plane, MotionEvent motionEvent){
        Log.i("joe", "Create Object");
        Anchor anchor = hitResult.createAnchor();
        float[] anchorX = anchor.getPose().getXAxis();
        float[] anchorY = anchor.getPose().getYAxis();
        float[] anchorZ = anchor.getPose().getZAxis();
        Log.i("joe", "Anchor X: " + anchorX.toString());
        Log.i("joe", "Anchor Y: " + anchorY.toString());
        Log.i("joe", "Anchor Z: " + anchorZ.toString());

    }

    //press on the floating action button
    //open our toolbar
    public void FloatingActionPress(){

    }


    //called above..onUpdate for the scene
    //local position would be of parent.. world is of root
    private void onUpdate(){
        Vector3 position =  arFragment.getArSceneView().getScene().getCamera().getWorldPosition();
        Quaternion rotation =  arFragment.getArSceneView().getScene().getCamera().getWorldRotation();
        String logmessage = "Camera Position - X: " + String.valueOf(position.x) + " Y: " + String.valueOf(position.y) + " Z: " + String.valueOf(position.z);
        Log.i("OnUpdate", logmessage);

    }


    //zeros the camera's x,y,z
    private void ZeroWorldPosition(){
        Log.i("joe","camera is zeroed");
        Vector3 v = new Vector3(0,0,0);
        arFragment.getArSceneView().getScene().getCamera().setWorldPosition(v);
    }

    /**
     * Returns false and displays an error message if Sceneform can not run, true if Sceneform can run
     * on this device.
     *
     * <p>Sceneform requires Android N on the device as well as OpenGL 3.0 capabilities.
     *
     * <p>Finishes the activity if Sceneform can not run
     */
    public static boolean checkIsSupportedDeviceOrFinish(final Activity activity) {
        if (Build.VERSION.SDK_INT < VERSION_CODES.N) {
            Log.e(TAG, "Sceneform requires Android N or later");
            Toast.makeText(activity, "Sceneform requires Android N or later", Toast.LENGTH_LONG).show();
            activity.finish();
            return false;
        }
        String openGlVersionString =
                ((ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE))
                        .getDeviceConfigurationInfo()
                        .getGlEsVersion();
        if (Double.parseDouble(openGlVersionString) < MIN_OPENGL_VERSION) {
            Log.e(TAG, "Sceneform requires OpenGL ES 3.0 later");
            Toast.makeText(activity, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG)
                    .show();
            activity.finish();
            return false;
        }
        return true;
    }


}


//Toolbox data adapter
class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private String[] mDataset;

    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ConstraintLayout textView;
        public MyViewHolder(ConstraintLayout v) {
            super(v);
            textView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(String[] myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        ConstraintLayout v = (ConstraintLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.toolbar_item, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        //holder.textView.setText(mDataset[position]);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}