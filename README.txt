Start the mongo DB
> cd  C:\Users\mar_9\Desktop\Thesis\SecondSupervisor\Development\mongodb-win32-x86_64-2008plus-ssl-4.0.6\bin
> mongod.exe

Start the application 
> cd C:\Users\mar_9\Documents\NetBeansProjects\coursematcher
> mvn clean package && java -jar target\unimatcher-1.0-SNAPSHOT.jar



Db queries : 

db.courseEntry.find()


1 page with 

I. Searching:
Search input filed 
After user's submit results are shown on the same page 

Search is by regex of course title 

II. Module matcher 
- make in one color all module with same name


II. filtering 



Home work: 
- as much as more universities made by end 
- UI how it wlooks like
- if u can also some CSS ready to use
- fix the rendering of the modules on the second course



go to  http://localhost:8080/tokens for token genration 



Template for a htmp page 

<html>
    <head>
        <title>TODO supply a title</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        
         <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="css/font-awesome.min.css">
        <link rel="stylesheet" href="css/templatemo_main.css">

    </head>
    <body>
        <div id="main-wrapper">
            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 text-left templatemo-logo margin-top-20">
                <h1 class="templatemo-site-title">
                    <a href="#">Smart student</a>
                </h1>
            </div>
            
        </div>

         <div class="backstretch" style="left: 0px; top: 0px; overflow: hidden; margin: 0px; padding: 0px; height: 937px; width: 1256px; z-index: -999999; position: fixed;">
            <img src="images/zoom-bg-1.jpg" style="position: absolute; margin: 0px; padding: 0px; border: none; width: 1561.67px; height: 937px; max-height: none; max-width: none; z-index: -999999; left: -152.833px; top: 0px;">
        </div>
 
    </body>
</html>
