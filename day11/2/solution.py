with open('../input.txt','rt') as f:
    grid = []
    for line in f:
        grid.append(list(line.rstrip()))

    def is_inside(g,i,j):
        return i>=0 and i<len(g) and j>=0 and j<len(g[0])

    def scan_direction(g,i,j,shift_i,shift_j):
        while is_inside(g,i,j):
            if g[i][j]=='L' or g[i][j]=='#':
                return i,j
            i+=shift_i
            j+=shift_j
        return None
    def count_neighbours(g,i,j):
        cnt_occ = 0
        cnt_empty = 0
        for shift_i in range(-1,2):
            for shift_j in range(-1,2):
                if shift_i==0 and shift_j==0:
                    continue
                
                if not is_inside(g,i+shift_i,j+shift_j):
                    continue
                pos = scan_direction(g,i+shift_i,j+shift_j,shift_i,shift_j)
                if pos is None:
                    continue
                x,y = pos
                if g[x][y]=='L':
                    cnt_empty+=1
                if g[x][y]=='#':
                    cnt_occ+=1

        return cnt_occ,cnt_empty

    def step(g):
        to_change=[]
        for i in range(len(g)):
            for j in range(len(g[0])):
                if g[i][j]=='.':
                    continue
                occ,empty=count_neighbours(g,i,j)
                if g[i][j]=='L' and occ==0:
                    to_change.append((i,j))
                if g[i][j]=='#' and occ>=5:
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
        # for line in grid:
        #     print(''.join(line))
        # print()
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

