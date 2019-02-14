﻿using System;
using MongoDB.Bson;
using MongoDB.Driver;
using System.Collections.Generic;
using System.Text;

namespace MongoCom
{
    public class DBObject
    {
        private BsonDocument document;
        public DBObject(ObjectId id, string name, float x, float y, float z, float rx, float ry, float rz, float s, String[] info)
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
                {"Scale", new BsonString(s.ToString()) },
                {"info", new BsonArray(info) }
            };
        }
        public BsonDocument returnDoc()
        {
            return document;
        }
    }
}
