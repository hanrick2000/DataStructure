## [1306. Jump Game III](https://leetcode.com/problems/jump-game-iii/)

Given an array of non-negative integers `arr`, you are initially positioned at `start` index of the array. When you are at index `i`, you can jump to `i + arr[i]` or `i - arr[i]`, check if you can reach to **any** index with value 0.

Notice that you can not jump outside of the array at any time.

 

Example 1:

```
Input: arr = [4,2,3,0,3,1,2], start = 5
Output: true
Explanation: 
All possible ways to reach at index 3 with value 0 are: 
index 5 -> index 4 -> index 1 -> index 3 
index 5 -> index 6 -> index 4 -> index 1 -> index 3 
```

Example 2:

```
Input: arr = [4,2,3,0,3,1,2], start = 0
Output: true 
Explanation: 
One possible way to reach at index 3 with value 0 is: 
index 0 -> index 4 -> index 1 -> index 3
```

Example 3:

```
Input: arr = [3,0,2,1,2], start = 2
Output: false
Explanation: There is no way to reach at index 1 with value 0.
```

Constraints:

- `1 <= arr.length <= 5 * 10^4`
- `0 <= arr[i] < arr.length`
- `0 <= start < arr.length`

## Answer
### Method 2 - BFS - :rocket: 0ms

```java
class Solution {
    public boolean canReach(int[] arr, int start) {
        Queue<Integer> q = new LinkedList();
        q.offer(start);
        boolean[] seen = new boolean[arr.length];
        while (!q.isEmpty()) {
            int idx = q.poll();
            if (arr[idx] == 0) return true;
            seen[idx] = true;
            
            if (idx + arr[idx] < arr.length && !seen[idx + arr[idx]]) q.offer(idx + arr[idx]);
            if (idx - arr[idx] >= 0 && !seen[idx - arr[idx]]) q.offer(idx - arr[idx]);
        }
        
        return false;
    }

}
```

### Method 1 - DFS - :rocket: 0ms

```java
class Solution {
    //boolean[] seen;
    public boolean canReach(int[] arr, int start) {
        //seen = new boolean[arr.length];
        
        return dfs(arr, start);
    }
    
    private boolean dfs(int[] arr, int start) {
        if (start >= arr.length || start < 0) return false;
        
        if (arr[start] == 0) return true;
        
        //if (seen[start]) return false;
        
        if (arr[start] < 0) return false;
        
        //seen[start] = true;
        arr[start] = -arr[start];
        if (dfs(arr, start + arr[start])) return true;
        if (dfs(arr, start - arr[start])) return true;
        arr[start] = -arr[start];
        //seen[start] = false;
        
        return false;
    }
}
```