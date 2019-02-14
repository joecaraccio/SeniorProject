using NUnit.Framework;
using MongoCom;
using System.Collections.Generic;
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
            HoloTourCom test = new HoloTourCom("TestTourzTors");
            test.createNewTour(6.5);
            string[] into = { "test", "test" };
            DBObject a = new DBObject(test.returnTourId(), test.returnTourName(), "temp", (float)2.0, (float)3.0, (float)0.003920392, (float)5.0, (float)6, (float)7, (float)1, into);
            DBObject b = new DBObject(test.returnTourId(), test.returnTourName(), "baloon", (float)2.0, (float)3.0, (float)0.003920392, (float)1, (float)11, (float)71, (float)1, into);
            DBObject c = new DBObject(test.returnTourId(), test.returnTourName(), "test", (float)2.0, (float)3.0, (float)0.003920392, (float)2, (float)6.1, (float)1, (float)1, into);
            DBObject d = new DBObject(test.returnTourId(), test.returnTourName(), "Arrow", (float)2.0, (float)3.0, (float)0.003920392, (float)1, (float)1, (float)1, (float)1, into);
            DBObject e = new DBObject(test.returnTourId(), test.returnTourName(), "Arrow", (float)2.0, (float)3.0, (float)0.003920392, (float)3, (float)1, (float)1, (float)1, into);
            test.setObject(a.returnDoc());
            test.setObject(b.returnDoc());
            test.setObject(c.returnDoc());
            test.setObject(d.returnDoc());
            test.setObject(e.returnDoc());
            test = new HoloTourCom("Tested");
            test.createNewTour(6.5);
            a = new DBObject(test.returnTourId(), test.returnTourName(), "temp", (float)2.0, (float)3.0, (float)0.003920392, (float)5.0, (float)6, (float)7, (float)1, into);
            b = new DBObject(test.returnTourId(), test.returnTourName(), "baloon", (float)2.0, (float)3.0, (float)0.003920392, (float)1, (float)11, (float)71, (float)1, into);
            c = new DBObject(test.returnTourId(), test.returnTourName(), "test", (float)2.0, (float)3.0, (float)0.003920392, (float)2, (float)6.1, (float)1, (float)1, into);
            d = new DBObject(test.returnTourId(), test.returnTourName(), "Arrow", (float)2.0, (float)3.0, (float)0.003920392, (float)1, (float)1, (float)1, (float)1, into);
            e = new DBObject(test.returnTourId(), test.returnTourName(), "Arrow", (float)2.0, (float)3.0, (float)0.003920392, (float)3, (float)1, (float)1, (float)1, into);
            test.setObject(a.returnDoc());
            test.setObject(b.returnDoc());
            test.setObject(c.returnDoc());
            test.setObject(d.returnDoc());
            test.setObject(e.returnDoc());
            LogInfo user1 = new LogInfo(test.returnTourId(), 6.5, 1.2, 1.2, 1.2, 2.343, 2.3, 21.2, 1);
            test.setObject(user1.returnDoc());
            user1 = new LogInfo(test.returnTourId(), user1.returnUserId(), 6.5, 1.2, 1.2, 1.2, 2.343, 2.3, 21.2, 2);
            test.setObject(user1.returnDoc());
            user1 = new LogInfo(test.returnTourId(), user1.returnUserId(), 6.5, 1.2, 1.2, 1.2, 2.343, 2.3, 21.2, 3);
            test.setObject(user1.returnDoc());
            user1 = new LogInfo(test.returnTourId(), user1.returnUserId(), 6.5, 1.2, 1.2, 1.2, 2.343, 2.3, 21.2, 4);
            test.setObject(user1.returnDoc());
            Assert.Pass();
        }
        [Test]
        public void GetOldTourInfo()
        {
            HoloTourCom test = new HoloTourCom("Tested");
            test.openTour();
            List<DBObject> x = test.getObjects();
            if (test.getAdminHeight() == 6.5 && x[0].returnInfo()[2] == "test")
                Assert.Pass();
            else
                Assert.Fail("value: " + test.getAdminHeight().ToString());
        }
        [Test]
        public void checkNames()
        {
            HoloTourCom test = new HoloTourCom("TestT");
            if (test.showAllTourNames().Contains("TestTourz"))
            {
                Assert.Pass();
            }
            else
            {
                Assert.Fail();
            }
        }
        [Test]
        public void deleteTour()
        {
            HoloTourCom test = new HoloTourCom("Testing");
            test.openTour();
            test.removeAllTourObj();
            Assert.Pass();
        }

    }
}