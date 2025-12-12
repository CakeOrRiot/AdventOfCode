use std::{
    collections::HashMap,
    fs::File,
    io::{BufRead, BufReader},
};

fn part1() -> i64 {
    let file = File::open("input").unwrap();
    let reader = BufReader::new(file);

    let mut graph: HashMap<String, Vec<String>> = HashMap::new();

    for line in reader.lines() {
        let line = line.unwrap();
        let tokens: Vec<String> = line.split(": ").map(|x| x.to_string()).collect();
        let from = tokens[0].to_string();
        let to_list: Vec<String> = tokens[1].split(' ').map(|x| x.to_string()).collect();
        graph.insert(from, to_list);
    }

    count_paths(&graph, &"you".to_string(), &"out".to_string())
}

fn count_paths(graph: &HashMap<String, Vec<String>>, node: &String, finish: &String) -> i64 {
    let mut memory: HashMap<String, i64> = HashMap::new();
    count_paths_help(graph, node, finish, &mut memory)
}

fn count_paths_help(
    graph: &HashMap<String, Vec<String>>,
    node: &String,
    finish: &String,
    memory: &mut HashMap<String, i64>,
) -> i64 {
    if node == finish {
        return 1;
    }
    if memory.contains_key(node) {
        return memory[node];
    }
    let mut total_paths = 0;
    for neighbor in graph.get(node).unwrap_or(&vec![]) {
        total_paths += count_paths_help(graph, neighbor, finish, memory);
    }
    memory.insert(node.to_string(), total_paths);
    total_paths
}

fn part2() -> i64 {
    let file = File::open("input").unwrap();
    let reader = BufReader::new(file);

    let mut graph: HashMap<String, Vec<String>> = HashMap::new();
    for line in reader.lines() {
        let line = line.unwrap();
        let tokens: Vec<String> = line.split(": ").map(|x| x.to_string()).collect();
        let from = tokens[0].to_string();
        let to_list: Vec<String> = tokens[1].split(' ').map(|x| x.to_string()).collect();
        graph.insert(from, to_list);
    }
    let svr_fft = count_paths(&graph, &"svr".to_string(), &"fft".to_string());
    let fft_dac = count_paths(&graph, &"fft".to_string(), &"dac".to_string());
    let dac_out = count_paths(&graph, &"dac".to_string(), &"out".to_string());

    return svr_fft * fft_dac * dac_out;
}

fn main() {
    println!("{}", part1());
    println!("{}", part2());
}
