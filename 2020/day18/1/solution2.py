class Num():
    def __init__(self,val):
        self.val = val

    def __sub__(self,other):
        return Num(self.val*other.val)
    
    def __add__(self, other):
        return Num(self.val+other.val)

    def __repr__(self):
        return str(self.val)

    def __str__(self):
        return f"Num({self.val})"

with open('../input.txt','rt') as f:
    answ = Num(0)
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
                # print(item[j:])
                t = item[j:]
                if t=='+':
                    tokens.append('+')
                elif t=='*':
                    tokens.append('-')
                else:
                    tokens.append(str(Num(int(item[j:]))))
            else:
                tokens.append(str(Num(int(item[j:ind]))))
                for k in range(ind,len(item)):
                    tokens.append(item[k])
        
        expr =  ''.join(tokens)
        res=eval(expr)
        print(expr,res,sep='\n',end='\n\n')
        answ = answ + res
    print(answ)

        # break