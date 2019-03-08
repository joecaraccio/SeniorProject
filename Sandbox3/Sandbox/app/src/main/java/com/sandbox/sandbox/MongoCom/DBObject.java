

package com.sandbox.sandbox.MongoCom;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.BsonArray;
import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;


import static java.lang.System.in;


public class DBObject
{
    private Document document;
    private String tourName;
    private String objectType;
    private double objLocX;
    private double objLocY;
    private double objLocZ;
    private double roll;
    private double pitch;
    private double yaw;
    private double scale;
    private List<String> info;

    public DBObject( String name, String type, double x, double y, double z, double rx, double ry, double rz, double s, List<String> info)
    {
        this.tourName = name;
        this.objectType = type;
        this.objLocX = x;
        this.objLocY = y;
        this.objLocZ = z;
        this.roll = rx;
        this.pitch = ry;
        this.yaw = rz;
        this.scale = s;
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
        document.put("scale", s);
        document.put("info", info);
    }
    public DBObject(Document recievedDocument)
    {
        this.document = recievedDocument;
        this.tourName = (String)recievedDocument.get("tourName");
        this.objectType = (String)recievedDocument.get("objectType");
        this.objLocX = Double.valueOf((recievedDocument.get("x-coordinate").toString()));
        this.objLocY = Double.valueOf((recievedDocument.get("y-coordinate").toString()));
        this.objLocZ = Double.valueOf((recievedDocument.get("z-coordinate").toString()));
        this.roll = Double.valueOf((recievedDocument.get("roll").toString()));
        this.pitch = Double.valueOf((recievedDocument.get("pitch").toString()));
        this.yaw = Double.valueOf((recievedDocument.get("yaw").toString()));
        this.scale = Double.valueOf((recievedDocument.get("scale").toString()));
        //Need to convert this one to String array and test this
        info = (List<String>)recievedDocument.get("info");
    }
    public Document returnDoc()
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
    public double returnObjLocX()
    {
        return objLocX;
    }
    public double returnObjLocY()
    {
        return objLocY;
    }
    public double returnObjLocZ()
    {
        return objLocZ;
    }
    public double returnRoll()
    {
        return roll;
    }
    public double returnPitch()
    {
        return pitch;
    }
    public double returnYaw()
    {
        return yaw;
    }
    public double returnScale()
    {
        return scale;
    }
    public List<String> returnInfo()
    {
        return info;
    }
}