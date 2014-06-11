Ingress-Version-Extractor
==================

This java-application allows you to extract the client-id from every Ingress-Apk and to save it into a file. Thus you will be able to get the latest id-string, needed to connect your Ingress-Bot to the official servers. This tool is a stand-alone version of the component used in the Ingress-Bot, which was developed by me quite a long time ago :D.

Example of an extracted client-id: `2013-08-07T00:06:39Z a52083df5202 opt`

Agreement
---------

***This source is for educational purpose only.***

You agree that you are solely responsible for any breach of your obligations under the Ingress Terms of Service (http://www.ingress.com/terms) or any applicable law or regulation, and for the consequences (including any loss or damage) of any such breach.

YOUR USE OF THIS CODE IS AT YOUR OWN DISCRETION AND RISK AND YOU EXPRESSLY UNDERSTAND AND AGREE THAT YOU ARE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, CONSEQUENTIAL OR EXEMPLARY DAMAGES THAT MAY BE INCURRED BY YOU, INCLUDING THE LOSS OF INGRESS ACCOUNT.

How To Use
------------------

What you need to get first:

* (latest) Ingress-Apk (from Google Play Store or somewhere else)
* apktool.jar **[already included]**

What you have to do next:

1. First you have to place the `apktool.jar` into the `res/`-folder, if it isn't already in there.
2. The only thing that comes next, is to launch the application and to set two parameters.<br />`java IVEMain <apkFilePath> <releaseDate>`

Short example:

`java IVEMain res/apktool.jar 2013-08`

=> `2013-08-07T00:06:39Z a52083df5202 opt`

Now that you've successfully obtained the necessary client-id, you can use it as payload for the *nemesisSoftwareVersion*.

`{"nemesisSoftwareVersion":"2013-08-07T00:06:39Z a52083df5202 opt","deviceSoftwareVersion":"4.1.1"}`

Further Information
---------------------------

* Author: Dennis Leroy Wigand
* Version: 1.0.0

Libraries used:

* [apktool](http://code.google.com/p/android-apktool/) - A tool for reverse engineering Android apk files.

Licenses
-------------

Didn't give a thought about that yet. So feel free to use it as you like.
