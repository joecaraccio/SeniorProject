using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Urho;

namespace Sandbox.Desktop
{
    class Program
    {
        //launchs the platform on desktop
        //makes it a little easier for testing
        static void Main(string[] args)
        {
            Console.WriteLine("Hey Joe");
            Console.WriteLine("Hey Joe");
            Console.WriteLine("Hey Joe");
            Console.WriteLine("Hey Joe");
            Console.WriteLine("Hey Joe");
            string path = System.IO.Directory.GetCurrentDirectory();

            Urho.Desktop.DesktopUrhoInitializer.AssetsDirectory = "../../../Assets";
            new Main().Run();

        }
    }
}
