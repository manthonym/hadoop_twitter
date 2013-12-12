Hadoop Twitter
==============

Utilisation 
-----------

To run the application:

`hadoop jar ./target/DataLoader-0.0.1-jar-with-dependencies.jar /input folder/ /ouputfolder`

Input files need to be in hdfs:
Use `hadoop fs -put /localfilesystem /hdfs`

Roadmap
-------
Cleaning HTML files (removing tags)
Using M/R algorithm


To do
-----
Delete useless wods (Lucene FrenchAnalyzer)
Create Mahout Script for training
Create Mahout Scrip tweet analyzer


Copyright
---------
Anthony