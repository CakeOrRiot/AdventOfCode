with open('../input.txt','rt') as f:
    requered = {'byr','iyr','eyr','hgt','hcl','ecl','pid'}
    valid = 0
    lines = f.readlines()
    i=0
    while i<len(lines):
        ok = True
        actual = set()
        while i<len(lines) and lines[i]!='\n':
            line = lines[i]
            tokens = line.split()
            for token in tokens:
                actual.add(token.split(':')[0])
            i+=1
        for x in requered:
            if x not in actual:
                ok = False
                break
            
        if ok:
            valid+=1
        i+=1
    print(valid)


