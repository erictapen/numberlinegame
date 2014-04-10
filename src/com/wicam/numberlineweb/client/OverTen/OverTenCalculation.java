package com.wicam.numberlineweb.client.OverTen;

import com.google.gwt.user.client.rpc.IsSerializable;

public class OverTenCalculation implements IsSerializable {

	private Sign sign;
	private int first;
	private int second;
	private boolean taken;
	
	public enum Sign implements IsSerializable{
		ADD {
			@Override
			public String toString() {
				return "+";
			}
		},
		SUB {
			@Override
			public String toString() {
				return "-";
			}
		},
		MULT {
			@Override
			public String toString() {
				return "x";
			}
		},
		DIV {
			@Override
			public String toString() {
				return "÷";
			}
		};

	
		public static int calc(Sign s, int a, int b) throws ArithmeticException{
			switch (s) {
			case ADD:
				return a+b;
			case SUB:
				return a-b;
			case MULT:
				return a*b;
			case DIV:
				return a/b;
			default:
				return 0;
			}
		}
	
	}

	/*
	 * For IsSerializable, "0 + 0"
	 */
	public OverTenCalculation(){
		this.sign = Sign.ADD;
		this.first = 0;
		this.second = 0;
		this.taken = false;
	}
	
	public OverTenCalculation(Sign sign, int first, int second) {
		this.sign = sign;
		this.first = first;
		this.second = second;
		this.taken = false;
	}
	
	// Clone-constructor
	public OverTenCalculation(OverTenCalculation other) {
		this.first = other.getFirst();
		this.second = other.getSecond();
		this.sign = other.getSign();
		this.taken = other.isTaken();
	}
	
	
	/**
	 * @return Calculates the result of this
	 * @throws ArithmeticException In case of division by zero
	 */
	public int calc() throws ArithmeticException {
		return Sign.calc(this.sign, this.first, this.second);
	}
	
	
	/**
	 * @return Returns a string like "2 + 5"
	 */
	@Override
	public String toString() {
		return ""+this.first+" "+this.sign+" "+this.second;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		} else if ((obj == null) || (obj.getClass() != this.getClass())) {
			return false;
		}
		OverTenCalculation c = (OverTenCalculation) obj;
		return (c.getFirst() == this.first) && (c.getSecond() == this.second) && (c.getSign() == this.sign);
	}


	/**
	 * @return Returns sign
	 */
	public Sign getSign() {
		return sign;
	}


	/**
	 * Set sign
	 * @param sign Set sign to this value
	 */
	public void setSign(Sign sign) {
		this.sign = sign;
	}


	/**
	 * @return Returns first
	 */
	public int getFirst() {
		return first;
	}


	/**
	 * Set first
	 * @param first Set first to this value
	 */
	public void setFirst(int first) {
		this.first = first;
	}


	/**
	 * @return Returns second
	 */
	public int getSecond() {
		return second;
	}


	/**
	 * Set second
	 * @param second Set second to this value
	 */
	public void setSecond(int second) {
		this.second = second;
	}
	

	/**
	 * Set taken
	 * @param taken Set taken to this value
	 */
	public void setTaken(boolean taken) {
		this.taken = taken;
	}

	/**
	 * @return Returns taken
	 */
	public boolean isTaken() {
		return taken;
	};
	
	
	
	
}
