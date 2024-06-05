function isPalindrome(s: string): boolean {
    const newS = s.replace("[^A-Za-z0-9]", "").toLowerCase();
    const reversedS = newS.split("").reverse().join("");
    return newS === reversedS;
};

function isPalindrome1(s: String): boolean {
    let left = 0;
    let right = s.length - 1;

    while (left < right) {
        while (left < right && !alphaNum(s[left])) {
            left++;
        }

        while (left < right && !alphaNum(s[right])) {
            right--;
        }

        if (s[left].toLowerCase != s[right].toLowerCase) {
            return false;
        }

        left++;
        right--;
    }
    return true;
}

function alphaNum(c: String): boolean {
    return ("A".charCodeAt(0) <= c.charCodeAt(0) && "Z".charCodeAt(0) >= c.charCodeAt(0)) ||
        ("a".charCodeAt(0) <= c.charCodeAt(0) && "z".charCodeAt(0) >= c.charCodeAt(0)) ||
        ("0".charCodeAt(0) <= c.charCodeAt(0) && "9".charCodeAt(0) >= c.charCodeAt(0));
}