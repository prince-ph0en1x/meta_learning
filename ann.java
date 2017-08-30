import java.io.*;
import java.text.*;
class nn
{
	public static void main(String args[])
	{
		// For Training
		// input dataset
		double tsI[][] = {{0,0,1},{0,1,1},{1,0,1},{1,1,1}};
		// output dataset 
		double tsO[][] = {{0},{0},{1},{1}};//{{0},{1},{1},{0}};
		
		int layers = 2;		// Adjustable - number of layers
		int nodes[] = new int[layers];
		int i, j;
		for (i = 0; i < layers-1; i++)
			nodes[i] = 4;	// Adjustable - layer structure
		nodes[layers-1] = 1;// Last layer converges to single output
		
		// iterate over array of 2D array pointers
		double syn0[][] = new double[tsI[0].length][nodes[0]];
		double syn1[][] = new double[nodes[0]][nodes[1]];
		
		// initialize weights randomly with mean 0
		for (i = 0; i < syn0.length; i++)
			for (j = 0; j < syn0[0].length; j++)
				syn0[i][j] = 2*Math.random()-1;
		for (i = 0; i < syn1.length; i++)
			for (j = 0; j < syn1[0].length; j++)
				syn1[i][j] = 2*Math.random()-1;
		
		double l0[][] = new double[tsI.length][tsI[0].length];	// same as ts0
		double l1[][] = new double[tsI.length][nodes[0]];
		double l2[][] = new double[tsI.length][nodes[1]];
		
		double l1_error[][] = new double[tsI.length][nodes[0]];
		double l1_delta[][] = new double[tsI.length][nodes[0]];
		
		double l2_error[][] = new double[tsI.length][nodes[1]];
		double l2_delta[][] = new double[tsI.length][nodes[1]];
		
		double syn0_updt[][] = new double[tsI[0].length][nodes[0]];
		double syn1_updt[][] = new double[nodes[0]][nodes[1]];
		l0 = tsI;	
		for (int iter = 0; iter < 100000; iter++) {
			// forward propagation
			l1 = nonlin(dot(l0,syn0),false);
			l2 = nonlin(dot(l1,syn1),false);
			// how much did we miss?
			l2_error = sub(tsO,l2);
			// multiply how much we missed by the slope of the sigmoid at the values in l1
			l2_delta = mul(l2_error,nonlin(l2,true));
			l1_error = dot(l2_delta,transpose(syn1));
			l1_delta = mul(l1_error,nonlin(l1,true));
			// update weights
			syn1_updt = dot(transpose(l1),l2_delta);
			for (i = 0; i < syn1_updt.length; i++)
				for (j = 0; j < syn1_updt[0].length; j++)
					syn1[i][j] += syn1_updt[i][j];
				
			syn0_updt = dot(transpose(l0),l1_delta);
			for (i = 0; i < syn0_updt.length; i++)
				for (j = 0; j < syn0_updt[0].length; j++)
					syn0[i][j] += syn0_updt[i][j];
			printMatrix(transpose(l2));
		}
		//printMatrix(l1);
		//printMatrix(l2);
		//System.out.println("\nPrediction : \n"+l2[0][0]+"\n"+l2[1][0]+"\n"+l2[2][0]+"\n"+l2[3][0]);
	}
	public static double[][] nonlin(double x[][], boolean deriv)
	{
		double y[][] = new double[x.length][x[0].length];
		int i, j;
		for (i = 0; i < x.length; i++) {
			for (j = 0; j < x[0].length; j++) {
				if (deriv)
					y[i][j] = x[i][j]*(1-x[i][j]);
				else
					y[i][j] = 1/(1+Math.exp(-x[i][j]));
			}
		}
		return y;		
	}
	public static double[][] mul(double m1[][], double m2[][])
	{
		double m3[][] = new double[m1.length][m1[0].length];
		int i, j;
		for (i = 0; i < m1.length; i++)
			for (j = 0; j < m1[0].length; j++)
				m3[i][j] = m1[i][j]*m2[i][j];
		return m3;
	}
	public static double[][] sub(double m1[][], double m2[][])
	{
		double m3[][] = new double[m1.length][m1[0].length];
		int i, j;
		for (i = 0; i < m1.length; i++)
			for (j = 0; j < m1[0].length; j++)
				m3[i][j] = m1[i][j]-m2[i][j];
		return m3;
	}
	public static double[][] transpose(double m1[][])
	{
		double m2[][] = new double[m1[0].length][m1.length];
		int i, j;
		for (i = 0; i < m2.length; i++) {
			for (j = 0; j < m2[0].length; j++) {
				m2[i][j] = m1[j][i];
			}
		}
		return m2;
	}
	public static double[][] dot(double m1[][], double m2[][])
	{
		double m3[][] = new double[m1.length][m2[0].length];
		int i, j, k;
		for (i = 0; i < m3.length; i++) {
			for (j = 0; j < m3[0].length; j++) {
				m3[i][j] = 0;
				for (k = 0; k < m1[0].length; k++)
					m3[i][j] += m1[i][k]*m2[k][j];
			}
		}
		return m3;
	}
	public static void printMatrix(double m[][])
	{
		int i, j;
		DecimalFormat df = new DecimalFormat("0.0000"); 
		for (i = 0; i < m.length; i++) {
			for (j = 0; j < m[0].length; j++)
				System.out.print(df.format(m[i][j])+"\t");
			System.out.println();
		}
	}
	

}
