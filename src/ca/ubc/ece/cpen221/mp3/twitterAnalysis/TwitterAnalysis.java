package ca.ubc.ece.cpen221.mp3.twitterAnalysis;

import java.util.List;

/*
 * This class' scope is limited to the package. <-- remove this line before mp3 submission
 * 
 */

class TwitterAnalysis{
    
    /*Implement the following methods:
     * 
     *   Type of queries:
     *      commonInfluencers()
     *      numRetweets()
     *      
     *   Query file processing:
     *      main()
     * 
     */
    
    /**
     * Returns a list of common Twitter users that both user a and b follow
     * 
     * @param a
     * @param b
     * @return List of common Twitter users that both users a and b follow
     * 
     */
    private static List<UserId> commonInfluencers( UserId a, UserId b ){
        return null;
    }
    
    /**
     * Returns the number of retweets before a tweet by user a reaches user b
     * 
     * @param a
     * @param b
     * @return Number of retweets before a tweet by user a reaches user b
     * 
     */
    private static int numRetweets( UserId a, UserId b ){
        return 0;
    }
    
    public static void main(String[ ] args){
        /*
         * main() should take in two arguments:
         * 
         * 1. name of file that contains list of queries
         * 2. name of file that should be used to write output of queries to
         * 
         * commonInfluencers and numRetweets should be called here during query file processing.
         * 
         */
    }
    
}