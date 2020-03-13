[![badge-jdk](https://img.shields.io/badge/jdk-8-green.svg)](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
[![License badge](https://img.shields.io/badge/license-Apache2-green.svg)](http://www.apache.org/licenses/LICENSE-2.0)
[![Twitter Follow](https://img.shields.io/twitter/follow/qarepo.svg?style=social)](https://twitter.com/qarepo)


#  QARepo Core Framework (QCF)

QARepo core framework(QCF...pronounced cough) is Java-based framework that aims to simplify automated functional, UI and regression testing. QCF takes an opinionated approach to test automation and is designed to provide developers and testers the ability to easily tests web applications. QCF acts a wrapper on top of [Selenium WebDriver](https://github.com/SeleniumHQ/selenium/tree/master/java/client/src/org/openqa/selenium) and uses [TestNG](https://testng.org/doc/) testing framework. QCF is OS neutral and runs on Mac, Windows, and Linux.
It manages local WebDriver binaries via [WebDriverManager](https://github.com/bonigarcia/webdrivermanager/) and remote WebDrivers via SeleniumHQ's docker images. QARepo core is containerize using Docker and will run on any operating system or browser. 

The framework is developer-centric and features the ability to run tests in the cloud, via Docker containers or locally. QCF has various tools and utilities for managing common test actions (e.g. window handling, alerts, Data Provider, file readers etc.).  QCF currently manages reporting using Lo4j2 but we are currently working on rich HTML reports.
QCF is work in progress but its a labor-of-love. [Drop us a line](#issues-or-need-help) if you have questions or suggestions. _Happy testing!_

---
   
## Maven Dependency 
  ```
    <dependency>
       <groupId>com.qarepo</groupId>
      <artifactId>core-framework</artifactId>
       <version>1.0.0-SNAPSHOT</version>
    </dependency>
  ```

## Basic Usage

Managing WebDrivers, parallel test execution and threading can be difficult. QCF features a DriverFactory, threadsafe WebDriver instances and parallel test execution. 
This project includes a few sample tests that you can run locally using WebDriverManager binaries or via Docker containers. Review the [Running TestNG Suite](#Running TestNG Suite) for details. 

* _TestNG config files are located in directory /src/main/resources/test-configs/_
   * **Docker conf**: docker-testng.xml
   * **TestNG conf**: testng.xml

## Running TestNG Suite

Below are details on how to run this test suite and the sample tests. You can run the tests using Docker, shaded executable Jar or locally from an IDE or command line.
 
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
3. Run *-SHADED.jar with ```String``` testng.xml file:
   ```
   $ java -jar ./target/core-framework-1.0.0-SHADED.jar "./src/main/resources/test-configs/testng.xml"
   ```     
   
#### Using testng.xml and maven
1. Clone directory: 
    ```
    $ git clone https://github.com/kdyck/qaframework-core.git
    ```
2. Navigate to ./src/resources/test-config directory
2. Open testng.xml conf file for url under test
3. Run the testng.xml conf-file

### Issues or Need help? 
* [rafaeldyck@gmail.com](mailto:rafaeldyck@gmail.com)
* [kdyck00@gmail.com](mailto:kdyck00@gmail.com)
* [admin@qarepo.com](mailto:admin@qarepo.com)

