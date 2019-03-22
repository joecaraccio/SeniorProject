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
        private String tourName;
        private String userID;
        private Double userHeight;
        private Double objLocX;
        private Double objLocY;
        private Double objLocZ;
        private Double roll;
        private Double pitch;
        private Double yaw;
        private Double w;
        private int time;
        private String stamp;

        public LogInfo(BsonDocument recievedDocument)
        {
            this.document = recievedDocument;
            this.tourName =  (string)recievedDocument["tourName"];
            this.userID = (string)recievedDocument["userID"];
            this.userHeight = Convert.ToDouble(recievedDocument["userHeight"]); 
            this.objLocX = Convert.ToDouble(recievedDocument["x-coordinate"]);
            this.objLocY = Convert.ToDouble(recievedDocument["y-coordinate"]);
            this.objLocZ = Convert.ToDouble(recievedDocument["z-coordinate"]);
            this.roll = Convert.ToDouble(recievedDocument["roll"]);
            this.pitch = Convert.ToDouble(recievedDocument["pitch"]);
            this.yaw = Convert.ToDouble(recievedDocument["yaw"]);
            this.w = Convert.ToDouble(recievedDocument["w"]);
            this.time = Convert.ToInt32(recievedDocument["time"]);
            this.stamp = (string)recievedDocument["timeStamp"].ToString();
            //Need to convert this one to string array and test this 
            //this.info = (BsonArray)recievedDocument["info"];
        }
        public BsonDocument returnDoc()
        {
            return document;
        }
       public string returnTourName()
        {
            return this.tourName;
        }
        public string returnUserID()
        {
            return this.userID;
        }
        public Double returnUserHeight()
        {
            return this.userHeight;
        }
        public Double returnX()
        {
            return this.objLocX;
        }
        public Double returnY()
        {
            return this.objLocY;
        }
        public Double returnZ()
        {
            return this.objLocZ;
        }
        public Double returnRoll()
        {
            return this.roll;
        }
        public Double returnPitch()
        {
            return this.pitch;
        }
        public Double returnYaw()
        {
            return this.yaw;
        }
        public Double returnW()
        {
            return this.w;
        }
        public int returnTime()
        {
            return this.time;
        }
        public String returnStamp()
        {
            return this.stamp;
        }
    }
}
