using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using MongoCom;
using Excel = Microsoft.Office.Interop.Excel;
using Microsoft.Office.Interop;

namespace ConsoleApp4
{
    class Program
    {
        static void Main(string[] args)
        {
            // The code provided will print ‘Hello World’ to the console.
            // Press Ctrl+F5 (or go to Debug > Start Without Debugging) to run your app.
            Console.WriteLine("Hello World!");
            HoloTourCom com = new HoloTourCom("tour");
            List<LogInfo> logs = com.dumpLogInfo();
            Excel.Application ex = new Excel.Application();
            ex.Visible = true;
            ex.Workbooks.Add();
            Excel._Worksheet sheet = (Excel.Worksheet)ex.ActiveSheet;
            sheet.Cells[1,"A"] = "tourName";
            sheet.Cells[1, "B"] = "userID";
            sheet.Cells[1, "C"] = "userHeight";
            sheet.Cells[1, "D"] = "objLocX";
            sheet.Cells[1, "E"] = "objLocY";
            sheet.Cells[1, "F"] = "objLocZ";
            sheet.Cells[1, "G"] = "roll";
            sheet.Cells[1, "H"] = "pitch";
            sheet.Cells[1, "I"] = "yaw";
            sheet.Cells[1, "J"] = "w";
            sheet.Cells[1, "K"] = "frame";
            sheet.Cells[1, "L"] = "timeStamp";
            for ( int i =0; i < logs.Count; i ++)
            {
                sheet.Cells[2 + i, "A"] = logs[i].returnTourName();
                sheet.Cells[2 + i, "B"] = logs[i].returnUserID();
                sheet.Cells[2 + i, "C"] = logs[i].returnUserHeight();
                sheet.Cells[2 + i, "D"] = logs[i].returnX();
                sheet.Cells[2 + i, "E"] = logs[i].returnY();
                sheet.Cells[2 + i, "F"] = logs[i].returnZ();
                sheet.Cells[2 + i, "G"] = logs[i].returnRoll();
                sheet.Cells[2 + i, "H"] = logs[i].returnPitch();
                sheet.Cells[2 + i, "I"] = logs[i].returnYaw();
                sheet.Cells[2 + i, "J"] = logs[i].returnW();
                sheet.Cells[2 + i, "K"] = logs[i].returnTime();
                sheet.Cells[2 + i, "L"] = logs[i].returnStamp();
            }
            com.deleteLogInfo();
            // Go to http://aka.ms/dotnet-get-started-console to continue learning how to build a console app! 
        }
    }
}
