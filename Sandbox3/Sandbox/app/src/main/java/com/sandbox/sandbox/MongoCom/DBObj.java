package com.sandbox.sandbox.MongoCom;

//import com.mongodb.MongoClient;
//import com.mongodb.client.MongoCollection;
//import com.mongodb.client.MongoDatabase;

import org.bson.BsonArray;
import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;


import static java.lang.System.in;


public class DBObj
{
    private final Document document;
    private String tourName;
    private String objectType;
    private float objLocX;
    private float objLocY;
    private float objLocZ;
    private float roll;
    private float pitch;
    private float yaw;
    private float w;
    private float scale;
    private List<String> info;

    public DBObj( String name, String type, float x, float y, float z, float rx, float ry, float rz, float rw, float s, List<String> info)
    {
        this.tourName = name.toLowerCase();
        this.objectType = type;
        this.objLocX = x;
        this.objLocY = y;
        this.objLocZ = z;
        this.roll = rx;
        this.pitch = ry;
        this.yaw = rz;
        this.scale = s;
        this.w = rw;
        this.info = info;
        document = new Document();
        document.put("tourName", name );
        document.put("objectType", type);
        document.put("x-coordinate", x);
        document.put("y-coordinate", y);
        document.put("z-coordinate", z);
        document.put("roll", rx);
        document.put("pitch", ry);
        document.put("yaw", rz);
        document.put("w", rw);
        document.put("scale", s);
        document.put("info", info);
    }
    public DBObj(Document recievedDocument)
    {
        this.document = recievedDocument;
        this.tourName = (String)recievedDocument.get("tourName");
        this.objectType = (String)recievedDocument.get("objectType");
        this.objLocX = Float.valueOf((recievedDocument.get("x-coordinate").toString()));
        this.objLocY = Float.valueOf((recievedDocument.get("y-coordinate").toString()));
        this.objLocZ = Float.valueOf((recievedDocument.get("z-coordinate").toString()));
        this.roll = Float.valueOf((recievedDocument.get("roll").toString()));
        this.pitch = Float.valueOf((recievedDocument.get("pitch").toString()));
        this.yaw = Float.valueOf((recievedDocument.get("yaw").toString()));
        this.w = Float.valueOf((recievedDocument.get("w").toString()));
        this.scale = Float.valueOf((recievedDocument.get("scale").toString()));
        //Need to convert this one to String array and test this
        info = (List<String>)recievedDocument.get("info");
    }
    public final Document returnDoc()
    {
        return document;
    }
    public String returnTourName()
    {
        return tourName;
    }
    public String returnObjType()
    {
        return objectType;
    }
    public float returnObjLocX()
    {
        return objLocX;
    }
    public float returnObjLocY()
    {
        return objLocY;
    }
    public float returnObjLocZ()
    {
        return objLocZ;
    }
    public float returnRoll()
    {
        return roll;
    }
    public float returnPitch()
    {
        return pitch;
    }
    public float returnYaw()
    {
        return yaw;
    }
    public float returnW(){
        return w;
    }
    public float returnScale()
    {
        return scale;
    }
    public List<String> returnInfo()
    {
        return info;
    }
    public String returnWriteString() {
        return String.format("{\"tourName\": \"%s\", "+ "\"objectType\": \"%s\", "+ "\"x-coordinate\": \"%f\", "+ "\"y-coordinate\": \"%f\", "+ "\"z-coordinate\": \"%f\", "+ "\"roll\": \"%f\", "
                + "\"pitch\": \"%f\", "+ "\"yaw\": \"%f\", "+ "\"w\": \"%f\", "+ "\"scale\": \"%f\"}", tourName, objectType, objLocX, objLocY, objLocZ, roll, pitch, yaw, w, scale);
    }
}

