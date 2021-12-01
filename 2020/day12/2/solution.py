from math import cos,sin,pi
with open('../input.txt','rt') as f:
    wayp_coord = [10,1]
    ship_coord = [0,0]
    def rotate(x,y,angle,direction):
        c=cos(pi*angle/180)
        s=sin(pi*angle/180)
        if angle==90:
            c=0
            s=1
        elif angle==180:
            c=-1
            s=0
        elif angle==270:
            c=0
            s=-1
        else:
            raise Exception
        xx = x*c-direction*y*s
        yy = direction*x*s+y*c
        return [xx,yy]


    for line in f:
        direction, val = line[0],int(line.rstrip()[1:])
        if direction == 'N':
            wayp_coord[1]+=val
        if direction == 'S':
            wayp_coord[1]-=val
        if direction == 'E':
            wayp_coord[0]+=val
        if direction=='W':
            wayp_coord[0]-=val
        if direction == 'L':
            wayp_coord = rotate(wayp_coord[0],wayp_coord[1],val,1)
        if direction=='R':
            wayp_coord = rotate(wayp_coord[0],wayp_coord[1],val,-1)
        if direction=='F':
            ship_coord[0]+=wayp_coord[0]*val
            ship_coord[1]+=wayp_coord[1]*val
    
    print(abs(ship_coord[0])+abs(ship_coord[1]))