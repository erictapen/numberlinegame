package com.wicam.numberlineweb.server;


import java.util.ArrayList;

public class AdaptationFunctions {
	
	// information function
	public static ArrayList<Double> getInformationBirnbaum(double theta, ArrayList<Double> epsi, ArrayList<Double> delta)
	{
		ArrayList<Double> infoList = new ArrayList<Double>();
		
		for (int i = 0; i < epsi.size(); i++)
		{
			infoList.add(delta.get(i)*delta.get(i) * Math.exp(delta.get(i) * (theta - epsi.get(i))) / 
				((1 + Math.exp(delta.get(i) * (theta - epsi.get(i))))*(1 + Math.exp(delta.get(i) * (theta - epsi.get(i)))))) ;
		}
		
		return infoList;
	}
	
	// information function
		public static double getInformationBirnbaum(double theta, double epsi, double delta)
		{		
			return delta*delta * Math.exp(delta * (theta - epsi)) / 
					((1 + Math.exp(delta * (theta - epsi)))*(1 + Math.exp(delta * (theta - epsi))));
		}
	
	// Birnbaum model
	public static ArrayList<Double> getBirnbaumModelValue(double theta, ArrayList<Double> epsi, ArrayList<Double> delta)
	{
		ArrayList<Double> valueList = new ArrayList<Double>();
		
		for (int i = 0; i < epsi.size(); i++)
		{
			valueList.add(Math.exp(delta.get(i) * (theta - epsi.get(i))) / (1 + Math.exp(delta.get(i) * (theta - epsi.get(i))))) ;
		}
		
		return valueList;
	}
	
	// Birnbaum model
	public static Double getBirnbaumModelValue(double theta, double epsi, double delta)
	{
		return (Math.exp(delta * (theta - epsi)) / (1 + Math.exp(delta * (theta - epsi)))) ;
	}

	// simulate response (need to come from user)
	// returns either 0 or 1
	public static int simulateResponse(double theta, double epsi, double delta)
	{
		return Math.random() <= getBirnbaumModelValue(theta,epsi,delta)?1:0;
	}
	
	// random initialization (may incorporate prior information on user)
	public static double initTheta()
	{
		return Math.random() - 0.5;
	}
	
	public static int selectItem(double theta,  ArrayList<Double> epsi, ArrayList<Double> delta, ArrayList<Double> presentedItems)
	{
		int selected = 0;
		double max = 0;
		for (int i = 0; i < presentedItems.size(); i++)
		{
			if (Math.abs(presentedItems.get(i) - 0.0) < 0.0000001) // if d == 0
			{
				presentedItems.set(i, 0.0);
			}
			else
			{
				presentedItems.set(i, getInformationBirnbaum(theta, epsi.get(i), delta.get(i)));
			}
		}
		return selected;
	}
	
	public static double updateTheta()
	{
		double theta = 0.0;
		
		int theta_step = 1;
		double tolerance = 1e-7;
		int diff_iter = 1;
		
		
		return theta;
	}
	
	public static ArrayList<Double> initilizeList(double d, int elements)
	{
		ArrayList<Double> list = new ArrayList<Double>();
		for (int i = 0; i < elements; i++)
		{
			list.add(d);
		}
		return list;
	}
	
	public static void printArrayList(ArrayList<Double> list)
	{
		for (Double d : list)
		{
			System.out.print(d + " ");
		}
		System.out.println();
	}
}
