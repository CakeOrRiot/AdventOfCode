class Node:
    def __init__(self, data):
        self.data = data
        self.next = None

    def __repr__(self):
        return str(self.data)

    def __str__(self):
        return self.__repr__()

    def printList(self,cnt):
        its = 0
        cur = self
        while its<cnt:
            print(f'{cur}->',end=' ')
            cur=cur.next
            its+=1
        print()

cups = list(map(int,list('614752839')))
# cups = list(map(int,list('389125467')))
val_to_node = dict()
head = Node(cups[0])
val_to_node[cups[0]] = head
cur = head
for cup in cups[1:]:
    nd = Node(cup)
    val_to_node[cup]=nd
    cur.next = nd
    cur = nd

max_val = max(cups)

for x in range(max_val+1,int(1e6)+1):
    nd = Node(x)
    val_to_node[x]=nd
    cur.next = nd
    cur = nd
max_val = int(1e6)

cur.next=head
cur = head

# print(cups)
# n = len(cups)
# # print(n)
moves = 10000000
for move in range(moves):
    removed = [cur.next,cur.next.next,cur.next.next.next]
    cur_cup = cur.data
    cur.next = cur.next.next.next.next
    
    dest = cur_cup-1
    if dest<=0:
        dest = max_val
    while dest in map(lambda x: x.data,removed):
        dest-=1
        if dest<=0:
            dest = max_val

    dest_node = val_to_node[dest]
    tmp = dest_node.next
    dest_node.next = removed[0]
    removed[-1].next = tmp
    
    cur = cur.next    
cur = head

while True:
    if cur.data == 1:
        print(cur.next.data*cur.next.next.data)
        break
    cur = cur.next

        

    