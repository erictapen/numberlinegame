package com.wicam.numberlineweb.server;


import java.util.ArrayList;

public class AdaptationFunctions {
	
	// information function
	public static ArrayList<Double> getInfoBM(double theta, ArrayList<Double> epsi, ArrayList<Double> delta)
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
	public static double getInfoBM(double theta, double epsi, double delta)
	{		
		return delta*delta * Math.exp(delta * (theta - epsi)) / 
				((1 + Math.exp(delta * (theta - epsi)))*(1 + Math.exp(delta * (theta - epsi))));
	}
	
	public static ArrayList<Double> getProbCorrectResponseBM(double theta, ArrayList<Double> epsi, ArrayList<Double> delta)
	{
		ArrayList<Double> valueList = new ArrayList<Double>();
		
		for (int i = 0; i < epsi.size(); i++)
		{
			valueList.add(Math.exp(delta.get(i) * (theta - epsi.get(i))) / (1 + Math.exp(delta.get(i) * (theta - epsi.get(i))))) ;
		}
		
		return valueList;
	}
	
	public static Double getProbCorrectResponseBM(double theta, double epsi, double delta)
	{
		return (Math.exp(delta * (theta - epsi)) / (1 + Math.exp(delta * (theta - epsi)))) ;
	}

	// simulate response (need to come from user)
	// returns either 0 or 1
	public static int simulateResponse(double thetaTrue, double epsi, double delta)
	{
		if (Double.isNaN(epsi) || (Double.isNaN(delta)))
				return Integer.MIN_VALUE;
		else
			return Math.random() <= getProbCorrectResponseBM(thetaTrue,epsi,delta)?1:0;
	}
	
	// random initialization (may incorporate prior information on user)
	public static ArrayList<Double> initTheta(int n)
	{
		ArrayList<Double> thetaList = new ArrayList<Double>();
		
		for(int i = 0; i < n; i++)
		{
			double newTheta = Math.random()-1.5;
			thetaList.add(newTheta);
		}
		
		return thetaList;
	}
	
	// random initialization (may incorporate prior information on user)
	public static ArrayList<Double> initInfo(int n)
	{	
		return initilizeList(0.0, n);
	}
	
	public static int selectItem(
			ArrayList<Double> theta,  
			ArrayList<ArrayList<Double>> epsi, 
			ArrayList<ArrayList<Double>> delta, 
			ArrayList<Double> infoSum,
			ArrayList<Integer> presentedItems)
	{
		int nrule = delta.size();
		int nitems = presentedItems.size();
		ArrayList<ArrayList<Double>> infoList = initilizeList(nrule,nitems);
		ArrayList<ArrayList<Double>> infoTmp = initilizeList(nrule,nitems);
		
		ArrayList<Double> infoTotal = initilizeList(0.0, nrule);
		ArrayList<Double> weightsRule = initilizeList(0.0, nrule);
		
		for (int r = 0; r < nrule; r++)
		{
			infoList.set(r,getInfoBM(theta.get(r), epsi.get(r),delta.get(r)));
			infoTotal.set(r, getSum(getInfoBM(theta.get(r), epsi.get(r),delta.get(r))));
			weightsRule.set(r, 1.0 - infoSum.get(r)/infoTotal.get(r));
			infoTmp.set(r, mult(infoList.get(r), weightsRule.get(r)));
		}
		
		ArrayList<Double> infoVec = initilizeList(0.0, nrule);
		
		for (int i = 0; i < nitems; i++)
		{
			for (int r = 0; r < nrule; r++)
			{
				double setVec = 0.0;
				if(!infoTmp.get(r).get(i).isNaN())
					setVec = infoTmp.get(r).get(i);
				infoVec.set(i, infoVec.get(i) + setVec);
			}
		}
		
		double max = 0;
		int maxIndex = 0;
		for (int i = 0; i < presentedItems.size(); i++)
		{
			double cur = 0;
			if (presentedItems.get(i) == 0)
			{
				cur = infoVec.get(i);
			}
			
			if (cur > max){
				max = cur;
				maxIndex = i;
			}
		}
		return maxIndex;
	}
	
	public static double updateTheta(double theta, ArrayList<Double> epsi, ArrayList<Double> delta, ArrayList<Integer> respItem, int slct)
	{	
		double theta_step = 0.5;
		double tolerance = 1e-5;
		int diff_iter = 1;
		int maxSteps = 1000;
		
		if(respItem.get(slct) == Integer.MIN_VALUE)
			return theta;
		
		if (epsi.get(slct).isNaN() || delta.get(slct).isNaN())
			return theta;
		
		
		
		return theta;
	}
	
	private static ArrayList<Double> mult(ArrayList<Double> list, double d)
	{
		ArrayList<Double> multList = new ArrayList<Double>();
		for(double ld : list)
		{
			multList.add(ld*d);
		}
		return multList;
	}
	
	private static double getSum(ArrayList<Double> list)
	{
		double sum = 0.0;
		for (Double d: list)
		{
			if (!d.isNaN())
				sum += d;
		}
		return sum;
	}
	
	public static ArrayList<ArrayList<Double>> initilizeList(int elements, int elements2)
	{
		ArrayList<ArrayList<Double>> list = new ArrayList<ArrayList<Double>>();
		for (int i = 0; i < elements; i++)
		{
			list.add(initilizeList(0.0,elements2));
		}
		return list;
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
	
	public static void main(String[] args)
	{
		ArrayList<Double> list = initilizeList(0.0,5);
		System.out.println(list.size());
		for (Double d: list)
		{
			System.out.println("Double: " + d);
		}
	}
}
