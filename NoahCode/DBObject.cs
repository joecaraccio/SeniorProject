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
        public DBObject(ObjectId id, string name, string type, float x, float y, float z, float rx, float ry, float rz, float sx, float sy, String[] info)
        {
            document = new BsonDocument
            {
                {"tourID", id },
                {"tourName", new BsonString(name) },
                {"objType", new BsonString(type) },
                {"x-coordinate", new BsonString(x.ToString()) },
                {"y-coordinate", new BsonString(y.ToString()) },
                {"z-coordinate", new BsonString(z.ToString()) },
                {"rotationX", new BsonString(rx.ToString()) },
                {"rotationY", new BsonString(ry.ToString()) },
                {"ScaleX", new BsonString(sx.ToString()) },
                {"ScaleY", new BsonString(sx.ToString()) },
                {"info", new BsonArray(info) }
            };
        }
        public BsonDocument returnDoc()
        {
            return document;
        }
    }
}
