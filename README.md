# cpsc410_project2_team18
# Starry Analyzer

## Summary
**Contributors: David Kim, Hee Su Kim, Josh Rayo, Toji Nakabayashi, Won Tae Lee**

Starry Analyzer allows users to analyze methods in their Java project and view the results visually.<br/>
Each of the stars represent a method which can be clicked on for more details.<br/>
Stars glowing in green represent methods that passed all tests, stars glowing in yellow represent methods with warnings, and stars glowing in red represent methods with errors.<br/>
The arrows represent method dependencies within its class, and are visualized like a constellation.<br/>
Users can navigate between classes by clicking on the Class name at the top left corner.<br/>
Every time a page is refreshed, the stars will get generated at different random location.<br/>

### Video Link:
https://youtu.be/Tsl__cAysWI

### Screenshots:
![1](https://github.students.cs.ubc.ca/cpsc410-2020w-t1/cpsc410_project2_team18/blob/master/screenshot1.png)
![2](https://github.students.cs.ubc.ca/cpsc410-2020w-t1/cpsc410_project2_team18/blob/master/screenshot2.png)

### What the analyzer checks for:
- Loop conditions should be true at least once
- Loops should not be infinite
- Checking for infinite while loop
- Empty method
- Nested blocks of code should not be left empty
- Unused used/changed variables
- Unused method parameters
- Unused imports
- TODO tags(should be removed in end)
- Method dependencies within the same class

## Tech Stack
- Java back-end with AST parser library
- JavaScript with React front-end

## Installation

This project is a maven (Java) and React (JavaScript) project. You will need to install maven and install required dependencies for React. 

### Install Java AST Parser library
- How to install maven on Windows:

    1. Download "Binary zip archive" from https://maven.apache.org/download.cgi
    2. Unzip and put "apache-maven-3.6.3" folder wherever you like. 
    3. Add "where_you_put_it\apache-maven-3.6.3\bin" to PATH environment variable
    4. test by running "mvn --version"
    5. You might have to set the JAVA_HOME environment varialble if it's not already set. For IntelliJ users, you can find it from "C:\Users\YourUser\.jdks\openjdk-15" - just make that your JAVA_HOME environment variable

- How to compile our project:

    From the root of the project run: "mvn clean package"
    You will find the jar file in "project_root/target". 
    You can run the compiled jar file using "java -jar <path_to_jar>"

- JavaParser references:

    https://javaparser.org/getting-started.html  (Get their free e-book from here)
    https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/index.html

### Install dependencies for React
Make sure you have **npm** installed. You can check if it's already installed by running the command `npm -version` or `npm --version` on the terminal. If not installed, refer to the following link: (https://www.npmjs.com/get-npm).

Inside `cpsc410_project2_team18\src\ui` directory, run `npm install`. This will install the required dependencies for the front-end.

## Running the Project

1. `cpsc410_project2_team18\src\main\java\com\Parser` includes the main method that should be run. Add this as the Main class under configuration.
2. When you run the `Parser` class, you will be prompted with a window telling you to select a Java file or a directory. Navigate to the project you would like to analyze,then select the root project folder or individual files you want to analyze. Click 'Open'. (You can also analyze the sample files in `cpsc410_project2_team18\src\main\resources`.)
3. Then the front-end (React) should automatically start after a while. Note that it might take a while. Usually it's going to automatically open a browser for localhost at Port 3000, but if it doesn't, you might have to manually open a browser then go to `http://localhost:3000/`.
