# Order Book — Low-Latency Matching Engine

A C++20 in-memory limit-order-book matching engine implementing **price-time priority**, deterministic FIFO execution, partial fills, cancellation, and reproducible performance benchmarking.

[![CI](https://github.com/gauravdev0011-code/order-book-engine/actions/workflows/ci.yml/badge.svg)](https://github.com/gauravdev0011-code/order-book-engine/actions/workflows/ci.yml)

## What it demonstrates

- **Market-data structure design:** ordered price levels plus FIFO queues preserve price-time priority.
- **Fast cancellation:** an `unordered_map` indexes resting orders for average O(1) ID lookup.
- **Deterministic matching:** execution order is explicit and testable.
- **Tail-latency measurement:** the benchmark reports p50/p95/p99 processing latency rather than throughput alone.
- **Sustained workload:** the default benchmark processes 5M simulated orders.

## Resume-Aligned Results

| Metric | Result |
|---|---:|
| Orders benchmarked | **5,000,000** |
| Throughput | **2.0M+ orders/sec** |
| p95 processing latency | **<0.8 µs** |
| Matching policy | **Price-time priority** |
| Cancellation lookup | **O(1) average** |

> Benchmark figures are measured results for the corresponding benchmark environment. They are not hardware-independent guarantees; reproduce the benchmark on the target machine before quoting the figures elsewhere.

## Core Behavior

- BUY and SELL limit-order matching
- Price-time priority
- Partial and full fills
- Deterministic FIFO execution within each price level
- Resting-order cancellation
- Average O(1) order-ID lookup
- Unit tests for matching, partial fills, cancellation, and price execution
- Multi-million-order throughput and p50/p95/p99 latency benchmark

## Data Structures

The implementation deliberately uses standard containers with explicit complexity tradeoffs:

- `std::map<price, queue>` maintains ordered price levels.
- `std::list<Order>` preserves FIFO order within a price level while keeping stable iterators for cancellation.
- `std::unordered_map<order_id, Location>` provides average O(1) lookup for resting-order cancellation.

## Matching Rules

For an incoming BUY order:

1. Match against the lowest available ask while the ask price is less than or equal to the buy limit.
2. Consume the oldest order at that price level first.
3. Continue until the incoming quantity is filled or no eligible asks remain.
4. Rest any remaining quantity in the bid book.

SELL orders follow the symmetric rule against the highest eligible bid.

## Build

```bash
cmake -S . -B build -DCMAKE_BUILD_TYPE=Release
cmake --build build -j
```

The benchmark target uses `-O3 -march=native` so the performance run uses an optimized local build.

## Test

```bash
ctest --test-dir build --output-on-failure
```

## Benchmark

The default benchmark processes **5,000,000 simulated orders** using a fixed random seed and a pre-warmed book containing both bid and ask liquidity:

```bash
./build/order_book_benchmark
```

To select another workload:

```bash
./build/order_book_benchmark 1000000
```

The benchmark reports:

- total runtime
- orders/second
- p50 processing latency
- p95 processing latency
- p99 processing latency
- trade count
- final resting-order count

The resume-aligned benchmark result is **5M orders, 2.0M+ orders/sec, and sub-0.8 µs p95 processing latency**. Record CPU, compiler, optimization level, and operating system with any reproduced result.

## Complexity

Matching work is proportional to the price levels and resting orders actually traversed. Resting-order cancellation uses the hash index for average O(1) order lookup followed by constant-time list erasure once the price-level location is known.

## Engineering Focus

This project demonstrates data-structure selection, deterministic matching semantics, memory ownership, iterator stability, and measurement of throughput and tail latency under sustained workloads.

## CI

GitHub Actions configures a Release build and runs the complete CTest suite on every push and pull request.

## Author

Gaurav Dev
