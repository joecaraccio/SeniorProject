using System;
using System.Collections.Generic;
using MongoDB.Bson;
using MongoDB.Driver;

namespace MongoCom
{
    public class HoloTourCom
    {

        private ObjectId tourID;
        private string tourName;
        private string objType;
        private float objLocX;
        private float objLocY;
        private float objLocZ;
        private float roll;
        private float pitch;
        private float yaw;
        private float scale;
        private string connectionString;
        private MongoClient dbClient;
        private IMongoDatabase dataBase;
        
        public HoloTourCom(String tourName)
        {
            //string user = "admin";
            //string pass = "admin1";
            //String authen = "SCRAM-SHA-1";
            //MongoInternalIdentity inId = new MongoInternalIdentity("admin", user);
            //PasswordEvidence passEvidnce = new PasswordEvidence(pass);
            //MongoCredential mongoCredential = new MongoCredential(authen, inId, passEvidnce);
            //List<MongoCredential> credentials = new List<MongoCredential>() { mongoCredential };

            //MongoClientSettings settings = new MongoClientSettings();
            //settings.Credentials = credentials;
            //settings.Server = new MongoServerAddress("ds157574.mlab.com", 57574);
            //dbClient = new MongoClient(settings);
            connectionString = "mongodb://admin:admin1@ds157574.mlab.com:57574?connect=replicaSet";
            dbClient = new MongoClient(connectionString); 
            dataBase = dbClient.GetDatabase("holotours");
            tourID = ObjectId.GenerateNewId();
            
        }

        public String test()
        {
            return "AAAAAAAAAAAAAAAAAAAAAA";
        }
        public void setObject(BsonDocument holoObject, String objectType)
        {
            var objGrp = dataBase.GetCollection<BsonDocument>(objectType);
            //BsonDocument document = objGrp.Find(FilterDefinition<BsonDocument>.Empty).Single();
            objGrp.InsertOne(holoObject);
            //try
            //{
                //using (var cursor = await dbClient.ListDatabasesAsync())
                //{
                    //await cursor.ForEachAsync(document => Console.WriteLine(document.ToString()));
                //}
            //}
            //catch
            //{
                //throw new IndexOutOfRangeException();
            //}
        }
        public String[] getObjects()
        {
            string[] temp = { "", "", "" };
            string connectionString = "mongodb://admin:admin1@ds157574.mlab.com:57574";
            var dbClient = new MongoClient(connectionString);
            var session = dbClient.StartSession();
            IMongoDatabase dataBase = dbClient.GetDatabase("holotours");
            
            var filter = new BsonDocument("ProjectID", tourID);

            return temp;
        }
        public ObjectId returnTourId()
        {
            return tourID;
        }
    }
}
