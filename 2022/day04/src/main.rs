use std::fs::File;
use std::io::{prelude::*, BufReader};

const FILE_PATH: &str = "input.txt";

fn solve_1() -> u32 {
    let file = File::open(FILE_PATH).expect("Can't read file");
    let lines: Vec<Vec<Vec<i32>>> = BufReader::new(file)
        .lines()
        .map(|s| s.unwrap())
        .map(|s| {
            s.split(',')
                .map(|x| x.split('-').map(|y| y.parse::<i32>().unwrap()).collect())
                .collect()
        })
        .collect();
    fn is_inside(a: i32, b: i32, c: i32, d: i32) -> bool {
        return (a >= c && a <= d && b >= c && b <= d) || (c >= a && c <= b && d >= a && d <= b);
    }

    return lines
        .iter()
        .map(|line| is_inside(line[0][0], line[0][1], line[1][0], line[1][1]))
        .filter(|x| *x)
        .collect::<Vec<bool>>()
        .len() as u32;
}

fn solve_2() -> u32 {
    let file = File::open(FILE_PATH).expect("Can't read file");
    let lines: Vec<Vec<Vec<i32>>> = BufReader::new(file)
        .lines()
        .map(|s| s.unwrap())
        .map(|s| {
            s.split(',')
                .map(|x| x.split('-').map(|y| y.parse::<i32>().unwrap()).collect())
                .collect()
        })
        .collect();
    fn overlaps(a: i32, b: i32, c: i32, d: i32) -> bool {
        return (a <= c && b >= c) || (a >= c && a <= d);
    }

    return lines
        .iter()
        .map(|line| overlaps(line[0][0], line[0][1], line[1][0], line[1][1]))
        .filter(|x| *x)
        .collect::<Vec<bool>>()
        .len() as u32;
}

fn main() {
    println!("{}", solve_1());
    println!("{}", solve_2());
}
