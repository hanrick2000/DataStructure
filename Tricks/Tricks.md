1. 想得到BFS的路径，可以使用Map来保存每个结点generate出来的节点。最后再通过DFS来遍历。
2. 单向BFS耗费内存，可以使用双向BFS。
3. 剪枝(pruning)的时候，有时为了防止重复，如下，在backtracking的过程中可能还会产生`[5,3]`等。为了避免重复，可以使每一层的遍历仅从`2`,`3`,`5`开始。
在代码中我们往下层传递的不是`level + 1`而是`i`。详情请见[Combination Sum](https://github.com/weltond/DataStructure/blob/master/LeetCode/backtracking/Lc39CombinationSum.java)
```
Input: candidates = [2,3,5], target = 8,
A solution set is:
[
  [2,2,2,2],
  [2,3,3],
  [3,5]
]
```
4. 多画recursion tree来理解题，必要的时候考虑清楚memoize什么东西以及为何要memoize。

- 对于[Word Break II](https://github.com/weltond/DataStructure/blob/master/LeetCode/backtracking/Lc140WordBreakII.java)来说，给的例子可能看不出为何要memo，但是考虑到这个例子`input [aaaa], dict [a,aa,aaa]`就会发现有很多重复计算的地方。画出recursion tree.发现`lvl2`中第一个的subtree与`lvl1`中第二个的subtree的结果完全一样。那么memo的东西可以以`map`来存储当前节点为key, 其子树的各个枝为Value。
```
                                                 root                                         <---- lvl 0
                a(aaa)                    |     aa(aa)         |       aaa(a)                 <---- lvl 1
    a(aa)     |   aa(a)    |   aaa()      | a(a)  |  aa()      |       a()                    <---- lvl 2
 a(a) |  aa() |    a()     |              | a()   |            |                              <---- lvl 3
a()   |       |                                                                               <---- lvl 4
```
5. 对于2sum，3sum，4sum基本就两种做法：
  - Two Pointers：固定前1个或2个，然后对剩下的两个做双指针。注意必须**排序**。
  - Hash Table： 存储之前的和，再在后面查找map中的值。无需排序。

6. Combination Sum相关问题。可以参考[这里](https://leetcode.com/problems/permutations/discuss/18239/A-general-approach-to-backtracking-questions-in-Java-(Subsets-Permutations-Combination-Sum-Palindrome-Partioning))

想清楚recursion时候从哪里作为起始点。注意必须先**排序**。
  - 6.1 Combination Sum: Given Non-Duplicate array and target, return solution set. **Each number can be used unlimited**.
```java
void bt(int[] arr, int level, int rem){
    for (int i = level) {
        list.add();
        bt(arr, i, rem - arr[i]); // next level start from i, NOT level + 1, to 1) reuse number; 2) avoid duplicate result
        list.remove();
    }
}
```
  - 6.2 Combination Sum II: Given Duplicate array and target, return solution set. **Each number can ONLY be used once**.
```java
void bt(int[] arr, int level, int rem) {
    Set<Integer> set = new HashSet();
    for (int i = level) {
        if (!set.add(arr[i])) continue; // skip same value on the same level
        if (rem - arr[i] < 0) break;  // pruning
        
        list.add();
        bt(arr, i + 1, rem - arr[i]); // next level start from i + 1 becuase each number can only be used once.
        list.remove();
    }
}
```
  - 6.3 Combintaion Sum III: Find all set combinations of `k` numbers that add up to a number `n`.
```java
void bt(int k, int lvl, int cnt, int n） {
    if (cnt > k) return;  // pruning
    if (rem == 0 && cnt == k) {} // base case
    
    for (int i = lvl) {
        if (n < i) break; // pruning
        
        list.add();
        bt(k, i + 1, cnt + 1);  // next level obviously starts from i + 1 because each number is unique
        list.remove();
    }
}
```
  - 6.4 Combination: Return all possible combinations of `k` numbers out of 1...n. 
```java
dfs(int start, int end, int depth) {
    if (depth == 0) {}  // base case
    for (int i = start; i <= end - depth + 1) {
        list.add();
        dfs(i + 1, end, depth - 1);
        list.remove();
    }
}
```
  - 6.5 Combination Sum IV: Given array Non-duplicate. Find number of combinations that add up to target.
    - **Compare with Coin change 2**
    - DFS + Memoization
    - DP. dp[i] represents how many combinations to get to sum i.
```java
// ============== DFS + Memoization ===============
int dfs(int[] nums, int rem, int[] memo) {
    // SHOULDN'T use 0 as default
    // Otherwise we won't know whether is no result or not visited yet.
    if (memo[rem] != -1) return memo[rem];  
    if (rem == 0) return 1; // base case
    for (int i = 0) {
        if (rem < nums[i]) break; // pruning
        sum += dfs(nums, rem- nums[i], memo);
    }
    memo[rem] = sum;
    return sum;
}
``` 
```java

// ============== DP ===============
int[] dp = int[target + 1];
dp[0] = 1;
for (int i = 0; i <= target) {
    for (int j = 0; j < nums.length) {
        if (i < nums[j]) continue;
        dp[i] += dp[i - nums[j]];
    }
}
return dp[target];
```
7. 降维。
  - 7.1 一维dp
  - 7.2 二维dp。
    - 如[Unique Path](https://github.com/weltond/DataStructure/blob/master/LeetCode/dp/Lc62UniquePaths.java). 因为每个格子只等于其上面和左面的数值之和，我们就可以已第一列为起始，对剩余的每列进行处理。
    ```
    dp[i][j] = dp[i-1][j] + dp[i][j-1]
    ===>
    for (int i = 1 ~ cols)
      for (int j = 1 ~ rows)
        dp[j] = dp[j] + dp[j-1]
    ```

8. Coin Change 
- I: 求最少数量。
  - 用一维dp： `dp[i] = Math.min(dp[i], dp[i - coins[j]] + 1);`
  - Recursion: `count[rem] = (min == Integer.MAX_VALUE) ? -1 : min;`
- II: 求unique ways。用二维dp然后降维。
  - 注意此题与Combination Sum IV的区别。此题需要unique而CS IV不需要unique
9. Palindrom
- [Longest Substring](https://github.com/weltond/DataStructure/blob/master/LeetCode/dp/Lc5LongestPalindromicSubstring.java)
  - boolean dp[i][j]: `dp[i][j] = s.charAt(i) == s.charAt(j) && ( j <= i + 2 || dp[i + 1][j - 1]);`
- [Longest Subsequence](https://github.com/weltond/DataStructure/blob/master/LeetCode/dp/Lc516LongestPalindromicSubsequence.java)
  - int dp[i][j]: `dp[i][j] = s.charAt(i) == s.charAt(j) ? (2 + dp[i + 1][j - 1]) : (Math.max(dp[i + 1][j], dp[i][j - 1]));`
- [Substrings](https://github.com/weltond/DataStructure/blob/master/LeetCode/dp/Lc647PalindromicSubstrings.java)
  - Same as **Longest Substring**
- [Longest Palindrome - Math problem](https://github.com/weltond/DataStructure/blob/master/LeetCode/hashmap/Lc409LongestPalindrome.java)
10. [K sum 通用代码](https://github.com/weltond/DataStructure/blob/master/LeetCode/array/18-4-Sum.md)
11. **DP is a programming method**.
- Optimal Substructure
  - Can be solved optimally by breaking it into sub-problems and then recursively finding the optimal solutions to the sub-problems.
- Overlapping sub-problems
  - Sub-problems are overlapped such that we can compute only once and store the solution for future use
  - Reduce time complexity (Exponential to polynomial)
  - If sub-problem do not overlap ----> Divide and conquer
- No-after effect
  - The optimal solution of a subproblem will not change when it was used to solve a bigger problem optimally
  
12. [DP vs Search vs Greedy](https://www.zhihu.com/question/23995189/answer/35429905)

**一个问题是该用递推、贪心、搜索还是动态规划，完全是由这个问题本身阶段间状态的转移方式决定的！**

- 每个阶段只有一个状态->**递推**；
- 每个阶段的最优状态都是由上一个阶段的最优状态得到的->**贪心**；
- 每个阶段的最优状态是由之前所有阶段的状态的组合得到的->**搜索**；
- 每个阶段的最优状态可以从之前某个阶段的某个或某些状态直接得到而不管之前这个状态是如何得到的->**动态规划**。

`每个阶段的最优状态可以从之前某个阶段的某个或某些状态直接得到, 这个性质叫做最优子结构`；

`而不管之前这个状态是如何得到的这个性质叫做无后效性。`

另：其实动态规划中的最优状态的说法容易产生误导，以为只需要计算最优状态就好，LIS问题确实如此，转移时只用到了每个阶段“选”的状态。但实际上有的问题往往需要对每个阶段的所有状态都算出一个最优值，然后根据这些最优值再来找最优状态。比如背包问题就需要对前i个包（阶段）容量为j时（状态）计算出最大价值。然后在最后一个阶段中的所有状态种找到最优值。

13. **DP Galleries**
- 1D, 2 Sets of sub-problems
  - [926. Flip String to Monotone Increasing]()
  - [845. Longest Mountain in Array]()
- 1D w/ multiple states/ `dp[i][0], dp[i][1], dp[i][2]...` (i is the problem size, usually 3 at most)
  - [801. Minimum Swaps To Make Sequence Increasing]()
  - [790. Domino and Tromino Tiling]()
  - [926. Flip String to Monotone Increasing]()
  - [818. Race Car]()

14. **DP Template** (From Hua Hua)
![DP](https://github.com/weltond/DataStructure/blob/master/Learn%20Git/dp.PNG)

14.1 1-D DP
- Input O(n)
  - dp[i] = (opt-) sol of a subproblem (`A[0->i]`). **Only depends on const smaller problems.**
  - Time: O(n), Space: O(n)
  - Examples: [70. Climbing Stairs](), [198. House Robber](), [801. Minimum Swaps To Make Sequences Increasing](), [790. Domino and Tromino Tiling](), [746. Min Cost Climbing Stairs]()
  - **Template**
  ```python
  dp = [0] * n    # init DP array
  for i = 1 to n:
    dp[i] = f(dp[i - 1], dp[i - 2]...)
  
  return dp[n]
  ```
- Input O(n)
  - dp[i] = (-opt) sol of a subproblem (`A[0->i]`). ** Depends on all smaller problems.** (`dp[0],...dp[i-1]`)
  - Time: O(n^2), Space: O(n)
  - Examples: [139. Word Break](), [818. Race Cars]()
  - **Template**
  ```python
  dp = new int[n]                         # init DP array
  for i = 1 to n:                         # problem size
    for j = 1 to l - 1:                   # sub-problem size
      dp[i] = max/min(dp[i], f(dp[j])     # find the opt sol from all opt sols of smaller problems
      
  return dp[n]
  ```
- Input O(m) + O(n), two arrays / Strings
  - dp[i][j] = (-opt) sol of a subproblem (`A[0->i], B[0->j]`). **Depends on const smaller problems.** (`dp[i-1][j], dp[i][j-1], dp[i-1][j-1]`)
  - Time: O(mn), Space: O(mn)
  - Examples: [72. Edit Distance](), [712. Minimum ASCII Delete Sum for Two Strings]()
  - **Template**
  ```python
  dp = new int[n][m]                        # init DP array
  for i = 1 to n:                           # problem size of input 1
    for j = 1 to m:                         # problem size of input 2
      dp[i][j] = f(dp[i-1][j-1], dp[i-1][j], dp[i][j-1])
      
  return dp[n][m]
  ```
- Input O(n)
  - dp[i][j] = (opt-) sol of a subproblem (`A[i->j]`), a sub-array of the input. **Each subproblem depends on O(n) smaller problems.**
  - Time: O(n^3), Space: O(n)
  - Exapmles: [312. Burst Ballons](), [664. Strange Pointer]()
  - Template:
  ```python
  dp = new int[m][n]                        # init DP array
  for l = 1 to n:                           # problem size
    for i = 1 to n - l:                     # Sub-problem start
      j = i + l - 1                         # Sub-problem end
      for k = i to j:                       # try all possible break points
        dp[i][j] = max(dp[i][j], f(dp[i][k], dp[k][j]))  # merge two subproblems
      
  return dp[1][n]
  ```
  
14.2 2-D DP
- Input O(mn)
  - dp[i][j] = sol of (`A[0->i][0->j]`). **Each subproblem depends on O(1) subproblem**.
  - Time: O(mn), Space: O(mn)
  - Examples: [62. Unique Paths](), [64. Minimum Path Sum]()
  - **Template**
  ```python
  dp = new int[n][m]                        # init 2D array
  for i = 1 to n:                           # row top->down
    for j = 1 to m:                         # col left->right
      dp[i][j] = f(dp[i-1][j], dp[i][j-1])
      
  return dp[n][m] / max(dp[n])
  ```

- Input O(mn)
  - dp[k][i][j] = sol of (`A[0->i][0->j]`) after k steps. **Each subproblem depends on O(1) subproblems**.
  - Time: O(kmn), Space: O(kmn) -> O(mn)
  - Examples: [576. Out of Boundary Paths](), [688. Knight Probability in Chessboard]()
  - **Template**
  ```python 
  dp = new int[K][n][m]                         # init 3D array
  for k = 1 to K:                               # steps / problem size
    for i = 1 to n:
      for j = 1 to m:                         
       dp[k][i][j] = f(dp[k-1][i+di][j+dj])
      
  return dp[K][n][m] / g(dp[K])
  ```

15. DP Subsequence V.S. Substring
- for substring problem, we usually use only one for loop because for each index, we only care about the relationship between its two neighbors 
- for subsequence problem, we use two for loops , because for each index, any other indexes can do something. [300. LIS](https://github.com/weltond/DataStructure/blob/master/LeetCode/dp/300-Longest-Increasing-Subsequence.md) && [673. Number of LIS](https://github.com/weltond/DataStructure/blob/master/LeetCode/dp/673-Number-of-Longest-Increasing-Subsequence.md)  

16. PQ的Comparator
- 在pq的源码中，如果没有实现自己的comparator，则会在`shiftUp(int k, E x)`中调用自己的Comparable. 其中`k`是你插入当前对象`x`进入PQ的`queue`的索引
- 每次`offer()`的时候，都会执行`shiftUp(k,x)`以保证quque中第0个元素是我们要`poll()`(**永远返回queue的第0个元素**)出来的。
- 在`shiftUp(k,x)`中，会`while(k>0)`，在循环中拿对象`x`与其`parent`(`parent = (k-1)>>>1`)的对象`e`比较: `comparator.compare(x, e) >= 0`.如果满足则退出循环，代表整个PQ满足"最小堆"(上面的元素都**逻辑小于**下面的元素).
- trick就在这里的**逻辑小于**。默认情况下，`x.compareTo(e)`的意思是如果`x < e` 则返回负数。那么为了让`comparator.compare(x,e)`一直**小于0**的话，就可以让对象`x`一直shiftUp到堆顶。因此，为了实现最大堆，意即让最大的对象`x`放到堆顶，我们需要使`comparator.compare(x, e)`一直**小于0**. 已知`x`是最大的，要让其一直小于0（返回负数）,则可以让其`return e - x;`，这样可以保证`x`出现在堆顶。
- 根据以上，可以用**lambda**来实现最大堆：`(o1, o2) -> o2 - o1`. 这里，在前面的元素代表我们上面提到的`x`。

17. 中序遍历树的时候，用BFS或者DFS都会有`O(n)`的extra space，用**Morris Traversal**可以只用`O(1)`的空间。
18. Use **PQ** to deal with [621. Task Schedule](https://github.com/weltond/DataStructure/blob/master/LeetCode/greedy/621-Task-Scheduler.md), [767. Reorganize String](https://github.com/weltond/DataStructure/blob/master/LeetCode/heap/767-Reorganize-String.md), [358. Rearrange String K Distance Apart](https://github.com/weltond/DataStructure/blob/master/LeetCode/heap/358-Rearrage-String-K-Distance-Apart.md) and etc.
19. 写最大堆得时候，尽量用`Collections.reverseOrder`而不是`(o1,o2)->o2-o1`，因为输入的Integer有可能是`Integer.MIN_VALUE`or`Integer.MAX_VALUE`.
20. Multiple of K: [523.Continuous Subarray Sum](https://github.com/weltond/DataStructure/blob/master/LeetCode/dp/523-Continuous-Subarray-Sum.md)
