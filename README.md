# Bus Route Simulation

## Task Description

Write a program that simulates a bus route system.

The bus travels through predefined bus stops. At each stop, passengers may leave the bus and new passengers may board. Some passengers have **priority status** (elderly, disabled, pregnant, etc.), meaning they must always board before regular passengers.

---

## Requirements

1. Passengers at each bus stop wait in a queue (`Queue` or `LinkedList`).

2. Each bus stop can contain a maximum of **10 passengers**.

3. Each passenger has the following information:
    - `name`
    - `isPriority` (`boolean`)

4. The bus has a maximum capacity of **5 passengers**.

5. The bus visits the stops in the following order:

   ```
   Stop 1 → Stop 2 → Stop 3
   ```

   At every stop:

    - Randomly **1–2 passengers** leave the bus.
    - Waiting passengers board the bus.
    - If there are priority passengers waiting, they always board before regular passengers.
    - The simulation result should be different on every execution.

6. After visiting all **3 bus stops**, display:

    - Final passengers inside the bus.
    - Remaining passengers at each bus stop.

7. Every execution may produce a different result because:

    - Different passengers may leave the bus.
    - Different passengers may board the bus.

---

## Collections Used

Only the collection types covered during the course should be used.

- `List`
- `Queue`
- `Deque`
- `LinkedList`
- `PriorityQueue`
- `Stack`

Choose the most appropriate collection for each part of the implementation while keeping the solution efficient.

---

## Expected Output

### Example 1

```text
🚌 Stop 1 reached.
Aysel(PRIORITY) boarded the bus.
Rauf boarded the bus.
Ali boarded the bus.

Bus now: [Aysel(PRIORITY), Rauf, Ali]
Stop 1 remaining: []

🚌 Stop 2 reached.
Ali left the bus.
Rauf left the bus.
Nigar(PRIORITY) boarded the bus.
Samir(PRIORITY) boarded the bus.
Murad boarded the bus.

Bus now: [Aysel(PRIORITY), Nigar(PRIORITY), Samir(PRIORITY), Murad]
Stop 2 remaining: []

🚌 Stop 3 reached.
Murad left the bus.
Sevinc(PRIORITY) boarded the bus.
Leyla boarded the bus.

Bus now: [Aysel(PRIORITY), Nigar(PRIORITY), Samir(PRIORITY), Sevinc(PRIORITY), Leyla]
Stop 3 remaining: [Tural]

✅ Simulation finished.

Final passengers in bus:
[Aysel(PRIORITY), Nigar(PRIORITY), Samir(PRIORITY), Sevinc(PRIORITY), Leyla]
```

---

### Example 2

```text
🚌 Stop 1 reached.
Aysel(PRIORITY) boarded the bus.
Rauf boarded the bus.
Ali boarded the bus.

Bus now: [Aysel(PRIORITY), Rauf, Ali]
Stop 1 remaining: []

🚌 Stop 2 reached.
Ali left the bus.
Nigar(PRIORITY) boarded the bus.
Samir(PRIORITY) boarded the bus.
Murad boarded the bus.

Bus now: [Aysel(PRIORITY), Rauf, Nigar(PRIORITY), Samir(PRIORITY), Murad]
Stop 2 remaining: []

🚌 Stop 3 reached.
Murad left the bus.
Sevinc(PRIORITY) boarded the bus.

Bus now: [Aysel(PRIORITY), Rauf, Nigar(PRIORITY), Samir(PRIORITY), Sevinc(PRIORITY)]
Stop 3 remaining: [Leyla, Tural]

✅ Simulation finished.

Final passengers in bus:
[Aysel(PRIORITY), Rauf, Nigar(PRIORITY), Samir(PRIORITY), Sevinc(PRIORITY)]
```

---

## Terminal Output

Example execution of the program:

![Terminal Output](image.png)