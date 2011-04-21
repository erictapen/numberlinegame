package com.wicam.numberlineweb.client.chat;


import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;

/**
 * A simple chatview
 * @author patrick
 *
 */

public class ChatView extends Composite{


	final AbsolutePanel motherPanel = new AbsolutePanel();

	final TextArea ta = new TextArea();
	final TextBox tb = new TextBox();
	final Button sendButton = new Button();
	final int boxWidth = 750;

	private ChatController c;

	public ChatView() {

		this.initWidget(motherPanel);

	}

	public void addController(ChatController c) {
		this.c=c;
	}

	public void init() {

		ta.setWidth((boxWidth-100) + "px");
		ta.setHeight("70px");

		tb.setWidth((boxWidth-100) + "px");
		ta.setReadOnly(true);
		ta.addStyleName("chatwindow");
		
		ta.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				tb.setFocus(true);
				
			}
			
			
			
		});
		
		sendButton.setWidth("86px");
		sendButton.setHeight("106px");
		sendButton.setText("Senden");

		sendButton.addClickHandler((ClickHandler) c);

		motherPanel.getElement().getStyle().setPosition(Position.RELATIVE);
		motherPanel.setHeight("110px");

		motherPanel.add(ta);
		motherPanel.add(tb);

		motherPanel.add(sendButton);

		motherPanel.setWidgetPosition(ta, 0, 0);
		motherPanel.setWidgetPosition(tb, 0, 82);
		motherPanel.setWidgetPosition(sendButton, boxWidth-88, 2);

		//this provides sending behaviour when pressing enter
		tb.addKeyDownHandler(new KeyDownHandler() {

			@Override
			public void onKeyDown(KeyDownEvent event) {

				if (event.getNativeKeyCode() == 13) {
					sendButton.click();
				}
			}
		});
	}


	/**
	 * Return the text entered in the msg line
	 * @return
	 */
	public String getEnteredText() {
		return tb.getValue();
	}

	public void clearEnteredText() {
		tb.setValue("");
	}

	/**
	 * Add a line to the chat view
	 * @param from
	 * @param msg
	 */
	public void addLine(String from, String msg) {

		String old = ta.getValue();
		if (!old.equals("")) old += "\n";
		old += from + ": " + msg;
		ta.setValue(old);
		ta.setCursorPos(ta.getValue().length());
		ta.getElement().setScrollTop(ta.getElement().getScrollHeight());

	}

	public void addLine(String msg) {

		String old = ta.getValue();
		if (!old.equals("")) old += "\n";
		old += msg;
		ta.setValue(old);
		ta.setCursorPos(ta.getValue().length());
		ta.getElement().setScrollTop(ta.getElement().getScrollHeight());

	}



}
