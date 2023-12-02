with open("input.txt") as f:
    res = 0
    for line in f:
        game_id, line = line.split(":")
        game_id = int(game_id.split(" ")[1])
        groups = line.split(";")
        limits = {"red": 0, "green": 0, "blue": 0}
        for g in groups:
            takeouts = g.split(",")
            for cubes in takeouts:
                number, color = cubes.strip().split(" ")
                number = int(number)
                limits[color] = max(limits[color], number)
        mul = 1
        for _, v in limits.items():
            mul *= v
        res += mul
    print(res)
