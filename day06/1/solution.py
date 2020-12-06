with open('../input.txt','rt') as f:
    res = 0
    cur = set()
    lines = f.readlines()
    for i in range(len(lines)):
        if lines[i]=='' or lines[i]=='\n':
            print(cur)
            res+=len(cur)
            cur=set()
        else:
            for ch in lines[i]:
                if ch!='\n':
                    cur.add(ch)

    res+=len(cur)
    print(res)
        