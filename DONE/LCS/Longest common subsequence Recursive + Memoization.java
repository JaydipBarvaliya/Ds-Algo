class Solution {
    public int longestCommonSubsequence(String s1, String s2) {
        
        char[] X=s1.toCharArray();
        char[] Y=s2.toCharArray();
        int m = X.length;
        int n = Y.length;
        
        
        int matrix[][] = new int[ m+1 ][ n+1 ];
            for (int[] row : matrix)
                Arrays.fill(row, -1);
        
        return lcs(matrix, X, Y, m, n );
        
        
    }
    
    
    int lcs( int matrix[][], char[] X, char[] Y, int m, int n ){
  
  
	if (m == 0 || n == 0) return 0;
	
	if(matrix[m][n] != -1) return matrix[m][n];
	
	if (X[m-1] == Y[n-1]){
	    matrix[m][n] =  1 + lcs(matrix, X, Y, m-1, n-1);  
	}else {
	    matrix[m][n] =  Math.max( lcs(matrix, X, Y, m, n-1), lcs(matrix, X, Y, m-1, n) );
	}
	
	return matrix[m][n];
}
    
}