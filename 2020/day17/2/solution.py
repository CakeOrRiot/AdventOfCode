from collections import defaultdict


def main():
    with open('../input.txt','rt') as f:
        content = list(map(lambda line: list(line.rstrip()), f.readlines()))
        active_cells = set()
        for i in range(len(content)):
            for j in range(len(content[0])):
                if content[i][j]=='#':
                    active_cells.add((0,i,j,0))
        constraints = [[-1,1],[-1,len(content)+1],[-1,len(content)+1],[-1,1]]
        for _ in range(6):
            to_update=[]
            for z in range(constraints[0][0],constraints[0][1]+1):
                for x in range(constraints[1][0],constraints[1][1]+1):
                    for y in range(constraints[2][0],constraints[2][1]+1):
                        for w in range(constraints[3][0],constraints[3][1]+1):
                            active_cnt = 0
                            for xs in range(-1,2):
                                for ys in range(-1,2):
                                    for zs in range(-1,2):
                                        for ws in range(-1,2):
                                            if xs==0 and ys==0 and zs ==0 and ws==0:
                                                continue
                                            p=(z+zs,x+xs,y+ys,w+ws)
                                            if p in active_cells:
                                                active_cnt+=1
                            if (z,x,y,w) in active_cells and not 2<=active_cnt<=3:
                                to_update.append((z,x,y,w))
                            if (z,x,y,w) not in active_cells and active_cnt==3:
                                to_update.append((z,x,y,w))
                # return
            # print(to_update)
            for z,x,y,w in to_update:
                if (z,x,y,w) in active_cells:
                    # print('REMOVING')
                    active_cells.remove((z,x,y,w))
                else:
                    active_cells.add((z,x,y,w))
            for constr in constraints:
                constr[0]-=1
                constr[1]+=1
            # print(active_cells)
        
        print(len(active_cells))
                        

if __name__=='__main__':
    main()
