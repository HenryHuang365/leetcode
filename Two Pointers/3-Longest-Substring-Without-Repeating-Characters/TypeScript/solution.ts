function lengthOfLongestSubstring(s: string): number {
    let max: number = 0;
    let left: number = 0;
    let charSet: Set<string> = new Set();
    for (let right = 0; right < s.length; right++) {
        if (!charSet.has(s[right])) {
            charSet.add(s[right]);
            max = Math.max(max, right - left + 1);
        } else {
            while (charSet.has(s[right])) {
                charSet.delete(s[left]);
                left += 1;
            }
            charSet.add(s[right]);
        }
    }
    return max;
};

// Second solution

function lengthOfLongestSubstring_2(s: string): number {
    let max = 0;
    let charSet: Set<string> = new Set();
    let left = 0;
    let right = 0;

    while (right < s.length) {
        if (!charSet.has(s[right])) {
            charSet.add(s[right++]);
            max = Math.max(max, charSet.size);
        } else {
            charSet.delete(s[left++]);
        }
    }
    return max;
}