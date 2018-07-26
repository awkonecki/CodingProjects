import java.util.HashMap;
import java.util.ArrayList;

public class Challenge14 {

    // [F]unctional Implementation
    // Normal recursion, cant do greedy just due to wording of the problem 
    // makes it quite obvious (two possible operations).  Also want a count of
    // all combinations.
    public static int targetSum(int [] nums, int target) {
        return targetSum(nums, target, 0);
    }

    private static int targetSum(int [] nums, int target, int index) {
        if (index >= nums.length) {
            if (target == 0) {
                return 1;
            }
            else {
                return 0;
            }
        }

        return targetSum(nums, target + nums[index], index + 1) +
            targetSum(nums, target - nums[index], index + 1);
    }

    // [A]nalysis
    // [1] Is the implementation optimal substructure?
    // Yes - the execution for each step of the recursion is self contained.
    // [2] Does the problem have repeating subproblems?
    // Yes - there are multiple calls onto the same index position with the
    // same value is possible.

    // Run Time Performance
    // [1] The maximum height of the tree is equal to the total number of nums
    // that are being investigated.
    // [2] the Branching factor is (2) due to the fact that there are two possible
    // operations per node at a given level.
    // Run time performance is therefore 2^(N)

    // Memory Performance
    // Is equal to the height of the recursion tree due to manging the memory 
    // associated with the call stack.  So O(N)

    // [S]ubproblem Identification & Memoization
    public static int targetSumDP(int [] nums, int target) {
        ArrayList<HashMap<Integer, Integer>> dp = new ArrayList<HashMap<Integer, Integer>>(nums.length);

        for (int index = 0; index < nums.length; index++) {
            dp.add(null);
        }

        return targetSum(nums, target, 0, dp);
    }

    private static int targetSum(int [] nums, int target, int index, ArrayList<HashMap<Integer, Integer>> dp) {
        if (index >= nums.length) {
            if (target == 0) {
                return 1;
            }
            else {
                return 0;
            }
        }

        assert (dp.size() == nums.length);
        int sum = 0;

        if (dp.get(index) == null || !dp.get(index).containsKey(target)) {
            HashMap<Integer, Integer> map = dp.get(index);

            if (map == null) {
                map = new HashMap<Integer, Integer>();
            }

            sum = targetSum(nums, target + nums[index], index + 1, dp) 
                + targetSum(nums, target - nums[index], index + 1, dp);

            map.put(target, sum);

            dp.set(index, map);
        }
        else {
            sum = dp.get(index).get(target).intValue();
        }

        return  sum;  
    }

    // [T]urn the Problem Around
    public static int targetSumSerialDP(int [] nums, int target) {
        ArrayList<HashMap<Integer, Integer>> dp = new ArrayList<HashMap<Integer, Integer>>(nums.length);

        // for (int index = 0; index < nums.length; index++) {
        //    dp.add(null);
        //}

        // Now the hashmap maintains the following mantra
        // key - current value with operation from previous
        // value - number of numbers of variations for the specific value
        HashMap<Integer, Integer> prevNegMap = new HashMap<Integer, Integer>();
        HashMap<Integer, Integer> prevPosMap = new HashMap<Integer, Integer>();

        for (int index = 0; index < nums.length; index++) {    
            if (index == 0) {
                prevPosMap.put(0 + nums[index], 1);
                prevNegMap.put(0 - nums[index], 1);
            }
            else {
                HashMap<Integer, Integer> currPosMap = new HashMap<Integer, Integer>();
                HashMap<Integer, Integer> currNegMap = new HashMap<Integer, Integer>();

                for (int key : prevNegMap.keySet()) {
                    if (currPosMap.containsKey(key + nums[index])) {
                        currPosMap.put(key + nums[index], currPosMap.get(key + nums[index]).intValue() + prevNegMap.get(key).intValue() + 1);    
                    }
                    else {
                        currPosMap.put(key + nums[index], prevNegMap.get(key).intValue() + 1);
                    }

                    if (currNegMap.containsKey(key - nums[index])) {
                        currNegMap.put(key - nums[index], currNegMap.get(key - nums[index]).intValue() + prevNegMap.get(key).intValue() + 1);    
                    }
                    else {
                        currNegMap.put(key - nums[index], prevNegMap.get(key).intValue() + 1);
                    }
                }

                for (int key : prevPosMap.keySet()) {
                    if (currPosMap.containsKey(key + nums[index])) {
                        currPosMap.put(key + nums[index], currPosMap.get(key + nums[index]).intValue() + prevPosMap.get(key).intValue() + 1);    
                    }
                    else {
                        currPosMap.put(key + nums[index], prevPosMap.get(key).intValue() + 1);
                    }

                    if (currNegMap.containsKey(key - nums[index])) {
                        currNegMap.put(key - nums[index], currNegMap.get(key - nums[index]).intValue() + prevPosMap.get(key).intValue() + 1);    
                    }
                    else {
                        currNegMap.put(key - nums[index], prevPosMap.get(key).intValue() + 1);
                    }
                }

                prevNegMap = currNegMap;
                prevPosMap = currPosMap;
            }
        }

        // System.out.println(prevNegMap.getOrDefault(target, 0).intValue() + " " + prevPosMap.getOrDefault(target, 0).intValue());

        int sum = 0;

        if (prevNegMap.getOrDefault(target, 0).intValue() != 0) {
            sum += prevNegMap.getOrDefault(target, 0).intValue() / nums.length;
        }

        if (prevPosMap.getOrDefault(target, 0).intValue() != 0) {
            sum += prevPosMap.getOrDefault(target, 0).intValue() / nums.length;
        }

        return sum;
    }

    public static void main (String [] args) {
        assert (targetSum(new int [] {1,2,3,4}, 10) == 1);
        assert (targetSumDP(new int [] {1,2,3,4}, 10) == 1);
        assert (targetSumSerialDP(new int [] {1,2,3,4}, 0) == 2);
        assert (targetSumSerialDP(new int [] {0,1,2,3,4,5,6,7,8,9,10}, 0) == targetSumDP(new int [] {0,1,2,3,4,5,6,7,8,9,10}, 0));
    }
}