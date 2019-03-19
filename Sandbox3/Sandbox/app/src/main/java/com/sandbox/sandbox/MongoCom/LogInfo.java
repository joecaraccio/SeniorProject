package com.sandbox.sandbox.MongoCom;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.in;
import static java.lang.System.nanoTime;

public class LogInfo
{
    private Document document;
    private String tourName;
    private String userID;
    private float userHeight;
    private float objLocX;
    private float objLocY;
    private float objLocZ;
    private float roll;
    private float pitch;
    private float yaw;
    private float w;
    private float stamp;

    public LogInfo(String name, String userid, float height, float x, float y, float z, float rx, float ry, float rz, float rw, float time)
    {
        this.tourName = name;
        this.userID = userid;
        this.userHeight = height;
        this.objLocX = x;
        this.objLocY = y;
        this.objLocZ = z;
        this.roll = rx;
        this.pitch = ry;
        this.yaw = rz;
        this.w = rw;
        this.stamp = time;
        document = new Document();
        document.put("tourName", name);
        document.put("userID", userid);
        document.put("objectType", "userLogData");
        document.put("userHeight", height);
        document.put("x-coordinate", x);
        document.put("y-coordinate", y);
        document.put("z-coordinate", z);
        document.put("roll", rx);
        document.put("pitch", ry);
        document.put("yaw", rz);
        document.put("w", rw);
        document.put("time", time);
        document.put("timeStamp", new Timestamp(System.currentTimeMillis()).toString());
    }
    public String returnUserId()
    {
        return userID;
    }
    public LogInfo(Document recievedDocument)
    {
        this.document = recievedDocument;
        this.tourName = recievedDocument.get("tourName").toString();
        this.userID = recievedDocument.get("userID").toString();
        this.objLocX = Float.valueOf(recievedDocument.get("x-coordinate").toString());
        this.objLocY = Float.valueOf(recievedDocument.get("y-coordinate").toString());
        this.objLocZ = Float.valueOf(recievedDocument.get("z-coordinate").toString());
        this.roll = Float.valueOf(recievedDocument.get("roll").toString());
        this.pitch = Float.valueOf(recievedDocument.get("pitch").toString());
        this.yaw = Float.valueOf(recievedDocument.get("yaw").toString());
        this.w = Float.valueOf(recievedDocument.get("w").toString());
        this.stamp = Integer.valueOf(recievedDocument.get("time").toString());
        //Need to convert this one to string array and test this
        //this.info = (BsonArray)recievedDocument["info"];
    }
    public Document returnDoc()
    {
        return document;
    }

}
