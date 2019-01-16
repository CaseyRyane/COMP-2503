/**
 * The Kmer class contains a String of size k that represents a 
 * sub sequence of the genome file given
 * 
 * @author Casey Ryane
 *
 */

	/*
	 * Below in this class there are three hashCodes, hashCode 1 is the one which
	 * remains implemented, hashCode 2 and 3 have been commented out as they are 
	 * less effective and produce slower search times. 
	 * 
	 * Hashcode 1 utilizes a replication of the String hashCode without storing the Hash number as global variable.
	 * 
	 * HashCode 2 does not multiply itself by a prime like 31 and instead multiplies "itself" by
	 * the new addition to itself after adding on the value of a char at position [i]. This hashCode is usable
	 * for sequence lengths up to 6 but degrades when faced with longer sequence lengths 
	 * 
	 * HashCode 3 returns a constant value of 0 and degrades significantly when face with larger sequence lengths.
	 * 
	 * HashCode 3 is by far the worst as it results in the most collisions while hashCode 1 results in the 
	 * fewest collisions thus allowing it to achieve the fastest search times even when faced with greater
	 * 
	 * 
	 */

public class Kmer {

	
	private String sequence;	
	
	/**
	 * A default constructor for the kmer class that set the sequence to null
	 */
	public Kmer() {
		sequence = null;
	}

	/**
	 * A constructor for the kmer class that takes in a string that represents a 
	 * genome subsequence and stores it
	 * @param sequence subsequence of genome
	 */
	public Kmer(String sequence) {
		this.sequence = sequence;

	}

	/**
	 * Setter for the sequence, takes in string value
	 * @param sequence subsequence to be stored in kmer class
	 */
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	/**
	 * Returns the subSequence of the genome as a string
	 * @return subsequence
	 */
	public String getSequence() {
		return sequence;
	}

	/**
	 * Returns a formatted string containing the 
	 * sequence information
	 */
	public String toString() {
		return sequence;
	}
	

	/*
	 * Equals method that compares a given object to this object,
	 * Compare if the object are the same, and if the sequence within the 
	 * object are the same.
	 * 
	 * @ param a Kmer object to be compared to the current object
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Kmer kmer = (Kmer) o;
		if (sequence.equals(kmer.getSequence())) {
			return true;
		} else {
			return false;
		}
	}
	


	/**
	 * HashCode 1, 
	 * The below hashCode has proven to be the most efficient hashCode
	 * the average time for different length sequences are:
	 * 
	 *  Length 4:
	 *  Sequence: CACA 
	 *  Average search Time from 10 tests: 15.1 ms
	 *  
	 *  Length 5:
	 *  Sequence: CATGA
	 *  Average search Time from 10 tests: 40.3 ms
	 *  
	 *  Length 6:
	 * 	Sequence: CATGCA
	 *  Average search Time from 10 tests: 38.5 ms
	 */
	
	@Override
	public int hashCode() {
		return sequence.hashCode();
	}

	
	
	/**
	 * HashCode 2 
	 * The below hashCode is the second most efficient hashCode
	 * The average search time for different lengths of sequences are:
	 * 
 	 *  Length 4:
	 *  Sequence: CACA 
	 *  Average search Time from 10 tests: 76.8 ms
	 *  
	 *  Length 5:
	 *  Sequence: CATGA
	 *  Average search Time from 10 tests: 78.8 ms
	 *  
	 *  Length 6:
	 * 	Sequence: CATGCA
	 *  Average search Time from 10 tests: 85.4 ms
	 *  
	 *  The hashCode starts to degrade when a longer sequence length is entered 
	*/
	
	/*
	@Override
	public int hashCode()
    {		
		int hash = 0;
        char value[] = sequence.toCharArray();
        if (value.length > 0) {
            char val[] = value;
            for (int i = 0; i < value.length; i++) {
                hash =  hash * hash + val[i];
            }
        }
        return hash;
    }
	*/
	
	/**
	 * HashCode 3  
	 * The below HashCode is the worst out of the three by far.
	 * 
 	 *  Length 4:
	 *  Sequence: CACA 
	 *  Average search Time from 10 tests: 36.2 ms
	 *  
	 *  Length 5:
	 *  Sequence: CATGA
	 *  Average search Time from 10 tests: 1316.4 ms
	 *  
	 *  Length 6:
	 * 	Sequence: CATGCA
	 *  Average search Time from 10 tests: 105.98 SECONDS
	 *  
	 *  The hashCode degrades significantly as soon as the sequence length surpasses 4
	 *  And becomes completely unusable once the sequence length passes 5. 
	 */
	/*
	@Override
	public int hashCode()
    {		
		return 0;
    }
	*/
	
	
	
}
