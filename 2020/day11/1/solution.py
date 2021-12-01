with open('../input.txt','rt') as f:
    grid = []
    for line in f:
        grid.append(list(line.rstrip()))
    print(grid)
    def count_neighbours(g,i,j):
        cnt_occ = 0
        cnt_empty = 0
        for shift_i in range(-1,2):
            for shift_j in range(-1,2):
                if shift_i==0 and shift_j==0:
                    continue
                if i+shift_i>=len(g) or i+shift_i<0:
                    continue
                if j+shift_j>=len(g[0]) or j+shift_j<0:
                    continue
                # print(i+shift_i,j+shift_j)
                if g[i+shift_i][j+shift_j]=='L':
                    cnt_empty+=1
                if g[i+shift_i][j+shift_j]=='#':
                    cnt_occ+=1
        return cnt_occ,cnt_empty

    def step(g):
        to_change=[]
        for i in range(len(g)):
            for j in range(len(g[0])):
                occ,empty=count_neighbours(g,i,j)
                if g[i][j]=='L' and occ==0:
                    to_change.append((i,j))
                if g[i][j]=='#' and occ>=4:
                    to_change.append((i,j))
        return to_change
    def opposite(val):
        if val =='#':
            return 'L'
        if val == 'L':
            return '#'
        raise Exception
    steps = 0
    while True:
        change = step(grid)
        if len(change)==0:
            break
        for i,j in change:
            grid[i][j] = opposite(grid[i][j])
        steps+=1
    cnt=0
    for line in grid:
        for x in line:
            if x=='#':
                cnt+=1
    print(cnt)

