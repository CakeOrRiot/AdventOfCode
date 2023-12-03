import re

matr = []
with open("input.txt") as f:
    for i, line in enumerate(f.readlines()):
        matr.append(line.strip())
res = 0
for i, line in enumerate(matr):
    iter_ = re.finditer("\d+", line)

    for x in iter_:
        adj = False
        num = int(x.group(0))
        border_width_1, border_width_2 = x.span()
        border_width_1 = max(0, border_width_1 - 1)
        border_width_2 = min(len(line), border_width_2 + 1)

        border_height_1 = max(0, i - 1)
        border_height_2 = min(len(matr), i + 2)
        for idx_w in range(border_width_1, border_width_2):
            for idx_h in range(border_height_1, border_height_2):
                if matr[idx_h][idx_w] not in [
                    ".",
                    "1",
                    "2",
                    "3",
                    "4",
                    "5",
                    "6",
                    "7",
                    "8",
                    "9",
                    "0",
                ]:
                    adj = True
                    break
        if adj:
            print("MATCH ", num)
            res += num

print(res)
