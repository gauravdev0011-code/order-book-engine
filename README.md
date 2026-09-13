# C++20 Low-Latency Order Book & Matching Engine

A deterministic in-memory limit-order-book matching engine implementing **price-time priority**, partial fills, cancellation, and reproducible performance benchmarking.

## Resume-Aligned Capabilities

- C++20 / CMake build
- Price-time-priority matching for BUY and SELL limit orders
- Partial and full fills
- Deterministic FIFO execution within each price level
- O(1)-average order-ID lookup for cancellation
- Unit tests for matching, partial fills, cancellation, and price execution
- Configurable multi-million-order benchmark reporting throughput and p50/p95/p99 latency

## Data Structures

The engine deliberately uses standard containers with clear complexity tradeoffs:

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

The benchmark target uses `-O3 -march=native` so benchmark results reflect an optimized local build.

## Test

```bash
ctest --test-dir build --output-on-failure
```

## Benchmark

The default benchmark processes **5,000,000 simulated orders** with a fixed random seed and a pre-warmed book containing both bid and ask liquidity:

```bash
./build/order_book_benchmark
```

To choose another workload size:

```bash
./build/order_book_benchmark 1000000
```

The benchmark reports:

- total runtime
- orders/second
- p50 per-order processing latency
- p95 per-order processing latency
- p99 per-order processing latency
- trade count
- final resting-order count

The resume reports a 5M-order run at 2.0M+ orders/sec with sub-0.8 µs p95 processing latency. Those figures are **environment-specific measurements**, not guaranteed limits. Re-run the benchmark on the target machine and record the CPU, compiler, CMake build type, and operating system alongside any published result.

## Complexity

For each incoming order, matching work is proportional to the number of price levels and resting orders actually traversed. Resting-order cancellation uses the hash index for average O(1) order lookup, followed by O(1) list erasure once the price level is located.

## Engineering Focus

This project is intended to demonstrate data-structure selection, deterministic matching semantics, memory ownership, iterator stability, and measurement of latency/throughput under a sustained workload.
