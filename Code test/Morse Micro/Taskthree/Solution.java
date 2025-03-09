import java.util.HashMap;

class Solution {
    public int solution(String s) {
        int minN = Integer.MAX_VALUE;
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        map.put('^', 1);
        map.put('<', 2);
        map.put('v', 3);
        map.put('>', 4);
        for (Character direction : map.keySet()) {
            int turns = 0;
            for (int i = 0; i < s.length(); i++) {
                Character c = s.charAt(i);
                int diff = 0;
                if (map.get(c) - map.get(direction) < 0) {
                    diff = map.get(direction) - map.get(c);
                } else if (map.get(c) - map.get(direction) > 0) {
                    diff = 4 - map.get(c) + map.get(direction);
                }
                turns += diff;
            }
            minN = Math.min(turns, minN);
        }
        return minN;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();

        System.out.println(solution.solution("^vv<"));
        System.out.println(solution.solution("vv>>vv"));
        System.out.println(solution.solution("<<<"));
    }
}