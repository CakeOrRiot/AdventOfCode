def get_answ(tree_map,shift):
    bottom_shift = shift[1]
    rigth_shift=shift[0]
    cur_line = 0
    cur_shift = 0
    answ = 0
    while cur_line<len(tree_map):
        if tree_map[cur_line][cur_shift%len(tree_map[0])]=='#':
            answ+=1
        cur_line+=bottom_shift
        cur_shift+=rigth_shift
    return answ

with open('../input.txt','rt') as f:
    tree_map = list(map(lambda line: line.rstrip('\n'), f.readlines()))
    res = 1
    for shift in [(1,1),(3,1),(5,1),(7,1),(1,2)]:
        res *= get_answ(tree_map,shift)
    print(res)
