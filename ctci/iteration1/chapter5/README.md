# Chapter 5 - Bit Manipulation
## Description
This contains the questions that are reflected in Cracking the Coding Interview Fifth Edition specifically chapter 5.

## Problems
### Problem 1
#### Problem Statement
>You are given two 32-bit numbers, N and M and two bit positions, i and j.  Write a method to insert M into N such that M starts at bit j and ends at bit i.  You can assume that the bits j through i have enough sapce to fit all of M.  That is, if M = 10011, you can assume that there are at least 5 bits between j and i.  You would not, for example, have j = 3 and i = 2 becasue M could not fully fit between bit 3 and bit 2.
>
Input:
- N = 10000000000, M = 1011, i = 2, j = 6
>
Output:
- N = 10000101100

#### Problem Discussion
- The whole number of M does not necessarily get to be placed in N.
- The problem accounts for N having enough sapce to accomadate it.
- Assumming the content of N at the positions provided is over-written completely.

#### Problem Design
- M will always be left shifted by i
- Need a mask to accept only certain bits of M but also can clear N.
- nMask = (-1 >> (32 - j)) | ~(-1 >> (32 - i))

#### Problem Issues
- Need to remember that -1 is all 1s, and not just msb 1.

#### Problem Solution
- Book solution is similar, I however take into account that M could be trimmed down.

### Problem 2
#### Problem Statement
>
Given a real number between 0 and 1 that is passed in as a double, print the binary representation.  If the number cannot be represented accurately in binary with at most 32 characters print error.

#### Problem Discussion
- Is this just calling Double.toBinaryString() and stopping at 32 bits?
- or does this just want the mantissa part?
- double on most systems is 64 bits.

#### Problem Design
- Don't really know what the problem wants.
- maybe wants to convert to where the number is like an integer?

#### Problem Issues
- completely forgot about fractional representation that is not IEEE floating / double.
- Will need to revisit.

#### Problem Solution
- Fairly trival once you remember the fractional representation outside of IEEE standard for float / doubles.
- .101 (base 2) = 1 * (2^1/2) + 0 * (2^1/4) + 1 * (2^1/8)

### Problem 3
#### Problem Statement
>
Given a positive integer, print the next smallest and the next largest number that have the same number of 1 bits in their binary representation.

#### Problem Discussion
- if the number is all 1s then there will be no next smallest.
- otherwise the next smallest is an exchange of a one to the left of a zero with the zero, with the right most one-zero pair.
- if the number is all 1s then the next largest will be << 1, with all lower 1s moved to the right, but only if the result is greater than 0.
- otherwise the next largest is an exchange of a one to the right of a zero with the zero, with the right most zero-one pair.  On this point if the exchange happens with the most significant 1 and the zero afterwards then need to do the same thing as if all ones.
- larger values strategy
  - a temp exists for the worse case
  - if a one is encountered and it is not the most significant 1, then save the one to a temp such that temp = temp << 1 | 1;
  - if a zero is encountered and temp > 0 && !temp < 0 

#### Problem Design
- iterate through from the base with two masks `01` for the greater value and `10` for the lesser value.
- the greater case needs to account for possible full shift and saving all detected ones in a temporary value with the same number of ones starting from the least significant bit position.
- save the main number to a temporary.  use the temporary for manipulation i.e. shifting right by 1 for each time.
- if the value is negative you can return due to the constraint of the problem.
- in the case of lesser value if get to the end and no `10` pattern has been found then a lesser value is not possible (all ones up to the msb), print itself / error message.
- in case of the upper need to watch out for roll-over to the negative range.  Which a swap occurs need to test the swap value for being negative.  If so print itself / error message.