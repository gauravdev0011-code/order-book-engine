#include "orderbook/order_book.hpp"

#include <algorithm>
#include <chrono>
#include <cstdint>
#include <iomanip>
#include <iostream>
#include <random>
#include <string>
#include <vector>

static double percentile(std::vector<std::uint64_t>& samples, double p) {
    const std::size_t index = static_cast<std::size_t>(p * (samples.size() - 1));
    std::nth_element(samples.begin(), samples.begin() + index, samples.end());
    return static_cast<double>(samples[index]);
}

int main(int argc, char** argv) {
    const std::size_t n = argc > 1 ? std::stoull(argv[1]) : 5'000'000;

    OrderBook book;
    std::vector<std::uint64_t> latency_ns;
    latency_ns.reserve(n);

    std::mt19937_64 rng(42);
    std::uniform_int_distribution<int> price(9'950, 10'050);
    std::uniform_int_distribution<int> quantity(1, 10);
    std::bernoulli_distribution side(0.5);

    // Pre-warm both sides so the benchmark exercises matching and resting paths.
    for (std::uint64_t id = 1; id <= 2'000; ++id) {
        book.process({
            id,
            10'000 + static_cast<int>(id % 21) - 10,
            5,
            id % 2 ? Side::Buy : Side::Sell
        });
    }

    const auto start = std::chrono::steady_clock::now();
    std::uint64_t trades = 0;

    for (std::uint64_t i = 0; i < n; ++i) {
        const Order order{
            2'001 + i,
            price(rng),
            static_cast<std::uint32_t>(quantity(rng)),
            side(rng) ? Side::Buy : Side::Sell
        };

        const auto t0 = std::chrono::steady_clock::now();
        trades += book.process(order).size();
        const auto t1 = std::chrono::steady_clock::now();

        latency_ns.push_back(
            static_cast<std::uint64_t>(
                std::chrono::duration_cast<std::chrono::nanoseconds>(t1 - t0).count()
            )
        );
    }

    const auto end = std::chrono::steady_clock::now();
    const double elapsed_s = std::chrono::duration<double>(end - start).count();

    std::cout << std::fixed << std::setprecision(2);
    std::cout << "orders=" << n << '\n';
    std::cout << "elapsed_s=" << elapsed_s << '\n';
    std::cout << "throughput_orders_per_sec=" << (static_cast<double>(n) / elapsed_s) << '\n';
    std::cout << "p50_latency_ns=" << percentile(latency_ns, 0.50) << '\n';
    std::cout << "p95_latency_ns=" << percentile(latency_ns, 0.95) << '\n';
    std::cout << "p99_latency_ns=" << percentile(latency_ns, 0.99) << '\n';
    std::cout << "trades=" << trades << '\n';
    std::cout << "resting_orders=" << book.order_count() << '\n';
}
