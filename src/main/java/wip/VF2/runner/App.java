package wip.VF2.runner;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import wip.VF2.core.State;
import wip.VF2.core.VF2;
import wip.VF2.graph.Graph;


public class App {

	public static void main(String[] args) throws FileNotFoundException {
		Path graphPath = Paths.get("/Users/luo123n/Projects/VF2/data/graphDB", "mygraphdb.data");
		Path queryPath = Paths.get("/Users/luo123n/Projects/VF2/data/graphDB", "Q12.my");
		Path outPath = Paths.get("/Users/luo123n/Projects/VF2/data/graphDB", "res_Q12.my");
		
		long startMilli = System.currentTimeMillis();
	
		PrintWriter writer = new PrintWriter(outPath.toFile());

		ArrayList<Graph> graphSet = loadGraphSetFromFile(graphPath, "Graph ");
		ArrayList<Graph> querySet = loadGraphSetFromFile(queryPath, "Query ");

		VF2 vf2= new VF2();
		
		System.out.println("Loading Done!");
		printTimeFlapse(startMilli);
		startMilli = System.currentTimeMillis();
		System.out.println();
		
		int queryCnt = 0;
		for (Graph queryGraph : querySet){
			queryCnt++;
			ArrayList<State> stateSet = vf2.matchGraphSetWithQuery(graphSet, queryGraph);
			if (stateSet.isEmpty()){
				System.out.println("Cannot find a map for: " + queryGraph.name);
				printTimeFlapse(startMilli);
				printAverageMatchingTime(startMilli, queryCnt);
				System.out.println();
				
				writer.write("Cannot find a map for: " + queryGraph.name + "\n\n");
				writer.flush();
			} else {
				System.out.println("Found " + stateSet.size() + " maps for: " + queryGraph.name);
				printTimeFlapse(startMilli);
				printAverageMatchingTime(startMilli, queryCnt);
				System.out.println();
				
				writer.write("Maps for: " + queryGraph.name + "\n");
				for (State state : stateSet){
					writer.write("In: " + state.targetGraph.name + "\n");
					// state.printMapping();
					state.writeMapping(writer);
				}		
				writer.write("\n");
				writer.flush();
			}
		}
		
		printTimeFlapse(startMilli);
	}
	
	/**
	 * Load graph set from file
	 * @param inpath	Input path
	 * @param namePrefix	The prefix of the names of graphs
	 * @return	Graph Set
	 * @throws FileNotFoundException
	 */
	private static ArrayList<Graph> loadGraphSetFromFile(Path inpath, String namePrefix) throws FileNotFoundException{
		ArrayList<Graph> graphSet = new ArrayList<Graph>();
		Scanner scanner = new Scanner(inpath.toFile());
		Graph graph = null;
		while (scanner.hasNextLine()){
			String line = scanner.nextLine().trim();
			if (line.equals("")){
				continue;
			} else if (line.startsWith("t")) {
				String graphId = line.split(" ")[2];
				if (graph != null){
					graphSet.add(graph);
				}
				graph = new Graph(namePrefix + graphId);
			} else if (line.startsWith("v")) {
				String[] lineSplit = line.split(" ");
				int nodeId = Integer.parseInt(lineSplit[1]);
				int nodeLabel = Integer.parseInt(lineSplit[2]);
				graph.addNode(nodeId, nodeLabel);
			} else if (line.startsWith("e")) {
				String[] lineSplit = line.split(" ");
				int sourceId = Integer.parseInt(lineSplit[1]);
				int targetId = Integer.parseInt(lineSplit[2]);
				int edgeLabel = Integer.parseInt(lineSplit[3]);
				graph.addEdge(sourceId, targetId, edgeLabel);
			}
		}
		scanner.close();
		return graphSet;
	}
	
	private static void printTimeFlapse(long startMilli){
		long currentMili=System.currentTimeMillis();
		System.out.println(((currentMili - startMilli) / 1000) + " seconds elapsed");
	}
	
	private static void printAverageMatchingTime(long startMilli, int queryCnt){
		long currentMili=System.currentTimeMillis();
		System.out.println(((currentMili - startMilli) / queryCnt) + " milliseconds per graph in average.");
	}
}