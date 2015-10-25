package ca.ubc.ece.cpen221.mp3.twitterAnalysis;

/*
 * This class' scope is limited to the package. <-- remove this line before mp3 submission
 * 
 * Used for representing twitter users.
 * 
 */

class UserId{
    /*
     * Implement a simple object UserId, which is a really long integer. 
     * I harsh'ed copied Vertex.java's implementation. How should we (if we do) give credit?
     * 
     * Current representation: String 
     * 
     *      e.g. userid : 14838508
     *           userid : 98032178
     *          
     */
    
    private String userId;

    /**
     * Create a new userId with a given number 
     * 
     * @param userId
     *            with which to identify the user
     */
    public UserId(String userId) {
        this.userId = userId;
    }

    /**
     * Obtain the number associated with a user
     * 
     * @return userId associated with this user
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Set the number for a userId
     * 
     * @param userId
     *            to set for the user
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Check equality of users. This method overrides equals( ) in Object.
     * 
     * @return true if this userId is equal to the obj otherwise return false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof UserId)) {
            return false;
        }
        UserId other = (UserId) obj;
        return userId.equals(other.userId);
    }

    /**
     * For fast equality checking. This method overrides hashCode() in Object.
     * 
     * @return a hash code for this userId
     */
    @Override
    public int hashCode() {
        return userId.hashCode();
    }

    /**
     * Obtain a string representation of the userId
     * 
     * @return the number associated with the user as their string
     *         representation.
     */
    @Override
    public String toString() {
        return userId;
    }
    
}