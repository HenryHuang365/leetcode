// The API: int read4(char *buf) reads 4 characters at a time from a file.

// The return value is the actual number of characters read. For example, it returns 3 if there is only 3 characters left in the file.

// By using the read4 API, implement the function int read(char *buf, int n) that reads n characters from the file.

// Example 1:

// Input: buf = "abc", n = 4
// Output: "abc"
// Explanation: The actual number of characters read is 3, which is "abc".
// Example 2:

// Input: buf = "abcde", n = 5
// Output: "abcde"
// Note:
// The read function will only be called once for each test case.
// """
// """
// Algorithm:
// 1. Declare index = 0
// 2. while index < n:
//     2.a reinitial a new empty array [""] * 4
//     2.b update count from read4()
//     2.c if count == 0: break (EOF)
//     2.4 updat count = min(count, n - index) (can not exceed max buffer length n)
//     2.5 buf[i:] = buf4[:count]
//     2.6 index += count
// 3. return index

// T: O(n)
// S: O(1)


class Solution {
    public int read(char[] buf, int n) {        
        int numCharsRead = 0;

        while (numCharsRead < n) {
            char[] cache = {' ', ' ', ' ', ' '};
            int read4Chars = read4(cache);

            if (read4Chars == 0) {
                break;
            }

            int cacheCnt = 0; 
            while (read4Chars > 0 && numCharsRead < n) {
                buf[numCharsRead] = cache[cacheCnt];
                read4Chars--;
                numCharsRead++;
                cacheCnt++;
            }
        }

        return numCharsRead;
    }

    public int read4(char[] cache) {
        char[] charsToAdd = {'a', 'b', 'c', 'd'};
        for (int i = 0; i < 4; i++) {
            cache[i] = charsToAdd[i];
        }
        return cache.length;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int n = Integer.parseInt(args[0]);
        char[] buf = {' ', ' ', ' ', ' '};
        int result = solution.read(buf, n);

        System.out.println("Chars read: " + result);
        System.out.println(buf);
    }
}