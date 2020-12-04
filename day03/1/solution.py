with open('../input.txt','rt') as f:
    cur_line = 0
    cur_shift = 0
    answ = 0
    tree_map = list(map(lambda line: line.rstrip('\n'), f.readlines()))
    for line in tree_map:
        if line[cur_shift%len(tree_map[0])]=='#':
            answ+=1
        cur_line+=1
        cur_shift+=3
    print(answ)
