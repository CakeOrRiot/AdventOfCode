import string
from collections import deque
def apply(expr):
    x,op,y = expr
    op_res = 0
    if op=='+':
        op_res=str(int(x)+int(y))
    elif op=='*':
        op_res = str(int(x)*int(y))
    else:
        raise Exception
    return op_res


with open('../input.txt','rt') as f:
    ops = {'+','*'}
    answ = 0
    for line in f:
        line = line.rstrip()
        tokens = []
        spl = line.split(' ')
        for i in range(len(spl)):
            item=spl[i]
            j=0
            while item[j]=='(':
                tokens.append('(')
                j+=1
            ind = item.find(')')
            if ind ==-1:
                tokens.append(item[j:])
            else:
                tokens.append(item[j:ind])
                for k in range(ind,len(item)):
                    tokens.append(item[k])
        if len(tokens)>190:
            raise Exception("TOO LONG")
        i=0
        stack = ['-1' for _ in range(200)]
        top = -1
        cur_val=0
        while i<len(tokens):
            stack[top+1]=tokens[i]
            top+=1
            while stack[top]==')':
                if top<2:
                    raise Exception(") and top<2")
                expr = []
                top-=1
                while stack[top]!='(':
                    expr.append(stack[top])
                    top-=1
                if len(expr)==3:
                    stack[top+1]=apply(expr)
                    top+=1
                    break
                elif len(expr)==1:
                    stack[top]=stack[top+1]
                else:
                    raise Exception("len expr !=3 !=1")
        
            while top>1 and str.isnumeric(stack[top]) and stack[top-1] in ops:
                expr = [stack[top-2],stack[top-1],stack[top]]
                top-=2
                stack[top]=apply(expr)
                
            i+=1
        cur = 0
        while cur<top:
            stack[cur+2] = apply([stack[cur],stack[cur+1],stack[cur+2]])
            cur+=2
        stack[0]=stack[cur]
        add = int(stack[0].strip('()'))
        print(tokens,add)
        answ+=add
    print(answ)


        
    