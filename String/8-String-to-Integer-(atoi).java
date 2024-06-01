class Solution {
    public int myAtoi(String s) {
        s = s.trim();
        if (s.isEmpty()) {
            return 0;
        }

        int result = 0;
        int i = 0;
        int sign = 1;

        if (s.charAt(0) == '-') {
            i++;
            sign = -1;
        } else if (s.charAt(0) == '+') {
            i++;
        }

        while (i < s.length()) {
            if (!Character.isDigit(s.charAt(i)))
                break;

            int digit = s.charAt(i) - '0';

            if (result > (Integer.MAX_VALUE - digit) / 10) {
                return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }

            result = result * 10 + digit;
            i++;
        }

        return result = result * sign;
    }
}