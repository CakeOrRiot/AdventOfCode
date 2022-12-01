use std::fs;

const FILE_PATH: &str = "input.txt";

fn solve_1() -> i32 {
    let contents = fs::read_to_string(FILE_PATH).expect("Unable to read file");
    let split: Vec<&str> = contents.split('\n').collect();
    let mut cur_sum = 0;
    let mut max_sum = 0;
    for cur in &split[1..] {
        let x = *cur;
        if x.is_empty() {
            max_sum = std::cmp::max(max_sum, cur_sum);
            cur_sum = 0;
        } else {
            cur_sum += x.parse::<i32>().unwrap();
        }
    }

    return max_sum;
}

fn solve_2() -> i32 {
    let contents = fs::read_to_string(FILE_PATH).expect("Unable to read file");
    let split: Vec<&str> = contents.split('\n').collect();
    let mut cur_sum = 0;
    let mut max_sum = 0;
    let mut calories = Vec::new();
    for cur in &split[1..] {
        let x = *cur;
        if x.is_empty() {
            max_sum = std::cmp::max(max_sum, cur_sum);
            calories.push(cur_sum);
            cur_sum = 0;
        } else {
            cur_sum += x.parse::<i32>().unwrap();
        }
    }
    calories.sort();
    let n = calories.len();
    return calories[n - 1] + calories[n - 2] + calories[n - 3];
}

fn main() {
    print!("{}\n", solve_1());
    print!("{}\n", solve_2());
}
