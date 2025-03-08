def ParallelSums(arr, token):
    size = len(arr)
    
    # 计算数组总和
    total_sum = sum(arr)
    
    # 如果总和是奇数，无法分为两个相等的子集，返回 -1
    if total_sum % 2 != 0:
        return "-1"
    
    set_sum = total_sum // 2
    
    # 将数组排序，方便后续贪心选择
    values = sorted(arr)
    
    # 初始化低位和高位索引
    low = 0
    high = size - 1
    front = True  # 标志信号，决定是选最低值还是最高值
    
    set1 = []
    set2 = []
    
    # 贪心选择过程
    while low < high:
        # 当只剩下两个元素时，做特殊处理
        if low + 1 == high:
            current_sum = sum(set1)
            difference = set_sum - current_sum
            
            if difference == values[low]:
                set1.append(values[low])
                set2.append(values[high])
            else:
                set1.append(values[high])
                set2.append(values[low])
            low += 1
            high -= 1
        elif front:
            # 向 set1 和 set2 分别添加最低的两个值
            set1.append(values[low])
            set2.append(values[low + 1])
            low += 2
            front = False
        else:
            # 向 set1 和 set2 分别添加最高的两个值
            set1.append(values[high])
            set2.append(values[high - 1])
            high -= 2
            front = True
    
    # 将两个集合排序
    set1.sort()
    set2.sort()
    
    # 拼接两个集合的字符串形式，按照题目要求顺序输出
    result = ','.join(map(str, set1 + set2))
    
    # Token 替换部分
    final_output = ""
    for char in result:
        if char in token:
            final_output += f"--{char}--"
        else:
            final_output += char
    
    return final_output

# 测试用例
A = [16, 22, 35, 8, 20, 1, 21, 11]
B = [1, 2, 3, 4]
C = [1, 2, 1, 5]
D = [9, 1, 0, 5, 3, 2]
E = [2, 3, 1, 9, 3, 4, 4, 4]
F = [6, 2, 4, 1, 10, 25, 5, 3, 40, 4]

token = "7vq3d0ubc5e"

print(ParallelSums(A, token))  # 应输出 1,11,20,35,8,16,21,22 并处理 token
print(ParallelSums(B, token))  # 应输出 1,4,2,3 并处理 token
print(ParallelSums(C, token))  # 应输出 -1
print(ParallelSums(D, token))  # 应输出 0,1,9,2,3,5 并处理 token
print(ParallelSums(E, token))  # 应输出 1,2,3,9,3,4,4,4 并处理 token
print(ParallelSums(F, token))  # 应输出 1,2,3,4,40,4,5,6,10,25 并处理 token
