function strStr(haystack: string, needle: string): number {
    let lenHaystack = haystack.length;
    let lenNeedle = needle.length;

    if (lenHaystack < lenNeedle) {
        return -1;
    }

    for (let i = 0; i <= lenHaystack - lenNeedle; i++) {
        let j = 0;

        while (j < lenNeedle && haystack[i + j] == needle[j]) {
            j++;
        }

        if (j == lenNeedle) {
            return i;
        }
    }

    return -1;
};