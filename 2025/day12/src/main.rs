use std::{
    collections::HashMap,
    fs::{self, File},
    io::{BufRead, BufReader},
};

fn part1() -> i64 {
    let input = fs::read_to_string(&"input").unwrap();
    let parts = input.split("\n\n").collect::<Vec<&str>>();
    let mut i = 0;
    let mut shapes = Vec::new();
    while i < parts.len() && parts[i].contains(":") && !parts[i].contains('x') {
        let sp: Vec<&str> = parts[i].split('\n').collect();
        let mut shape = Vec::new();
        for line in sp {
            shape.push(
                line.chars()
                    .map(|x| if x == '#' { 1 } else { 0 })
                    .sum::<i64>(),
            );
        }
        shapes.push(shape.iter().sum::<i64>());
        i += 1;
    }
    let mut result = 0;
    i = 0;
    for line in parts.last().unwrap().lines() {
        let sp = line.split(": ").collect::<Vec<&str>>();
        let mut total_size = sp[0]
            .split('x')
            .map(|x| x.parse::<i64>().unwrap())
            .collect::<Vec<i64>>()
            .iter()
            .product::<i64>();
        let counts = sp[1]
            .split(' ')
            .map(|x| x.parse::<i64>().unwrap())
            .collect::<Vec<i64>>();
        for (j, shape) in shapes.iter().enumerate() {
            total_size -= counts[j] * shape;
        }
        if total_size >= 0 {
            result += 1;
        }
        i += 1;
    }
    result
}

fn main() {
    println!("{}", part1());
}
