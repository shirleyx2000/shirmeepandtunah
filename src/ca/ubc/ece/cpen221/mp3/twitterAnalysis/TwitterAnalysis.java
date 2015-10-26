package ca.ubc.ece.cpen221.mp3.twitterAnalysis;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import ca.ubc.ece.cpen221.mp3.graph.AdjacencyListGraph;
import ca.ubc.ece.cpen221.mp3.graph.AdjacencyMatrixGraph;
import ca.ubc.ece.cpen221.mp3.graph.Algorithms;
import ca.ubc.ece.cpen221.mp3.graph.PathNotFoundException;
import ca.ubc.ece.cpen221.mp3.staff.Graph;
import ca.ubc.ece.cpen221.mp3.staff.Vertex;


class TwitterAnalysis{
    
    /**
     * Returns a list of common Twitter users that both user a and b follow
     * 
     * @param a
     * @param b
     * @return List of common Twitter users that both users a and b follow
     * 
     */
    private static List<Vertex> commonInfluencers( Vertex a, Vertex b, Graph g ){
        List<Vertex> ci = new ArrayList<Vertex>(); 
        
        //downstream neighbor of a and b are common users they follow
        ci = Algorithms.commonDownstreamVertices(g, a, b);
        
        return Collections.unmodifiableList(ci);
    }
    
    /**
     * Returns the number of retweets before a tweet by user a reaches user b
     * 
     * @param a
     * @param b
     * @return Number of retweets before a tweet by user a reaches user b
     * 
     */
    private static String numRetweets( Vertex follower, Vertex influencer, Graph g ){
        Integer numRTW = 0; 
        
        try {
            numRTW = Algorithms.shortestDistance(g, follower, influencer);
        } catch (PathNotFoundException pnfe) { 
            return "No retweets";
        }
        
        return numRTW.toString();
    }
    

    /**
     * main() should take in two arguments:
     * 
     * 1. name of file that contains list of queries
     * 2. name of file that should be used to write output of queries to
     * 
     * "java TwitterAnalysis queryInput.txt queryOutput.txt"
     * 
     */
    public static void main(String[ ] args){
        
        //output file, ready to write
        PrintWriter writer = null;
        try {            writer = new PrintWriter(args[1], "UTF-8");
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        
        //other declarations 
        Graph g = new AdjacencyListGraph(); 
        List<String> pastQueries = new ArrayList<String>();
        List<Vertex> ciResults = new ArrayList<Vertex>();
        String numRtwResults; 
        
        //form the graph to pass into two methods 
        FileInputStream tweetStream;
        try {            String datasetsDir = System.getProperty("user.dir").concat("\\datasets");
            tweetStream = new FileInputStream(datasetsDir + "\\twitter.txt");

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        
        try {
            BufferedReader tweetReader = new BufferedReader(
                    new InputStreamReader(tweetStream));
            
            String line;
            while ((line = tweetReader.readLine()) != null) {
                //for each line, form the edges and add vertices
                String[] vertices = line.split(" -> ");
                Vertex v1 = new Vertex(vertices[0]);
                Vertex v2 = new Vertex(vertices[1]);
                g.addVertex(v1);
                g.addVertex(v2);
                g.addEdge(v1, v2);
            }
            
            tweetReader.close();
            tweetStream.close();
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        FileInputStream queryStream; 
        try{
            //Place entire directory path in argument 0{            queryStream = new FileInputStream(args[0]);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        
        try {
            BufferedReader queryReader = new BufferedReader(
                    new InputStreamReader(queryStream));
            
            String singleLine; 

            while ((singleLine = queryReader.readLine()) != null) {
                String[] queryWords = singleLine.split(" ");
                //MUST end in '?'
                if (!queryWords[queryWords.length-1].equals("?")) {
                    continue; 
                }
                //Cannot accept any other format either 
                else if (queryWords.length != 4 && 
                   (!queryWords[0].equals("commonInfluencer") || !queryWords[0].equals("numRetweets"))){
                    continue; 
                } else {
                    // check for duplicate query 
                    if (pastQueries.contains(singleLine)) {
                        continue; 
                    } else {
                        //ACTUALLY EXECUTE COMMAND HERE
                        pastQueries.add(singleLine);
                        
                        if (singleLine.contains("commonInfluencer")){
                            ciResults = commonInfluencers(new Vertex(queryWords[1]), new Vertex(queryWords[2]), g); 
                            writer.println("query: " + singleLine);
                            writer.println("<result>");
                            for (Vertex v : ciResults) {
                                writer.println(v.toString());
                            }
                            writer.println("</result>");
                        } else if (singleLine.contains("numRetweets")) {
                            numRtwResults = numRetweets(new Vertex(queryWords[1]), new Vertex(queryWords[2]), g);
                            writer.println("query: " + singleLine);
                            writer.println("<result>");
                            writer.println(numRtwResults);
                            writer.println("</result>");
                        } else {
                            continue; 
                        }
                    }
                }
            }
            queryReader.close();
            queryStream.close(); 
            
        } catch (Exception e) {
            throw new RuntimeException(e);

    }
        writer.close();

}
}