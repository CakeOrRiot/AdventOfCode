use std::{
    fs::File,
    io::{BufRead, BufReader},
};

fn part1() -> i64 {
    let file = File::open("input").unwrap();
    let reader = BufReader::new(file);
    let mut grid = Vec::new();

    for line in reader.lines() {
        grid.push(line.unwrap());
    }

    let mut result: i64 = 0;
    let n = grid.len() as i32;
    let m = grid[0].len() as i32;
    for i in 0..n {
        for j in 0..m {
            if grid[i as usize].chars().nth(j as usize).unwrap() != '@' {
                continue;
            }
            let mut count = 0;
            for shift_i in -1..=1 {
                for shift_j in -1..=1 {
                    if i + shift_i < 0
                        || i + shift_i >= n
                        || j + shift_j < 0
                        || j + shift_j >= m
                        || (shift_i == 0 && shift_j == 0)
                    {
                        continue;
                    }
                    if grid[(i + shift_i) as usize]
                        .chars()
                        .nth((j + shift_j) as usize)
                        .unwrap()
                        == '@'
                    {
                        count += 1;
                    }
                }
            }
            if count < 4 {
                result += 1;
            }
        }
    }

    result
}

fn part2() -> i64 {
    let file = File::open("input").unwrap();
    let reader = BufReader::new(file);
    let mut grid: Vec<Vec<char>> = Vec::new();

    for line in reader.lines() {
        grid.push(line.unwrap().chars().collect());
    }

    let mut result: i64 = 0;
    let n = grid.len() as i32;
    let m = grid[0].len() as i32;

    loop {
        let mut positions_to_remove = Vec::new();

        for i in 0..n {
            for j in 0..m {
                if grid[i as usize][j as usize] != '@' {
                    continue;
                }
                let mut count = 0;
                for shift_i in -1..=1 {
                    for shift_j in -1..=1 {
                        if i + shift_i < 0
                            || i + shift_i >= n
                            || j + shift_j < 0
                            || j + shift_j >= m
                            || (shift_i == 0 && shift_j == 0)
                        {
                            continue;
                        }
                        if grid[(i + shift_i) as usize][(j + shift_j) as usize] == '@' {
                            count += 1;
                        }
                    }
                }
                if count < 4 {
                    result += 1;
                    positions_to_remove.push((i, j));
                }
            }
        }
        if positions_to_remove.is_empty() {
            break;
        }
        for (i, j) in positions_to_remove {
            grid[i as usize][j as usize] = '.';
        }
    }
    result
}

fn main() {
    println!("{}", part1());
    println!("{}", part2());
}
