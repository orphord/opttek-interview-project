# opttek-interview-project

### Summary
The big idea is that there is a single production line of some sort which needs to create some number of different products in some sequence and that the transition between each product to be produced on the line incurs some cost.  These costs are assymetrical -- so transitioning from product B to product C is *not* the same cost as transitioning from product C to product B.  

The program will have the matrix of transition costs between each product line, and an initial ordering of products to be manufactured, the exercise is to swap neighbors in the ordering until an optimal point is reached.  So, if the initial sequence of products to be created was A,E,I,O,U then we could swap A and E or I and O, but **not** E and U.

### Instructions
1.

## Approach
I decided to create a good old Java application to do this work.  I considered using Spring-MVC and there were some advantages to that, but I thought that the approach that I did end up taking was simple enough to not require the extra weight of Spring.

My first observation for this problem was that since the production times of each product doesn't change as the sequence order changes, optimizing the total production time for the set of products was to find the sequence which would minimize transition costs.  The process is as follows:
* Beginning with the initial sequence (I call this the "baseline" sequence), invoke a thread for each potential transition swap and calculate the total transition time of the new sequence.
* Sort the resulting sequences based on how big an improvement over the baseline sequence and make the sequence which had the greatest effect on total transition time the new baseline.
* Repeat this process until the sorted sequences has a zero (or worse) effect on the overall transition time relative to the baseline.
* At this point the baseline sequence is the optimized sequence and will be returned to the user and its overall production time (including both production and transitions).

