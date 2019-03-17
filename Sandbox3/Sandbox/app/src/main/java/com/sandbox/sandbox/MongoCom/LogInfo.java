package com.sandbox.sandbox.MongoCom;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.in;

public class LogInfo
{
    private Document document;
    private String tourName;
    private int userID;
    private float userHeight;
    private float objLocX;
    private float objLocY;
    private float objLocZ;
    private float roll;
    private float pitch;
    private float yaw;
    private float stamp;

    public LogInfo(String name, int userid, float height, float x, float y, float z, float rx, float ry, float rz, float time)
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
    }
    public int returnUserId()
    {
        return userID;
    }
    public LogInfo(Document recievedDocument)
    {
        this.document = recievedDocument;
        this.tourName = recievedDocument.get("tourName").toString();
        this.objLocX = Float.valueOf(recievedDocument.get("x-coordinate").toString());
        this.objLocY = Float.valueOf(recievedDocument.get("y-coordinate").toString());
        this.objLocZ = Float.valueOf(recievedDocument.get("z-coordinate").toString());
        this.roll = Float.valueOf(recievedDocument.get("roll").toString());
        this.pitch = Float.valueOf(recievedDocument.get("pitch").toString());
        this.yaw = Float.valueOf(recievedDocument.get("yaw").toString());
        //Need to convert this one to string array and test this
        //this.info = (BsonArray)recievedDocument["info"];
    }
    public Document returnDoc()
    {
        return document;
    }

}
