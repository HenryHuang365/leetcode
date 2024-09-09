class Solution {
    public String solution(int AA,int AB,int BB) {

        //initialisation
        int a=AA;
        int b=BB;
        int c=AB;
        String s="";

        //if value of a is greater than b
        if(a>b)
        {
            for(int i=0;i<b;i++)
            {
                s=s+"AA";
                s=s+"BB";

            }
            for(int i=0;i<c;i++)
            {
                s=s+"AB";
            }
            s=s+"AA";
        }

        //if value of a is equal to b
        else if(a==b)
        {
            for(int i=0;i<b;i++)
            {
                s=s+"AA";
                s=s+"BB";

            }
            for(int i=0;i<c;i++)
            {
                s=s+"AB";
            }
        }

        //if value of b is greater than a
        else if(b>a){
            for(int i=0;i<a;i++)
            {
                s=s+"BB";
                s=s+"AA";

            }
            s=s+"BB";
            for(int i=0;i<c;i++)
            {
                s=s+"AB";
            }
        }

        //if the value of a and b are both 0
        else{
            for(int i=0;i<c;i++)
            {
                s=s+"AB";
            }
        }
        return s;   //returning the final string
    }


    public static void main(String[] args) {
        Solution solution = new Solution();
        
        // Test case 1: AA = 5, AB = 0, BB = 2
        int AA1 = 5, AB1 = 0, BB1 = 2;
        System.out.println("Test case 1:");
        System.out.println("AA = " + AA1 + ", AB = " + AB1 + ", BB = " + BB1);
        System.out.println("Result: " + solution.solution(AA1, AB1, BB1)); // Expected: "AABBAABBAA"

        // Test case 2: AA = 1, AB = 2, BB = 1
        int AA2 = 1, AB2 = 2, BB2 = 1;
        System.out.println("\nTest case 2:");
        System.out.println("AA = " + AA2 + ", AB = " + AB2 + ", BB = " + BB2);
        System.out.println("Result: " + solution.solution(AA2, AB2, BB2)); // Expected: Several possible answers

        // Test case 3: AA = 0, AB = 2, BB = 0
        int AA3 = 0, AB3 = 2, BB3 = 0;
        System.out.println("\nTest case 3:");
        System.out.println("AA = " + AA3 + ", AB = " + AB3 + ", BB = " + BB3);
        System.out.println("Result: " + solution.solution(AA3, AB3, BB3)); // Expected: "ABAB"

        // Test case 4: AA = 0, AB = 0, BB = 10
        int AA4 = 0, AB4 = 0, BB4 = 10;
        System.out.println("\nTest case 4:");
        System.out.println("AA = " + AA4 + ", AB = " + AB4 + ", BB = " + BB4);
        System.out.println("Result: " + solution.solution(AA4, AB4, BB4)); // Expected: "BB"

        // Test case 5: Custom case, AA = 3, AB = 3, BB = 3
        int AA5 = 3, AB5 = 3, BB5 = 3;
        System.out.println("\nTest case 5:");
        System.out.println("AA = " + AA5 + ", AB = " + AB5 + ", BB = " + BB5);
        System.out.println("Result: " + solution.solution(AA5, AB5, BB5)); // Expected: Should be valid, no "AAA" or "BBB"
    }
}
