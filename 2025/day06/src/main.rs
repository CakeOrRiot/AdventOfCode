use std::{
    fs::{self, File},
    io::{BufRead, BufReader},
};

fn part1() -> i64 {
    let file = File::open("input").unwrap();
    let reader = BufReader::new(file);
    let mut matr: Vec<Vec<i64>> = Vec::new();
    let mut operations: Vec<char> = Vec::new();
    for mb_line in reader.lines() {
        let line = mb_line.unwrap();
        let tokens: Vec<&str> = line.split(' ').filter(|x| !x.is_empty()).collect();
        if tokens[0].chars().nth(0).unwrap() == '*' || tokens[0].chars().nth(0).unwrap() == '+' {
            operations = tokens.iter().map(|x| x.chars().nth(0).unwrap()).collect();
        } else {
            let numbers = tokens.iter().map(|x| x.parse::<i64>().unwrap()).collect();
            matr.push(numbers);
        }
    }

    let mut result: i64 = 0;
    for i in 0..matr[0].len() {
        let mut column_result: i64 = matr[0][i];
        for j in 1..matr.len() {
            if operations[i] == '+' {
                column_result += matr[j][i];
            } else {
                column_result *= matr[j][i];
            }
        }
        result += column_result;
    }

    result
}

fn part2() -> i64 {
    let input = fs::read_to_string(&"input").unwrap();
    let lines = input.lines().collect::<Vec<&str>>();
    let horizontal_length = lines[0].len();
    let vertical_length = lines.len();
    let mut result: i64 = 0;
    let mut current_numbers = Vec::new();
    for column in 0..horizontal_length {
        let mut number: i64 = 0;
        let mut empty = true;
        for i in 0..vertical_length {
            let digit = lines[i]
                .chars()
                .nth(column)
                .unwrap_or('-')
                .to_digit(10)
                .unwrap_or(100) as i64;
            if digit == 100 {
                continue;
            }
            empty = false;
            number = number * 10 + digit;
        }
        if empty {
            let last_line = lines.last().unwrap();
            for i in (0..=column).rev() {
                let ch = last_line.chars().nth(i).unwrap();
                if ch == '*' {
                    result += current_numbers.iter().product::<i64>();
                    break;
                }
                if ch == '+' {
                    result += current_numbers.iter().sum::<i64>();
                    break;
                }
            }
            current_numbers.clear();
        } else {
            current_numbers.push(number);
        }
    }

    let last_line = lines.last().unwrap();
    for i in (0..horizontal_length).rev() {
        let ch = last_line.chars().nth(i).unwrap();
        if ch == '*' {
            result += current_numbers.iter().product::<i64>();
            break;
        }
        if ch == '+' {
            result += current_numbers.iter().sum::<i64>();
            break;
        }
    }
    current_numbers.clear();

    result
}

fn main() {
    println!("{}", part1());
    println!("{}", part2());
}
