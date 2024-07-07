function decrypt(code: number[], k: number): number[] {
    let list: number[] = Array(code.length).fill(0);
    if (k == 0) return list;

    let start = 1;
    let end = k;

    if (k < 0) {
        k = -k;
        start = code.length - k;
        end = code.length - 1;
    }
    let sum = 0;
    for (let i = start; i <= end; i++) {
        sum += code[i];
    }

    for (let i = 0; i < code.length; i++) {
        list[i] = sum;
        end++;
        sum = sum + code[end % code.length] - code[start % code.length];
        start--;
    }

    return list;
};