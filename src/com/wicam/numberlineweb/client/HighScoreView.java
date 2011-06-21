package com.wicam.numberlineweb.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class HighScoreView extends Composite {


	private final VerticalPanel highScoreList = new VerticalPanel();
	private final ScrollPanel highScoreScrollWrap = new ScrollPanel();
	private final FlowPanel p = new FlowPanel();

	private ArrayList<? extends Player> playerList;
	private String[] colorList;



	public HighScoreView(ArrayList<? extends Player> playerList, String[] colorList) {

		this.playerList = playerList;
		this.colorList = colorList;

	}

	public void init(Panel panel) {

		p.add(new HTML("<div class='selectorTitle'>Highscore</div>"));

		p.addStyleName("highscore-wrap");
		
		highScoreScrollWrap.setHeight("300px");
		highScoreScrollWrap.setWidth("400px");
		
		Button close = new Button("Schließen");
		
		
		close.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				History.back();
				
			}
			
		});
		

		
		
		highScoreScrollWrap.addStyleName("highscore");
		highScoreScrollWrap.add(highScoreList);

		Collections.sort(playerList);

		Iterator<? extends Player> i = playerList.iterator();

		int n=0;

		while (i.hasNext()) {

			Player current=i.next();
			n++;
			highScoreList.add(new HTML("<div style='color:" + colorList[current.getColorId()]  +"'class='highscore-entry'><span class='highscore_pos'>"+n+". </span><span class='highscore_name'>"+current.getName()+" </span><span class='highscore_points'>"+current.getPoints()+"</span></div>"));

		}
		
		p.add(highScoreScrollWrap);
		
		close.getElement().getStyle().setFloat(Float.RIGHT);
		close.getElement().getStyle().setMarginBottom(20, Unit.PX);
		p.add(close);
		RootPanel.get().add(p);
		this.initWidget(p);
		panel.add(this);

	}


}
