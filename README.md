# Order Book Matching Engine

This project is a simple real-time matching engine built in Java using Spring Boot.
It simulates how buy and sell orders are matched in trading systems using price-time priority.

---

## What it does

* Accepts BUY and SELL orders via REST API
* Matches orders based on:

  * Best price first
  * Then earliest order (FIFO)
* Supports partial fills
* Streams executed trades in real time using WebSocket

---

## How matching works

* Buy orders match with the lowest available sell price
* Sell orders match with the highest available buy price
* If quantities don’t match exactly, the remaining part stays in the order book

The order book is stored in memory using:

* `TreeMap` → keeps prices sorted
* `Queue` → maintains time priority

---

## Example

1. Add a sell order:

```json
{ "price": 100, "quantity": 10, "side": "SELL" }
```

2. Add a buy order:

```json
{ "price": 100, "quantity": 5, "side": "BUY" }
```

3. Result:

* Trade executed for quantity 5
* Remaining sell order stays with quantity 5

---

## API

**POST /orders**

```json
{
  "price": 100,
  "quantity": 10,
  "side": "BUY"
}
```

Response:

```json
[
  {
    "tradeId": "...",
    "price": 100,
    "quantity": 5
  }
]
```

---

## WebSocket

* Endpoint: `/ws`
* Topic: `/topic/trades`

Each executed trade is pushed to connected clients.

---

## Tech stack

* Java
* Spring Boot
* WebSocket (STOMP)
* Maven

---

## Notes

* This is an in-memory engine (no database)
* Focus was on matching logic and real-time updates
* Designed to keep logic simple and easy to follow

---

## Possible improvements

* Order cancellation
* Persistent storage
* Handling concurrent requests
* Performance optimization under high load
