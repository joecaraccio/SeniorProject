package com.sandbox.sandbox.MongoCom;

import com.mongodb.DBCursor;
import com.mongodb.Block;
import com.mongodb.ServerAddress;
import com.mongodb.async.SingleResultCallback;
import com.mongodb.async.client.*;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.connection.ClusterSettings;
import com.mongodb.ConnectionString;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.Document;
import org.bson.BsonObjectId;
import org.bson.types.ObjectId;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

//import com.mongodb.client.model.Indexes;
//import com.mongodb.client.model.IndexOptions;
//import com.mongodb.client.model.Filters;


import static com.mongodb.client.model.Filters.eq;
import static java.lang.System.in;

/**
 * Converted from C# code
 */

public class MongoCom {

    private String tourName;
    private float userHeight;
    private MongoDatabase dataBase;
    private MongoClient dbClient;

    /**
     * This constructor opens the communication to the server and takes in name of tour as a
     * variable to use as a unique identifier for each different tour setup
     * @param tourName
     */
    public MongoCom(String tourName)
    {
        //String connectionString = "mongodb://admin:TourGuide3546@ds157574.mlab.com:57574/holotours";
        //MongoClientURI uri = new MongoClientURI(connectionString);
        //dbClient = new MongoClient(uri);
        MongoClientURI uri = new MongoClientURI(
                "mongodb://admin:admin1@cluster0-shard-00-00-ka88r.mongodb.net:27017,cluster0-shard-00-01-ka88r.mongodb.net:27017,cluster0-shard-00-02-ka88r.mongodb.net:27017/test?ssl=true&replicaSet=Cluster0-shard-0&authSource=admin&retryWrites=true");

        dbClient = new MongoClient(uri);
        dataBase = dbClient.getDatabase("holotours");
        this.tourName = tourName;
    }

    /**
     * This function gets any of the tours that have been previously made, this is in the case
     * that we want to give user a menu of tours to open from
     * @return
     */
    public List<String> showAllTourNames()
    {
        List<String> names = new ArrayList<>();
        MongoCollection<Document> collection = dataBase.getCollection("tours");

        MongoCursor<Document> tours = collection.find().iterator();
        while(tours.hasNext())
        {
            names.add(tours.next().get("tourName").toString());
        }
        return names;
    }

    /**
     * creates a new tour object, takes in the tour makers height. If the tourName given from the
     * constructor already exists then throws error, if not creates a new entry in the tours database
     * table.
     * @param height
     */
    public void createNewTour( float height)
    {
        //if (showAllTourNames().contains(tourName))
        //{
        //    throw new IllegalArgumentException();
        //}
        //else
        {
            userHeight = height;
            String s = Double.toString(height);
            Document doc = new Document();
            doc.put("tourName", tourName );
            doc.put("userHeight", new BsonString(s) );
            MongoCollection tourIndex = dataBase.getCollection("tours");
            tourIndex.insertOne(doc);
        }
    }

    /**
     * if the tour has already been created then this function is called, it fills the original users
     * height into the com item so that you can get it using the returnHeight function.
     */
    public void openTour()
    {
        MongoCollection collection = dataBase.getCollection("tours");
        Document id = (Document)collection.find(eq("tourName", tourName)).first();
        userHeight = Float.valueOf(id.get("userHeight").toString());
    }

    /**
     * This function is used to add new items to the database. Code must pass in either a DBObject.returnDoc()
     * or a LogInfo.returnDoc() this will store the DBObject based off of the ObjType in a table called that object type.
     * @param holoObject
     */
    public void setObject(Document holoObject)
    {
        MongoCollection objGrp = dataBase.getCollection(holoObject.get("objectType").toString());
        objGrp.insertOne(holoObject);
    }

    /**
     * This returns all objects assosiated with a project in the form of a list of DBObjects, you can
     * then itterate through this list and call the various return functions of DBObject to get the
     * values assosiated with each object and the object type itself.
     * @return
     */
    public List<DBObj> getObjects()
    {
        List<DBObj> objects = new ArrayList<DBObj>();
        //loop through each collection
        MongoCursor<String> dbNames = dataBase.listCollectionNames().iterator();
        MongoCollection col = dataBase.getCollection("foundStuff");
        while( dbNames.hasNext() )
        {
            String s = dbNames.next();
            if (s.equals("tours") || s.equals("userLogData") || s.equals("system.indexes"))
            {

            }
            else
            {
                MongoCollection collection = dataBase.getCollection(s);
                MongoCursor<Document> obj = collection.find(eq("tourName", tourName)).iterator();
                while(obj.hasNext())
                {
                    DBObj doc = new DBObj(obj.next());
                    //This line is just for my testing so ii can see results in db
                    //col.InsertOne(doc.returnDoc());
                    objects.add(doc);
                }
            }
        }

        //get all items with matching id
        //return them
        return objects;
    }

    /**
     * This removes all objects no matter the table based off of the tourName that was passed in when
     * the com object was created.
     */
    public void removeAllTourObj()
    {
        //loop through each collection
        MongoCursor<String> dbNames = dataBase.listCollectionNames().iterator();
        while(dbNames.hasNext())
        {
            String s = dbNames.next();
            if (s == "userLogData" || s == "system.indexes")
            {

            }
            else
            {
                MongoCollection collection = dataBase.getCollection(s);
                if(collection.find(eq("tourName", tourName)).iterator().hasNext())
                    collection.deleteMany(eq("tourName", tourName));
                else
                {

                }

            }
        }


    }
    public void close()
    {
        dbClient.close();
    }
    /**
     * This simply returns the name of the tour
     * @return
     */
    public String returnTourName()
    {
        return tourName;
    }

    /**
     * This returns the height of the user who made the tour.
     * @return
     */
    public float getAdminHeight()
    {
        return userHeight;
    }

    /**
     * Main function as an example for use of the code
     * @param args
     */
    public static void main(String [ ] args){
        List<String> testArray = new ArrayList();
        testArray.add("test");
        testArray.add("testing");
        testArray.add("Information being stored in array");
        //MongoCom com1 = new MongoCom("tour1");
        //com1.openTour();
        //com1.createNewTour(6.0);
        //DBObject obj1 = new DBObject(com1.returnTourName(), "Arrow", 1, 2.1, 2.3, 2.3, 1.2, 1.23232, 1.2, testArray);
        //DBObject obj2 = new DBObject(com1.returnTourName(), "Arrow", 1.2, 2.1, 2.3, 2.3, 1.2, 1.23232, 1.2, testArray);
        //DBObject obj3 = new DBObject(com1.returnTourName(), "Arrow", 1.23, 2.1, 2.3, 2.3, 1.2, 1.23232, 1.2, testArray);
        //DBObject obj4 = new DBObject(com1.returnTourName(), "Arrow", 1.222, 2.1, 2.3, 2.3, 1.2, 1.23232, 1.2, testArray);
        //DBObject obj5 = new DBObject(com1.returnTourName(), "Arrow", 1.1, 2.1, 2.3, 2.3, 1.2, 1.23232, 1.2, testArray);
        //com1.setObject(obj1.returnDoc());
        //com1.setObject(obj2.returnDoc());
        //com1.setObject(obj3.returnDoc());
        //com1.setObject(obj4.returnDoc());
        //com1.setObject(obj5.returnDoc());
        MongoCom com2 = new MongoCom("tour2");
        com2.createNewTour(6.0f);
        DBObj obj6 = new DBObj(com2.returnTourName(), "Arrow", 1f, 2.1f, 2.3f, 2.3f, 1.2f, 1.23232f, 1.222232f, 1.2f, testArray);
        DBObj obj7 = new DBObj(com2.returnTourName(), "Arrow", 1.2f, 2.1f, 2.3f, 2.3f, 1.2f, 1.23232f, 122.2f, 1.2f, testArray);
        DBObj obj8 = new DBObj(com2.returnTourName(), "Arrow", 1.23f, 2.1f, 2.3f, 2.3f, 1.2f, 1.23232f, 1.2f, 0.000f, testArray);
        DBObj obj9 = new DBObj(com2.returnTourName(), "Arrow", 1.222f, 2.1f, 2.3f, 2.3f, 1.2f, 1.23232f, 1.2f, 0.02932f, testArray);
        DBObj obj10 = new DBObj(com2.returnTourName(), "Arrow", 1.1f, 2.1f, 2.3f, 2.3f, 1.2f, 1.23232f, 0.0202032f, 1.2f, testArray);
        com2.setObject(obj6.returnDoc());
        com2.setObject(obj7.returnDoc());
        com2.setObject(obj8.returnDoc());
        com2.setObject(obj9.returnDoc());
        com2.setObject(obj10.returnDoc());

        List<String> names = com2.showAllTourNames();
        for(int i =0; i < names.size(); i ++)
            System.out.println(names.get(i));
        List<DBObj> docs = com2.getObjects();
        for( int i =0; i < docs.size(); i ++)
        {
            System.out.println("Document: " + i);
            System.out.println("Tour Name: " + docs.get(i).returnTourName());
            System.out.println("Type: " + docs.get(i).returnObjType());
            System.out.println("LocX : " + docs.get(i).returnObjLocX());
            System.out.println("LocY: " + docs.get(i).returnObjLocY());
            System.out.println("LocZ: " + docs.get(i).returnObjLocZ());
            System.out.println("Roll: " + docs.get(i).returnRoll());
            System.out.println("Pitch: " + docs.get(i).returnPitch());
            System.out.println("Yaw: " + docs.get(i).returnYaw());
            System.out.println("Scale: " + docs.get(i).returnScale());
            List<String> information = docs.get(i).returnInfo();
            for( int j = 0; j < information.size(); j++)
            {
                System.out.println("Information: " + j + " " + information.get(j));
            }
        }
        com2.removeAllTourObj();
        LogInfo user1 = new LogInfo("tour1", 1, 5.7f, 1.232f, 1.23232f, 2.1212f, 34.232f, 1.21212121212f, 2.32323232f, 100f);
        com2.setObject(user1.returnDoc());
        //com1.close();
        com2.close();
    }

}



