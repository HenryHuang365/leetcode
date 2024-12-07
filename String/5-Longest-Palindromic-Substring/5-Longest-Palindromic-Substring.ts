function longestPalindrome(s: string): string {
    let res: string = "";
    let max: number = 0;

    for (let i = 0; i < s.length; i++) {
        // odd 
        let l = i;
        let r = i;
        while (l >= 0 && r <= s.length - 1 && s[l] == s[r]) {
            if (r - l + 1 > max) {
                res = s.substring(l, r + 1);
                max = r - l + 1;
            }
            l -= 1;
            r += 1;
        }

        // even 
        l = i;
        r = i + 1;
        while (l >= 0 && r <= s.length - 1 && s[l] == s[r]) {
            if (r - l + 1 > max) {
                res = s.substring(l, r + 1);
                max = r - l + 1;
            }
            l -= 1;
            r += 1;
        }
    }
    return res;
};