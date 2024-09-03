function longestSubstring(s: string, k: number): number {
    if (s.length < k) return 0;
    let max = 0;
    const n = s.length;
    for (let target_unique = 1; target_unique <= 26; target_unique++) {
        const frequency: number[] = new Array(26).fill(0);
        let start = 0;
        let end = 0;
        let unique = 0;
        let uniqueMoreThanK = 0;
        while (end < n) {
            if (unique <= target_unique) {
                const index = s.charCodeAt(end) - 65;
                if (frequency[index] == 0) {
                    unique++;
                }
                frequency[index]++;
                if (frequency[index] == k) {
                    uniqueMoreThanK++;
                }
                end++;
            } else {
                const index = s.charCodeAt(start) - 65;
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
