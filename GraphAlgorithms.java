import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author DIVYESH VED
 * @version 1.0
 * @userid dved6
 * @GTID 903600373
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if (start  == null) {
            throw new IllegalArgumentException("The start vertex you have entered is null.");
        }
        if (graph == null) {
            throw new IllegalArgumentException("The graph you are looking is in null");
        }
        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("The start vertex does not exist in the graph");
        }
        Set<Vertex<T>> visitedSet = new HashSet<>();
        Queue<Vertex<T>> queue = new LinkedList<>();
        Map<Vertex<T>, List<VertexDistance<T>>> adjMap = graph.getAdjList();
        LinkedList<Vertex<T>> returnList = new LinkedList<>();
        visitedSet.add(start);
        queue.add(start);
        returnList.add(start);
        while (!queue.isEmpty() && visitedSet.size() != graph.getVertices().size()) {
            Vertex<T> vertexRemove = queue.remove();
            for (VertexDistance<T> vertexDist: adjMap.get(vertexRemove)) {
                Vertex<T> vertexW = vertexDist.getVertex();
                if (!visitedSet.contains(vertexW)) {
                    visitedSet.add(vertexW);
                    queue.add(vertexW);
                    returnList.add(vertexW);
                }
            }
        }
        return returnList;
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * all points for this method.
     *
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("The start vertex you have entered is null.");
        }
        if (graph == null) {
            throw new IllegalArgumentException("The graph you are looking is in null");
        }
        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("The start vertex does not exist in the graph");
        }
        Set<Vertex<T>> visitedSet = new HashSet<>();
        LinkedList<Vertex<T>> returnList = new LinkedList<>();
        Map<Vertex<T>, List<VertexDistance<T>>> adjMap = graph.getAdjList();

        dfsHelper(start, adjMap, visitedSet, returnList);
        return returnList;
    }

    /**
     * Helper method that allows for recursion for dfs.
     * @param start the intial vertex.
     * @param adjMap the adjacent vertex list
     * @param visitedSet the set containing the vertices that have been visited.
     * @param returnList the list that returns the order that vertices are visited
     * @param <T> the input type.
     */
    public static <T> void dfsHelper(Vertex<T> start,
                                     Map<Vertex<T>, List<VertexDistance<T>>> adjMap,
                                     Set<Vertex<T>> visitedSet,
                                     LinkedList<Vertex<T>> returnList) {
        if (!visitedSet.contains(start)) {
            visitedSet.add(start);
            returnList.add(start);
            for (VertexDistance<T> vertexV: adjMap.get(start)) {
                Vertex<T> vertexW = vertexV.getVertex();
                if (!visitedSet.contains(vertexW)) {
                    dfsHelper(vertexW, adjMap, visitedSet, returnList);
                }
            }
        }
    }

    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty yet.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("The start vertex you have entered is null.");
        }
        if (graph == null) {
            throw new IllegalArgumentException("The graph you are looking is in null");
        }
        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("The start vertex does not exist in the graph");
        }
        Set<Vertex<T>> visitedSet = new HashSet<>();
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
        PriorityQueue<VertexDistance<T>> prioQ = new PriorityQueue<>();
        Map<Vertex<T>, Integer> distanceMap = new HashMap<>();
        for (Vertex<T> currentVertex: graph.getVertices()) {
            if (currentVertex.equals(start)) {
                distanceMap.put(currentVertex, 0);
            } else {
                distanceMap.put(currentVertex, Integer.MAX_VALUE);
            }
        }
        prioQ.add(new VertexDistance<>(start, 0));
        while (!prioQ.isEmpty() && visitedSet.size() != graph.getVertices().size()) {
            VertexDistance<T> remove = prioQ.remove();
            Vertex<T> removedVertex =  remove.getVertex();
            int removeDistance = remove.getDistance();

            if (!visitedSet.contains(removedVertex)) {
                visitedSet.add(removedVertex);
                distanceMap.replace(removedVertex, removeDistance);

                for (VertexDistance<T> i : adjList.get(removedVertex)) {
                    if (!(visitedSet.contains(i.getVertex()))) {
                        Vertex<T> vertexV = i.getVertex();
                        int distanceV = removeDistance + i.getDistance();
                        prioQ.add(new VertexDistance<>(vertexV, distanceV));
                    }
                }
            }
        }
        return distanceMap;
    }

    /**
     * Runs Kruskal's algorithm on the given graph and returns the Minimal
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * Kruskal's will also require you to use a Disjoint Set which has been
     * provided for you. A Disjoint Set will keep track of which vertices are
     * connected given the edges in your current MST, allowing you to easily
     * figure out whether adding an edge will create a cycle. Refer
     * to the DisjointSet and DisjointSetNode classes that
     * have been provided to you for more information.
     *
     * You should NOT allow self-loops or parallel edges into the MST.
     *
     * By using the Disjoint Set provided, you can avoid adding self-loops and
     * parallel edges into the MST.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Set, and any class that implements the aforementioned
     * interfaces.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param graph the graph we are applying Kruskals to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null
     */
    public static <T> Set<Edge<T>> kruskals(Graph<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("The graph you are looking through is null");
        }
        DisjointSet<Vertex<T>> disjointSet = new DisjointSet<>();
        int numVertices = graph.getVertices().size();
        PriorityQueue<Edge<T>> prioQ = new PriorityQueue<>();
        Set<Edge<T>> mstSet = new HashSet<>();
        prioQ.addAll(graph.getEdges());
        while (mstSet.size() < 2 * (numVertices - 1)) {
            Edge<T> removedEdge = prioQ.poll();
            if (removedEdge == null) {
                return null;
            }
            Vertex<T> vertexU = removedEdge.getU();
            Vertex<T> vertexV = removedEdge.getV();

            if (!(disjointSet.find(vertexU).equals(disjointSet.find(vertexV)))) {
                mstSet.add(removedEdge);
                mstSet.add(new Edge<>(vertexV, vertexU, removedEdge.getWeight()));
                disjointSet.union(vertexU, vertexV);
            }
        }
        return mstSet;
    }
}
