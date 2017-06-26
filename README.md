# Event-Driven-Framework

## Downloads:

1. [Apache Kafka](https://kafka.apache.org/downloads). Download the .tgz file and extract to home Directory.
2. [Twitter4j Jar](http://www.java2s.com/Code/JarDownload/twitter4j/twitter4j.jar.zip). Download .zip file and extract to home directory.
3. [Dgraph](https://docs.dgraph.io/get-started/). Download using curl.
4. [Docker](https://www.docker.com/community-edition). Download. (We wont be using docker.)

## Initial Background \services to run:-
1. Zookeeper instance for Kafka. 

    Command:-(For linux and mac)
    > bin/zookeeper-server-start.sh config/zookeeper.properties
    
    (For Windows)
    > bin\windows\zookeeper-server-start.bat config\zookeeper.properties
2. Kafka Broker instance. 

    Command:- (For Linux and Mac)
    > bin/kafka-server-start.sh config/server.properties
    
    (For Windows)
    > bin\windows\kafka-server-start.bat config\server.properties
    
3. Dgraph. 
    
    Command:- (Linux and mac only)
    > dgraph

## Extra work for Apache Kafka:-

It is needed to create a topic for communication in the Publisher and Subscriber Model. 

    Command:- (Linux and Mac)
    > bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic topic-name
    
    For Windows
    > bin\windows\kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic topic-name

## Running Apache Kafka-Producer and Kafka-Consumer:-

Now that all the above services are running run the program in a terminal **kafka_producer.java** file in the **_kafka-files_** folder.

    Command to Execute the file:-
    > javac -cp "/path/to/kafka/kafka_2.11-0.9.0.0/lib/*" kafka_producer.java

    Command to run the file:-
    > java -cp "/path/to/kafka/kafka_2.11-0.9.0.0/lib/*":. kafka_producer
    
The above command should display the hashtags on the terminal.

Now in a new terminal run the **kafka_consumer.java** file in the **_kafka-files_** folder.

    Command to Execute the file:-
    > javac -cp "/path/to/kafka/kafka_2.11-0.9.0.0/lib/*" kafka_consumer.java

    Command to run the file:-
    > java -cp "/path/to/kafka/kafka_2.11-0.9.0.0/lib/*":. kafka_consumer

After running the Consumer You will get a string on the terminal which will be inserted into the Dgraph localhost client.

## Setting up Dgraph and Entering the data.

Since Dgraph instance is already up and running, Lets run the **CsvToDgraph.java** file from the **_csvtodgraph_** folder and get the query string in a file.

The file does the following:-
1. Read data from the .csv file.
2. Process them into a **field** object.
3. Create the query string required for Dgraph.

**At the end Copy the string and paste on a terminal and run.**

## Fetching data from Dgraph:

Data is fetched from dgraph from the web page currently but soon i will change it form the web page to any programmetic method. Copy pasting the result to a **.json** file and using it in the javascript in **d3.js**.

## Instructions:

1. Deploy the web server:- In command prompt go to the folder and run 

> node HTTP_SERVER.js

2. Open the Web Browser and open the following URL:-

> http://localhost:8080/HTML/Start.html

**Thank You**
