using System;
using System.Collections.Generic;
using MongoDB.Bson;
using MongoDB.Driver;

//fix the info portion of DBObjects to convert correctly
namespace MongoCom
{
    public class HoloTourCom
    {

        private ObjectId tourID;
        private string tourName;
        private double userHeight;
        private string connectionString;
        private MongoClient dbClient;
        private IMongoDatabase dataBase;

        public HoloTourCom(String tourName)
        {
            connectionString = "mongodb://admin:admin1@cluster0-shard-00-00-ka88r.mongodb.net:27017,cluster0-shard-00-01-ka88r.mongodb.net:27017,cluster0-shard-00-02-ka88r.mongodb.net:27017/test?ssl=true&replicaSet=Cluster0-shard-0&authSource=admin&retryWrites=true";
            dbClient = new MongoClient(connectionString);
            dataBase = dbClient.GetDatabase("holotours");
            this.tourName = tourName;
        }
        public List<String> showAllTourNames()
        {
            var names = new List<String>();
            var collection = dataBase.GetCollection<BsonDocument>("tours");
            List<BsonDocument> tours = collection.Find(Builders<BsonDocument>.Filter.Empty).ToList();
            foreach (BsonDocument doc in tours)
            {
                names.Add((string)doc["tourName"]);
            }
            return names;
        }
        public void createNewTour(double height)
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
            foreach (String s in dbNames)
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
                    if (collection.Find(filter).Any())
                        collection.DeleteMany(filter);
                    else
                    {

                    }

                }
            }


        }
        public List<LogInfo> dumpLogInfo()
        {
            var docs = new List<LogInfo>();
            var collection = dataBase.GetCollection<BsonDocument>("tours");
            List<BsonDocument> tours = collection.Find(Builders<BsonDocument>.Filter.Empty).ToList();
            foreach (BsonDocument doc in tours)
            {
                docs.Add(new LogInfo(doc));
            }
            return docs;
        }
        public void deleteLogInfo()
        {
            var filter = Builders<BsonDocument>.Filter.Empty;
            //loop through each collection
            var collection = dataBase.GetCollection<BsonDocument>("tours");
            collection.DeleteMany(filter);

        }
        public ObjectId returnTourId()
        {
            return tourID;
        }
        public string returnTourName()
        {
            return tourName;
        }
        public double getAdminHeight()
        {
            return userHeight;
        }
    }
}
