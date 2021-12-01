door_pb = 17115212
card_pb = 3667832


def step(num,sn):
    num*=sn
    num%=20201227
    return num

door_loop_size = 1
num = 1
while True:
    num = step(num,7)
    if num==door_pb:
        break
    door_loop_size+=1
num = 1
print(door_loop_size)
for lp in range(door_loop_size):
    num = step(num,card_pb)

print(num)