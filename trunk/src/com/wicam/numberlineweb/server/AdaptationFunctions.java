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
		ArrayList<ArrayList<Double>> infoList = initilizeListOfList(nrule,nitems);
		ArrayList<ArrayList<Double>> infoTmp = initilizeListOfList(nrule,nitems);
		
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
		double diff_iter = 1.0;
		int maxSteps = 10;
		
		if(respItem.get(slct) == Integer.MIN_VALUE)
			return theta;
		
		if (epsi.get(slct).isNaN() || delta.get(slct).isNaN())
			return theta;
		
		int nresp = countNotNA(respItem);
		int ncorrect = getSumInteger(respItem);
		
		if (ncorrect == 0)
			return theta - theta_step;
		else
			if (ncorrect == nresp)
				return theta + theta_step;
			else
				// Newton-Raphson
			{
				int ii = 0;
				while(diff_iter > tolerance && ii <= maxSteps)
				{
					ii = ii + 1;
					ArrayList<Double> p = getProbCorrectResponseBM(theta, epsi, delta);
					Double theta_new = theta - getSum(mult(delta, sub(respItem, p))) / getSum(mult(mult(delta,delta),mult(p, sub(1,p))));
					
					if (theta_new.isNaN())
						theta_new = theta + (Math.random()<0.5?-1.1:1.1) * tolerance;
					if(theta_new.isInfinite())
						theta_new = theta + Math.signum(theta_new) * 1.1 * tolerance;
					
					diff_iter = Math.abs(theta_new - theta);
					theta = theta_new;
				}
				return theta;
			}
	}
	
	private static ArrayList<Double> sub(int value , ArrayList<Double> list)
	{
		ArrayList<Double> multList = new ArrayList<Double>();
		for(int i = 0; i < list.size(); i++)
		{
			if (value == Integer.MIN_VALUE || list.get(i).isNaN())
				multList.add(Double.NaN);
			else
				multList.add(value-list.get(i));
		}
		return multList;
	}
	
	private static ArrayList<Double> sub(ArrayList<Integer> list1, ArrayList<Double> list2)
	{
		ArrayList<Double> multList = new ArrayList<Double>();
		for(int i = 0; i < list1.size(); i++)
		{
			if (list1.get(i) == Integer.MIN_VALUE || list2.get(i).isNaN())
				multList.add(Double.NaN);
			else
				multList.add(list1.get(i)-list2.get(i));
		}
		return multList;
	}
	
	private static ArrayList<Double> mult(ArrayList<Double> list1, ArrayList<Double> list2)
	{
		ArrayList<Double> multList = new ArrayList<Double>();
		for(int i = 0; i < list1.size(); i++)
		{
			if (list1.get(i).isNaN() || list2.get(i).isNaN())
				multList.add(Double.NaN);
			else
				multList.add(list1.get(i)*list2.get(i));
		}
		return multList;
	}
	
	private static int countNotNA(ArrayList<Integer> list)
	{
		int count = 0;
		for (int i = 0; i < list.size(); i++)
		{
			if (list.get(i) != Integer.MIN_VALUE)
				count++;
		}
		return count;
	}
	
	private static ArrayList<Double> mult(ArrayList<Double> list, Double d)
	{
		ArrayList<Double> multList = new ArrayList<Double>();
		for(Double ld : list)
		{
			if (ld.isNaN() || d.isNaN())
				multList.add(Double.NaN);
			else
				multList.add(ld*d);
		}
		return multList;
	}
	
	private static int getSumInteger(ArrayList<Integer> list)
	{
		int sum = 0;
		for (Integer i: list)
		{
			if (i != Integer.MIN_VALUE)
				sum += i;
		}
		return sum;
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
	
	public static ArrayList<ArrayList<Double>> initilizeListOfList(int elements, int elements2)
	{
		ArrayList<ArrayList<Double>> list = new ArrayList<ArrayList<Double>>();
		for (int i = 0; i < elements; i++)
		{
			list.add(initilizeList(0.0,elements2));
		}
		return list;
	}
	
	public static ArrayList<ArrayList<Double>> initilizeListOfList(int elements, int elements2, double d)
	{
		ArrayList<ArrayList<Double>> list = new ArrayList<ArrayList<Double>>();
		for (int i = 0; i < elements; i++)
		{
			list.add(initilizeList(d,elements2));
		}
		return list;
	}
	
	public static ArrayList<ArrayList<Integer>> initilizeListOfListNAsInteger(int elements, int elements2)
	{
		ArrayList<ArrayList<Integer>> list = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < elements; i++)
		{
			list.add(initilizeListInteger(Integer.MIN_VALUE,elements2));
		}
		return list;
	}
	
	public static ArrayList<ArrayList<Double>> initilizeListOfListNAs(int elements, int elements2)
	{
		ArrayList<ArrayList<Double>> list = new ArrayList<ArrayList<Double>>();
		for (int i = 0; i < elements; i++)
		{
			list.add(initilizeList(Double.NaN,elements2));
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
	
	public static ArrayList<Integer> initilizeListInteger(int value, int elements)
	{
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < elements; i++)
		{
			list.add(value);
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
	
	public static ArrayList<Double> initilizeRandomList(double d1, double d2, int elements)
	{
		ArrayList<Double> list = new ArrayList<Double>();
		for (int i = 0; i < elements; i++)
		{
			double randomDouble = Math.random()*(Math.abs(d1-d2))+d1;
			list.add(randomDouble);
		}
		return list;
	}
	
	public static ArrayList<Double> getNotPresented(ArrayList<Double> list, ArrayList<Integer> presented)
	{
		ArrayList<Double> newList = new ArrayList<Double>();
		for (int i = 0; i < presented.size(); i++)
		{
			if(presented.get(i) != 0)
				newList.add(list.get(i));
		}
		return newList;
	}
	
	public static void simulate()
	{
		Parametermatrix pm = new Parametermatrix();
		ArrayList<ArrayList<Double>> epsi = pm.getEpsiThree();
		ArrayList<ArrayList<Double>> delta = pm.getDeltaThree();
		
		int nitems = epsi.get(0).size();
		int nrules = epsi.size();
		
		ArrayList<Double> theta_true = initilizeRandomList(-2.0, 0.0, nrules);
		
		int max_iter = 20;
		
		ArrayList<Integer> presented = initilizeListInteger(0, nrules);
		ArrayList<ArrayList<Integer>> respItem = initilizeListOfListNAsInteger(nrules,nitems);
		ArrayList<ArrayList<Double>> thetaVec = initilizeListOfList(nrules,nitems,(double)max_iter);
		ArrayList<Double> infoVec = initilizeList(0.0, nitems);
		ArrayList<Double> theta = initTheta(nrules);
		ArrayList<Double> theta0 = initTheta(nrules);
		ArrayList<Double> infoSum = initInfo(nrules);
		
		ArrayList<ArrayList<Double>> infoList = initilizeListOfList(nrules,nitems);
		ArrayList<ArrayList<Double>> infoTmp = initilizeListOfList(nrules,nitems);
		ArrayList<Double> infoTotal = initilizeList(0.0, nrules);
		ArrayList<Double> weightsRule = initilizeList(0.0, nrules);
		
		for (int iter = 0; iter < max_iter; iter++)
		{
			for(int r = 0; r < nrules; r++)
			{
				infoList.set(r, getInfoBM(theta.get(r), epsi.get(r), delta.get(r)));
				infoTotal.set(r, getSum(getInfoBM(theta.get(r), epsi.get(r), delta.get(r))));
				weightsRule.set(r, 1.0 - infoSum.get(r)/infoTotal.get(r));
				infoTmp.set(r, mult(infoList.get(r), weightsRule.get(r)));
			}
			for(int i = 0; i < nitems; i++)
			{
				for (int r = 0; r < nrules; r++)
				{
					infoVec.set(i, infoVec.get(i) + infoTmp.get(r).get(i)==Double.NaN?0:infoTmp.get(r).get(i));
				}
			}
			
			int slct = selectItem(theta, epsi, delta, infoSum, presented);
			presented.set(slct, iter);
			
			for(int r = 0; r < nrules; r++)
			{
				respItem.get(r).set(slct, simulateResponse(theta_true.get(r), epsi.get(r).get(slct),delta.get(r).get(slct)));
			}
			
			for(int r = 0; r < nrules; r++)
			{
				theta.set(r, updateTheta(theta.get(r),epsi.get(r),delta.get(r),respItem.get(r),slct));
				infoSum.set(r, getSum(getInfoBM(theta.get(r),getNotPresented(epsi.get(r),presented),getNotPresented(delta.get(r),presented))));
			}
		}
		
		printArrayList(theta_true);
		printArrayList(theta);
	}
	
	public static void main(String[] args)
	{
		simulate();
	}
}
