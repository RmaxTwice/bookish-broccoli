## (WIP) Image Editor ##

This image editor is a software development project designed and built to fulfill the requirements outlined in the assignment of the current curriculum of the subject *Digital Image Processing* of the Computer School of the Sciences Faculty of the Universidad Central de Venezuela.

### Contents: ###
- Authors.
- Requirements.
- Assignment.
- Features.
- Future Ideas / Observations.

### Authors

- Rafael Vasquez
- Raquel Escalante

### Requirements

This project is being developed using Java 8 and Netbeans IDE 8.2, using only the Java core libraries.

### Assignment

The assignment is a multi-stage software development  project, in which each succesive stage builds upon the previous ones. In short it is to:

> Build a GUI application in Java (Netbeans) that allows its users to open, view, edit and finally save images in **format BMP and Netpbm** (\*.ppm, \*.pgm, \*.pbm), also implementing the features described in the *Features* Section. 

**The starting point of this repository is the complete application of this first stage.**

***(WIP):*** As the next stages get released to be worked on, either author shall update this readme document.

### Features

The application must have the following features implemented:

##### First Stage
- Negative of an image.
- Conversion to grayscale.
- Conversion to black and white.
- 90º rotation Clockwise and Counter-Clockwise of the image.
- Display number of unique colors present in the image.
- *(Optimizing possible)* Saving the images in the most efficient way possible, memory space wise.
- *(Optimizing possible)* Saving and Opening of images in a custom RLE compression format.

##### Second Stage
-  Display image information, such as: dimensions, bits per pixel, number of unique colors and **(Pending)** dots per inch.
-  Generation and display of the image's Histogram
-  Contrast and Brightness adjustment.
-  Dynamic Thresholding
-  Scaling and free rotation (no angle constraint).
-  Zoom in & Zoom out.
-  Gradient calculation using Sobel, Roberts and Prewitt filters.
-  Average, median and Laplacian of Gaussian (LoG) filters.
-  Apply a custom kernel filter to the image, there should be a simple and elegant way to choose the size and values of it. (Said kernel must be minimun 2x1 or 1x2 to a maximun of 7x7).
- **(Optional & Pending)** Thread based acceleration of the application in java, will only be taken into account if the rest of the features were successfully implemented.

##### Third Stage

From here on, the use of the JavaCV library is allowed and encouraged to do the heavy lifting when necessary.

- **(Pending)** Color reduction of an image of 24 bits using at least 2 different approaches (palette conversion, pixel's bit reduction, k-means, etc.)
- **(Pending)** Automatic Thresholding using OTSU (JavaCV and your own implementation) and another method.
- **(Pending)** Morphological image processing such as: Erosion, Dilation, Opening and Closing. Also allowing the user to create his/her own structuring element (kernel).
- **(Pending)** Undo and Redo at least 2 operations.

### Future Ideas / Observations
- **(Pending)** Real-time image change while selecting threshold dynamically.
- **(Pending)** Super-Sampling in Scaling operation.
*(WIP)*


