class MethodTwoLeastKCharacters {
    public int longestSubstring(String s, int k) {
        int max = 0;
        int n = s.length();

        for (int target_unique = 1; target_unique <= 26; target_unique++) {
            int start = 0;
            int end = 0;
            int unique = 0;
            int uniqueMoreThanK = 0;
            int[] frequency = new int[26];

            while (end < n) {
                if (unique <= target_unique) {
                    int index = s.charAt(end) - 'a';
                    if (frequency[index] == 0) {
                        unique++;
                    }
                    frequency[index]++;
                    if (frequency[index] == k) {
                        uniqueMoreThanK++;
                    }
                    end++;
                } else {
                    int index = s.charAt(start) - 'a';
                    if (frequency[index] == k) {
                        uniqueMoreThanK--;
                    }
                    frequency[index]--;
                    if (frequency[index] == 0) {
                        unique--;
                    }
                    start++;
                }
                if (unique == target_unique && unique == uniqueMoreThanK) {
                    max = Math.max(max, end - start);
                }
            }
        }
        return max;
    }

    public static void main(String[] args) {
        MethodTwoLeastKCharacters leastKCharacters = new MethodTwoLeastKCharacters();

        System.out.println("Output: " + leastKCharacters.longestSubstring("bbaaacbd", 3));
        System.out.println("Output: " + leastKCharacters.longestSubstring("ababbc", 2));
    }
}
