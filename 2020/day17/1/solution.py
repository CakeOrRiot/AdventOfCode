from collections import defaultdict


def main():
    with open('../input.txt','rt') as f:
        content = list(map(lambda line: list(line.rstrip()), f.readlines()))
        active_cells = set()
        for i in range(len(content)):
            for j in range(len(content[0])):
                if content[i][j]=='#':
                    active_cells.add((0,i,j))
        constraints = [[-1,1],[-1,len(content)+1],[-1,len(content)+1]]
        for _ in range(6):
            to_update=[]
            for z in range(constraints[0][0],constraints[0][1]+1):
                for x in range(constraints[1][0],constraints[1][1]+1):
                    for y in range(constraints[2][0],constraints[2][1]+1):
                        active_cnt = 0
                        for xs in range(-1,2):
                            for ys in range(-1,2):
                                for zs in range(-1,2):
                                    if xs==0 and ys==0 and zs ==0:
                                        continue
                                    p=(z+zs,x+xs,y+ys)
                                    if p in active_cells:
                                        active_cnt+=1
                        if (z,x,y) in active_cells and not 2<=active_cnt<=3:
                            to_update.append((z,x,y))
                        if (z,x,y) not in active_cells and active_cnt==3:
                            to_update.append((z,x,y))
            for z,x,y in to_update:
                if (z,x,y) in active_cells:
                    active_cells.remove((z,x,y))
                else:
                    active_cells.add((z,x,y))
            for constr in constraints:
                constr[0]-=1
                constr[1]+=1
        
        print(len(active_cells))
                        

if __name__=='__main__':
    main()
