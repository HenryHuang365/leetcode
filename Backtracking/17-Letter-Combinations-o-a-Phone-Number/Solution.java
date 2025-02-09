import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

class Solution {
    public List<String> letterCombinations(String digits) {
        if (digits.isEmpty())
            return new ArrayList<>();
        HashMap<Character, String[]> map = new HashMap<Character, String[]>();
        map.put('2', new String[] { "a", "b", "c" });
        map.put('3', new String[] { "d", "e", "f" });
        map.put('4', new String[] { "g", "h", "i" });
        map.put('5', new String[] { "j", "k", "l" });
        map.put('6', new String[] { "m", "n", "o" });
        map.put('7', new String[] { "p", "q", "r", "s" });
        map.put('8', new String[] { "t", "u", "v" });
        map.put('9', new String[] { "w", "x", "y", "z" });
        char[] digitsArray = digits.toCharArray();
        List<List<String>> list = new ArrayList<>();
        List<String> path = new ArrayList<>();
        backtracking(map, digitsArray, list, path, digits.length(), 0);
        List<String> result = new ArrayList<>();
        for (List<String> l : list) {
            StringBuilder sb = new StringBuilder();
            for (String s : l) {
                sb.append(s);
            }
            result.add(sb.toString());
        }
        return result;
    }

    public void backtracking(HashMap<Character, String[]> map, char[] digitsArray, List<List<String>> list,
            List<String> path, int size, int start) {
        if (path.size() == size) {
            list.add(new ArrayList<>(path));
        } else {
            String[] letters = map.get(digitsArray[start]);
            for (int i = 0; i < letters.length; i++) {
                path.add(letters[i]);
                backtracking(map, digitsArray, list, path, size, start + 1);
                path.remove(path.size() - 1);
            }
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println("Output: " + solution.letterCombinations("23"));
    }
}