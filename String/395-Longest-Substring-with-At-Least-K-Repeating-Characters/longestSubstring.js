function longestSubstringInJs(s, k) {
    if (s.length < k) return 0;
    let max = 0;
    const n = s.length;
    for (let target_unique = 1; target_unique <= 26; target_unique++) {
        const frequency = new Array(26).fill(0);
        let start = 0;
        let end = 0;
        let unique = 0;
        let uniqueMoreThanK = 0;
        while (end < n) {
            if (unique <= target_unique) {
                const index = s.charCodeAt(end) - 'a'.charCodeAt(0);
                if (frequency[index] == 0) {
                    unique++;
                }
                frequency[index]++;
                if (frequency[index] == k) {
                    uniqueMoreThanK++;
                }
                end++;
            } else {
                const index = s.charCodeAt(start) - 'a'.charCodeAt(0);
                if (frequency[index] == k) {
                    uniqueMoreThanK--;
                }
                frequency[index]--;
                if (frequency[index] == 0) {
                    unique--;
                }                            
                start++;
            }

            if (unique == target_unique && unique == uniqueMoreThanK) {
                max = Math.max(max, end - start);
            }
        }

    }
    return max;
};

console.log("Output: ", longestSubstringInJs("ababbc", 2));