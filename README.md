# opttek-interview-project

### Summary
The big idea is that there is a single production line of some sort which needs to create some number of different products in some sequence and that the transition between each product to be produced on the line incurs some cost.  These costs are assymetrical -- so transitioning from product B to product C is *not* the same cost as transitioning from product C to product B.  

The program will have the matrix of transition costs between each product line, and an initial ordering of products to be manufactured, the exercise is to swap neighbors in the ordering until an optimal point is reached.

## Approach

