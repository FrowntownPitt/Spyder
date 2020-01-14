# Spyder Controls Platform

## Intro
This is a fork of the [FIRST Tech Challenge control system](https://github.com/ftctechnh/ftc_app).  The intent of this project is to construct a compact demonstration of the autonomous and localization features offered by the platform.  

For observers not familiar with the given FTC SDK, all code written for this demo lives in [TeamCode/src/main/java/org/firstinspires/ftc/teamcode](TeamCode/src/main/java/org/firstinspires/ftc/teamcode).

### Robot Configuration
The Spyder plaform consists of 4 REV Core Hex motors placed in a "plus" configuration, each with a single REV 4-inch omni wheel.  There is a ZTE Speed phone mounted in Portrait mode facing forward, with a REV Expansion Hub placed on the left side, oriented with the USB port facing up and the IO ports facing rightward (knowing this is important for understanding the orientation of the internal IMU). 

[RobotConfiguration.java](TeamCode/src/main/java/org/firstinspires/ftc/teamcode/RobotConfiguration.java) can be used to handle setting up the motors and the IMU, along with driving the robot.  A very easy to understand example usage can be found in [Holonomic.java](TeamCode/src/main/java/org/firstinspires/ftc/teamcode/Holonomic.java)

### Vuforia Configuration
FIRST uses the [Vuforia Augmented Reality](https://www.vuforia.com/) platform to allow teams to find navigation targets to localize their robots to the field. The example code given by FIRST is very hard to read because there is so much configuration required to get it set up.  I broke it down and stripped out as much as possible into [Configuration.java](TeamCode/src/main/java/org/firstinspires/ftc/teamcode/Configuration.java) under the Vuforia internal class.  This handles initializing and "placing" the navigation targets, along with setting up some Vuforia internals.  Unfortunately, not everything could be stripped out into this configuration because of the things Vuforia needs to be initialized internally.  Also, localization happens by polling each target, which caused any such refactoring to make the configuration hard to understand instead.  

[HolonomicVuforiaNav.java](TeamCode/src/main/java/org/firstinspires/ftc/teamcode/HolonomicVuforiaNav.java) uses the Vuforia Configuration in a very basic way - Allow the user to drive and display where on the field the Vuforia system believes you are based on image recognition.
       
---

## Old FIRST Readme portion (stripped down)

## Welcome!
This GitHub repository contains the source code that is used to build an Android app to control a *FIRST* Tech Challenge competition robot.  To use this SDK, download/clone the entire project to your local computer.

If you are new to the *FIRST* Tech Challenge software and control system, you should visit the online wiki to learn how to install, configure, and use the software and control system:

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;https://github.com/ftctechnh/ftc_app/wiki

Note that the wiki is an "evergreen" document that is constantly being updated and edited.  It contains the most current information about the *FIRST* Tech Challenge software and control system.

## Downloading the Project
It is important to note that this repository is large and can take a long time and use a lot of space to download. If you would like to save time and space, there are some options that you can choose to download only the most current version of the Android project folder:

* If you are a git user, *FIRST* recommends that you use the --depth command line argument to only clone the most current version of the repository:

<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;git clone --depth=1 https://github.com/ftctechnh/ftc_app.git</p>

* Or, if you prefer, you can use the "Download Zip" button available through the main repository page.  Downloading the project as a .ZIP file will keep the size of the download manageable.

* You can also download the project folder (as a .zip or .tar.gz archive file) from the Downloads subsection of the Releases page for this repository.

Once you have downloaded and uncompressed (if needed) your folder, you can use Android Studio to import the folder  ("Import project (Eclipse ADT, Gradle, etc.)").

## Getting Help
### User Documentation and Tutorials
*FIRST* maintains an online wiki with information and tutorials on how to use the *FIRST* Tech Challenge software and robot control system.  You can access the wiki at the following address:

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;https://github.com/ftctechnh/ftc_app/wiki

### Javadoc Reference Material
The Javadoc reference documentation for the FTC SDK is now available online.  Visit the following URL to view the FTC SDK documentation as a live website:

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;http://ftctechnh.github.io/ftc_app/doc/javadoc/index.html    

Documentation for the FTC SDK is also included with this repository.  There is a subfolder called "doc" which contains several subfolders:

 * The folder "apk" contains the .apk files for the FTC Driver Station and FTC Robot Controller apps.
 * The folder "javadoc" contains the JavaDoc user documentation for the FTC SDK.

### Online User Forum
For technical questions regarding the SDK, please visit the FTC Technology forum:

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;http://ftcforum.usfirst.org/forumdisplay.php?156-FTC-Technology
