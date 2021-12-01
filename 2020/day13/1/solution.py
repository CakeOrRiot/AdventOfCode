with open('../input.txt','rt') as f:
    starting = int(f.readline())
    line = f.readline().rstrip()
    ids =list(map(int,filter(lambda x: x!='x',line.split(','))))
    cur = starting
    while True:
        ok = False
        for bus_id in ids:
            if cur%bus_id==0:
                print((cur-starting)*bus_id)
                ok = True
                break
        if ok:
            break
        cur+=1