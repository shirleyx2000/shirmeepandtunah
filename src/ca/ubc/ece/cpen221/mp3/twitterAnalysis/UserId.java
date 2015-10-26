package ca.ubc.ece.cpen221.mp3.twitterAnalysis;

import ca.ubc.ece.cpen221.mp3.staff.Vertex;

/*
 * This class' scope is limited to the package. <-- remove this line before mp3 submission
 * 
 * Used for representing twitter users.
 * 
 */

class UserId extends Vertex {
    /*
     * Implement a simple object UserId, which is a really long integer. 
     * 
     *      e.g. userid : 14838508
     *           userid : 98032178
     *          
     */

    /**
     * Create a new userId with a given number 
     * 
     * @param userId
     *            with which to identify the user
     */
    public UserId(String userId) {
        super(userId);
    }

}