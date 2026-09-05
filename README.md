# C++20 Order Book & Matching Engine

A deterministic in-memory limit-order-book matching engine implementing **price-time priority**, partial fills, cancellation, and reproducible performance benchmarking.

## Features

- C++20 / CMake build
- Price-time-priority matching
- BUY and SELL limit orders
- Partial and full fills
- Order cancellation
- Deterministic FIFO execution within each price level
- O(1)-average order-ID lookup for cancellation
- Reproducible 1M-order benchmark
- Unit tests covering matching and cancellation behavior

## Data Structures

- `std::map<price, queue>` maintains price levels in execution order.
- `std::list<Order>` preserves FIFO order within each price level while allowing stable iterators.
- `std::unordered_map<order_id, Location>` indexes resting orders for direct cancellation.

## Build

```bash
cmake -S . -B build -DCMAKE_BUILD_TYPE=Release
cmake --build build -j
```

## Test

```bash
ctest --test-dir build --output-on-failure
```

## Benchmark

Run 1,000,000 simulated orders:

```bash
./build/order_book_benchmark 1000000
```

The benchmark reports total runtime, orders/sec, p50/p95/p99 per-order latency, trade count, and final resting-order count. Results are machine-dependent; record benchmark results together with CPU, compiler, and build configuration before publishing them as performance claims.
