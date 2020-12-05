with open('../input.txt','rt') as f:
    ids = set()
    for line in f:
        row_info = line[:7]
        column_info=line[7:]
        l=0
        r=127
        for x in row_info:
            if x =='F':
                r=(l+r)//2
            else:
                l=int((l+r)/2+0.5)
        row = l
        l=0
        r=7
        for x in column_info:
            if x =='L':
                r=(l+r)//2
            else:
                l=int((l+r)/2+0.5)
        column = r
        seat_id = row*8+column
        ids.add(seat_id)
    l = sorted(list(ids))
    for i,x in enumerate(l):
        if l[i]+1!=l[i+1]:
            print(l[i]+1)
            