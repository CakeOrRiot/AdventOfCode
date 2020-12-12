with open('../input.txt','rt') as f:
    north = 0
    east = 0
    cur_dir='E'
    order = ['N','W','S','E']
    for line in f:
        direction, val = line[0],int(line.rstrip()[1:])
        if direction == 'N':
            north+=val
        if direction == 'S':
            north-=val
        if direction == 'E':
            east+=val
        if direction=='W':
            east-=val
        if direction == 'L':
            cur_dir=order[(order.index(cur_dir)+val//90)%4]
        if direction=='R':
            cur_dir=order[(order.index(cur_dir)-val//90)%4]
        if direction=='F':
            if cur_dir=='N':
                north+=val
            if cur_dir=='S':
                north-=val
            if cur_dir=='E':
                east+=val
            if cur_dir=='W':
                east-=val
    print(abs(north)+abs(east))