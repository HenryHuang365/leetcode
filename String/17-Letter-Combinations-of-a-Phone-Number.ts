function letterCombinations(digits: string): string[] {
    if (!digits.length) {
        return [];
    }

    const digits_letters = {
        '2': "abc",
        '3': 'def',
        '4': 'ghi',
        '5': 'jkl',
        '6': 'mno',
        '7': 'pqrs',
        '8': 'tuv',
        '9': 'wxyz'
    };

    const res: string[] = [];
    
    function backtrack(idx, comb: string) {
        if (idx == digits.length) {
            res.push(comb);
            return;
        }

        const letters = digits_letters[digits[idx]]

        for (const letter of letters) {
            backtrack(idx + 1, comb + letter);
        }
        
    }

    backtrack(0, "");

    return res;
};