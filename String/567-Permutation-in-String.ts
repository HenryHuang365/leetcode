function checkInclusion(s1: string, s2: string): boolean {
    if (s1.length > s2.length) {
        return false;
    }

    let s1Count = new Array(26).fill(0);
    let s2Count = new Array(26).fill(0);

    for (let i = 0; i < s1.length; i++) {
        s1Count[s1.charCodeAt(i) - 'a'.charCodeAt(0)]++;
        s2Count[s2.charCodeAt(i) - 'a'.charCodeAt(0)]++;
    }

    let matches = 0;

    for (let i = 0; i < 26; i++) {
        if (s1Count[i] == s2Count[i]) {
            matches++;
        }
    }

    let l = 0;

    for (let r = s1.length; r < s2.length; r++) {
        if (matches == 26) {
            return true;
        }

        let index = s2.charCodeAt(r) - 'a'.charCodeAt(0);
        s2Count[index]++;
        if (s2Count[index] == s1Count[index]) {
            matches++;
        } else if (s2Count[index] - 1 == s1Count[index]) {
            matches--;
        }

        index = s2.charCodeAt(l) - 'a'.charCodeAt(0);
        s2Count[index]--;
        if (s2Count[index] == s1Count[index]) {
            matches++;
        } else if (s2Count[index] + 1 == s1Count[index]) {
            matches--;
        }

        l++;
    }

    return matches == 26;
};