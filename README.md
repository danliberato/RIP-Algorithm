# RIP-Algorithm
This program simulates a RIP (Routing Information Protocol).

CONCEPTS:
- There are 4 threads;
- Each thread represents a router (Node);
- All router are initialized with 3 entries on its Routing Table, each entrie represents a initial distance between the current router and its neighbors;
- We assume that distance 999 is infinte;

How it works?
- A node tell to imediate nodes to update its routing table;
- When a node receives a call, also receives the routing table from who sent the call;
- The node now starts to compare its routing table entries to the entries from the received routing table;
- If the current entrie has a destin router that is the same as who sent it, then calculates the total distance between previous and next hop;
- If the current entrie has an infinite distance or the total cost is less than actual, updates its current entrie whit these values;
- Last step is tell all its neighbors to update the respective routing tables
