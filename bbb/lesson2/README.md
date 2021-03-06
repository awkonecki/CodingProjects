# Lesson 2 - Recursion with Iteration, SubProblems, Divide & Conquer Patterns
## Lesson Description
Implementation of actual recursion problems following one of the described recursion patterns listed below.
- iteration
- subproblems
- divide & conquer

## Problems
### Problem 1
#### Problem Statement
>
Print out the odd indicies of an integer array.
>
```
Input : [1,2,3,4,5,6,7,8,9,10]
Output : 
2 // occurs on index 1
4 // occurs on index 3
6 // occurs on index 5
8 // occurs on index 7
10 // occurs on index 9
```

#### Problem Discussion
- an iterative implementation would be quite trival
  - just iterate through the array two at a time starting at index 1
  - print at each index that is less than the overall array length
- expected operational time will be O(N) since will hit every other element within the array which is N/2 == N for complexity.
- expected memory impact will also be O(N) due to linear relationship to the number of elements within the array.

#### Problem Design
- take the serial approach and just convert into a recursive iteration problem.
- have each layer of the recursive function be responsible for the printing out of the current index and then call on the next index with the increment by two.
- if the passed in index is greater than that of the array total length, then just return (base case).

#### Problem Issues
- None

### Problem 2
#### Problem Statement
>
Find all substrings of a string.

#### Problem Discussion
- a substring of a string are consecutive combinations of characters within the string.
- `abcd` would result in the following substrings
  - `<empty>`
  - `a`
  - `b`
  - `c`
  - `d`
  - `ab`
  - `bc`
  - `cd`
  - `abc`
  - `bcd`
  - `abcd`
- this is an iterative pattern
- total number of strings being generated should be on the order of O(N^2) where N is the length of the original string.
- will have something along the lines of `N + (N - 1) + (N - 2) + ... 2 + 1 = N(N + 1) / 2 = O(N^2)`
- want only the unique set of substrings, thus if have repeating patterns will get repeating substrings.

#### Problem Design
- the original code provided multiple functions, 3 in total and wants to remove the unnecessary third.
- the original operates on at a specific index and then increments by one for the substring range resulting in the following substring
  - `a`
  - `ab`
  - `abc`
  - `abcd`
  - `b`
  - `bc`
  - `bcd`
  - `c`
  - `cd`
  - `d`
- the presentation of the data is position ordered
- can take the original code and move the iteration control to the first function with the substring functionality contained to the second function / the first recursive function.
- keep a `HashSet` of all substrings seen.  If the substring is a new string then add it to the set.

#### Problem Issues
- None

### Problem 3
#### Problem Statement
>
Implement the `Towers of Hanoi` to find the total number of moves it takes to move N disks.
>
Provide the following within the implementation.
- refer to the source destination and aux positions as indicies {0,1,2}
- create a `Move` class that represents a move of a disk.  Include the disk index, source and destination.  Set the return value of moving to be the list of `Move` objects.

#### Problem Discussion
- would have thought more of creating a `Disk` class over a `Move` class with the `Disk` having a move method.
- know that a disk can only be moved to another location iff
  - the location has no disks
  - the top disk at a specific location is larger than the disk being moved to a target location.

#### Problem Design
- have three stacks, contained within an array.  
- use the indicies provided above to index into for a from to destination
- will only need to perform push and pop operations to move disks around
- disks will be valued 1 through N

#### Problem Issues
- I can not see the solution programmatically.
- I have done this problem around 10 to 15 times in my career but always have to look at the solution.
- Don't know the correct mental reference for approaching this problem.
- Problems in this class where I can not see the sub-problem logic prevents me from making a solution.
- This problem is on my `memorize` set of problems prior to an interview.  Can you help me move it off this list?
- I don't see the aid of stair step problem usage for this, even though I can answer the stair step problem in an interview and have.

#### Problem Solution
- three main operations
  - move N - 1 elements that are above N from source to aux
  - move the Nth element from source to destination
  - move all the N-1 elements from aux to destination

### Problem 4
#### Problem Statement
>
Given a binary tree with distinct elements, print all possible arrays that could have led to this tree.

#### Problem Discussion
- depth of the node determines some order with respect to lower order nodes for a given tree branch.
- problem should be around a divide and conquer technique & permutation
- expect the time complexity to be greater than that of O(2^N) due to iterating over both left and right arraylists along with numerous copies.

#### Problem Design
- create supporting code to support the generation of a BST.
- nodes at the same level can occur in any order since they will end up in the same place
- due to the level being a controlling factor will likely need to support a depth first search approach to the problem to create the permutation of the insertion arrays. 

#### Problem Issues
- just a lot of code.
- spent a lot of time trying to address the array permutation.  Was trying to approach it differently but ended up going with what I knew how to do.
- once figured out how I wanted to do the permutations of each list, was not difficult.

### Problem 5
#### Problem Statement
>
- 1. What did you like about this submission?
  - I liked that it was actual coding.
- 2. What didn't you like about this assignment?
  - Would have liked some more on how to approach the specific patterns besides just answer the questions.
  - From the lecture on the review of lesson 1, if I was asked to say implement some of the pieces I would not know how.  
- 3. Would like the problems to build off of each other, so that way I can see a bit more of how to approach the patterns.  At the moment it is very much of if I see it I can answer it.  If I can not then I have issues.  This is quite true in problem 3 even though it is a problem I have answered numerous times throughout the years.