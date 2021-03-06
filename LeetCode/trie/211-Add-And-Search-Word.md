## [211. Add and Search Word - Data structure design](https://leetcode.com/problems/add-and-search-word-data-structure-design/)

![](https://github.com/weltond/DataStructure/blob/master/medium.PNG)

Design a data structure that supports the following two operations:

`void addWord(word)`

`bool search(word)`

`search(word)` can search a literal word or a regular expression string containing only letters `a-z` or `.`. A `.` means it can represent any one letter.

Example:

```
addWord("bad")
addWord("dad")
addWord("mad")
search("pad") -> false
search("bad") -> true
search(".ad") -> true
search("b..") -> true
```

- Note: You may assume that all words are consist of lowercase letters `a-z`.

## Answer
### Method 1 - Trie - :rocket: 32ms (96.01%)

```java
class WordDictionary {

    Trie root;
    /** Initialize your data structure here. */
    public WordDictionary() {
        root = new Trie();
    }
    
    /** Adds a word into the data structure. */
    public void addWord(String word) {
        root.add(word);
    }
    
    /** Returns if the word is in the data structure. A word could contain the dot character '.' to represent any one letter. */
    public boolean search(String word) {
        return search(word, 0, root);
    }
    
    private boolean search(String s, int lvl, Trie root) {
        if (lvl == s.length()) return root.end;
        
        char c = s.charAt(lvl);
        if (c == '.') {
            for (int i = 0; i < 26; i++) {
                if (root.children[i] != null && search(s, lvl + 1, root.children[i])) return true;
            }
        } else {
            int idx = c - 'a';
            if (root.children[idx] != null && search(s, lvl + 1, root.children[idx])) return true;
        }
        
        return false;
    }
}

/**
 * Your WordDictionary object will be instantiated and called as such:
 * WordDictionary obj = new WordDictionary();
 * obj.addWord(word);
 * boolean param_2 = obj.search(word);
 */

class Trie {
    Trie[] children;
    boolean end;
    public Trie() {
        children = new Trie[26];
    }
    
    public void add(String s) {
        Trie tmp = this;
        for (int i = 0, len = s.length(); i < len; i++) {
            int idx = s.charAt(i) - 'a';
            if (tmp.children[idx] == null) {
                tmp.children[idx] = new Trie();
            }
            tmp = tmp.children[idx];
        }
        tmp.end = true;
    }
}
```

```java
class WordDictionary {
    Trie root;
    /** Initialize your data structure here. */
    public WordDictionary() {
        root = new Trie();
    }
    
    /** Adds a word into the data structure. */
    public void addWord(String word) {
        root.add(word);
    }
    
    /** Returns if the word is in the data structure. A word could contain the dot character '.' to represent any one letter. */
    public boolean search(String word) {
        return search(word, 0, root);
    }
    
    private boolean search(String s, int lvl, Trie root) {
        if (root == null) return false;
        
        if (lvl == s.length()) {
            return root.isWord;
        }
        
        char c = s.charAt(lvl);
        if (c != '.') {
            if (search(s, lvl + 1, root.children[c - 'a'])) return true;;
        } else {
            for (int i = 0; i < 26; i++) {
                if (search(s, lvl + 1, root.children[i])) return true;
            }
        }
        
        return false;
    }
}

/**
 * Your WordDictionary object will be instantiated and called as such:
 * WordDictionary obj = new WordDictionary();
 * obj.addWord(word);
 * boolean param_2 = obj.search(word);
 */

class Trie {
    Trie[] children;
    boolean isWord;
    public Trie() {
        children = new Trie[26];
    }
    
    public void add(String word) {
        Trie tmp = this;
        for (int i = 0, len = word.length(); i < len; i++) {
            int idx = word.charAt(i) - 'a';
            if (tmp.children[idx] == null) {
                tmp.children[idx] = new Trie();
            }
            tmp = tmp.children[idx];
        }
        tmp.isWord = true;
    }
    
    public boolean isEmpty(Trie root) {
        for (int i = 0; i < 26; i++) {
            if (root.children[i] != null) return false;
        }
        return true;
    }
}
```

```java
class WordDictionary {
    
    Trie root;
    /** Initialize your data structure here. */
    public WordDictionary() {
        root = new Trie();
    }
    
    /** Adds a word into the data structure. */
    public void addWord(String word) {
        root.add(word);
    }
    
    /** Returns if the word is in the data structure. A word could contain the dot character '.' to represent any one letter. */
    public boolean search(String word) {
        return search(word, 0, root);
    }
    
    private boolean search(String word, int lvl, Trie t) {
        if (lvl == word.length()) {
            return t.isWord;
        }
        
        char c = word.charAt(lvl);
        if (c == '.') {
            for (int i = 0; i < 26; i++) {
                if (t.children[i] != null && search(word, lvl + 1, t.children[i])) {
                    return true;
                }
            }
            
            return false;
        }
        
        int idx = c - 'a';
        
        if (t.children[idx] == null) return false;
        
        return search(word, lvl + 1, t.children[idx]);
    }
}

class Trie {
    Trie[] children;
    boolean isWord;
    
    public Trie() {
        children = new Trie[26];
        isWord = false;
    }
    
    public void add(String w) {
        Trie tmp = this;
        for (int i = 0, len = w.length(); i < len; i++) {
            int idx = w.charAt(i) - 'a';
            if (tmp.children[idx] == null) {
                tmp.children[idx] = new Trie();
            }
            
            tmp = tmp.children[idx];
        }
        
        tmp.isWord = true;
    }
}
/**
 * Your WordDictionary object will be instantiated and called as such:
 * WordDictionary obj = new WordDictionary();
 * obj.addWord(word);
 * boolean param_2 = obj.search(word);
 */


class WordDictionary {
    TrieNode root;
    /** Initialize your data structure here. */
    public WordDictionary() {
        root = new TrieNode();
    }
    
    /** Adds a word into the data structure. */
    public void addWord(String word) {
        TrieNode tmp = root;
        for (int i = 0; i < word.length(); i++) {
            int idx = word.charAt(i) - 'a';
            if (tmp.children[idx] == null) {
                tmp.children[idx] = new TrieNode();
            }
            tmp = tmp.children[idx];
        }
        tmp.isEnd = true;
        tmp.word = word;
    }
    
    /** Returns if the word is in the data structure. A word could contain the dot character '.' to represent any one letter. */
    public boolean search(String word) {
        return helper(word, root, 0);
    }
    
    public boolean helper(String word, TrieNode root, int level) {
        if (level == word.length()) return root.isEnd;
        
        if (word.charAt(level) == '.') {
            for (int i = 0; i < 26; i++) {
                if (root.children[i] != null) {
                    if (helper(word, root.children[i], level + 1)) {
                        return true;
                    }
                }
            }
        } else {
            char c = word.charAt(level);
            int index = c - 'a';
            if (root.children[index] != null) {
                if (helper(word, root.children[index], level + 1)) {
                    return true;
                }
            }
        }
        
        return false;   
    }
}

class TrieNode {
    TrieNode[] children = new TrieNode[26];
    boolean isEnd;
    String word;
}

/**
 * Your WordDictionary object will be instantiated and called as such:
 * WordDictionary obj = new WordDictionary();
 * obj.addWord(word);
 * boolean param_2 = obj.search(word);
 */
```

