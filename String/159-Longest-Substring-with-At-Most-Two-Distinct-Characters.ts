function lengthOfLongestSubstringTwoDistinct(s: string): number {
    const counts: Map<string, number> = new Map();
    let ans = 0; 
    let left = 0;
    for (let right = 0; right < s.length; right++) {
        const currChar = s.charAt(right);
        counts.set(currChar, (counts.get(currChar) || 0) + 1);
        while (counts.size > 2) {
            const leftChar = s.charAt(left);
            counts.set(leftChar, counts.get(leftChar)! - 1);
            if (counts.get(leftChar) == 0) counts.delete(leftChar);
            left++;
        }
        ans = Math.max(ans, right - left + 1);
    }
    return ans;
};

console.log("output: ", lengthOfLongestSubstringTwoDistinct("eceba"))