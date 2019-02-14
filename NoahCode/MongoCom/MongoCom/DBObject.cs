using System;
using MongoDB.Bson;
using MongoDB.Driver;
using System.Collections.Generic;
using System.Text;

namespace MongoCom
{
    public class DBObject
    {
        private BsonDocument document;
        private ObjectId tourID;
        private string tourName;
        private double objLocX;
        private double objLocY;
        private double objLocZ;
        private double roll;
        private double pitch;
        private double yaw;
        private double scale;
        private String[] info;
        public DBObject(ObjectId id, string name, double x, double y, double z, double rx, double ry, double rz, double s, String[] info)
        {
            document = new BsonDocument
            {
                {"tourID", id },
                {"tourName", new BsonString(name) },
                {"x-coordinate", new BsonString(x.ToString()) },
                {"y-coordinate", new BsonString(y.ToString()) },
                {"z-coordinate", new BsonString(z.ToString()) },
                {"roll", new BsonString(rx.ToString()) },
                {"pitch", new BsonString(ry.ToString()) },
                {"yaw", new BsonString(rz.ToString()) },
                {"scale", new BsonString(s.ToString()) },
                {"info", new BsonArray(info) }
            };
        }
        public DBObject(BsonDocument recievedDocument)
        {
            this.document = recievedDocument;
            this.tourID = (ObjectId)recievedDocument["tourID"];
            this.tourName = (string)recievedDocument["tourName"];
            this.objLocX = Convert.ToDouble(((string)recievedDocument["x-coordinate"]));
            this.objLocY = Convert.ToDouble(((string)recievedDocument["y-coordinate"]));
            this.objLocZ = Convert.ToDouble(((string)recievedDocument["z-coordinate"]));
            this.roll = Convert.ToDouble(((string)recievedDocument["roll"]));
            this.pitch = Convert.ToDouble(((string)recievedDocument["pitch"]));
            this.yaw = Convert.ToDouble(((string)recievedDocument["yaw"]));
            this.scale = Convert.ToDouble(((string)recievedDocument["scale"]));
            //Need to convert this one to string array and test this 
            //this.info = (BsonArray)recievedDocument["info"];
        }
        public BsonDocument returnDoc()
        {
            return document;
        }
        public ObjectId returnTourID()
        {
            return tourID;
        }
        public string returnTourName()
        {
            return tourName;
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
        private String[] returnInfo()
        {
            return info;
        }
    }
}
