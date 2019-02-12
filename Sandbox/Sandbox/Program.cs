using System;
using Urho;

namespace Sandbox
{
    class Program : Application
    {
        [Preserve]
        public Program() : base(new ApplicationOptions(assetsFolder: "Data") { Height = 1024, Width = 576, Orientation = ApplicationOptions.OrientationType.Portrait }) { }

        [Preserve]
        public Program(ApplicationOptions opts) : base(opts) { }

        static Program()
        {

        }

        protected override void Start()
        {

        }


    }
}
