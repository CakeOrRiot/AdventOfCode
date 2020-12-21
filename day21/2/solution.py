from collections import Counter
with open('../input.txt','rt') as f:
    allergens_to_ingredients = dict()
    all_ingrediesnts = Counter()
    for line in f:
        line = line.strip()
        ingredients,allergens = line.split(' (')
        allergens = allergens[len('contains '):].split(' ')
        allergens[-1]=allergens[-1][:-1]
        for i in range(len(allergens)):
            if allergens[i][-1]==',':
                allergens[i] = allergens[i][:-1]
        ingredients = ingredients.split(' ')
        for ingr in ingredients:
            all_ingrediesnts[ingr]+=1
        for allergen in allergens:
            # print(allergens_to_ingredients)
            if allergen in allergens_to_ingredients:
                allergens_to_ingredients[allergen] = allergens_to_ingredients[allergen].intersection(set(ingredients))
            else:
                allergens_to_ingredients[allergen] = set(ingredients)
    print(allergens_to_ingredients)
    print(all_ingrediesnts)
    while True:
        any_1 = False
        to_remove = []
        for allergen in allergens_to_ingredients:
            if len(allergens_to_ingredients[allergen])==1:
                ingr = list(allergens_to_ingredients[allergen])[0]
                
                for allergen1 in allergens_to_ingredients:
                    if allergen==allergen1:
                        continue
                    allergens_to_ingredients[allergen1].discard(ingr)

        for allergen in allergens_to_ingredients:
            if len(allergens_to_ingredients[allergen])!=1:
                any_1=True
        if not any_1:
            break
    tuples = sorted([(allergen,list(allergens_to_ingredients[allergen])[0]) for allergen in allergens_to_ingredients])
    print(tuples)
    for t in tuples:
        print(t[1],end=',')
    # print(allergens_to_ingredients)
    # safe_ingredients = set()
    # for ingredient in all_ingrediesnts:
    #     is_safe = True
    #     for allergen in allergens_to_ingredients:
    #         if ingredient in allergens_to_ingredients[allergen]:
    #             is_safe=False
    #             break
    #     if is_safe:
    #         safe_ingredients.add(ingredient)
    # print(safe_ingredients)
    # res = 0
    # for safe_ingr in safe_ingredients:
    #     res+=all_ingrediesnts[safe_ingr]

    # print(res)