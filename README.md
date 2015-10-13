**CPEN 221 / Fall 2015: Machine Problem 3**
The Graph ADT and Social Networks
===

### Logistics

+ This is a group assignment and you will be assigned to groups.
+ This is a 2-week machine problem, and will be due Oct/26.
+ The weight for this machine problem is 7%.

### Background

In this assignment, you will implement a `Graph` interface using two different graph representations. You will then develop several algorithms that use the `Graph` interface that might be used in a social network.

Your goals for this machine problem are to:
+ Understand and apply the concept of encapsulation;
+ Understand interfaces;
+ Understand what graphs are and how they can be represented;
+ Implement some basic graph algorithms.

### Instructions

#### Graph Implementations
First, write two classes that implement the `ca.ubc.ece.cpen221.mp3.staff.Graph` interface, which represents a directed graph.
+ **Adjacency List**: Inside the package `ca.ubc.ece.cpen221.mp3.graph`, implement the `AdjacencyListGraph` class. Your implementation must internally represent the graph as an _adjacency list_. If you are not familiar with the adjacency list representation of graphs, see the Wikipedia page on the adjacency list representation as a reference.
+ **Adjacency Matrix**: Next, implement the `AdjacencyMatrixGraph` class in the `ca.ubc.ece.cpen221.mp3.graph` package. Your implementation must internally represent the graph as an adjacency matrix. If you are not familiar with the adjacency matrix representation of graphs, see the Wikipedia page on the adjacency matrix representation as a reference.

#### Algorithm Implementations
For this part of the assignment, you will write three algorithms that might be used for social network analysis using your graph implementations. 

Your algorithms must use only the methods provided in the interface, and can not use any features specific to the implementation of `Graph` being used. Your algorithms must work correctly on any correct implementation of a `Graph`, including your `AdjacencyMatrixGraph` and `AdjacencyListGraph`.

+ **Breadth First Search (BFS)**: Implement the breadth first search algorithm to traverse a graph.
+ **Depth First Search (DFS)**: Implement the depth first search algorithm to traverse a graph.
+ **Shortest distance**: Implement a method to find the shortest distance between two vertices in an unweighted graph. In an unweighted graph $G$, given two vertices $s$ and $t$, the shortest distance between the two vertices is the number of edges that would have to be traversed to get to $b$ from $a$. The distance between a vertex and itself is 0. If no path exists from $a$ to $b$ then your method should take appropriate action.
+ **Common upstream vertices**: Given a graph $G$ and two vertices $a$ and $b$ in $G$, your implementation should return a list of all vertices $u$ such that there is an edge from $u$ to $a$ and an edge from $u$ to $b$. If there are no such vertices then your implementation should return an empty list.
+ **Common downstream vertices**: Given a graph $G$ and two vertices $a$ and $b$ in $G$, your implementation should return a list of all vertices $v$ such that there is an edge from $a$ to $v$ and an edge from $b$ to $v$.  

Suggest Friend: Implement the suggestFriend method in the Algorithms class that will return a good friend suggestion for a user. Given a graph G and a vertex s, you should return a vertex d, such that d ̸= s, d is not adjacent to s, and d and s share as many adjancent vertices as possible (i.e., the intersection of the neighborhood of d and the neighborhood of s is as large as possible). If multiple vertices satisfy the above requirement, you may return an arbitrary vertex that satisfies the requriement. If such a d does not exist or if the size of the maximum intersection is 0, then return null.

In a social network, this might be used to provide friend suggestions because you are likely to know someone who has many mutual friends with you.
Testing Your Code
To check the correctness of your code, you must write at least three more methods to test your graph and algorithm implementations. We have provided some code in the Main class to help you start testing your implementations; please add a few more test cases to each method. Then, write at least three more of your own methods to test the other parts of your graph and algorithm implementations. Try to write tests that check the correctness of normal cases as well as edge cases of your algorithms.

Evaluation
Overall this homework is worth 100 points. To earn full credit you must:
• Properly encapsulate your implementation. Use the most restrictive access level that makes sense for each of your fields and methods (i.e. use private unless you have a good reason not to). Instead of manipulating class fields directly, make them private and implement getter and setter methods to manipulate them from outside of the class. See Controlling Access to Members of a Class for a reference.
• Not edit any files in the ca.ubc.ece.cpen221.mp3.staff package or any of the method declarations we’ve initially provided for you.
• Make sure your code is readable. Use proper indentation and whitespace, abide by standard Java naming conventions, and add additional comments as necessary to document your code.
• Follow the Java code conventions, especially for naming and commenting. Hint: use <kbd>Ctrl</kbd> + <kbd>Shift</kbd> + <kbd>F</kbd> to auto-format your code!
Additional hints:
• You may create helper classes and helper methods to help you with the assignment, as long as your code is compatible with the provided staff interfaces.
• The tasks may be underspecified. In case of doubt, use your judgment or ask a question on Piazza. If you want to communicate your assumptions, use comments in the source code.
• You are not required to throw any exceptions on faulty input arguments for this assignment. Your implementation may behave arbitrarily on incorrect input. We will learn more about Exceptions later. You can assume that when we grade your code, our method arguments will conform to the specifications described in this handout and our comments in the code.

￼
• As long as your code runs in a reasonable amount of time, and returns the correct values, you do not need to worry about the complexity of your algorithms.
We will grade your work approximately as follows:
• Correct graph implementations: 40 points
• Correct algorithm implementations: 40 points
• Good style and program design: 15 points
• At least three additional test methods: 5 points