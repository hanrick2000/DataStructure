## [698. Partition to K Equal Sum Subsets](https://leetcode.com/problems/partition-to-k-equal-sum-subsets/)

Given an array of integers nums and a positive integer k, find whether it's possible to divide this array into k non-empty subsets whose sums are all equal.

Example 1:

```
Input: nums = [4, 3, 2, 3, 5, 2, 1], k = 4
Output: True
Explanation: It's possible to divide it into 4 subsets (5), (1, 4), (2,3), (2,3) with equal sums.
```

Note:

- `1 <= k <= len(nums) <= 16`.
- `0 < nums[i] < 10000`.

## Answer
### Method 1 - DFS - :rocket: 1ms (100%)

```java
class Solution {
    public boolean canPartitionKSubsets(int[] nums, int k) {
        int sum = 0;
        
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }
        
        if (sum % k != 0) return false;
        
        int each = sum / k;
        
        return dfs(nums, each, 0, k, each, new boolean[nums.length]);
        
    }
    
    private boolean dfs(int[] nums, int subSum, int start, int k, int sum, boolean[] used) {
        if (k == 0) return true;
        
        if (subSum == 0) {
            return dfs(nums, sum, 0, k - 1, sum, used);
        }
        
        
        for (int i = start; i < nums.length; i++) {
            if (used[i] || subSum < nums[i]) continue;
            used[i] = true;
            
            if (dfs(nums, subSum - nums[i], i + 1, k, sum, used)) {
                return true;
            }
            
            used[i] = false;
        }
        
        return false;
    }
}
```
