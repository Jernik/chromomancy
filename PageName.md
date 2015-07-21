# Introduction #
A tutorial for 0.11.6-ish that covers the basics


# Details #
The  Basics
> Chromomancy runs in two threads, the main thread that handles all of the actual game stuff, and the drawingThread, which handles drawing to the screen.

> The main game loop is located inside of DisplayObjects.Display constructor. Inside of the main game loop, the method step() is called. Step() calls all of the sub-step classes, checks collisions, and basically takes care of all updates.

Just about every class has a class field “public DisplayObjects.Display D;”
This enables every object to access and manipulate the data of every other object
For example, a bullet could access D.you.luminence to decrease it.

Maps
All maps extend Maps.map. Maps hold entities, keep track of which maps are attached to this map, if there is a pylon on the map, etc.