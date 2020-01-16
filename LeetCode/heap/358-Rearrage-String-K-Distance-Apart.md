## [358. Rearrange String k Distance Apart](https://www.cnblogs.com/grandyang/p/5586009.html)

![](https://github.com/weltond/DataStructure/blob/master/medium.PNG)

Given a non-empty string str and an integer k, rearrange the string such that the same characters are at least distance k from each other.

All input strings are given in lowercase letters. If it is not possible to rearrange the string, return an empty string "".

Example 1:

```
str = "aabbcc", k = 3
Result: "abcabc"
The same letters are at least distance 3 from each other.
```

Example 2:

```
str = "aaabc", k = 3 
Answer: ""
It is not possible to rearrange the string.
```

Example 3:

```
str = "aaadbbcc", k = 2
Answer: "abacabcd"
Another possible answer is: "abcabcda". The same letters are at least distance 2 from each other.
```

## Answer
### Method 1 - Heap -

- Just like []().

```java
public String rearrangeString(String str, int k) {
    if(k==0)
        return str;
 
    //initialize the counter for each character
    final HashMap<Character, Integer> map = new HashMap<Character, Integer>();
    for(int i=0; i<str.length(); i++){
        char c = str.charAt(i);
        if(map.containsKey(c)){
            map.put(c, map.get(c)+1);
        }else{
            map.put(c, 1);
        }
    }
 
    //sort the chars by frequency
    PriorityQueue<Character> queue = new PriorityQueue<Character>(new Comparator<Character>(){
        public int compare(Character c1, Character c2){
            if(map.get(c2).intValue()!=map.get(c1).intValue()){
                return map.get(c2)-map.get(c1);
            }else{
                return c1.compareTo(c2);
            }
        }
    });
 
 
    for(char c: map.keySet())
        queue.offer(c);
 
    StringBuilder sb = new StringBuilder();
 
    int len = str.length();
 
    while(!queue.isEmpty()){
 
        int cnt = Math.min(k, len);
        ArrayList<Character> temp = new ArrayList<Character>();
 
        for(int i=0; i<cnt; i++){
            if(queue.isEmpty())
                return "";
 
            char c = queue.poll();
            sb.append(String.valueOf(c));
 
            map.put(c, map.get(c)-1);
 
            if(map.get(c)>0){
                temp.add(c);
            }
 
            len--;
        }
 
        for(char c: temp)
            queue.offer(c);
    }
 
    return sb.toString();
}
```
