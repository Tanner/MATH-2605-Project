package hilbert;

import java.util.ArrayList;
import java.util.Arrays;
import jama.Matrix;
import jama.util.Maths;

public class Hilbert
{
	private double[][] array;
	private Matrix matrix,l,u,b,q,r;
	private int size;
	
	
	public Hilbert(int columns)
	{
		size=columns;
		b=new Matrix(columns,1,1);
		array=new double[columns][columns];
		for(int i=0;i<array.length;i++)
			for(int j=0;j<array[i].length;j++)
				array[i][j]=(1.0/(i+j+1));
		matrix=new Matrix(array);
	}
	
	public Hilbert(double[][] array)
	{
		size=array.length;
		b=new Matrix(size,1,1);
		this.array=array;
		matrix=new Matrix(array);
	}
	public double error1LU()
	{
		Matrix a=l.times(u).minus(matrix);
		double max=0;
		double temp=0;
		for(int i=0;i<a.getRowDimension();i++)
		{
			temp=0;
			for(int j=0;j<a.getColumnDimension();j++)
				temp+=Math.abs(a.get(i,j));
			if(temp>max)
					max=temp;
		}
		return max;
	}
	
	public double error1QR()
	{
		Matrix a=q.times(r).minus(matrix);
		double max=0;
		double temp=0;
		for(int i=0;i<a.getRowDimension();i++)
		{
			temp=0;
			for(int j=0;j<a.getColumnDimension();j++)
				temp+=Math.abs(a.get(i,j));
			if(temp>max)
					max=temp;
		}
		return max;
	}
	
	public double error2(Matrix x)
	{
		double max=0;
		Matrix a=matrix.times(x).minus(b);
		for(int i=0;i<a.getRowDimension();i++)
			if(Math.abs(a.get(i, 0))>max)
				max=a.get(i, 0);
		return max;
	}
	
	public void LUDecomp()
	{
		double[][] LU = matrix.getArrayCopy();
		int size = matrix.getRowDimension();
		size = matrix.getColumnDimension();
		double[] LUrowi;
		double[] LUcolj=new double[size];
		for(int j=0;j<size;j++)
		{
			for(int i=0;i<size;i++)
				LUcolj[i]=LU[i][j];
			for(int i=0;i<size;i++)
			{
				LUrowi=LU[i];
				int kmax=Math.min(i, j);
				double temp = 0.0;
				for(int k=0;k<kmax;k++)
					temp+=LUrowi[k]*LUcolj[k];

				LUrowi[j]=LUcolj[i]-=temp;
			}
			int p=j;
			for(int i=j+1;i<size;i++)
				if(Math.abs(LUcolj[i])>Math.abs(LUcolj[p]))
					p = i;
			if(p!=j)
			{
				for(int k=0;k<size;k++)
				{
					double t=LU[p][k];
					LU[p][k]=LU[j][k];
					LU[j][k]=t;
				}
			}
			if (j<size&LU[j][j]!=0.0) 
				for(int i=j+1;i<size;i++)
					LU[i][j] /= LU[j][j];
		}
		l=new Matrix(size,size);
		double[][] L=l.getArray();
		for(int i=0;i<size;i++)
			for(int j=0;j<size;j++)
				if (i>j)
					L[i][j] = LU[i][j];
				else if(i==j)
					L[i][j] = 1.0;
				else
					L[i][j] = 0.0;
		u=new Matrix(size,size);
		double[][] U=u.getArray();
		for(int i=0;i<size;i++)
			for(int j=0;j<size;j++)
			{
				if(i<=j)
					U[i][j]=LU[i][j];
				else
					U[i][j] = 0.0;
			}
	}
	
	public void householderQR()
	{		
		double[][] QR = matrix.getArrayCopy();
		double[] Rdiag = new double[size];
		for(int k=0;k<size;k++)
		{
			double norm = 0;
			for(int i=k;i<size;i++)
				norm=Maths.hypot(norm,QR[i][k]);
			if(norm!=0.0) 
			{
				if(QR[k][k]<0) 
					norm=-norm;
				for(int i=k;i<size;i++) 
					QR[i][k]/=norm;
				QR[k][k]+=1.0;
				for(int j=k+1;j<size;j++) 
				{
					double scale = 0.0;
					for(int i=k;i<size;i++) 
						scale+=QR[i][k]*QR[i][j];
					scale=-scale/QR[k][k];
					for(int i=k;i<size;i++) 
						QR[i][j]+=scale*QR[i][k];
				}
			}
			Rdiag[k]=-norm;
		}
		double[][] R=new double[size][size];
		for (int i=0;i<size;i++) 
			for (int j=0;j<size;j++) 
			{
				if (i<j)
					R[i][j]=QR[i][j];
				else if(i==j)
					R[i][j]=Rdiag[i];
				else
					R[i][j]=0.0;
			}
		r = new Matrix(R);
		double[][] Q = new double[size][size];
		for (int k=size-1;k>=0;k--)
		{
			for(int i=0;i<size;i++)
				Q[i][k]=0.0;
			Q[k][k]=1.0;
			for(int j=k;j<size;j++)
				if(QR[k][k]!=0)
				{
					double temp=0.0;
					for(int i=k;i<size;i++)
						temp+=QR[i][k]*Q[i][j];
					temp=-temp/QR[k][k];
					for(int i=k;i<size;i++)
						Q[i][j]+=temp*QR[i][k];
				}
		}
		q= new Matrix(Q);
		q.print(7,7);
		r.print(7,7);
	}
	
	public void givensQR()
	{
		r=new Matrix(matrix.getArrayCopy());
		ArrayList<Matrix> gs=new ArrayList<Matrix>();
		for(int i=0;i<size-1;i++)
		{
			for(int j=size-1;j>i;j--)
			{
				Matrix g=Matrix.identity(size, size);
				double angle=Math.atan(r.get(j,i)/r.get(j-1,i));
				angle=Math.toDegrees(angle);
				g.set(j-1,j-1,Math.cos(angle));
				g.set(j-1,j,Math.sin(angle));
				g.set(j,j-1,-Math.sin(angle));
				g.set(j, j,Math.cos(angle));
				gs.add(g);
				r=g.times(r);
			}
		}
		q=new Matrix(gs.get(0).getArrayCopy());
		for(int i=1;i<gs.size();i++)
			q=gs.get(i).times(q);
		q.print(7,7);
		r.print(7,7);
	}
	
	public int getSize()
	{
		return size;
	}
	
	public Matrix solveLU()
	{
		Matrix y=new Matrix(size,1,0);
		for(int i=0;i<size;i++)
		{
			double t=0.0;
			for(int j=0;j<i;j++)
				t+=l.get(i, j)*y.get(j,0);
			y.set(i, 0, (b.get(i,0)-t)/l.get(i,i));
		}
		Matrix x=new Matrix(size,1,0);
		for(int j=x.getRowDimension()-1;j>=0;j--)
		{
		    double t = 0.0;
		    for(int k=j+1;k<y.getRowDimension();k++)
		        t+=u.get(j, k)*x.get(k,0);
		    x.set(j,0,(y.get(j,0)-t)/u.get(j,j));
		}
		return x;
	}
	
	public Matrix solveQR()
	{
		Matrix y=q.transpose().times(b);
		Matrix x=new Matrix(size,1,0);
		for(int j=x.getRowDimension()-1;j>=0;j--)
		{
		    double t = 0.0;
		    for(int k=j+1;k<y.getRowDimension();k++)
		        t+=r.get(j, k)*x.get(k,0);
		    x.set(j,0,(y.get(j,0)-t)/r.get(j,j));
		}
		return x;
	}
	
	public void print()
	{
		matrix.print(7,7);
	}
}
