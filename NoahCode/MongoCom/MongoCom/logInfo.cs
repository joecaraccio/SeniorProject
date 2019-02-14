using System;
using MongoDB.Bson;
using MongoDB.Driver;
using System.Collections.Generic;
using System.Text;

namespace MongoCom
{
    public class LogInfo
    {
        private BsonDocument document;
        private ObjectId tourID;
        private ObjectId userID;
        private double userHeight;
        private double objLocX;
        private double objLocY;
        private double objLocZ;
        private double roll;
        private double pitch;
        private double yaw;
        private int stamp;

        public LogInfo(ObjectId id, ObjectId userid, double height, double x, double y, double z, double rx, double ry, double rz, int time)
        {
            this.tourID = id;
            this.userID = userid;
            this.userHeight = height;
            this.objLocX = x;
            this.objLocY = y;
            this.objLocZ = z;
            this.roll = rx;
            this.pitch = ry;
            this.yaw = rz;
            this.stamp = time;
            document = new BsonDocument
            {
                {"tourID", id },
                {"userID", userid },
                {"objectType", new BsonString("userLogData") },
                {"userHeight", new BsonString(height.ToString()) },
                {"x-coordinate", new BsonString(x.ToString()) },
                {"y-coordinate", new BsonString(y.ToString()) },
                {"z-coordinate", new BsonString(z.ToString()) },
                {"roll", new BsonString(rx.ToString()) },
                {"pitch", new BsonString(ry.ToString()) },
                {"yaw", new BsonString(rz.ToString()) }
            };
        }
        public LogInfo(ObjectId id, double height, double x, double y, double z, double rx, double ry, double rz, int time)
        {
            this.tourID = id;
            this.userID = ObjectId.GenerateNewId();
            this.userHeight = height;
            this.objLocX = x;
            this.objLocY = y;
            this.objLocZ = z;
            this.roll = rx;
            this.pitch = ry;
            this.yaw = rz;
            this.stamp = time;
            document = new BsonDocument
            {
                {"tourID", id },
                {"userID", userID },
                {"objectType", new BsonString("userLogData") },
                {"userHeight", new BsonString(height.ToString()) },
                {"x-coordinate", new BsonString(x.ToString()) },
                {"y-coordinate", new BsonString(y.ToString()) },
                {"z-coordinate", new BsonString(z.ToString()) },
                {"roll", new BsonString(rx.ToString()) },
                {"pitch", new BsonString(ry.ToString()) },
                {"yaw", new BsonString(rz.ToString()) }
            };
        }
        public ObjectId returnUserId()
        {
            return userID;
        }
        public LogInfo(BsonDocument recievedDocument)
        {
            this.document = recievedDocument;
            this.tourID = (ObjectId)recievedDocument["tourID"];
            this.objLocX = Convert.ToDouble(((string)recievedDocument["x-coordinate"]));
            this.objLocY = Convert.ToDouble(((string)recievedDocument["y-coordinate"]));
            this.objLocZ = Convert.ToDouble(((string)recievedDocument["z-coordinate"]));
            this.roll = Convert.ToDouble(((string)recievedDocument["roll"]));
            this.pitch = Convert.ToDouble(((string)recievedDocument["pitch"]));
            this.yaw = Convert.ToDouble(((string)recievedDocument["yaw"]));
            //Need to convert this one to string array and test this 
            //this.info = (BsonArray)recievedDocument["info"];
        }
        public BsonDocument returnDoc()
        {
            return document;
        }
       
    }
}
