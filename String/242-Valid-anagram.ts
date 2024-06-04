function isAnagram(s: string, t: string): boolean {
    if (s.length != t.length) {
        return false;
    }

    let countS: Map<string, number> = new Map();
    let countT: Map<string, number> = new Map();

    for (let i = 0; i < s.length; i++) {
        countS.set(s[i], (countS.get(s[i]) || 0) + 1);
        countT.set(t[i], (countT.get(t[i]) || 0) + 1);
    }

    if (countS.keys !== countT.keys) {
        return false;
    }

    for (let key of countS.keys()) {
        if (countS.get(key) !== countT.get(key)) {
            return false;
        }
    }
    return true;
};