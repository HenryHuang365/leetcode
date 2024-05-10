

class Solution {
    public boolean isSubsequence(String s, String t) {
        if (s.length() == t.length()) {
            System.err.println("eqaul");
            return s == t;
        }

        if (s.length() > t.length()) {
            return false;
        }
    
        int i = 0;
        int j = 0;
        while (i < t.length() && j < s.length()) {
            if (s.charAt(j) == t.charAt(i)) {
                j++;
            }
            i++;
        }
        
        return j == s.length();
    }
}

//t=  a b c

// ahbgdc
// t[0] t[i]