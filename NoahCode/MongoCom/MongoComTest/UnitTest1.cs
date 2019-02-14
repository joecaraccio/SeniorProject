using NUnit.Framework;
using MongoCom;
using System;
using MongoDB.Driver;
using MongoDB.Bson;

namespace Tests
{
    public class Tests
    {
        [SetUp]
        public void Setup()
        {
            
        }

        [Test]
        public void Test1()
        {
            HoloTourCom test = new HoloTourCom("TestTour");
            string[] into = { "test", "test" };
            DBObject a = new DBObject( test.returnTourId(), "Tour1", (float)2.0, (float)3.0, (float)4, (float)5.0, (float)6.0, (float)7.0, (float)8.0, into );
            test.setObject(a.returnDoc(), "Arrows");
            Assert.Pass();
        }
    }
}