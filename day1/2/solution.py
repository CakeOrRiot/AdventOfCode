with open('input.txt','rt') as f:
    lst = set(map(int,f.readlines()))
    for x in lst:
        for y in lst:
            if 2020-x-y in lst:
                print(x*y*(2020-x-y))