// http://iamtrask.github.io/2015/07/12/basic-python-network/

import java.io.*;
class nn
{
	public static void main(String args[])
	{
		int i, j, iter;
		// input dataset
		double tsI[][] = {{0,0,1},{0,1,1},{1,0,1},{1,1,1}};
		// output dataset 
		int tsO[][] = {{0},{0},{1},{1}};
		double syn0[][] = new double[tsI[0].length][1];
		// initialize weights randomly with mean 0
		for (i = 0; i < syn0.length; i++)
			syn0[i][0] = 2*Math.random()-1;
		double l0[][] = new double[tsI.length][tsI[0].length];
		double l1[][] = new double[tsO.length][tsO[0].length];
		double l1_error[][] = new double[tsO.length][tsO[0].length];
		double l1_delta[][] = new double[tsO.length][tsO[0].length];
		double syn0_updt[][] = new double[tsI[0].length][1];
		l0 = tsI;	
		for (iter = 0; iter < 10000; iter++) {
			// forward propagation
			l1 = dot(l0,syn0);
			for (i = 0; i < l1.length; i++)
				for (j = 0; j < l1[0].length; j++)
					l1[i][j] = nonlin(l1[i][j],false);
			// how much did we miss?
			for (i = 0; i < l1.length; i++)
				for (j = 0; j < l1[0].length; j++)
					l1_error[i][j] = tsO[i][j]-l1[i][j];
			// multiply how much we missed by the slope of the sigmoid at the values in l1
			for (i = 0; i < l1.length; i++)
				for (j = 0; j < l1[0].length; j++)
					l1_delta[i][j] = l1_error[i][j]*nonlin(l1[i][j],true);
			// update weights
			syn0_updt = dot(transpose(l0),l1_delta);
			for (i = 0; i < syn0_updt.length; i++)
				syn0[i][0] += syn0_updt[i][0];
		}
		System.out.println("\nPrediction : \n"+l1[0][0]+"\n"+l1[1][0]+"\n"+l1[2][0]+"\n"+l1[3][0]);
	}
	public static double nonlin(double x, boolean deriv)
	{
		if (deriv)
			return x*(1-x);
		else
			return 1/(1+Math.exp(-x));
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
	

}


/*
X = np.array([ [0,0,1],[0,1,1],[1,0,1],[1,1,1] ])
y = np.array([[0,1,1,0]]).T
syn0 = 2*np.random.random((3,4)) - 1
syn1 = 2*np.random.random((4,1)) - 1
for j in xrange(60000):
	l1 = 1/(1+np.exp(-(np.dot(X,syn0))))
	l2 = 1/(1+np.exp(-(np.dot(l1,syn1))))
	l2_delta = (y - l2)*(l2*(1-l2))
	l1_delta = l2_delta.dot(syn1.T) * (l1 * (1-l1))
	syn1 += l1.T.dot(l2_delta)
	syn0 += X.T.dot(l1_delta)
*/


/*
import numpy as np

# sigmoid function
def nonlin(x,deriv=False):
	if(deriv==True):
		return x*(1-x)
	return 1/(1+np.exp(-x))
 
# input dataset
X = np.array([  [0,0,1],
[0,1,1],
[1,0,1],
[1,1,1] ])
 
# output dataset           
y = np.array([[0,0,1,1]]).T
 
# seed random numbers to make calculation deterministic (just a good practice)
np.random.seed(1)
 
# initialize weights randomly with mean 0
syn0 = 2*np.random.random((3,1)) - 1
 
for iter in xrange(10000):
 
# forward propagation
l0 = X
l1 = nonlin(np.dot(l0,syn0))
 
# how much did we miss?
l1_error = y - l1
 
# multiply how much we missed by the
# slope of the sigmoid at the values in l1
l1_delta = l1_error * nonlin(l1,True)
 
# update weights
syn0 += np.dot(l0.T,l1_delta)
 
print "Output After Training:"
print l1
*/
