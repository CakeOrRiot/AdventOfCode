with open('../input.txt','rt') as f:
    numbers = [int(x) for x in f.readlines()]
    numbers.append(0)
    numbers.append(max(numbers)+3)
    numbers.sort()
    dp = [0 for _ in range(max(numbers)+1)]
    numbers_set = set(numbers)
    dp[0]=1
    for x in numbers:
        add = 0
        for k in range(1,4):
            if x-k in numbers_set:
                add+=dp[x-k]
        dp[x] += add

    print(dp[max(numbers)])
