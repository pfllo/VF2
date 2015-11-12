#Preface

This is a simple implementation of the VF2 algorithm.

The VF2 algorithm is used for (sub)graph isomorphism task.



Actually, this code is written for a homework of the Graph Data Management course in Peking University.
Since I don't have gold answers to the queries yet, the correctness of the code is not guaranteed. (I only manually checked a few results produced by this code, and it seems fine up to now)

Therefore, please don't use this code directly in product environment.

#Project Structure
*   doc: contains the VF2 paper
*   data: experiment data
*   src: source code

##data
*   mygraphdb.data: 10000 target graphs
*   Q4.my, ... , Q24.my: 1000 query graphs in each file. The difference between these files lies in that they have different number of edges (from 4 to 24).
*   readme.txt: description of these data

##src
*   wip.VF2.runner: main function
*   wip.VF2.graph: Graph, Node and Edge classes. These classes describe a graph.
*   wip.VF2.core: The VF2 class contains the main logic of VF2 algorithm. The State class is the state described in the paper, and it has some utility function to manipulate the a state. The Pair class is just a simple implementation of a (key, value) pair class.
  

#Usage
The main function can be called by passing command line arguments:

*   -t: The path of the target graph file (mygraphdb.data).
*   -q: The path of the query graph file (e.g Q4.my).
*   -o: The path of the output file.

Also, you can hard write these paths in the source code of App.java. Simply change three variables: graphPath, queryPath, outPath.