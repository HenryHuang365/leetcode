class Solution {
    /**
     * @param {string[]} strs
     * @returns {string}
     */
    encode(strs) {
        let res = "";
        for (let str of strs) {
            res = res + str.length.toString() + "#" + str;
        }
        return res;
    }

    /**
     * @param {string} str
     * @returns {string[]}
     */
    decode(str) {
        let res = [];

        let i = 0;

        while (i < str.length) {
            let j = i;
            while (str[j] !== "#") {
                j++;
            }

            const len = parseInt(str.slice(i, j));
            i = j + 1 + len;
            res.push(str.slice(j + 1, i));
        }

        return res;
    }
}
