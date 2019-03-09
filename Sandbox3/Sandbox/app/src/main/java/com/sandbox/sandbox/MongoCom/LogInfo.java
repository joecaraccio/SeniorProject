package com.sandbox.sandbox.MongoCom;

import com.mongodb.MongoClient;
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
    private double userHeight;
    private double objLocX;
    private double objLocY;
    private double objLocZ;
    private double roll;
    private double pitch;
    private double yaw;
    private int stamp;

    public LogInfo(String name, int userid, double height, double x, double y, double z, double rx, double ry, double rz, int time)
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
        this.objLocX = Double.valueOf(recievedDocument.get("x-coordinate").toString());
        this.objLocY = Double.valueOf(recievedDocument.get("y-coordinate").toString());
        this.objLocZ = Double.valueOf(recievedDocument.get("z-coordinate").toString());
        this.roll = Double.valueOf(recievedDocument.get("roll").toString());
        this.pitch = Double.valueOf(recievedDocument.get("pitch").toString());
        this.yaw = Double.valueOf(recievedDocument.get("yaw").toString());
        //Need to convert this one to string array and test this
        //this.info = (BsonArray)recievedDocument["info"];
    }
    public Document returnDoc()
    {
        return document;
    }

}
