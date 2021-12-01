with open('../input.txt','rt') as f:
    numbers = list(map(int,f.readlines()))
    bad_number = 36845998
    for start in range(len(numbers)):
        cur_sum = 0
        j=0
        while start+j<len(numbers) and cur_sum<bad_number:
            cur_sum+=numbers[start+j]
            j+=1
        if cur_sum==bad_number and j>1:
            res = [numbers[x] for x in range(start,start+j)]
            print(res)
            print(min(res)+max(res))
