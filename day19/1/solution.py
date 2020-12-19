import re

class Rule:
    def __init__(self,val,left,right):
        self.val = val
        self.left = left
        self.right = right

    def isLeaf(self):
        return self.val is not None

def get_regexp(rules,rule_num):
    if isinstance(rules[rule_num],str):
        return rules[rule_num]
    res = []
    for rule in rules[rule_num]:
        sub_res = []
        for sub_rule in rule:
            sub_res.append( get_regexp(rules,sub_rule))
        # sub_res.append(')')
        res.append(''.join(sub_res))
    r='|'.join(res)
    return f'({r})'
    

with open('../input1.txt','rt') as f:
    lines = f.readlines()
    rules = dict()
    i=0
    for line in lines:
        if line =='\n':
            i+=1
            break
        line = line.rstrip()
        ind = line.index(':')
        rule_num = int(line[:ind])
        cur = 0
        rules[rule_num] = [[]]
        tokens = line[ind+1:].split(' ')
        for token in tokens[1:]:
            if str.isnumeric(token):
                
                rules[rule_num][cur].append(int(token))
            elif token=='|':
                rules[rule_num].append([])
                cur+=1
            else:
                rules[rule_num] = token[1:-1]
    
        i+=1
    r ='^'+get_regexp(rules,0)+'$'
    res = 0
    print(r)
    regexp =  re.compile(r)
    for j in range(i,len(lines)):
        if re.match(regexp, lines[j]):
            res+=1
    print(res)