# Lesson 4 - Recursion with DFS Traversal Techniques
## Lesson Description
Implementation of actual recursion problems following one of the described recursion patterns listed below.
- DFS

## Problems
### Problem 1 - Greatest Product Path
#### Problem Statement
>
Given a n-by-n matrix of numbers, find the path from the upper left to the lower right that has the greatest product.
>
The only supported directions are to the right and down for traversing the matrix.
>
Provide a function to find the max product and a function to provide the path(s) of the maximum product.

#### Problem Discussion
- expecting that the max height of a recursion stack would be N + M + 1 where N is number of rows and M is the number of columns.
- two options to take per location right and down, expected branching factor expected is (2).
- the finding of the max product follows that of a dynamic problem as well in terms of design.
- will assume that max product will fit within a 32-bit integer.

#### Problem Design
##### Design Consideration #1
- will go ahead and do just the recursion implementatino first
- there will be two pieces that will maintain
  - the product
  - the current list of paths.
- this is a DFS problem since will effectively be going down an entire path to the end then will slowly back up the call stack handling the other possible traversals.

##### Design Consideration #2
- will implement the pieces to support dynamic programming technique for practice.

#### Problem Analysis
- Depending on implementation pieces there is an array of different performances.
  - Worse case for generic recursion for just max product 
    - Time - O(2^(M+N))
    - Memory - O(M+N)
  - Worse case for paths with max product
    - Time - O(2^(M+N) + ((M+N-2)!/(M-1)!*(N-1)!*(M+N)))
    - Memory - O(((M+N-2)!/(M-1)!*(N-1)!*(M+N)))
- DP
  - Time O(M*N)
  - Memory O(M*N)
- Serial
  - Time O(M*N)
  - Memory (M*N)

#### Problem Issues
- Nothing with respect to the recursion other than the base case had a slight bug in it.

### Problem 2 - Lowest Common Ancestor
#### Problem Statement
>
Given a tree, and two nodes, write a function to find the lowest common ancestor between the two nodes.

#### Problem Discussion
- Guess is that will provide the actual nodes not the value of the nodes.
- the tree is not guaranteed to have any orientation w.r.t the data contained within the nodes.
- tree will also be given to the function
- will need to add supporting code to actually develop the tree.
- can search the two nodes first in a classic DFS / BFS fashion if need be.
  - if the other node is found then the starting node is the least common ancestor of the two
  - if neither are found then will need to search from the root, but if the nodes are found, wont need to go down
- might want to just start from the root and traverse.

#### Problem Design
##### Design Consideration #1
- four possible states when searching
  - 1. Neither node found
  - 2. Node A found
  - 3. Node B found
  - 4. Node A & B found
- the first occurrence in which case (4) occurs is the least common ancestor node within the graph.
- to get around the issue of return two types can take advantage of an array of node of size (1) which will contain the LCA of the two nodes within the tree.
- if the array is emtpy then no LCA was found.
- return enumeration value of the four cases above.

#### Problem Analysis
- 

#### Problem Issues
- 