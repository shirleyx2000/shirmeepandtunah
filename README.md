**CPEN 221 / Fall 2015: Machine Problem 3**
The Graph ADT and Social Networks
===

### Logistics

+ This is a group assignment and you will be assigned to groups.
+ This is a 2-week machine problem, and will be due **Oct/26 6pm**.
+ The weight for this machine problem is 7% of your raw score in the course.

### Background

In this assignment, you will implement a `Graph` interface using two different graph representations. You will then develop several algorithms that use the `Graph` interface that might be used in a social network.

Your goals for this machine problem are to:
+ Understand and apply the concept of encapsulation;
+ Understand interfaces;
+ Understand what graphs are and how they can be represented;
+ Implement some basic graph algorithms.

### Instructions

#### Graph Implementations
First, write two classes that implement the `ca.ubc.ece.cpen221.mp3.staff.Graph` interface, which represents a _directed_ graph.
+ **Adjacency List**: Inside the package `ca.ubc.ece.cpen221.mp3.graph`, implement the `AdjacencyListGraph` class. Your implementation must internally represent the graph as an _adjacency list_. If you are not familiar with the adjacency list representation of graphs, see the Wikipedia page on the adjacency list representation as a reference.
+ **Adjacency Matrix**: Next, implement the `AdjacencyMatrixGraph` class in the `ca.ubc.ece.cpen221.mp3.graph` package. Your implementation must internally represent the graph as an adjacency matrix. If you are not familiar with the adjacency matrix representation of graphs, see the Wikipedia page on the adjacency matrix representation as a reference.

#### Algorithm Implementations
For this part of the assignment, you will implement algorithms that might be used for social network analysis using your graph implementations. 

Your algorithms must use only the methods provided in the interface, and can not use any features specific to the implementation of `Graph` being used. Your algorithms must work correctly on any correct implementation of a `Graph`, including your `AdjacencyMatrixGraph` and `AdjacencyListGraph`.

+ **Breadth first search (BFS)**: Implement the breadth first search algorithm to traverse a graph.
+ **Depth first search (DFS)**: Implement the depth first search algorithm to traverse a graph.
+ **Shortest distance**: Implement a method to find the shortest distance between two vertices in an unweighted graph. In an unweighted graph _G_, given two vertices _s_ and _t_, the shortest distance between the two vertices is the number of edges that would have to be traversed to get to _b_ from _a_. The distance between a vertex and itself is 0. If no path exists from _a_ to _b_ then your method should take appropriate action.
+ **Common upstream vertices**: Given a graph _G_ and two vertices _a_ and _b_ in _G_, your implementation should return a list of all vertices _u_ such that there is an edge from _u_ to _a_ and an edge from _u_ to _b_. If there are no such vertices then your implementation should return an empty list.
+ **Common downstream vertices**: Given a graph _G_ and two vertices _a_ and _b_ in _G_, your implementation should return a list of all vertices _v_ such that there is an edge from _a_ to _v_ and an edge from _b_ to _v_.  

### Social Network Analysis

An anonymized dataset from Twitter is provided in the file `datasets/twitter.txt`. The file contains many rows, with each row containing an entry of the form `a -> b` to indicate that `a` follows `b`.

We would like to answer the following questions from this dataset:
+ **Common influencers**: Given users `a` and `b`, who are the common users that both `a` and `b` follow?
+ **Retweets**: Suppose user `a` tweets a message, what is the minimum number of retweets needed before `a`'s tweet appears in `b`'s feed? (The assumption here is that a tweet will appear in `b`'s feed if the tweet originated from one of the users that `b` follows.)

You should implement suitable methods for such analysis, including a `public static void main(String[ ] args)` method in a class named `TwitterAnalysis` within the package called `twitterAnalysis`. 

The `main( )` method referred to here should take two arguments: 

1. the name of a file that contains a list of queries, and 
2. the name of a file that should be used to write the output of the queries to.

The format for submitting queries to your implementation shall be:
`<query type> <user a> <user b>`.

A file may have more than one query, with one line per query. There are two query types: `commonInfluencers` and `numRetweets`.

The line `commonInfluencers 14838508 98032178 ?` indicates that we want to find the users that both userid:14838508 and userid:98032178 follow. The corresponding output in the output file should have the following format:
```
query: commonInfluencers 14838508 98032178
<result>
	... // (list of common influencers, one per row)
</result>
```

We can similarly have a query `numRetweets 14838508 98032178 ?`, which asks for the number of retweets before a tweet by userid:14838508 reaches user:98032178. The corresponding output should be as follows:
```
query: numRetweets 14838508 98032178
<result>
0 // (or appropriate number)
</result>
```

If the input query file has multiple queries then the output file should have the results to each of those queries, in the same order as in the input file. 

+ Duplicate queries should be ignored. 
+ All queries end with `?`; lines in the query input file that do not end with a `?` can also be ignored.

### Testing Your Code
Use JUnit to test the correctness of your implementation. Write tests that check the correctness of normal cases as well as edge cases of the Graph ADT and the algorithms. Tests should be in the package `ca.ubc.ece.cpen221.mp3.tests`.

### Evaluation
To earn full credit you must:
+ Properly encapsulate your implementation. Use the most restrictive access level that makes sense for each of your fields and methods (i.e., use `private` unless you have a good reason not to). Instead of manipulating class fields directly, make them `private` and implement getter and setter methods to manipulate them from outside of the class. 
+ Not edit any files in the `ca.ubc.ece.cpen221.mp3.staff` package or any of the method declarations we’ve initially provided for you.
+ Make sure your code is readable. Use proper indentation and whitespace, abide by standard Java naming conventions, and add additional comments as necessary to document your code.
+ Follow the Java code conventions, especially for naming and commenting. 

### Additional hints
+ You may create helper classes and helper methods to help you with the assignment, as long as your code is compatible with the provided interfaces.
+ The tasks may be underspecified. Use your judgment. Write specifications. You can ask reasonable questions on Piazza.
+ As long as your code runs in a reasonable amount of time, and returns the correct values, you do not need to worry about the complexity of your algorithms.
+ Consider using <kbd>Ctrl</kbd> + <kbd>Shift</kbd> + <kbd>F</kbd> in Eclipse to auto-format your code.

### Grading guidelines
We will grade your work _approximately_ as follows:

<table>
	<tr><td>Correct graph implementations</td><td>30%</td></tr>
	<tr><td>Correct algorithm implementations</td><td>30%</td></tr>
	<tr><td>Correct Twitter data analysis</td><td>10%</td></tr>
	<tr><td>Tests</td><td>15%</td></tr>
	<tr><td>Good style and program design</td><td>15%</td></tr>
</table>

### Submission instructions

+ You should commit all source code to your assigned Github repository under a branch named `mp3`.
+ Since you will be working in groups, only one submission per group is expected.
+ You should submit a PDF file to Gradescope with the the following information:
	+ Names of students in the group;
	+ Github repository to which work was committed;
	+ An estimate of the number of hours of work to complete this MP;
	+ A short discussion (<= 100 words) on whether any other methods should be added to the `Graph` interface. Justify why a new method should be added or justify why it is complete as-is.