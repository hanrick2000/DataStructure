## Description
Implement a simple client for GFS (Google File System, a distributed file system), it provides the following methods:

1. `read(filename)`. Read the file with given filename from GFS.
2. `write(filename, content)`. Write a file with given filename & content to GFS.

There are two private methods that already implemented in the base class:

1. `readChunk(filename, chunkIndex)`. Read a chunk from GFS.
2. `writeChunk(filename, chunkIndex, chunkData)`. Write a chunk to GFS.

To simplify this question, we can assume that the chunk size is chunkSize bytes. 
(In a real world system, it is 64M). The GFS Client's job is splitting a file into multiple chunks (if need) 
and save to the remote GFS server. chunkSize will be given in the constructor. 
You need to call these two private methods to implement read & write methods.

## Example
```
GFSClient(5)
read("a.txt")
>> null
write("a.txt", "World")
>> You don't need to return anything, but you need to call writeChunk("a.txt", 0, "World") to write a 5 bytes chunk to GFS.
read("a.txt")
>> "World"
write("b.txt", "111112222233")
>> You need to save "11111" at chink 0, "22222" at chunk 1, "33" at chunk 2.
write("b.txt", "aaaaabbbbb")
read("b.txt")
>> "aaaaabbbbb"
```
## Raw Code
```java
/* Definition of BaseGFSClient
 * class BaseGFSClient {
 *     private Map<String, String> chunk_list;
 *     public BaseGFSClient() {}
 *     public String readChunk(String filename, int chunkIndex) {
 *         // Read a chunk from GFS
 *     }
 *     public void writeChunk(String filename, int chunkIndex,
 *                            String content) {
 *         // Write a chunk to GFS
 *     }
 * }
 */


public class GFSClient extends BaseGFSClient {
    /*
    * @param chunkSize: An integer
    */public GFSClient(int chunkSize) {
        // do intialization if necessary
    }

    /*
     * @param filename: a file name
     * @return: conetent of the file given from GFS
     */
    public String read(String filename) {
        // write your code here
    }

    /*
     * @param filename: a file name
     * @param content: a string
     * @return: nothing
     */
    public void write(String filename, String content) {
        // write your code here
    }
}
```
