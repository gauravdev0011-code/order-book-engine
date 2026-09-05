cmake_minimum_required(VERSION 3.20)
project(order_book_engine LANGUAGES CXX)

set(CMAKE_CXX_STANDARD 20)
set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(CMAKE_CXX_EXTENSIONS OFF)

add_library(orderbook src/order_book.cpp)
target_include_directories(orderbook PUBLIC include)
target_compile_options(orderbook PRIVATE -Wall -Wextra -Wpedantic)

add_executable(order_book_tests tests/order_book_tests.cpp)
target_link_libraries(order_book_tests PRIVATE orderbook)

enable_testing()
add_test(NAME order_book_tests COMMAND order_book_tests)

add_executable(order_book_benchmark benchmarks/benchmark.cpp)
target_link_libraries(order_book_benchmark PRIVATE orderbook)
target_compile_options(order_book_benchmark PRIVATE -O3 -march=native)
