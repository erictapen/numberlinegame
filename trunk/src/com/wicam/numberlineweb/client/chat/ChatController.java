package com.wicam.numberlineweb.client.chat;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;

public class ChatController implements ClickHandler, KeyPressHandler{

	ChatCoordinator c;
	
	public ChatController(ChatCoordinator c) {
		this.c=c;
	}	
	
	@Override
	public void onClick(ClickEvent event) {
		c.send();
	}

	@Override
	public void onKeyPress(KeyPressEvent event) {
		// TODO Auto-generated method stub
		
	}

}
