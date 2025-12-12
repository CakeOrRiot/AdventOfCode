use std::{
    collections::VecDeque,
    fs::File,
    io::{BufRead, BufReader},
};

use z3::{Optimize, ast::Int};

fn part1() -> i64 {
    let file = File::open("input").unwrap();
    let reader = BufReader::new(file);
    let mut states = Vec::new();
    let mut operations = Vec::new();
    for line in reader.lines() {
        let line = line.unwrap();
        let mut tokens = line.split(' ').collect::<Vec<&str>>();
        tokens = tokens[0..tokens.len() - 1].to_vec();
        let state = tokens[0][1..tokens[0].len() - 1]
            .chars()
            .rev()
            .map(|c| if c == '#' { 1 } else { 0 })
            .fold(0, |acc, x| 2 * acc + x);
        states.push(state);
        let mut task_operations = Vec::new();
        for token in tokens.iter().skip(1) {
            let op: i32 = token[1..token.len() - 1]
                .split(',')
                .map(|num| 2_i32.pow(num.parse::<u32>().unwrap()))
                .sum();
            task_operations.push(op);
        }
        operations.push(task_operations);
    }
    let mut result = 0;
    for task in 0..operations.len() {
        let task_operations = &operations[task];
        let mut queue = VecDeque::new();
        queue.push_back((0, 0));
        let mut used = std::collections::HashSet::new();
        'outer: while !queue.is_empty() {
            let (state, len) = queue.pop_front().unwrap();
            for op in task_operations {
                let new_state = state ^ op;
                if used.contains(&new_state) {
                    continue;
                }
                queue.push_back((new_state, len + 1));
                if new_state == states[task] {
                    result += len + 1;
                    break 'outer;
                }
                used.insert(new_state);
            }
        }
    }

    result
}

fn part2() -> i64 {
    let file = File::open("input").unwrap();
    let reader = BufReader::new(file);
    let mut result = 0;
    for line in reader.lines() {
        let line = line.unwrap();
        let mut tokens = line.split(' ').collect::<Vec<&str>>();
        tokens = tokens[1..tokens.len()].to_vec();
        let ops_tokens = tokens.iter().take(tokens.len() - 1).collect::<Vec<_>>();
        let joltages = tokens
            .last()
            .map(|x| &x[1..x.len() - 1])
            .unwrap()
            .split(',')
            .map(|x| x.parse::<i32>().unwrap())
            .collect::<Vec<i32>>();

        let alphas = (0..ops_tokens.len())
            .map(|i| Int::fresh_const(format!("alpha_{}", i).as_str()))
            .collect::<Vec<_>>();
        let solver = Optimize::new();
        for alpha in &alphas {
            solver.assert(&alpha.ge(&Int::from_i64(0)));
        }
        for eq_idx in 0..joltages.len() {
            let mut value = Int::from_i64(0);
            for (i, op_token) in ops_tokens.iter().enumerate() {
                let ops = op_token[1..op_token.len() - 1]
                    .split(',')
                    .map(|s| s.parse::<usize>().unwrap())
                    .collect::<Vec<_>>();
                if ops.contains(&eq_idx) {
                    value = &value + &alphas[i];
                }
            }
            solver.assert(&value.eq(&Int::from_i64(joltages[eq_idx] as i64)));
        }
        let sum_alphas: Int = alphas.iter().sum();
        solver.minimize(&sum_alphas);
        if solver.check(&[]) == z3::SatResult::Sat {
            let model = solver.get_model().unwrap();
            let sum = alphas
                .iter()
                .map(|a| model.eval(a, false).unwrap().as_i64().unwrap())
                .sum::<i64>();
            result += sum;
        }
    }
    result
}

fn main() {
    println!("{}", part1());
    println!("{}", part2());
}
