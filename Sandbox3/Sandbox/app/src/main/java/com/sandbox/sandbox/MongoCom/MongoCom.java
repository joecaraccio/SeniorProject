package com.sandbox.sandbox.MongoCom;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.DBObject;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.in;

/**
 * Created by joe on 2/16/19.
 */

public class MongoCom {

    private ObjectId tourID;
    private String TourName;
    private double UserHeight;
    private String ConnectionString;
    private MongoClient dbClient;
    private MongoDatabase dataBase;

    public MongoCom(String tourName)
    {
        ConnectionString = "mongodb://admin:TourGuide3546@ds157574.mlab.com:57574/holotours";
        dbClient = new MongoClient(ConnectionString);
        dataBase = dbClient.getDatabase("holotours");
        this.TourName = tourName;
    }
/*
    public List<String> showAllTourNames()
    {
        List<String> names = new ArrayList<>();
        MongoCollection<Document> collection = dataBase.getCollection("tours");

        List<BsonDocument> tours = collection.Find(Builders<BsonDocument>.Filter.Empty).ToList();
        foreach(BsonDocument doc in tours)
        {
            names.Add((string)doc["tourString"]);
        }
        return names;
    }
    public void createNewTour( double height)
    {
        if (showAllTourNames().Contains(tourName))
        {
            throw new ArgumentException();
        }
        else
        {
            tourID = ObjectId.GenerateNewId();
            userHeight = height;
            var doc = new BsonDocument
            {
                {"tourID", tourID },
                {"tourString", new BsonString(tourName) },
                {"userHeight", new BsonString(height.ToString()) }
            };
            var tourIndex = dataBase.GetCollection<BsonDocument>("tours");
            tourIndex.InsertOne(doc);
        }
    }
    public void openTour()
    {
        var collection = dataBase.GetCollection<BsonDocument>("tours");
        var filter = Builders<BsonDocument>.Filter.Eq("tourString", tourName);
        var id = collection.Find(filter).Single();
        tourID = (ObjectId)id["tourID"];
        userHeight = Convert.ToDouble((string)id["userHeight"]);
    }
    public void setObject(BsonDocument holoObject)
    {
        var objGrp = dataBase.GetCollection<BsonDocument>((string)holoObject["objectType"]);
        objGrp.InsertOne(holoObject);
    }
    public List<DBObject> getObjects()
    {
        List<DBObject> objects = new List<DBObject>();
        var filter = Builders<BsonDocument>.Filter.Eq("tourID", tourID);
        //loop through each collection
        List<String> dbNames = dataBase.ListCollectionNames().ToList();
        var tourStuff = new List<BsonDocument>();
        var col = dataBase.GetCollection<BsonDocument>("foundStuff");
        foreach ( String s in dbNames)
        {
            if (s == "tours" || s == "userLogData" || s == "system.indexes")
            {

            }
            else
            {
                var collection = dataBase.GetCollection<BsonDocument>((string)s);
                tourStuff.AddRange(collection.Find(filter).ToList());
            }
        }

        foreach (BsonDocument i in tourStuff)
        {
            DBObject doc = new DBObject(i);
            //This line is just for my testing so ii can see results in db
            //col.InsertOne(doc.returnDoc());
            objects.Add(doc);
        }


        //get all items with matching id
        //return them
        return objects;
    }
    public void removeAllTourObj()
    {
        var filter = Builders<BsonDocument>.Filter.Eq("tourID", tourID);
        //loop through each collection
        List<String> dbNames = dataBase.ListCollectionNames().ToList();
        var tourStuff = new List<BsonDocument>();
        foreach (String s in dbNames)
        {
            if (s == "userLogData" || s == "system.indexes")
            {

            }
            else
            {
                var collection = dataBase.GetCollection<BsonDocument>((string)s);
                if(collection.Find(filter).Any())
                    collection.DeleteMany(filter);
                else
                {

                }

            }
        }


    }
    public ObjectId returnTourId()
    {
        return tourID;
    }
    public String returnTourName()
    {
        return TourName;
    }
    public double getAdminHeight()
    {
        return UserHeight;
    }
*/

}



