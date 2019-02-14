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
        public void MakeNewTourInfo()
        {
            HoloTourCom test = new HoloTourCom("Test");
            test.createNewTour();
            string[] into = { "test", "test" };
            DBObject a = new DBObject(test.returnTourId(), "Tour1", (float)2.0, (float)3.0, (float)0.003920392, (float)5.0, (float)6, (float)7, (float)1, into);
            DBObject b = new DBObject(test.returnTourId(), "Tour12", (float)2.0, (float)3.0, (float)0.003920392, (float)1, (float)11, (float)71, (float)1, into);
            DBObject c = new DBObject(test.returnTourId(), "Tour3", (float)2.0, (float)3.0, (float)0.003920392, (float)2, (float)6.1, (float)1, (float)1, into);
            DBObject d = new DBObject(test.returnTourId(), "Tour4", (float)2.0, (float)3.0, (float)0.003920392, (float)1, (float)1, (float)1, (float)1, into);
            DBObject e = new DBObject(test.returnTourId(), "Tour5", (float)2.0, (float)3.0, (float)0.003920392, (float)3, (float)1, (float)1, (float)1, into);
            test.setObject(a.returnDoc(), "Arrows");
            test.setObject(b.returnDoc(), "Balloons");
            test.setObject(c.returnDoc(), "Balloons");
            test.setObject(d.returnDoc(), "Balloons");
            test.setObject(e.returnDoc(), "Balloons");
            test = new HoloTourCom("Test");
            test.createNewTour();
            a = new DBObject(test.returnTourId(), "Tour1", (float)2.0, (float)3.0, (float)0.003920392, (float)5.0, (float)6, (float)7, (float)1, into);
            b = new DBObject(test.returnTourId(), "Tour12", (float)2.0, (float)3.0, (float)0.003920392, (float)1, (float)11, (float)71, (float)1, into);
            c = new DBObject(test.returnTourId(), "Tour3", (float)2.0, (float)3.0, (float)0.003920392, (float)2, (float)6.1, (float)1, (float)1, into);
            d = new DBObject(test.returnTourId(), "Tour4", (float)2.0, (float)3.0, (float)0.003920392, (float)1, (float)1, (float)1, (float)1, into);
            e = new DBObject(test.returnTourId(), "Tour5", (float)2.0, (float)3.0, (float)0.003920392, (float)3, (float)1, (float)1, (float)1, into);
            test.setObject(a.returnDoc(), "Arrows");
            test.setObject(b.returnDoc(), "Balloons");
            test.setObject(c.returnDoc(), "Balloons");
            test.setObject(d.returnDoc(), "Balloons");
            test.setObject(e.returnDoc(), "Balloons");
            Assert.Pass();
        }
        [Test]
        public void GetOldTourInfo()
        {
            HoloTourCom test = new HoloTourCom("TestTours");
            test.openTour();
            test.getObjects();
            Assert.Pass();
        }
    }
}