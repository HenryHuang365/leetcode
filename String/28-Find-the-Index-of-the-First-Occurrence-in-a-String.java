class Solution {
    public int strStr(String haystack, String needle) {
        int lenHaystack = haystack.length();
        int lenNeedle = needle.length();

        if (lenHaystack < lenNeedle) {
            return -1;
        }

        // Important: the i needs be to less or equal to the lenHaystack - lenNeedle.
        for (int i = 0; i <= lenHaystack - lenNeedle; i++) {
            int j = 0;
            while (j < lenNeedle && haystack.charAt(i + j) == needle.charAt(j)) {
                j++;
            }

            if (j == lenNeedle) {
                return i;
            }
        }
        return -1;
    }
}