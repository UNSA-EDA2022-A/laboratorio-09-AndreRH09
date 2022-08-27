package com.example.project;

import java.util.ArrayList;

public class GraphAdjacentList implements Graph {

    private ArrayList<Vertex> vertices;
    private int numVertices;

    public GraphAdjacentList() {
        vertices = new ArrayList<>();
    }

    // Agregar una arista desde un vertice 'from' a un vertice 'to'
    public boolean addEdge(int from, int to) {
        Vertex fromV = null, toV = null;
        for (Vertex v : vertices) {
            if (from == v.data) { // verificando si 'from' existe
                fromV = v;
            } else if (to == v.data) { // verificando si 'to' existe
                toV = v;
            }
            if (fromV != null && toV != null) {
                break; // ambos nodos deben existir, si no paramos
            }
        }
        if (fromV == null) {
            fromV = new Vertex(from);
            vertices.add(fromV);
            numVertices++;
        }
        if (toV == null) {
            toV = new Vertex(to);
            vertices.add(toV);
            numVertices++;
        }        
        return fromV.addAdjacentVertex(toV);
    }

    // Eliminamos la arista del vertice 'from' al vertice 'to'
    public boolean removeEdge(int from, int to) {
        Vertex fromV = null;
        for (Vertex v : vertices) {
            if (from == v.data) {
                fromV = v;
                break;
            }
        }
        if (fromV == null) {
            return false;
        }
        return fromV.removeAdjacentVertex(to);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Vertex v : vertices) {
            sb.append("Vertex: ");
            sb.append(v.data);
            sb.append("\n");
            sb.append("Adjacent vertices: ");
            for (Vertex v2 : v.adjacentVertices) {
                sb.append(v2.data);
                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public int getNumEdges(){
        int count = 0;
        for(int i = 0; i < this.vertices.size(); i++){
            count += this.vertices.get(i).adjacentVertices.size();
        }
        return count;
    }

    public int getNumVertices() {
        return numVertices;
    }

    public void setNumVertices(int numVertices) {
        this.numVertices = numVertices;
    }

    public ArrayList<Vertex> depthFirstSearch(Vertex n, ArrayList<Vertex> visited) {
        visited.add(n);
        for (Vertex vAdjacent: n.adjacentVertices) {
            if (!visited.contains(vAdjacent) ) {
                depthFirstSearch(vAdjacent, visited);
            }
        }
        return visited;
    }


    public int countConnectedComponents(){
        int count = 0;
        ArrayList <Vertex> visited = new ArrayList<Vertex>();
        
        //Usando la misma logica que en Matriz de Adyacencia se utiliza DFS
        for(Vertex v : vertices){    
            if (!visited.contains(v)) {
                depthFirstSearch(v, visited);
                count++;
            }
        }
        return count;
    }

    public boolean removeVertex(int vertex){
        //Se crea un arreglo para almacenar los vertices que tengan una arista con vertex
        ArrayList<Integer> toDelete = new ArrayList<Integer>();
        
        //Se itera para almacenar los vertices 
        for(Vertex v : vertices){    
            for (Vertex v2 : v.adjacentVertices) {
                //Si la data es proveniente de vertex, se almacena los vertices de "llegada"
                if(v.data == vertex){
                    toDelete.add(v2.data);
                }
                //Si la data "llega" a vertex, se almacena los vertices de "partida"
                if(v2.data == vertex){
                    toDelete.add(v.data);
                }
                
            }

        }

        //En caso vertex no posea conexiones (aristas), no se ingresa a eliminar
        for(int n: toDelete){   
            //Se remueve las aristas 
            removeEdge(vertex, n);
            removeEdge(n, vertex);

            //Cuando se llega al ultimo elemento, se reduce el numero de vertices en 1
            if(n == toDelete.get(toDelete.size()-1)){
                numVertices--;
                return true;
            }
        }

        return false;
    }

    public static void main(String args[]) {
        GraphAdjacentList graph = new GraphAdjacentList();
        graph.addEdge(1, 2);
        graph.addEdge(1, 5);
        graph.addEdge(2, 5);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 1);        
        System.out.println(graph);
    }
}
