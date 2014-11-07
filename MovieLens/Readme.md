###MOVIE RECOMMENDATION APP USING JAVA-SDK (From Eclipse IDE)

This tutorial uses the MovieLens dataset , to make a movie recommendation application in Java.

In this tutorial MovieLens dataset is used to train the recommendation engine (which we will see shortly), to generate top 4 suggested movies for any specific user.

Download the dataset from http://grouplens.org. We have used 100k dataset from this website. Alternatively, run from terminal

	$ curl -o ml-100k.zip http://files.grouplens.org/datasets/movielens/ml-100k.zip $ unzip ml-100k.zip


###ABOUT THE DATASET

MovieLens dataset consists of 100,000 ratings (1-5) from 943 users on 1682 movies.
We will use u.data in the archive(ml-100k.zip) for training, which contains


		| userID  | itenID  | userRatings  | TimeStamp  |    
		|--------:|--------:|-------------:|-----------:|    
		|   196   |   242   |       3      |  881250949 |  
		|   186   |   302   |       3      |  891717742 |  
		|    22   |   377   |       1      |  878887116 |  
		|   244   |    51   |       2      |  880606923 |  
		|   166   |   346   |       1      |  886397596 |  





**u.item**  - (itemID    movieTitle) form the archive can be used for retrieving the Movie Title.

**Note**: The archive contains other files also, but we are interested mainly in u.data and u.item


###PREREQUISITES

If you haven’t installed PredictionIO, you can install a minimal setup which consists of 

 * PredictionIO
 * Apache HBase (Standalone mode)
 * Apacke Spark
 * Elasticsearch

_Refer to installation page for more details._


**Lets Begin.**  
Download

 * ImportData.java
 * Show.java
 * pom.xml

>_Optional. For experts, use maven and modify the pom file accordingly to compile the project. We are using eclipse here, for simplicity and for audience not comfortable with maven._

#####Step 1: Launch Eventserver

From the terminal/console/command-prompt run, 

	$PIO_HOME/bin/pio eventserver
	$PIO_HOME is the installation directory of PredictionIO.

>_(Optional) If you like you may set this as_  

>		$export PIO_HOME=installation directory of PredictionIO


#####Step 2 : Configure Eclipse IDE for external jars. (Skip if you plan to use Maven or Maven plugin in Eclipse instead.)

Right click on your project folder in eclipse, and select BUILD PATH => CONFIGURE BUILD PATH. In the LIBRARIES tab, click ADD EXTERNAL JARS.

To get the all the external jar files required to build the project :-

**Alternate 1** (Recommended):
>Use the pom.xml to get jars!

Download the pom.xml file to any directory you like (eg. workspace directory)
Then form the terminal run the following commands.

	$ cd workspace
	$ mvn dependency:copy-dependencies

If you don’t have maven installed , or you are unclear try

>	In Ubuntu :

>		sudo apt-get install mvn  
>	In Windows: Download directly from website 
_Or try, http://www.tutorialspoint.com/maven/maven_environment_setup.htm_

All the jar files will get downloaded in the workspace/target/dependency directory automatically. Add these to the the build path in eclipse as mentioned above.



**Alternate 2**:  
>Download all the external jars manually.  

Download the following list of Java Libraries (jars) to your desired project folder.


1. [Client-0.8.0.jar](https://oss.sonatype.org/content/repositories/releases/io/prediction/client/0.8.0/)  
2. [Gson-2.2.4.jar](http://code.google.com/p/google-gson/)  
3. [Guava-18.0.jar](http://repository.opencastproject.org/nexus/content/repositories/public/com/google/guava/guava/18.0/)  
4. [Joda-time-2.3.jar](https://oss.sonatype.org/content/repositories/releases/joda-time/joda-time/2.3/)  
5. [Netty-3.9.2-final.jar](http://repo1.maven.org/maven2/io/netty/netty/3.9.2.Final/)  
6. [Slf4j-api-1.7.5.jar](http://mvnrepository.com/artifact/org.slf4j/slf4j-api/1.7.5)  
7. [Slf4j-nop-1.7.7.jar](http://repo1.maven.org/maven2/org/slf4j/slf4j-nop/1.7.7/)   
8. [Async-http-client-1.8.14.jar](https://oss.sonatype.org/content/repositories/releases/com/ning/async-http-client/1.8.14/)  


After you download all these jar files, make sure to add them to Build Path, as mentioned above.
	
	In Eclipse: CONFIGURE BUILD PATH=>Add External Jars

#####Step 3: Loading training data to eventserver.
Create a new class ImportData.java in your project folder.   Use the **ImportData.java** file here.
Pass the command line arguments 1 and ‘address of u.data on disk’. 

	In Eclipse: go to Run=>Run Configurations

>	Click on the arguments tab, and provide the arguments , "(app_id)(address of u.data)"

![image](https://raw.githubusercontent.com/abhishekambastha/PredictionIO-Java/master/MovieLens/images/arguments.png)

In our case, we are using app_id =1 and ~/ml-100k/u.data as arguments.  

#####Step 4: Create an engine instance.
In the terminal/console type 

		$ PIO_HOME/bin/pio instance io.prediction.engines.itemrec 
		$ cd io.prediction.engines.itemrec 
		$ $PIO_HOME/bin/pio register

_A folder io.prediction.engines.itemrec will get created in the current folder (home folder by default). 
You can change to your workspace/projects folder if you wish to create this instance folder in your desired location._

Go to this newly created folder io.prediction.engines.itemrec/params and edit the file: datasource.json

		{   
		"appId": <app_id>,   
		"actions": [
		     "view",
		     "like",
		     ...   ],
		   ... }
		Replace <app_id> with the integer you used in ImportData.java (1 in our case)

		{   
		"appId": 1,   
		"actions": [
		     "view",
		     "like",
		     ...   ],
		   ... }


#####Step 5: Train the engine instance and deploy
You are now ready to train the recommendation engine. Run the command:

	 $PIO_HOME/bin/pio train
Now deploy :-

	 $PIO_HOME/bin/pio deploy

_Check if Engine Server is deployed successfully:
Visit the page [http://localhost:8000](http://localhost:8000)_

You will see information associated with engine instance like when it is started, trained, its component classes and parameters.

#####Step 6: Retrieve the results.
Create another class( show.java) in eclipse. Use the sample code Show.java here.
Compile and run Show.java. You will get the itemIDs of the top 4 recommended movies for the user id provided in show.java.

Congratulations!! You have made your first item recommendation app.  


  

####BUT HOW DOES IT WORK? (Optional)

A detailed discussion is beyond the scope of this tutorial. But a brief explanation goes here.

This section is aimed to give a minimal understanding of PredictionIO and its API.

PredictionIO runs with the help of Apache HBase, Apache Spark and Elasticsearch. So you installed them before starting !

![image](https://raw.githubusercontent.com/abhishekambastha/PredictionIO-Java/master/MovieLens/images/pIOsetup.png)

 
![image](https://raw.githubusercontent.com/abhishekambastha/PredictionIO-Java/master/MovieLens/images/pIOrunning.png)
	

when we run 

	$PIO_HOME/bin/pio eventserver, a webservice is created.
 
The eventserver then

1.	Listens for the incoming training data. (For different apps, app_ids)
2.	Generates an instance (object) of the eventserver

		($PIO_HOME/bin/pio instance io.prediction.itemrec)
3.	Sets/Registers this instance for training 

		$PIO/bin/pio/register
4.	 Train the instance with the data captured in Step 1. 

		$PIO_HOME/bin/pio train
5.	Deploys this instance for the application’s use. 

		$PIO_HOME/bin/pio deploy
	This step binds the instance to a port (localhost:8000 by default).

**Note** : Before Training, we need to specify , which application this instance is associated with , so we set the app_id parameter in datasourse.json before step 4.


 
After the instance is deployed, an app can send query for recommendations and get results. The query can be send using http post, or any one of the SDK’s (Java, Python, Ruby or PHP).

The query result is a JSON object. For more details on JSON refer other sources.


