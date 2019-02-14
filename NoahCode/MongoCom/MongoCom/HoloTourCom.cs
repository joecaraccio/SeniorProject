using System;
using System.Collections.Generic;
using MongoDB.Bson;
using MongoDB.Driver;

//CANNOT HAVE ANY THING NAMED THE SAME OR ERRORS ENSUE
//Last things need to do
//remove projects (pas in title remove all objects assosiated
//fix the info portion of DBObjects to convert correctly
//in front end make sure people cant name things under the same name (possibly have program check title when they first pass in
//implement log info
namespace MongoCom
{
    public class HoloTourCom
    {

        private ObjectId tourID;
        private string tourName;
        private string connectionString;
        private MongoClient dbClient;
        private IMongoDatabase dataBase;
        
        public HoloTourCom(String tourName)
        {
            connectionString = "mongodb://admin:TourGuide3546@ds157574.mlab.com:57574/holotours";
            dbClient = new MongoClient(connectionString); 
            dataBase = dbClient.GetDatabase("holotours");
            this.tourName = tourName;
        }
        public void createNewTour()
        {
            tourID = ObjectId.GenerateNewId();
            var doc = new BsonDocument
            {
                {"tourID", tourID },
                {"tourString", tourName }
            };
            var tourIndex = dataBase.GetCollection<BsonDocument>("tours");
            tourIndex.InsertOne(doc);
        }
        public void openTour()
        {
            var collection = dataBase.GetCollection<BsonDocument>("tours");
            var filter = Builders<BsonDocument>.Filter.Eq("tourString", tourName);
            var id = collection.Find(filter).Single();
            tourID = (ObjectId)id["tourID"];
        }
        public void setObject(BsonDocument holoObject, String objectType)
        {
            var objGrp = dataBase.GetCollection<BsonDocument>(objectType);
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
                if (s == "tours")
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
                col.InsertOne(doc.returnDoc());
                objects.Add(doc);
            }


            //get all items with matching id
            //return them
            return objects;
        }
        public void removeTour()
        {

        }
        public ObjectId returnTourId()
        {
            return tourID;
        }
    }
}
