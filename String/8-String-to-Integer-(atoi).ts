function myAtoi(s: string): number {
    const MAX_VALUE = 2**31 - 1;
    const MIN_VALUE = -(2**31);
    let res = 0;
    const newString = s.trim();
    if (!newString) {
        return 0;
    }

    // checking sign
    let sign = 1;
    let start = 0;
    if (newString[0] == "-") {
        sign = -1;
        start += 1;
    } else if (newString[0] == "+") {
        start += 1;
    }

    // skipping the leading zero
    while (start < newString.length && newString[start] == "0") {
        start += 1;
    }

    while (start < newString.length) {
        if (newString[start] < '0' || newString[start] > '9') break;

        const digit = newString[start].charCodeAt(0) - '0'.charCodeAt(0);

        if (res > (MAX_VALUE - digit) / 10) {
            return sign === 1 ? MAX_VALUE : MIN_VALUE;
        }

        res = res * 10 + digit;
        start += 1;
    }

    res = res * sign;
    return res;
};