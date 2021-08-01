# AmazonFresh_ItemsDetails

This project aims to provide information of requested items from Amazon (Amazon Fresh category) and create a detailed reports of them based on below requirements:

Requirement no 1
------------------
Search for 10 items and create a report of top 10 results of each item.
It should contain - Category, Complete Item Description, Brand, Item Price (MRP, Sale-Price, Savings), Item weight, Net Quantity, In Stock(yes/no) and Link.

Requirement no 2
------------------
Find the total no. of pages for a given item.
Also log how many per page search results are present, and how many are unique.

Requirement no 3
------------------
Categorization - Find top 5 unique products that satisfy the condition (price < x rs)

Requirement no 4
------------------
Look out for all promotions and download images (store by folder of each date)

# Setup Details

## Pre-requisite

For setup, read and follow the below instructions:

1. Install GIT (https://git-scm.com/downloads)
2. Install JDK (https://www.oracle.com/in/java/technologies/javase/javase-jdk8-downloads.html)
3. Install Eclipse (https://www.eclipse.org/downloads/packages/release/neon/3/eclipse-ide-java-ee-developers)
	
## Quickstart

1. Open a Git Bash Prompt and run below command:
   `git clone https://github.com/Ishita2015/AmazonFresh_ItemsDetails.git`

2. Open Eclipse:
	1. Import Project as Maven -> Existing Maven Projects
	2. Select `AmazonFresh_ItemsDetails` project folder and click finish.

3. The project will be imported and nothing special to do.

4. Now, you can provide the list of items of your choice in `ApplicationConfig.properties` and run the `testng.xml` as TestNG suite.

5. Check the detailed report in `ResultsData` folder.
