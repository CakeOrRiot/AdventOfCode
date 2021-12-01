with open('../input.txt','rt') as f:
    numbers = list(map(int,f.readlines()))
    prev = set()
    for i in range(25):
        prev.add(numbers[i])
    for i in range(25,len(numbers)):
        ok = False
        for j in range(i-25,i):
            if numbers[i]-numbers[j] in prev:
                ok = True
                break
        if not ok:
            print(numbers[i])
        prev.remove(numbers[i-25])
        prev.add(numbers[i])

