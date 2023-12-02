with open("input.txt") as f:
    limits = {"red": 12, "green": 13, "blue": 14}
    res = 0
    for line in f:
        game_id, line = line.split(":")
        game_id = int(game_id.split(" ")[1])
        groups = line.split(";")
        is_valid = True
        for g in groups:
            takeouts = g.split(",")
            for cubes in takeouts:
                number, color = cubes.strip().split(" ")
                number = int(number)
                if limits[color] < number:
                    is_valid = False
        if is_valid:
            res += game_id
    print(res)
