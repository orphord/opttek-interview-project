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
  * This will have created a directory called opttek-interview-project/, navigate there (ie. cd opttek-interview-project/)
2. Type `mvn clean install`, the build will start and the set of tests will run, I would expect no failures, **If there is a failure, please call or get in touch, that would be surprising and I may need to check out what's up**.
3. Type `java -jar target/opttek-logistics-project.jar`.  This will run the process with the sequence (B, G, E, A, C, I, J, D, H, F) provided with the instructions.
  * Note: I did add the feature to allow a user to specify a sequence from the command line.  If you have a sequence you'd like to try type `java -jar target/opttek-logistics-project.jar A,B,C` (Note: no spaces) and the sequence "A,B,C" will be optimized.
4. The essential output is to the console as log4j messages.  The important information is between "********************" and "********************"


## Approach
I decided to create a good old Java application to do this work.  I considered using Spring-MVC and there were some advantages to that, but I thought that the approach that I did end up taking was simple enough to not require the extra weight of Spring.

My first observation for this problem was that since the production times of each product doesn't change as the sequence order changes, so optimizing the total production time for the set of products was to find the sequence which would minimize transition costs.  The process is as follows:
* Beginning with the initial sequence (I call this the "baseline" sequence), the `OptimizerService.optimize()` method loops through the sequence n-1 times where n is the number of Node objects in the sequence, this is equivalent to the number of transitions between Nodes in the exercise.
* The sequence -- modeled as a `NodeSequence` object -- is passed as a parameter to the `SwapAndCompare.checkOptimal()` method.
* The `checkOptimal()` method swaps the relevant `Node` objects and calculates the total transition time
  * If the total transition time is less than (i.e. an improvement on) the baseline sequence's total transition time **then** recursively call `OptimizerService.optimize()` on the swapped sequence.
  * If the total transition time is greater or equal to the baseline sequence's total transition time return the baseline sequence as the optimal sequence.  In this way the optimal sequence will be reached.
* Sort the resulting sequences based on how big an improvement over the baseline sequence and make the sequence which had the greatest effect on total transition time the new baseline.
* At the top `OptimizerService` a `Set` of sequences -- contained within a `SwapResponse` object -- will be returned and sorted by total transition time, the one at the top of the list is the optimal sequence.

## Additional Notes
The original implementation I created for this had two issues:
1. It found only a local minimum as it was only recursing down the path with the biggest net savings in total transfer time.  I fixed this issue by going down all paths that had a net gain (as opposed to no change or net loss) recursively.
2. I attempted to do a multi-threaded solution where each transition was tested in parallel and this worked well (and was more performant), but for certain cases my system (and presumably many systems) ran out of threads and I felt this was a bad look for an interview :-).  I took this performance feature out, but I do know how to do that for future reference.

Thanks for this exercise, it was fun and challenging.

