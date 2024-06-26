function characterReplacement(s: string, k: number): number {
    let list = new Map();
    let res = 0;
    let max = 0;
    let l = 0;

    for (let r = 0; r < s.length; r++) {             
        list.set(s[r], 1 + (list.get(s[r]) || 0)); 
        max = Math.max(max, list.get(s[r]));

        if ((r - l + 1) - max > k) {
            list.set(s[l], list.get(s[l]) - 1);
            l++;
        }        
        res = Math.max(res, r - l + 1);
    }
    return res;
};