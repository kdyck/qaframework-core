[![Build Status](https://travis-ci.org/bonigarcia/webdrivermanager.svg?branch=master)](https://travis-ci.org/bonigarcia/webdrivermanager)
[![badge-jdk](https://img.shields.io/badge/jdk-8-green.svg)](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
[![License badge](https://img.shields.io/badge/license-Apache2-green.svg)](http://www.apache.org/licenses/LICENSE-2.0)
[![Twitter Follow](https://img.shields.io/twitter/follow/qarepo.svg?style=social)](https://twitter.com/qarepo)

#  QARepo Core Framework 

A test framework that aims to simplify the setup and maintenance of Selenium WebDriver tests for Java. Our goal is to provided developers and testers the ability to write Selenium WebDriver tests with minimal configuration. It utilizes TestNG for managing test runs, [WebDriverManager](https://github.com/bonigarcia/webdrivermanager/) to manage binary drivers (e.g. *chromedriver*, *geckodriver*, etc.) needed for [Selenium WebDriver].
It features a DriverFactory, threadsafe WebDrivers for parallel test runs, WebDriverWaits for waits, polling and exception handling and utilities for processing user-agents, proxies and Selenium.


### Table of contents
* [QARepo-Core as Maven dependency](#Maven)
* [Basic Usage]()
* [Running TestNG Test Suite](#Running TestNG Suite)
    - [Using Docker Image](#Using Docker Image)
    - [Using Shaded Executable jar](#Using Shaded Executable jar)
    - [Using testng.xml and maven](#Using testng.xml and maven)
* [Technologies](#technologies)
* [Properties](#properties)
* [Need Help](#need-help)

 ----------------------------------
   
## Maven 
  ```
    <dependency>
       <groupId>com.qarepo</groupId>
      <artifactId>qaframework-core</artifactId>
       <version>1.0.0</version>
    </dependency>
  ```

## Basic Usage

This package included Sample test

* _TestNG config files are located in directory /src/main/resources/test-configs/_
   ++ **Docker conf**: docker-testng.xml
   ++ **Production conf**: testng.xml

## Running TestNG Suite

Below are details on how to run this test suite. You can run the tests using Docker, Shaded Executable Jar or Locally from an IDE or Terminal
 
#### Using Docker Image
1. Build docker-compose and start services: _(Dockerized TestNG suite service use stand-alone chrome from Selenium)_
    ```
    $ docker-compose up --build
    ```
   This will start the test service and selenium stand-alone chrome browser. 
   The test suite will begin running immediately so make sure you only 'include' the groups you want to run to optimize feedback.  
#### Using Shaded Executable jar
1. Clone directory: 
    ```
    $ git clone https://github.com/kdyck/qaframework-core.git
    ```
2. Clean project and build and package shaded executable jar:
    ```
    $ mvn clean package
    ```
3. Run *-SHADED.jar with ```String``` testng.xml and/or stage-testng.xml file:
   ```
   $ java -jar ./target/QAREPO-CORE-1.0.0-SHADED.jar "./src/main/resources/test-configs/testng.xml"
   ```     
   
#### Using testng.xml and maven
1. Clone directory: 
    ```
    $ git clone https://github.com/kdyck/qaframework-core.git
    ```
2. Navigate to ./src/resources/test-config directory
2. Open testng.xml conf file for url under test
3. Right-click testng.xml conf-file , click 'run' and select run as 'TestNG test' -or- while in *.xml file use shortcut CTRL+SHIFT+F10 (Intellij IDEA Only)

### Issues or Need help? 
* [rafaeldyck@gmail.com](mailto:rafaeldyck@gmail.com)
* [kdyck00@gmail.com](mailto:kdyck00@gmail.com)

