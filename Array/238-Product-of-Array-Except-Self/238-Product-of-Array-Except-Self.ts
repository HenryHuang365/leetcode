function productExceptSelf(nums: number[]): number[] {
    let list: number[] = [];
    const n = nums.length;

    let left_product: number[] = [];
    let right_product: number[] = [];

    let product1 = 1;
    for (let i = 0; i < n; i++) {
        product1 = product1 * nums[i];
        left_product.push(product1);
    }

    let product2 = 1;
    for (let j = n - 1; j >= 0; j--) {
        product2 = product2 * nums[j];
        right_product.push(product2);
    }

    console.log("left_product: ", left_product);
    console.log("right_product: ", right_product);

    for (let k = 0; k < n; k++) {
        let left_index = k - 1;
        let right_index = n - k - 2;

        let left_index_product = left_index >= 0 ? left_product[left_index] : 1;
        let right_index_product = right_index >= 0 ? right_product[right_index] : 1;

        const product = left_index_product * right_index_product;
        list.push(product);
    }
    return list;
};