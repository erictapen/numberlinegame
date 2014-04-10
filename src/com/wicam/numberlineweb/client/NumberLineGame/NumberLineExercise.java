package com.wicam.numberlineweb.client.NumberLineGame;


import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HTML;


public class NumberLineExercise extends HTML {


	private double fontsize = 30;
	private double manipulator = 7;

	@Override
	public void setText(String t) {


		super.setHTML("<div id='ex_wrap' style='width:200px;height:200px;'><div id='exercise' style='text-align:center;position:absolute'>" +t +"</div></div>");
		super.getElement().getStyle().setFontSize(30, Unit.PX);

		center();

	}

	private Timer t;


	public void booooing() {


		t = new Timer() {

			@Override
			public void run() {

				if (fontsize + manipulator > 30) {
					NumberLineExercise.this.getElement().getStyle().setFontSize(fontsize, Unit.PX);
					fontsize = fontsize +  manipulator ;
					manipulator = manipulator - 0.6;

				}else{
					NumberLineExercise.this.getElement().getStyle().setFontSize(30, Unit.PX);
					this.cancel();
					fontsize=30;
					manipulator=8;
				}

				center();
			}

		};

		t.scheduleRepeating(40);


	}
	
	
	private void center() {
		
		
		Element root = Document.get().getElementById("exercise");

		double h = root.getOffsetHeight();
		double w = root.getOffsetWidth();

		double hh = NumberLineExercise.this.getOffsetHeight();
		double ww = NumberLineExercise.this.getOffsetWidth();

		root.getStyle().setLeft((ww/2) - (w/2), Unit.PX);
		root.getStyle().setTop((hh/2) - (h/2), Unit.PX);
		
		
	}

}
