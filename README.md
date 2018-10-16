# opttek-interview-project

### Summary
The big idea is that there is a single production line of some sort which needs to create some number of different products in some sequence and that the transition between each product to be produced on the line incurs some cost.  These costs are assymetrical -- so transitioning from product B to product C is *not* the same cost as transitioning from product C to product B.  

The program will have the matrix of transition costs between each product line, and an initial ordering of products to be manufactured, the exercise is to swap neighbors in the ordering until an optimal point is reached.  So, if the initial sequence of products to be created was A,E,I,O,U then we could swap A and E or I and O, but **not** E and U.

### Install/Build Instructions
#### Required software
* Java 8 -- on my system I have Java 10 installed, but bytecode is compiled and tested to Java 8.
* Apache Maven v.3.5.2
* git -- on my system I have version 2.17.1, I would *guess* it works with others, but don't know that as a fact

### Instructions to install and run
1. In a terminal window navigate to the directory to which you would like to clone the git repository type `git clone https://github.com/orphord/opttek-interview-project.git` (<--or copy-paste this text).
  * This will have created a directory called gap-rule-test/, navigate there (ie. cd gap-rule-test/) (please forgive that I added "test" at the end of the name, I'm thinking of this as a test, so I named the repo that.
2. Type `mvn clean install`, the build will start and the set of tests will run, I would expect no failures, **If there is a failure, please call or get in touch, that would be surprising and I may need to check out what's up**.
3. Type `java -jar target/gap-rule-test-0.0.1-SNAPSHOT.jar`.  This will run the process with the `test-case.json` provided with the instructions.
  * Note: I did add the feature to allow a user to specify a json file with the command line parameter --file.loc, so on my system for example I would enter `java -jar target/gap-rule-test-0.0.1-SNAPSHOT.jar --file.loc=/home/orphord/dir/to/another-file.json` and that file would get picked up and processed.
4. The essential output is to the console as log4j messages.  The important information is between "======================..." and "=================..."


## Approach
I decided to create a good old Java application to do this work.  I considered using Spring-MVC and there were some advantages to that, but I thought that the approach that I did end up taking was simple enough to not require the extra weight of Spring.

My first observation for this problem was that since the production times of each product doesn't change as the sequence order changes, optimizing the total production time for the set of products was to find the sequence which would minimize transition costs.  The process is as follows:
* Beginning with the initial sequence (I call this the "baseline" sequence), invoke a thread for each potential transition swap and calculate the total transition time of the new sequence.
* Sort the resulting sequences based on how big an improvement over the baseline sequence and make the sequence which had the greatest effect on total transition time the new baseline.
* Repeat this process until the sorted sequences has a zero (or worse) effect on the overall transition time relative to the baseline.
* At this point the baseline sequence is the optimized sequence and will be returned to the user and its overall production time (including both production and transitions).

