function addBinary(a: string, b: string): string {
    let str: string[] = [];
    let i = a.length - 1; let j = b.length - 1; let carry = 0;

    while (i >= 0 || j >= 0) {
        let sum = carry;
        if (i >= 0) {sum += Number(a[i--]) - 0;}
        if (j >= 0) {sum += Number(b[j--]) - 0;}
        str.push((sum % 2).toString());
        carry = Math.floor(sum / 2);
    }

    if (carry != 0) {
        str.push("1");
    }

    return str.reverse().join("");
};