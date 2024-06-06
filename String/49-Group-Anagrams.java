// Given an array of strings strs, group the anagrams together. You can return
// the answer in any order.

// An Anagram is a word or phrase formed by rearranging the letters of a
// different word or phrase, typically using all the original letters exactly
// once.

// Example 1:

// Input: strs = ["eat","tea","tan","ate","nat","bat"]
// Output: [["bat"],["nat","tan"],["ate","eat","tea"]]
// Example 2:

// Input: strs = [""]
// Output: [[""]]
// Example 3:

// Input: strs = ["a"]
// Output: [["a"]]

// Constraints:

// 1 <= strs.length <= 104
// 0 <= strs[i].length <= 100
// strs[i] consists of lowercase English letters.
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;

class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>> list = new ArrayList<>();
        for (String str : strs) {
            boolean found = false;
            for (List<String> arr : list) {
                if (isAnagram(arr.getFirst(), str)) {
                    arr.add(str);
                    found = true;
                }
            }
            if (!found) {
                List<String> subArray = new ArrayList<>();
                subArray.add(str);
                list.add(subArray);
            }
        }
        return list;
    }

    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        HashMap<Character, Integer> countS = new HashMap<>();
        HashMap<Character, Integer> countT = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            countS.put(s.charAt(i), countS.getOrDefault(s.charAt(i), 0) + 1);
            countT.put(t.charAt(i), countT.getOrDefault(t.charAt(i), 0) + 1);
        }

        if (!countS.keySet().equals(countT.keySet())) {
            return false;
        }

        for (char c : countS.keySet()) {
            if (!countS.get(c).equals(countT.get(c))) {
                return false;
            }
        }

        return true;
    }

    public List<List<String>> groupAnagrams1(String[] strs) {
        HashMap<String, List<String>> anagramMap = new HashMap<>();

        for (String str : strs) {
            char[] charArray = str.toCharArray();
            Arrays.sort(charArray);

            String sortedString = new String(charArray);

            if (!anagramMap.containsKey(sortedString)) {
                // List<String> subList = new ArrayList<>();
                // subList.add(str);
                anagramMap.put(sortedString, new ArrayList<>());
            }
            // else {
            // anagramMap.get(sortedString).add(str);
            // }

            // The comments above is also working and it is my logic
            anagramMap.get(sortedString).add(str);
        }
        return new ArrayList<>(anagramMap.values());
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        String[] strs = { "eat", "tea", "tan", "ate", "nat", "bat" };
        System.out.println(sol.groupAnagrams(strs));
    }
}