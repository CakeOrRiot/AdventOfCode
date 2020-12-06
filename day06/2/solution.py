with open('../input.txt','rt') as f:
    res = 0
    cur = set()
    lines = f.readlines()
    is_start = True
    for i in range(len(lines)):
        if lines[i]=='' or lines[i]=='\n':
            is_start=True
            res+=len(cur)
            cur=set()
        else:
            cur_cur = set()
            for ch in lines[i]:
                if ch!='\n':
                    cur_cur.add(ch)
            if not is_start:
                cur= cur.intersection(cur_cur)
            else:
                cur=cur_cur
            is_start=False
    res+=len(cur)
    print(res)
        