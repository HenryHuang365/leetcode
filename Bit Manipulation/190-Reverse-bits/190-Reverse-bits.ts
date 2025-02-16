function reverseBits(n: number): number {
	let res = 0;
    for (let i = 0; i < 32; i++) {
        const bit = (n >>> i) & 1;
        res = (res << 1) | bit;
    }
    return res >>> 0;
};