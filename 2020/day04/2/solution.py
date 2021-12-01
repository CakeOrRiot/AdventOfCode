import re

with open('../input.txt','rt') as f:
    requered = {'byr','iyr','eyr','hgt','hcl','ecl','pid'}
    valid = 0
    lines = f.readlines()
    i=0
    while i<len(lines):
        ok = True
        actual = set()
        while i<len(lines) and lines[i]!='\n':
            # print(lines[i])
            line = lines[i]
            tokens = line.split()
            for token in tokens:
                field = token.split(':')[0]
                val = token.split(':')[1]
                if field=='byr':
                    val = int(val)
                    if 1920<=val<=2002:
                        actual.add(field)
                if field == 'iyr':
                    val = int(val)
                    if 2010<=val<=2020:
                        actual.add(field)
                if field == 'eyr':
                    val = int(val)
                    if 2020<=val<=2030:
                        actual.add(field)
                if field=='hcl':
                    if len(val)==7 and val[0]=='#' and re.match(r'#[0-9a-f]*',val) is not None:
                        actual.add(field)
                if field == 'hgt':
                    if val[-2:] == 'cm' and val[:len(val)-2].isnumeric():
                        val = int(val[:len(val)-2])
                        if 150<=val <=193:
                            actual.add(field)
                    elif val[-2:]=='in' and val[:len(val)-2].isnumeric():
                        val = int(val[:len(val)-2])
                        if 59<=val <=76:
                            actual.add(field)
                if field =='ecl':
                    if val in ['amb', 'blu' ,'brn', 'gry', 'grn', 'hzl', 'oth']:
                        actual.add(field)
                if field=='pid':
                    if val.isnumeric() and len(val)==9:
                        actual.add(field)
            i+=1
        for x in requered:
            if x not in actual:
                ok = False
                break
        if ok:
            valid+=1
        i+=1
    print(valid)


