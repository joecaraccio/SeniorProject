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
        private string objectType;
        private double objLocX;
        private double objLocY;
        private double objLocZ;
        private double roll;
        private double pitch;
        private double yaw;
        private double scale;
        private String[] info;

        public DBObject(ObjectId id, string name, string type, double x, double y, double z, double rx, double ry, double rz, double s, String[] info)
        {
            this.tourID = id;
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
            document = new BsonDocument
            {
                {"tourID", id },
                {"tourName", new BsonString(name) },
                {"objectType", new BsonString(type) },
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
            this.objectType = (string)recievedDocument["objectType"];
            this.objLocX = Convert.ToDouble(((string)recievedDocument["x-coordinate"]));
            this.objLocY = Convert.ToDouble(((string)recievedDocument["y-coordinate"]));
            this.objLocZ = Convert.ToDouble(((string)recievedDocument["z-coordinate"]));
            this.roll = Convert.ToDouble(((string)recievedDocument["roll"]));
            this.pitch = Convert.ToDouble(((string)recievedDocument["pitch"]));
            this.yaw = Convert.ToDouble(((string)recievedDocument["yaw"]));
            this.scale = Convert.ToDouble(((string)recievedDocument["scale"]));
            //Need to convert this one to string array and test this 
            var temp = (BsonArray)recievedDocument["info"];
            var bsonValues = temp.ToArray();
            info = new string[bsonValues.Length];
            for(int i = 0; i < bsonValues.Length; i ++)
            {
                info[i] = bsonValues[i].ToString();
            }
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
        public string returnObjType()
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
        public String[] returnInfo()
        {
            return info;
        }
    }
}
