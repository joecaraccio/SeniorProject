using System;
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
        private float rotationX;
        private float rotationY;
        private float rotationZ;
        private float scaleX;
        private float scaleY;
        
        public 
            HoloTourCom(String tourName)
        {
            string connectionString = "mongodb://admin:admin1@ds157574.mlab.com:57574";
            var dbClient = new MongoClient(connectionString);
            IMongoDatabase dataBase = dbClient.GetDatabase("holotours");
            var collection = dataBase.GetCollection<BsonDocument>("tours");
            this.tourName = tourName;
            this.tourID = ObjectId.GenerateNewId();
            var idDoc = new BsonDocument
            {
                {"tourID", tourID },
                {"tourName", new BsonString(tourName) }
            };
            
        }

        private void setObject()
        {
            string connectionString = "mongodb://admin:admin1@ds157574.mlab.com:57574";
            var dbClient = new MongoClient(connectionString);
            IMongoDatabase dataBase = dbClient.GetDatabase("holotours");
            
        }
        private String[] getObjects()
        {
            string[] temp = { "", "", "" };
            string connectionString = "mongodb://admin:admin1@ds157574.mlab.com:57574";
            var dbClient = new MongoClient(connectionString);
            var session = dbClient.StartSession();
            IMongoDatabase dataBase = dbClient.GetDatabase("holotours");
            var collection = dataBase.GetCollection<BsonDocument>("test");
            var filter = new BsonDocument("ProjectID", tourID);
            collection.FindSync(filter).ToList;

            return temp;
        }
    }
}
