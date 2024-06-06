function groupAnagrams(strs: string[]): string[][] {
    let anagramMap: Map<string, string[]> = new Map();
    for (let str of strs) {
        let charArray = str.split('');
        charArray.sort();
        const sortedString = charArray.join('');

        if (!anagramMap.has(sortedString)) {
            anagramMap.set(sortedString, []);
        }

        anagramMap.get(sortedString)?.push(str);
    }
    return Array.from(anagramMap.values());
};