import java.io.*;
import java.util.*;

class GFG{
    public static void main(String args[])throws IOException
    {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(in.readLine());
        while(t-- > 0){
            int N = Integer.parseInt(in.readLine());
            String input_line[] = in.readLine().trim().split("\\s+");
            int arr[] = new int[N];
            for(int i = 0;i < N;i++)
                arr[i] = Integer.parseInt(input_line[i]);
            
            Solution ob = new Solution();
            int x = ob.equalPartition(N, arr);
            if(x == 1)
                System.out.println("YES");
            else
                System.out.println("NO");
        }
    }
}


class Solution{
    static int equalPartition(int len , int items[]){
        
        int sum=0;
        
        for(int i: items)sum = sum + i; 

        if(sum % 2 != 0) return 0;
        
        return isSubsetSum(items, sum/2) ? 1 : 0;
    }
    
    static boolean isSubsetSum(int items[], int totalSum){
		
		boolean matrix[][] = new boolean[items.length + 1][totalSum + 1];

		for (int col=0;  col<=totalSum;  col++)
			matrix[0][col] = false;

		for (int row=0;  row<items.length+1;  row++)
			matrix[row][0] = true;


		for (int i = 1; i <= items.length; i++) {
			
			int currentItem  = items[i-1];
			for (int j = 1; j <= totalSum; j++) {
			    if(currentItem <= j){
			        matrix[i][j] = matrix[i-1][j] || matrix[i-1] [j - currentItem];
			    }else{
			    	matrix[i][j] = matrix[i-1][j];			        
			    }
            }
		}

		return matrix[items.length][totalSum];
	}
}
