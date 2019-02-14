using System;
using MongoDB.Bson;
using MongoDB.Driver;
using System.Collections.Generic;
using System.Text;

namespace MongoCom
{
    class logInfo
    {
        private BsonDocument document;
        public logInfo(ObjectId id, int height, float x, float y, float z, float rx, float ry, float rz, int stamp)
        {
            document = new BsonDocument
            {
                {"tourID", id },
                {"userID", ObjectId.GenerateNewId() },
                {"userHeight", new BsonInt32(height) },
                {"x-coordinate", new BsonString(x.ToString()) },
                {"y-coordinate", new BsonString(y.ToString()) },
                {"z-coordinate", new BsonString(z.ToString()) },
                {"roll", new BsonString(rx.ToString()) },
                {"pitch", new BsonString(ry.ToString()) },
                {"yaw", new BsonString(rz.ToString()) }
            };
        }
        public BsonDocument returnDoc()
        {
            return document;
        }
    }
}
