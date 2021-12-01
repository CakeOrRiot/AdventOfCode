with open('../input.txt','rt') as f:
    numbers = [int(x) for x in f.readlines()]
    numbers.append(0)
    numbers.append(max(numbers)+3)
    numbers.sort()
    diffs = {1:0,2:0,3:0}
    for i in range(1,len(numbers)):
        diff = numbers[i]-numbers[i-1]
        print(diff) 
        diffs[diff]+=1

    print(diffs[1]*diffs[3])
