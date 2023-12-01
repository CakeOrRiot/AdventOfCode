import re

res = 0
with open("input.txt") as f:
    for line in f:
        print(line)
        match1 = re.match("^.*?(\d).*$", line)
        match2 = re.match("^.*(\d).*$", line)
        res += int(match1[1] + match2[1])
print(res)
