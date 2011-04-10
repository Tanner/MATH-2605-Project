package hilbert;

import jama.Matrix;
import java.util.ArrayList;
import java.util.Arrays;

public class Test
{
	private static ArrayList<Hilbert> list;
	static int s=20;
	public static void main(String[] args)
	{
		list=new ArrayList<Hilbert>();
		for(int i=2;i<=20;i++)
		{
			Hilbert h=new Hilbert(i);
			list.add(h);
		}
//		double[][]array=new double[][]{{1,3,2,1},{1,1,4,1},{1,3,4,1},{1,1,2,-3}};
//		Hilbert h=new Hilbert(array);
//		h.givensQR();
		//list.get(2).givensQR();
		prob1();
		prob2();
		prob3();
	}
	
	public static void prob1()
	{
		double[] error1=new double[19];
		double[] error2=new double[19];
		for(int i=2;i<=s;i++)
		{
			Hilbert h=list.get(i-2);
			h.LUDecomp();
			Matrix x=h.solveLU();
			error1[i-2]=h.error1LU();
			error2[i-2]=h.error2(x);
		}
		System.out.println("Problem 1 error 1");
		System.out.println(Arrays.toString(error1));
		System.out.println("Problem 1 error 2");
		System.out.println(Arrays.toString(error2));
	}
	
	public static void prob2()
	{
		double[] error1=new double[19];
		double[] error2=new double[19];
		for(int i=2;i<=s;i++)
		{
			Hilbert h=list.get(i-2);
			h.householderQR();
			Matrix x=h.solveQR();
			error1[i-2]=h.error1QR();
			error2[i-2]=h.error2(x);
		}
		System.out.println("Problem 2 error 1");
		System.out.println(Arrays.toString(error1));
		System.out.println("Problem 2 error 2");
		System.out.println(Arrays.toString(error2));
	}
	
	public static void prob3()
	{
		double[] error1=new double[19];
		double[] error2=new double[19];
		for(int i=2;i<=s;i++)
		{
			Hilbert h=list.get(i-2);
			h.givensQR();
			Matrix x=h.solveQR2();
			error1[i-2]=h.error1QR2();
			error2[i-2]=h.error2(x);
		}
		System.out.println("Problem 3 error 1");
		System.out.println(Arrays.toString(error1));
		System.out.println("Problem 3 error 2");
		System.out.println(Arrays.toString(error2));
	}
}
