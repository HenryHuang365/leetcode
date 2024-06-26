function characterReplacement(s: string, k: number): number {
    let list = new Map();
    let res = 0;
    let l = 0;

    for (let r = 0; r < s.length; r++) {     
        let len = r - l + 1;   
        list.set(s[r], 1 + (list.get(s[r]) || 0));  

        if (len - Math.max(...list.values()) > k) {
            list.set(s[l], list.get(s[l]) - 1);
            l++;
        }
        len = r - l + 1;
        res = Math.max(res, len);
    }
    return res;
};