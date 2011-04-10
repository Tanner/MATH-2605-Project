package hilbert;

public class Test2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		double[][] array2=new double[][]{{1,2,3},{2,-1,1},{3,4,-1}};
		int size=array2.length;
		for(int k=0;k<size-1;k++)
		{
			for(int x=k+1;x<size;x++)
				array2[x][k]=array2[x][k]/array2[k][k];
			for(int i=k+1;i<size;i++)
				for(int j=k+1;j<size;j++)
					array2[i][j]=array2[i][j]-(array2[i][k]*array2[k][j]);
		}
		for(int i=0;i<array2.length;i++)
		{
			for(int j=0;j<array2[i].length;j++)
				System.out.print(array2[i][j]+" ");
			System.out.println();
		}

	}

}
