// https://leetcode.com/problems/evaluate-division/
class Solution {
    // ============ Sol 2: UNION FIND ============
    // 1ms, beat 100%
  class Node {
    public String parent;
    public double ratio;
    public Node(String parent, double ratio) {
      this.parent = parent;
      this.ratio = ratio;
    }
  }
  
  class UnionFindSet {
    private Map<String, Node> parents = new HashMap<>();
    
    public Node find(String s) {
      if (!parents.containsKey(s)) return null;
      Node n = parents.get(s);
      if (!n.parent.equals(s)) {
        Node p = find(n.parent);
        n.parent = p.parent;
        n.ratio *= p.ratio;
      }
      return n;
    }
    
    public void union(String s, String p, double ratio) {
      boolean hasS = parents.containsKey(s);
      boolean hasP = parents.containsKey(p);
      if (!hasS && !hasP) {
        parents.put(s, new Node(p, ratio));
        parents.put(p, new Node(p, 1.0));
      } else if (!hasP) {
        parents.put(p, new Node(s, 1.0 / ratio));
      } else if (!hasS) {
        parents.put(s, new Node(p, ratio));
      } else {
        Node rS = find(s);
        Node rP = find(p);
        rS.parent = rP.parent;
        rS.ratio = ratio / rS.ratio * rP.ratio;
      }
    }
  }
  
  public double[] calcEquation(String[][] equations, double[] values, String[][] queries) {
    UnionFindSet u = new UnionFindSet();
    
    for (int i = 0; i < equations.length; ++i)
      u.union(equations[i][0], equations[i][1], values[i]);
    
    double[] ans = new double[queries.length];
    
    for (int i = 0; i < queries.length; ++i) {      
      Node rx = u.find(queries[i][0]);
      Node ry = u.find(queries[i][1]);
      if (rx == null || ry == null || !rx.parent.equals(ry.parent))
        ans[i] = -1.0;        
      else
        ans[i] = rx.ratio / ry.ratio;
    }
    
    return ans;
  }
    
    // ============ Sol 1: GRAPH ==============
    // 35ms
    Map<String, HashMap<String, Double>> map = new HashMap();
    
    public double[] calcEquation(String[][] equations, double[] values, String[][] queries) {
        double[] res = new double[queries.length];
        
        // build graph
        for (int i = 0; i < equations.length; i++) {
            String x = equations[i][0];
            String y = equations[i][1];
            double k = values[i];
            // x / y = k : x -> <y, k>
            map.computeIfAbsent(x, n -> new HashMap<String, Double>()).put(y, k);
            // y / x = 1/k : y -> <x, 1/k>
            map.computeIfAbsent(y, n -> new HashMap<String, Double>()).put(x, 1 / k);
        }

        // query
        for (int i = 0; i < queries.length; i++) {
            String x = queries[i][0];
            String y = queries[i][1];
            if (!map.containsKey(x) || !map.containsKey(y)) {
                res[i] = -1;
                continue;
            }
            res[i] = dfs(x, y, new HashSet<String>());
        }
        
        return res;
    }
    
    public double dfs(String x, String y, HashSet<String> visited) {
        // base case
        if (x.equals(y)) return 1.0;
        
        if (!map.containsKey(x)) return -1.0;
        
        visited.add(x);
        
        for (String s : map.get(x).keySet()) {
            if (visited.contains(s)) continue;
            visited.add(s);
            
            double d = dfs(s, y, visited);
            
            // we don't have negative number in the problem.
            if (d != -1.0) {
                // a / c = (a / b) * (b / c) 
                return d * map.get(x).get(s);
            }
        }
        
        return -1.0;
    }
}
