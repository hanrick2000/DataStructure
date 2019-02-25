## Description
Design and implement a data structure for Least Recently Used (LRU) cache. It should support the following operations: `get` and `set`.

- `get(key)`: Get the value (will always be positive) of the key if the key exists in the cache, otherwise return -1.
- `set(key, value)`: Set or insert the value if the key is not already present. When the cache reached its capacity, it should invalidate the least recently used item before inserting a new item.

## Example
```
Input:
LRUCache(2)
set(2, 1)
set(1, 1)
get(2)
set(4, 1)
get(1)
get(2)
Output:
[1,-1,1]
```

## TO DO
```java
public class LRUCache {
    /*
    * @param capacity: An integer
    */public LRUCache(int capacity) {
        // do intialization if necessary
    }

    /*
     * @param key: An integer
     * @return: An integer
     */
    public int get(int key) {
        // write your code here
    }

    /*
     * @param key: An integer
     * @param value: An integer
     * @return: nothing
     */
    public void set(int key, int value) {
        // write your code here
    }
}
```

## Tag
**Linked List**