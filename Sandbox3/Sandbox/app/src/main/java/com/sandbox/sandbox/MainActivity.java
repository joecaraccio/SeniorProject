//Need to make the database comunication method
//Also need to store allObjects
//Need to run through slightly different object creation method
package com.sandbox.sandbox;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.core.Pose;
import com.google.ar.core.Session;
import com.google.ar.core.exceptions.CameraNotAvailableException;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.HitTestResult;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.stitch.core.services.mongodb.remote.RemoteDeleteResult;
import com.mongodb.stitch.core.services.mongodb.remote.RemoteInsertManyResult;
import com.mongodb.stitch.core.services.mongodb.remote.RemoteUpdateOptions;
import com.mongodb.stitch.core.services.mongodb.remote.RemoteUpdateResult;
import com.sandbox.sandbox.MongoCom.DBObj;
//import com.sandbox.sandbox.MongoCom.MongoCom;
import com.sandbox.sandbox.MongoCom.LogInfo;
import com.sandbox.sandbox.adapters.ImageGalleryAdapter;
import com.sandbox.sandbox.adapters.SoundGalleryAdapter;
import com.sandbox.sandbox.gallery.CreateList;
import com.sandbox.sandbox.gallery.MainGallery;
import com.sandbox.sandbox.tools.SoundObject;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import com.mongodb.stitch.android.core.auth.StitchUser;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteFindIterable;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoClient;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoCollection;
import com.mongodb.stitch.android.core.Stitch;
import com.mongodb.stitch.android.core.auth.StitchAuth;
import com.mongodb.stitch.android.core.auth.StitchAuthListener;
import com.mongodb.stitch.android.core.StitchAppClient;
import com.mongodb.stitch.android.services.mongodb.remote.SyncFindIterable;
import com.mongodb.stitch.core.auth.providers.anonymous.AnonymousCredential;
import com.mongodb.stitch.core.auth.providers.facebook.FacebookCredential;
import com.mongodb.stitch.core.auth.providers.google.GoogleCredential;
import com.mongodb.stitch.core.services.mongodb.remote.RemoteInsertOneResult;
import com.mongodb.stitch.core.services.mongodb.remote.sync.ChangeEventListener;
import com.mongodb.stitch.core.services.mongodb.remote.sync.DefaultSyncConflictResolvers;
import com.mongodb.stitch.core.services.mongodb.remote.sync.ErrorListener;
import com.mongodb.stitch.core.services.mongodb.remote.sync.internal.ChangeEvent;

import com.google.android.gms.tasks.Task;
import android.support.annotation.NonNull;
//https://github.com/google-ar/arcore-android-sdk/issues/110
/*
    you can create an anchor at any pose

 */


public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final double MIN_OPENGL_VERSION = 3.0;
    public static final int PICK_IMAGE = 1;


    final Context context = this;

    //Activity Results
    public static final int ImagePickResult = 1;


    private ArFragment arFragment;
    private ModelRenderable andyRenderable;
    private GestureDetector gestureDetector;
    private Session session;

    private FloatingActionButton fab;

    //Toolbox Variables
    private RecyclerView tb_recyclerView;
    private RecyclerView.Adapter tb_mAdapter;
    private RecyclerView.LayoutManager tb_layoutManager;

    //collection of scene nodes
    private List<Anchor> SceneNodes;

    //Android Screen Size Information
    int ScreenWidth = 0;
    int ScreenHeight = 0;
    int dialogWindowWidth = 0;
    int dialogWindowHeight = 0;

    boolean Press1 = false;

    //controls whether we can see the buttons or not
    boolean CreateMode = true;



    //Showed when an object is placed
    boolean AdjusterPanelActive = false;

    //Adjuster Panel
    private EditText edit_pos_x;
    private EditText edit_pos_y;
    private EditText edit_pos_z;
    private EditText edit_height;
    private EditText edit_width;

    private Button edit_button_save;
    private Button edit_button_delete;


    //currently selected node
    private Node selectedNode = null;
    private AnchorNode selectedAnchorNode = null;

    //active view.. used for resizing
    private View nodeView = null;

    private LinearLayout ControlPanel;
    private LinearLayout AdjustorPanel;

    //UI Components Map
    //maps our UI Components to variables
    Map<String, Integer> ComponentsMap = new HashMap<String,Integer>();


    private MediaPlayer global_mediaplayer = null;


    //Toolbar Dialogs
    Dialog imageDialog = null;
    Dialog videoDialog = null;
    Dialog soundDialog = null;
    Dialog slideShowDialog = null;
    Dialog modelShowDialog = null;

    private LinearLayout ControlPanelLayout = null;

    private List<SoundObject> SoundObjects;

    private CopyOnWriteArrayList<DBObj> allObjects;
    private ConcurrentLinkedQueue<LogInfo> logInfo;
    private int testModeFlag;
    private int fpsCount;
    private int logSizeCount;
    private int testLogCount;
    private StitchAppClient _client;
    private RemoteMongoClient _mongoClient;
    private RemoteMongoCollection _remoteCollection;

    private String tourName;
    private float creatorHeight;
    private String userID;
    //place holder for music dialog
    int AudioPoint = 0;

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






        LoadElements();

        this._client = Stitch.getDefaultAppClient();
        _mongoClient = this._client.getServiceClient(RemoteMongoClient.factory, "mongodb-atlas");
//        _remoteCollection = _mongoClient.getDatabase("holotours").getCollection("tours");
//        _client.getAuth().loginWithCredential(new AnonymousCredential()).continueWithTask(
//                new Continuation<StitchUser, Task<RemoteUpdateResult>>() {
//
//                    @Override
//                    public Task<RemoteUpdateResult> then(@NonNull Task<StitchUser> task) throws Exception {
//                        if (!task.isSuccessful()) {
//                            Log.e("STITCH", "Login failed!");
//                            throw task.getException();
//                        }
//
//                        //List<Document> docs = new ArrayList<>();
//                        //for( int i =0; i < allObjects.size(); i ++) {
//                        //    if( allObjects.get(i).returnObjType().equals("image"))
//                        //        docs.add(allObjects.get(i).returnDoc());
//                        //}
//                        final Document doc = new Document();
//                        doc.append("tourName", tourName);
//                        doc.append("height", creatorHeight);
//                        return _remoteCollection.insertOne(
//                                doc
//                        );
//                    }
//                });
        //ensure we hide keyboard
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        setContentView(R.layout.activity_ux);

        //Get the Root View of this Activity
        View rootView = getWindow().getDecorView().getRootView();
        rootView.setOnTouchListener(new View.OnTouchListener() {
            Handler handler = new Handler();

            int numberOfTaps = 0;
            long lastTapTimeMs = 0;
            long touchDownMs = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        touchDownMs = System.currentTimeMillis();
                        break;
                    case MotionEvent.ACTION_UP:
                        handler.removeCallbacksAndMessages(null);
                        //not a tap
                        if ((System.currentTimeMillis() - touchDownMs) > ViewConfiguration.getTapTimeout()) {
                            //it was not a tap
                            numberOfTaps = 0;
                            lastTapTimeMs = 0;
                            break;
                        }

                        if (numberOfTaps > 0
                                && (System.currentTimeMillis() - lastTapTimeMs) < ViewConfiguration.getDoubleTapTimeout()) {
                            numberOfTaps += 1;
                        } else {
                            numberOfTaps = 1;
                        }
                        lastTapTimeMs = System.currentTimeMillis();

                        if (numberOfTaps == 3) {
                            //Triple Tap!
                            //if we are in presentation mode this brings us back to edit mode
                            ToggleMode();
                        }
                }

                return true;
            }
        });


        AdjustorPanel = (LinearLayout) findViewById(R.id.adjusterpanel);
        AdjustorPanel.setVisibility(LinearLayout.GONE); //hide to start
        AdjusterPanelActive = false;
        SetupAdjustorPanel();

        ControlPanelLayout = (LinearLayout) findViewById(R.id.cp);
        //ControlPanelLayout.setVisibility(LinearLayout.G)



        //List of sound Objects
        this.SoundObjects = new ArrayList<>();

        //List of DBObjects to keep track of items made
        this.allObjects = new CopyOnWriteArrayList<>();
        this.logInfo = new ConcurrentLinkedQueue<>();

        //flag to know when in testing mode
        this.testModeFlag = 0;
        this.fpsCount = 0;
        this.testLogCount = 0;
        this.logSizeCount = 0;
        //Initalize tourName to be updated later by user
        tourName = "testTour";
        //Initialize height to average
        creatorHeight = 5.8f;

        //Setup Control Panel
        ControlPanel = (LinearLayout) findViewById(R.id.cp);
        if(CreateMode == false){
            ControlPanel.setVisibility(LinearLayout.GONE);
        }else{
            SetupControlPanelButtons();
        }


        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment);
        /*
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
        */

        //Remove that hand gesture early on...'plane discovery'
        arFragment.getPlaneDiscoveryController().hide();
        arFragment.getPlaneDiscoveryController().setInstructionView(null);

        //Nodes in Scene
        SceneNodes = new ArrayList<Anchor>();


        //Calculate the Dialog Sizes
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        ScreenHeight = displayMetrics.heightPixels;
        ScreenWidth = displayMetrics.widthPixels;
        //set dialog sizes
        dialogWindowHeight = (int) (ScreenHeight * 0.85f);
        dialogWindowWidth = (int) (ScreenWidth * 0.85f);

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

        //OnUpdate -
        arFragment.getArSceneView().getScene().addOnUpdateListener( frameTime -> {
            arFragment.onUpdate(frameTime);
            onUpdate();
        });

        /*
        arFragment.getArSceneView().getScene().addOnUpdateListener( frameTime -> {
            arFragment.onUpdate(frameTime);
            onUpdate();
        });
        */


        /*
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
                */

        //Create Tour Name Box
        AlertDialog.Builder inputBox = new AlertDialog.Builder(this);
        inputBox.setTitle("TourName");
        final EditText inputText = new EditText(this);
        inputText.setInputType(InputType.TYPE_CLASS_TEXT);
        inputBox.setView(inputText);
        inputBox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tourName = inputText.getText().toString();
            }
        });
        inputBox.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        inputBox.show();
    }

    //switches between Test Mode and Edit Mode
    public void ToggleMode(){
        if(testModeFlag == 1){
            //We are in Test Mode
            //Change to Edit Mode
            Log.i("joe", "User Triple Tap: Switch to Test Mode");
            testModeFlag = 0;
            testLogCount = 0;
            fpsCount = 0;
            logDump();
        }
    }

    //puts interface elements into list
    private void LoadElements(){
        //Initalize Components Map...map all components to strings
        ComponentsMap.put("test", R.layout.sandboxus_test);
        ComponentsMap.put("image", R.layout.component_image);

        /////////////////////////////////
    }

    //corrects state issues..ensure adjustor panel is open
    private void verifyAdjustorPanel(){
        if(AdjustorPanel.getVisibility() == LinearLayout.GONE){
            AdjusterPanelActive = false;
            ToggleAdjustorPanel();
        }
    }

    //show and hide adjustor panels
    private void ToggleAdjustorPanel(){
        /*
        if(AdjusterPanelActive == true){
            AdjusterPanelActive = false;
            AdjustorPanel.setVisibility(LinearLayout.GONE);
        }else{
            AdjusterPanelActive = true;
            AdjustorPanel.setVisibility(LinearLayout.VISIBLE);
        }
        */
    }

    //Sets up Elements controlling the AdjustorPanel
    private void SetupAdjustorPanel(){
        edit_pos_x = (EditText) findViewById(R.id.et_pos_x);
        edit_pos_y = (EditText) findViewById(R.id.et_pos_y);
        edit_pos_z = (EditText) findViewById(R.id.et_pos_z);
        edit_height = (EditText) findViewById(R.id.et_height);
        edit_width = (EditText) findViewById(R.id.et_width);
        edit_button_save = (Button) findViewById(R.id.node_save_button);
        edit_button_delete = (Button) findViewById(R.id.node_delete_button);


        //Save Button On Click Listener
        edit_button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("joe","Click1");
                EditButtonSaveClick();
            }
        });

        //Delete Button On Click Listener
        edit_button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("joe","Click2");
                EditButtonDeleteClick();
            }
        });


    }

    private void EditButtonSaveClick(){
        if(selectedNode != null && AdjusterPanelActive == true){

            ToggleAdjustorPanel();
            //done with this.. clear variable
            selectedNode = null;
        }
    }

    private void EditButtonDeleteClick(){
        if(selectedNode != null && AdjusterPanelActive == true){
            //remove from scene
            DeleteNode(selectedAnchorNode);


            ToggleAdjustorPanel();
            //make as null
            selectedNode = null;

        }
    }


    //
    private void DeleteNode(AnchorNode an){
        //an.setEnabled(false);
        //an.getAnchor().detach();
        //selectedNode = null;
        //selectedAnchorNode = null;
    }

    private void SetNodeAdjustor(Node n){
        selectedNode = n;


        SetAdjustorPos(n.getWorldPosition().x, n.getWorldPosition().y, n.getWorldPosition().z);

        //if renderable has a Height and Width
        if(nodeView != null){
            Log.i("joe ","Setting Node View");
            int h = nodeView.getLayoutParams().height;
            int w = nodeView.getLayoutParams().width;
            SetAdjustDimensions(h, w);
        }else{
            Log.i("joe","No Node View WTF");
        }




        ToggleAdjustorPanel();
        verifyAdjustorPanel();

    }

    private void SetAdjustorPos(float x, float y, float z){
        edit_pos_x.setText(Float.toString(x));
        edit_pos_z.setText(Float.toString(z));
        edit_pos_y.setText(Float.toString(y));

        //clear focus
        SetupFocus(edit_pos_x);
        SetupFocus(edit_pos_z);
        SetupFocus(edit_pos_y);
        SetupFocus(edit_width);
        SetupFocus(edit_height);




    }

    //set height and width
    private void SetAdjustDimensions(int height, int width){
        edit_width.setText(Integer.toString(width));
        edit_height.setText(Integer.toString(height));
        SetupFocus(edit_width);
        SetupFocus(edit_height);

        //Text Change Responders
        edit_height.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //New Text Values, lets convert to ints and set them
                UpdateViewHeight(10);

            }
        });


    }

    private void logDump()
    {
            _remoteCollection = _mongoClient.getDatabase("holotours").getCollection("tours");
            _client.getAuth().loginWithCredential(new AnonymousCredential()).continueWithTask(
                    new Continuation<StitchUser, Task<RemoteInsertManyResult>>() {

                        @Override
                        public Task<RemoteInsertManyResult> then(@NonNull Task<StitchUser> task) throws Exception {
                            if (!task.isSuccessful()) {
                                Log.e("STITCH", "Login Failed!");
                                throw task.getException();
                            }

                            final List<Document> docs = new ArrayList<>();
                            int length = logInfo.size();
                            for( int i =0; i < length; i ++) {
                                docs.add(logInfo.poll().returnDoc());
                            }
                            //final Document doc = allObjects.get(j).returnDoc();
                            return _remoteCollection.insertMany(
                                  docs
                            );
                        }
                    }
            ).addOnCompleteListener(new OnCompleteListener<RemoteInsertManyResult>() {
                @Override
                public void onComplete(@NonNull Task<RemoteInsertManyResult> task) {
                    if (task.isSuccessful()) {
                        Log.e("NOAH", "Files added");
                        return;
                    }
                    Log.e("STITCH", "Error: " + task.getException().toString());
                    task.getException().printStackTrace();
                }
            });
    }
    private void objectDump()
    {
        _remoteCollection = _mongoClient.getDatabase("holotours").getCollection("objects");
        _client.getAuth().loginWithCredential(new AnonymousCredential()).continueWithTask(new Continuation<StitchUser, Task<RemoteDeleteResult>>() {
            @Override
            public Task<RemoteDeleteResult> then(@NonNull Task<StitchUser> task) throws Exception {
                if (!task.isSuccessful()) {
                    Log.e("STITCH", "Login failed!");
                    throw task.getException();
                }
                Document filter = new Document().append("tourName", tourName);
                return _remoteCollection.deleteMany(filter);
            }
        }).continueWithTask(
                new Continuation<RemoteDeleteResult, Task<RemoteInsertManyResult>>() {

                    @Override
                    public Task<RemoteInsertManyResult> then(@NonNull Task<RemoteDeleteResult> task) throws Exception {
                        if (!task.isSuccessful()) {
                            Log.e("STITCH", "Delete Failed!");
                            throw task.getException();
                        }

                        final List<Document> docs = new ArrayList<>();
                        for( int i =0; i < allObjects.size(); i ++) {
                            docs.add(allObjects.get(i).returnDoc());
                        }
                        //final Document doc = allObjects.get(j).returnDoc();
                        return _remoteCollection.insertMany(
                                docs
                        );
                    }
                }
        ).addOnCompleteListener(new OnCompleteListener<RemoteInsertManyResult>() {
            @Override
            public void onComplete(@NonNull Task<RemoteInsertManyResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "Saved new Tour to DB", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.e("STITCH", "Error: " + task.getException().toString());
                task.getException().printStackTrace();
            }
        });
    }
    private void setupPremades(){
            _remoteCollection = _mongoClient.getDatabase("holotours").getCollection("objects");
            _client.getAuth().loginWithCredential(new AnonymousCredential()).continueWithTask(
                    new Continuation<StitchUser, Task<List<Document>>>() {

                        @Override
                        public Task<List<Document>> then(@NonNull Task<StitchUser> task) throws Exception {
                            if (!task.isSuccessful()) {
                                Log.e("STITCH", "Login failed!");
                                throw task.getException();
                            }

                            List<Document> docs = new ArrayList<>();
                            return _remoteCollection
                                    .find(new Document("tourName", tourName))
                                    .limit(100)
                                    .into(docs);
                        }
                    }
            ).addOnCompleteListener(new OnCompleteListener<List<Document>>() {
                @Override
                public void onComplete(@NonNull Task<List<Document>> task) {
                    if (task.isSuccessful()) {
                        Log.d("STITCH", "Found docs: " + task.getResult().toString() );// + task.getResult().toString());
                        List<Document> docs = task.getResult();
                        for( int i = 0; i < docs.size(); i ++)
                        {
                            if(docs.get(i).size() == 12) {
                                DBObj obj = new DBObj(docs.get(i));
                                allObjects.add(obj);
                                List<String> temp = obj.returnInfo();
                                int index = Integer.parseInt(temp.get(0));
                                if( obj.returnObjType().equals("image"))
                                    SetupImageComponent(index, CreateFaceNode(obj), 1);
                                else if(obj.returnObjType().equals("sound"))
                                    SetupSoundComponent(index, CreateFaceNode(obj), 1);
                            }
                        }
                        testModeFlag = 1;
                        return;
                    }
                    Log.e("STITCH", "Error: " + task.getException().toString());
                    task.getException().printStackTrace();
                }
            });
    }
    //update a view renderables height
    private void UpdateViewHeight(int h){
        if(nodeView != null){
            ViewGroup.LayoutParams params = nodeView.getLayoutParams();
            params.height = h;
            nodeView.requestLayout();
        }
    }


    //ensures intially focus is cleared
    private void SetupFocus(EditText t){
        t.setFocusableInTouchMode(false);
        t.setFocusable(false);
        t.setFocusableInTouchMode(true);
        t.setFocusable(true);
    }


    //Tap on Screen
    //creates an object on the view
    private void onSingleTap(MotionEvent tap) {
            //diabled for now!
            //ScreenPress(tap);


    } //onTap end

    //returns a pose that faces the user's current positon
    private Pose GetFacePose(){
        Log.i("joe", "GetFacePose");
        //makeTranslation(tx,ty,tz)...offsets by  1 unit in the Z direction (forward)
        Pose currentPose = arFragment.getArSceneView().getArFrame().getAndroidSensorPose().compose(Pose.makeTranslation(0,0,-1.0f)).extractTranslation();
        //Pose.makeT

        return null;
    }


    //Creates Node Faceing at Point
    private AnchorNode GetFaceNode(){
        //create a pose slightly in front of camera
        float distanceFromFace = -2.5f;
        Pose currentPose = arFragment.getArSceneView().getArFrame().getAndroidSensorPose().compose(Pose.makeTranslation(0,0,distanceFromFace)).extractTranslation();
        Vector3 WorldPosition = GetCameraPosition();
        float tempCardLocation[] = currentPose.getTranslation();
        Vector3 ElementPosition = new Vector3();
        ElementPosition.set(tempCardLocation[0], tempCardLocation[1], tempCardLocation[2]);

        Vector3 direction = Vector3.subtract(WorldPosition, ElementPosition);
        Quaternion lookRotation = Quaternion.lookRotation(direction, Vector3.up());

        Anchor anchor = arFragment.getArSceneView().getSession().createAnchor(currentPose);

        //Anchor anchor = session.createAnchor(currentPose);
        AnchorNode anchorNode = new AnchorNode(anchor);
        anchorNode.setParent(arFragment.getArSceneView().getScene());
        Node n1 = new Node();
        n1.setEnabled(true);
        n1.setWorldRotation(lookRotation);
        anchorNode.addChild(n1);

        return anchorNode;
    }

    //Creates Node Faceing at Point
    private AnchorNode CreateFaceNode( DBObj doc){
        //create a pose slightly in front of camera
        float[] vec1 = {doc.returnObjLocX(), doc.returnObjLocY(), doc.returnObjLocZ()};
        float[] vec2 = {0f, 0f, 0f, 1f};
        Pose currentPose = new Pose(vec1, vec2);
        Quaternion lookRotation = new Quaternion();
        lookRotation.w = doc.returnW();
        lookRotation.x = doc.returnRoll();
        lookRotation.y = doc.returnPitch();
        lookRotation.z = doc.returnYaw();

        Anchor anchor = arFragment.getArSceneView().getSession().createAnchor(currentPose);

        //Anchor anchor = session.createAnchor(currentPose);
        AnchorNode anchorNode = new AnchorNode(anchor);
        anchorNode.setParent(arFragment.getArSceneView().getScene());
        Node n1 = new Node();
        n1.setEnabled(true);
        n1.setWorldRotation(lookRotation);
        anchorNode.addChild(n1);

        return anchorNode;
    }

    //not currently used
    private void ScreenPress(MotionEvent tap){
        Log.i("joe","onSingleTap");
        //arFragment.getArSceneView().getScene().getCamera().get


        //Create a Pose inview of the camera
        Pose currentPose = arFragment.getArSceneView().getArFrame().getAndroidSensorPose().compose(Pose.makeTranslation(0,0,-1.0f)).extractTranslation();


        Vector3 WorldPosition = GetCameraPosition();

        float tempCardLocation[] = currentPose.getTranslation();
        Vector3 ElementPosition = new Vector3();
        ElementPosition.set(tempCardLocation[0], tempCardLocation[1], tempCardLocation[2]);

        Vector3 direction = Vector3.subtract(WorldPosition, ElementPosition);
        Quaternion lookRotation = Quaternion.lookRotation(direction, Vector3.up());




        Log.i("joe", currentPose.getRotationQuaternion().toString());
        Log.i("joe", currentPose.getXAxis().toString());
        Log.i("joe", currentPose.getYAxis().toString());
        Log.i("joe", currentPose.getZAxis().toString());
        Log.i("joe", currentPose.getTranslation().toString());
        Log.i("joe", "$$$$\n\n");

        Anchor anchor = arFragment.getArSceneView().getSession().createAnchor(currentPose);
        //Anchor anchor = session.createAnchor(currentPose);
        AnchorNode anchorNode = new AnchorNode(anchor);
        anchorNode.setParent(arFragment.getArSceneView().getScene());
        Node n1 = new Node();
        n1.setEnabled(true);
        n1.setWorldRotation(lookRotation);

        //Add to Scene Object collection
        //Add to Datbase
        //this.CreateObject(currentPose);

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
        SceneNodes.add(anchor);
    }


    //User Interface Buttons
    private void CP_Press_Audio(){

    }

    private void CP_Press_Video(){

    }

    private void CP_Press_Slideshow(){

    }

    private void CP_Press_Model(){

    }

    private void CP_Press_Image(){
        Log.i("joe", "Image Button Pressed");

        Log.i("joe","Image Gallery Opened");
        //Launch internal app image gallery
        Intent intent = new Intent(this, MainGallery.class);
        startActivityForResult(intent, ImagePickResult);

    }

    //handles when we return from other activitys
    //we check our global store file for a chancge
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try{
            arFragment.getArSceneView().getSession().resume();

        }catch(Exception e){

        }

        //ResumeSession();
        if (requestCode == ImagePickResult) {
           Log.i("joe","ImagePickResult");
           //Image has been picked
            SetupImageComponent((int) data.getExtras().get("Selected Picture Index"), GetFaceNode(), 0);

        }
    }

    //ensures our AR session has resumed
    private void ResumeSession(){
        Log.i("joe", "resuming session");
        try{
            arFragment.getArSceneView().getSession().pause();
           arFragment.getArSceneView().getSession().resume();
           //arFragment.getArSceneView().getArFrame().t
        } catch (CameraNotAvailableException cae){
            Log.i("joe", "Camera not Available Exception");
        }
        try{
            TimeUnit.SECONDS.sleep(5);

        } catch( Exception e ){

        }

    }


    //Adds sound to list
    private void AddSoundLocation(Node n, int index){
        float y = n.getWorldPosition().y;
        float x = n.getWorldPosition().x;
        float z = n.getWorldPosition().z;
        Log.i("joe", "Add Sound to location " + Float.toString(x) + "-" + Float.toString(y) + "-" + Float.toString(z));
        SoundObject so = new SoundObject(n.getWorldPosition(), index);

        this.SoundObjects.add(so);


    }

    private void SetupSoundComponent(int i, AnchorNode an, int premadeFlag){
        Log.i("joe", "Setup Sound Component for index: " + Integer.toString(i));

        //Setup the Node
        Node n1 = an.getChildren().get(0);
        //Used to add item to the allObject list
        if( premadeFlag == 0){
            CreateObject("sound", i, an, n1, 1.0f);
        }
        this.AddSoundLocation(n1, i);

        /*
        ViewRenderable.builder().setView(this, R.layout.sandbox_ui_sound).build()
                .thenAccept(
                        (renderable) -> {
                            n1.setRenderable(renderable);
                            View view = renderable.getView();
                            int soundID = ResourceLink.soundID[i];
                            final MediaPlayer mp = MediaPlayer.create(this, soundID);
                            view.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Log.i("joe", "Music Player Component Pressed - " + Integer.toString(i));
                                    //toggle music player
                                    if(mp.isPlaying() == false){
                                        Log.i("joe", "Music Player is False.. Start it");
                                        //check where we are on current audio duration
                                        if(AudioPoint == 0){
                                            mp.seekTo(0);
                                        } else if( AudioPoint < mp.getDuration()){
                                            mp.seekTo(AudioPoint);
                                        } else{
                                            //restart the clip
                                            mp.seekTo(0);
                                        }

                                        mp.start();
                                    }else{
                                        Log.i("joe", "Music Playing is True.. Stop it");
                                        mp.pause();
                                        AudioPoint = mp.getCurrentPosition();
                                    }
                                }
                            });

                        })
                .exceptionally(
                        (throwable) -> {
                            throw new AssertionError("Could not load plane card view.", throwable);
                        });
        */

    }


    //Called from the result of our imagePicker
    //passed information of where our image is
    //https://stackoverflow.com/questions/3669239/how-to-drag-an-image-by-touching-in-android
    private void SetupImageComponent(int i, AnchorNode an, int premadeFlag){
        Log.i("joe","Setup Image Component for index: " + Integer.toString(i));

        //Setup the Node
        Node n1 = an.getChildren().get(0);

        ViewRenderable.builder().setView(this, R.layout.sandbox_ui_image).build()
                .thenAccept(
                (renderable) -> {
                    n1.setRenderable(renderable);

                    View view = renderable.getView();
                    //this.nodeView = view;
                    LinearLayout ll = (LinearLayout) view;
                    ViewGroup.LayoutParams params = ll.getLayoutParams();
                    //params.height = 1000;
                    params.width = 600;
                    ll.setLayoutParams(params);



                    //int h = nodeView.getLayoutParams().height;
                    //int w = nodeView.getLayoutParams().width;
                    //SetAdjustDimensions(h, w);

                    ExhibitObject eobject = new ExhibitObject(an, n1);
                    eobject.image = view.findViewById(R.id.imageview1);
                    eobject.image.setImageResource(ResourceLink.image_ids[i]);
                    eobject.SetupImageTouch();
                    eobject.SetupControlPanel(view);


                    /*
                    ImageView im = view.findViewById(R.id.imageview1);
                    //set image to place
                    im.setImageResource(ResourceLink.image_ids[i]);

                    //Setup on Touch Listener .. capture drags
                    im.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {

                            Log.i("joe", "image view has been touched");

                            return false;
                        }
                    });
                    */


                    //Used to add item to the allObject list
                    if( premadeFlag == 0){
                        CreateObject("image",i, an, n1, 1.0f);
                    }
                })
        .exceptionally(
                (throwable) -> {
                    throw new AssertionError("Could not load plane card view.", throwable);
                });


        //Add this Node to allow our adjuster tool to edit it
        //SetNodeAdjustor(n1);
        //selectedAnchorNode = an;
    }


    //sets up the buttons on click methods bottom right
    private void SetupControlPanelButtons(){
        FloatingActionButton cp_audio_fab = (FloatingActionButton) ControlPanel.findViewById(R.id.cp_audio);
        cp_audio_fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SoundSelectorDialog();
            }
        });

        FloatingActionButton cp_video_fab = (FloatingActionButton) ControlPanel.findViewById(R.id.cp_video);
        cp_video_fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CP_Press_Video();
            }
        });

        FloatingActionButton cp_slideshow_fab = (FloatingActionButton) ControlPanel.findViewById(R.id.cp_slides);
        cp_slideshow_fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CP_Press_Slideshow();
            }
        });

        FloatingActionButton cp_model_fab = (FloatingActionButton) ControlPanel.findViewById(R.id.cp_model);
        cp_model_fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CP_Press_Model();
            }
        });

        FloatingActionButton cp_image_fab = (FloatingActionButton) ControlPanel.findViewById(R.id.cp_image);
        cp_image_fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ImageSelectorDialog();
                //CP_Press_Image();
            }
        });

        FloatingActionButton cp_save_fab = (FloatingActionButton) ControlPanel.findViewById(R.id.cp_save);
        cp_save_fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if( allObjects.size() > 0) {
                    Toast.makeText(context, "Saving to DB...", Toast.LENGTH_SHORT).show();
                    objectDump();
                }
            }
        });

        FloatingActionButton cp_playTour_fab = (FloatingActionButton) ControlPanel.findViewById(R.id.cp_playTour);
        cp_playTour_fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if( allObjects.size() <= 0 ) {
                    setupPremades();
                    userID = UUID.randomUUID().toString();;
                    cp_playTour_fab.hide();
                    cp_image_fab.hide();
                    cp_audio_fab.hide();
                    cp_model_fab.hide();
                    cp_save_fab.hide();
                    cp_slideshow_fab.hide();
                    cp_video_fab.hide();

                    ToggleToolbar();
                }
            }
        });

    }

    //show or hide the Toolbar containing all the floating action buttons
    public void ToggleToolbar(){

    }




    //logs where our created object is
    public void CreateObject(String type, int itemIndex, Node anchor, Node childNode, float scale){
        //Used to add item to the allObject list
        List<String> temp = new ArrayList<>();
        //for( int i = 0; i < itemIndex.length; i ++)
        temp.add("" + itemIndex);
        DBObj tempObj = new DBObj(this.tourName, type, anchor.getWorldPosition().x, anchor.getWorldPosition().y, anchor.getWorldPosition().z, childNode.getWorldRotation().x, childNode.getWorldRotation().y, childNode.getWorldRotation().z, childNode.getWorldRotation().w, scale, temp);
        this.allObjects.add(tempObj);

    }


    //Remove a placed object from the database
    public void RemoveImage(String type, int itemIndex, Node anchor, Node childNode, float scale){

    }


    //loads device image picker, allows the user to select an image from gallary
    public void SelectImage(){
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

        startActivityForResult(chooserIntent, PICK_IMAGE);
    }


        //called when the user creates an object
    public void CreateObject1(HitResult hitResult, Plane plane, MotionEvent motionEvent){
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

    //returns camera position
    private Vector3 GetCameraPosition(){
        Vector3 position =  arFragment.getArSceneView().getScene().getCamera().getWorldPosition();
        return position;
    }

    //returns camera quanternion
    private Quaternion GetCameraRotation(){
        Quaternion rotation =  arFragment.getArSceneView().getScene().getCamera().getWorldRotation();
        return rotation;
    }

    //called above..onUpdate for the scene
    //local position would be of parent.. world is of root
    private void onUpdate(){
        Vector3 position =  arFragment.getArSceneView().getScene().getCamera().getWorldPosition();
        Quaternion rotation =  arFragment.getArSceneView().getScene().getCamera().getWorldRotation();
        String logmessage = "Camera Position - X: " + String.valueOf(position.x) + " Y: " + String.valueOf(position.y) + " Z: " + String.valueOf(position.z);
        Log.i("OnUpdate", logmessage);

        //Track if we are in audio range
        this.TrackAudioRange();
        if( testModeFlag == 1)
        {
            if( fpsCount >= 10) {
                if (logInfo.size() >= 10) {
                    logDump();
                    //logSizeCount = 0;
                }

                LogInfo log = new LogInfo(tourName, userID, creatorHeight, position.x, position.y, position.z, rotation.x, rotation.y, rotation.z, rotation.w, testLogCount);
                logInfo.add(log);
                //logSizeCount += 1;
                testLogCount += 1;
                fpsCount = 0;
            }
            fpsCount += 1;
        }

    }


    //calculates the distance between two vectors
    //ignnoring the Y plane is this case
    private float CalculateDistance(Vector3 p1, Vector3 p2){
        float x1 = (p1.x - p2.x) * (p1.x - p2.x);
        float x2 = (p1.z - p2.z) * (p1.z - p2.z);
        float x3 = x1 + x2;
        float f = (float) Math.sqrt(x3);
        return f;
    }
/*

Log.i("joe", "Music Player is False.. Start it");
    //check where we are on current audio duration
                                        if(AudioPoint == 0){
        mp.seekTo(0);
    } else if( AudioPoint < mp.getDuration()){
        mp.seekTo(AudioPoint);
    } else{
        //restart the clip
        mp.seekTo(0);
    }

                                        mp.start();
}else{
        Log.i("joe", "Music Playing is True.. Stop it");
        mp.pause();
        AudioPoint = mp.getCurrentPosition();
        */


    //plays the closest audio that we are currently in range of
    //performs a short, subtle fade out as you leave the range
    //https://stackoverflow.com/questions/38380495/android-studio-mediaplayer-how-to-fade-in-and-out
    private void TrackAudioRange(){
        //NEED TO ADD IN RANGFE
        //add button for play mode

        boolean ChangingTrack = false;
        Vector3 UserPositon =  arFragment.getArSceneView().getScene().getCamera().getWorldPosition();


        //must also be within this range
        float DistanceThreshold = 2.0f;

        int closestIndex = -1;
        float closeDistance = 0;

        int currentPlaying = -1;

        for(int i = 0; i < this.SoundObjects.size(); i++) {
            SoundObject sotemp = this.SoundObjects.get(i);
            float Distance = CalculateDistance(UserPositon, sotemp.SoundPosition);

            Log.i("joe", "Looping Sound " + Integer.toString(i) + " Distance: " + Float.toString(Distance));


            //if within playable range
            if(Distance <= DistanceThreshold){
                if (closestIndex == -1) {
                    closestIndex = i;
                    closeDistance = Distance;
                } else {
                    //Only change if we are closer
                    if (Distance < closeDistance) {
                        closestIndex = i;
                        closeDistance = Distance;
                    }
                }
            }



            //check if this is currently playing
            if(sotemp.playing == true){
                currentPlaying = i;
            }


        }

        //Okay.. we have our closest.. if its not playing lets play it
        //quickly make sure the previous isn't playing
        if(currentPlaying != closestIndex && closestIndex != -1){
            Log.i("joe", "starting a new track");
            if(currentPlaying != -1 && global_mediaplayer != null){
                //we are also pausing an old track
                SoundObject sotemp = this.SoundObjects.get(currentPlaying);
                sotemp.playing = false;
                global_mediaplayer.pause();
                sotemp.duration = global_mediaplayer.getCurrentPosition();

            }



            SoundObject so = this.SoundObjects.get(closestIndex);
            int soundID = ResourceLink.soundID[so.SoundIndex];
            global_mediaplayer = MediaPlayer.create(getApplicationContext(), soundID);

            //check if we are resuming
            if(so.duration != -1){
                //check if time is still left
                if( so.duration < global_mediaplayer.getDuration()) {
                    global_mediaplayer.seekTo(so.duration);
                }else{
                    //lets restart
                    global_mediaplayer.seekTo(0);

                }

            }
            global_mediaplayer.start();
            so.playing = true;

        }else if(closestIndex == -1 && currentPlaying != -1){
            //we havent't found a replacement but we are out of range of previous
            if(global_mediaplayer != null){
                global_mediaplayer.pause();
                this.SoundObjects.get(currentPlaying).playing = false;
                this.SoundObjects.get(currentPlaying).duration = global_mediaplayer.getCurrentPosition();
            }
        }





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


    //dialog views

    private void ModelSelectorDialog(){
        modelShowDialog = new Dialog(context);
        View convertView = LayoutInflater.from(context).inflate(R.layout.dialog_image_selector, null);
        modelShowDialog.setContentView(convertView);
        modelShowDialog.setTitle("Select Video");


        RecyclerView rv = (RecyclerView) convertView.findViewById(R.id.imagegallery);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 3);
        rv.setLayoutManager(layoutManager);
        rv.setHasFixedSize(true);

        //show the dialog
        modelShowDialog.show();
        //resize dialog
        modelShowDialog.getWindow().setLayout(dialogWindowWidth, dialogWindowHeight);

    }

    private void VideoSelectorDialog() {
        videoDialog = new Dialog(context);

        View convertView = LayoutInflater.from(context).inflate(R.layout.dialog_image_selector, null);
        imageDialog.setContentView(convertView);
        imageDialog.setTitle("Select Video");


        RecyclerView rv = (RecyclerView) convertView.findViewById(R.id.imagegallery);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 3);
        rv.setLayoutManager(layoutManager);
        rv.setHasFixedSize(true);

        //form our list
        ArrayList<String> SoundNames = new ArrayList<>();
        for(int i = 0; i< ResourceLink.soundNames.length; i++) {
            SoundNames.add(ResourceLink.soundNames[i]);
        }

        SoundGalleryAdapter sga;
        sga = new SoundGalleryAdapter(context,SoundNames);
        rv.setAdapter(sga);


        //show the dialog
        videoDialog.show();
        //resize dialog
        videoDialog.getWindow().setLayout(dialogWindowWidth, dialogWindowHeight);
    }

    private void SoundSelectorDialog(){
        soundDialog = new Dialog(context);

        View convertView = LayoutInflater.from(context).inflate(R.layout.dialog_image_selector, null);
        soundDialog.setContentView(convertView);
        soundDialog.setTitle("Select Video");


        RecyclerView rv = (RecyclerView) convertView.findViewById(R.id.imagegallery);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 1);
        rv.setLayoutManager(layoutManager);
        rv.setHasFixedSize(true);

        //form our list
        ArrayList<String> SoundNames = new ArrayList<>();
        for(int i = 0; i< ResourceLink.soundNames.length; i++) {
            SoundNames.add(ResourceLink.soundNames[i]);
        }

        SoundGalleryAdapter sga;
        sga = new SoundGalleryAdapter(context,SoundNames);
        rv.setAdapter(sga);

        //show the dialog
        soundDialog.show();
        //resize dialog
        soundDialog.getWindow().setLayout(dialogWindowWidth, dialogWindowHeight);
    }

    //Creates a dialog with a recycler view which allows you to select an image
    private void ImageSelectorDialog(){
        imageDialog = new Dialog(context);

        View convertView = LayoutInflater.from(context).inflate(R.layout.dialog_image_selector, null);
        imageDialog.setContentView(convertView);
        imageDialog.setTitle("Select Image");

        RecyclerView rv = (RecyclerView) convertView.findViewById(R.id.imagegallery);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 3);
        rv.setLayoutManager(layoutManager);
        rv.setHasFixedSize(true);

        // data to populate the RecyclerView with list
        ArrayList<Integer> ImageIDS = new ArrayList<>();
        for(int i = 0; i< ResourceLink.image_ids.length; i++) {
            ImageIDS.add(ResourceLink.image_ids[i]);
        }


        ImageGalleryAdapter adapter;
        adapter = new ImageGalleryAdapter(context,ImageIDS);
        rv.setAdapter(adapter);

        //show the dialog
        imageDialog.show();
        //resize dialog
        imageDialog.getWindow().setLayout(dialogWindowWidth, dialogWindowHeight);
    }

    //called when an imageview is pressed
    public void ImageDialogCallback(Integer i){
        Log.i("joe", "Image Callback has been Pressed!");
        //verify dialog view is open
        if(imageDialog != null && imageDialog.isShowing() == true){
            imageDialog.dismiss();
            Log.i("joe", "time to create an image view");
            SetupImageComponent(i, GetFaceNode(), 0);
            //if( allObjects.size() > 2 )
            //    objectDump();
        }
    }

    //called when an imageview is pressed
    public void SoundDialogCallback(Integer i){
        Log.i("joe", "Sound Callback has been Pressed!");
        //verify dialog view is open
        if(soundDialog != null && soundDialog.isShowing() == true){
            soundDialog.dismiss();
            Log.i("joe", "time to create a sound view");
            SetupSoundComponent(i, GetFaceNode(), 0);
        }
    }


    private ArrayList<CreateList> prepareDataImages(){

        ArrayList<CreateList> theimage = new ArrayList<>();
        for(int i = 0; i< ResourceLink.image_titles.length; i++){
            CreateList createList = new CreateList();
            createList.setImage_title(ResourceLink.image_titles[i]);
            createList.setImage_ID(ResourceLink.image_ids[i]);
            createList.setImageIndex(i);
            theimage.add(createList);
        }
        return theimage;
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

/*

// set the custom dialog components - text, image and button
        TextView text = (TextView) dialog.findViewById(R.id.text);
        text.setText("Android custom dialog example!");
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        image.setImageResource(R.drawable.ic_launcher_background);

        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
 */