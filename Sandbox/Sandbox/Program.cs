using System;
using System.Collections.Generic;
using Windows.ApplicationModel.Core;
using Urho;
using Urho.Gui;
using Urho.Actions;
using Urho.SharpReality;
using Urho.Shapes;
using Urho.Resources;

namespace Sandbox
{
   
    // Entry Point to HoloLens
    internal class Program
    {
        [MTAThread]
        static void Main()
        {
            var appViewSource = new UrhoAppViewSource<SandboxApplication>(new ApplicationOptions("Data"));
            appViewSource.UrhoAppViewCreated += OnViewCreated;
            CoreApplication.Run(appViewSource);
        }

        static void OnViewCreated(UrhoAppView view)
        {
            view.WindowIsSet += View_WindowIsSet;
        }

        static void View_WindowIsSet(Windows.UI.Core.CoreWindow coreWindow)
        {
            // you can subscribe to CoreWindow events here
        }
    }

    public class SandboxApplication : StereoApplication
    {
        //Mode Enum 
        //use to determine what mode the application is in
        //Mode Explanation
        // 1 - View - Select an Exhibit to View 
        // 2 - Create - Create a new Exhibit (or edit an existing)
        // 3 - AudioOnly - Audio Only Tour of an Exhibit
        enum AppMode { View = 1, Create, AudioOnly};

        int mode = (int) AppMode.View; //Default to View Mode


        Node earthNode;

        Window UtilityBar;
        UIElement uiRoot;

        int Mode = 

        public SandboxApplication(ApplicationOptions opts) : base(opts) { }

        /*
         * All resources are housed in Data/.... (such as textures)
         * 
         * API REFERNCE: https://developer.xamarin.com/api/root/Urho/
         * The HoloToolKit ported from Unity is located in Urho.SharpReality.HoloToolkit
         * 
         * ALWAYS use LeftHanded Coordinate System (https://docs.microsoft.com/en-us/windows/uwp/graphics-concepts/coordinate-systems)
         * meaning Z is outward.. RightHanded means Z is inward
         * X is Left Right, Z is Forward Back (Greater the Value the farther away), Y is Vertical
         * 
         * have a cortana/gesture based menu that can appear
         * which is atrans parent window locked at that certain point
         * 
         * 
         * use URHO gui elements to create things like timeline
         * might need to make those square to you relatively
           object placement focuses the object..allows you to move it
        */

        //Entry Point of Application
        protected override async void Start()
        {
           
            // Create a basic scene, see StereoApplication
            base.Start();

            var cache = ResourceCache;

            // Enable input
            EnableGestureManipulation = true;
            EnableGestureTapped = true;

            //Show Loading Hologram

            //Initalize Database Connection

            //Indicate if connection is succesful



            // Create a node for the Earth
            earthNode = Scene.CreateChild();
            earthNode.Position = new Vector3(0, 0, 1.5f); //1.5m away
            earthNode.SetScale(0.3f); //D=30cm

            // Scene has a lot of pre-configured components, such as Cameras (eyes), Lights, etc.
            DirectionalLight.Brightness = 1f;
            DirectionalLight.Node.SetDirection(new Vector3(-1, 0, 0.5f));

            //Sphere is just a StaticModel component with Sphere.mdl as a Model.
            var earth = earthNode.CreateComponent<Sphere>();
            earth.Material = Material.FromImage("Textures/Earth.jpg");

            var moonNode = earthNode.CreateChild();
            moonNode.SetScale(0.27f); //27% of the Earth's size
            moonNode.Position = new Vector3(1.2f, 0, 0);

            // Same as Sphere component:
            var moon = moonNode.CreateComponent<StaticModel>();
            moon.Model = CoreAssets.Models.Sphere;

            moon.Material = Material.FromImage("Textures/Moon.jpg");

            // Run a few actions to spin the Earth, the Moon and the clouds.
            earthNode.RunActions(new RepeatForever(new RotateBy(duration: 1f, deltaAngleX: 0, deltaAngleY: -4, deltaAngleZ: 0)));
            await TextToSpeech("Hello world from UrhoSharp!");

            // More advanced samples can be found here:
            // https://github.com/xamarin/urho-samples/tree/master/HoloLens
        }

        //This Constantly Runs
        //timeStep = frame in seconds (this field is seconds that we can use as timestamp)
        protected override void OnUpdate(float timeStep)
        {
            //System.Diagnostics.Debug.WriteLine("HI JOE " + timeStep.ToString());
            Vector3 R = RightCamera.Node.WorldPosition;
            Vector3 L = LeftCamera.Node.WorldPosition;
            System.Diagnostics.Debug.WriteLine(L.X.ToString() + " - " + L.Y.ToString() + " - " + L.Z.ToString());
        }

        void testFunction()
        {
            //Urho.SharpReality.SpatialMeshInfo
            //var t1 = Urho.SharpReality.SpatialMeshInfo;
        }

        //interact with the Hololens Spatial Mapping API
        //return the user's point
        //uses LEFT HAND (x-left and right, z-front and back, y - vertical)
        Vector3 GetUserPosition()
        {
            return LeftCamera.Node.WorldPosition;
        }

        //Timestamp logging mode

        //Functions to be written
        //PlaceObject
        // we need to play an object knowing X,Y,Z... Scale..


        // For HL optical stabilization (optional)
        public override Vector3 FocusWorldPoint => earthNode.WorldPosition;

        //Handle input:

        Vector3 earthPosBeforeManipulations;
        public override void OnGestureManipulationStarted() => earthPosBeforeManipulations = earthNode.Position;
        public override void OnGestureManipulationUpdated(Vector3 relativeHandPosition) =>
            earthNode.Position = relativeHandPosition + earthPosBeforeManipulations;

        public override void OnGestureTapped() {
            System.Diagnostics.Debug.WriteLine("")
        }


        public override void OnGestureDoubleTapped() {

        }
    }
}