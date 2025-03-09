class Solution {
    public String solution(String message, int K) {
        if (message.length() <= K) {
            return message;
        }
        String[] words = message.split(" ");
        int length = 3;
        int i = 0;
        StringBuilder stringBuilder = new StringBuilder();
        while (i < words.length) {
            String word = words[i];
            length += word.length() + 1;
            // System.out.println(length);
            if (length <= K) {
                stringBuilder.append(word + " ");
            } else {
                break;
            }
            i++;
        }
        stringBuilder.append("...");
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        Solution solution = new Solution();

        System.out.println(solution.solution("And now here is my secret", 15));
        System.out.println(solution.solution("There is an animal with four legs", 15));
        System.out.println(solution.solution("super dog", 4));
        System.out.println(solution.solution("how are you", 20));
    }
}
