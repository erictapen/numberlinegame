package com.wicam.numberlineweb.client;


import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TextBox;

/**
 * A simple popup box, prompting a question, returning a string value
 * @author patrick
 *
 */

public class TextPopupBox extends DialogBox {


	final TextBox text = new TextBox();
	final Button ok = new Button("OK!");


	public TextPopupBox(String request, String value) {

		this();
		setText(request);
		text.setValue(value);	

	}


	public TextPopupBox() {

		final FlowPanel p = new FlowPanel();

		p.add(text);
		p.add(ok);

		setWidget(p);

		//this adds ok-functionality when pressing enter
		text.addKeyDownHandler(new KeyDownHandler() {

			public void onKeyDown(KeyDownEvent event) {

				if (event.getNativeKeyCode() == 13)
					TextPopupBox.this.ok.click();
			}
		});

	}

	@Override 
	public void show() {

		super.show();
		this.center();
		text.setFocus(true);

	}

	public String getTextValue() {
		return text.getValue();
	}

	public void setTextValue(String value) {
		text.setValue(value);
	}

	public void setEnabled(boolean b) {
		ok.setEnabled(b);
		text.setEnabled(b);
	}

	/**
	 * Adds a click handler to the OK-Button. Will also be
	 * called when pressing enter.
	 * @param handler
	 */
	public void addClickHandler(ClickHandler handler) {
		ok.addClickHandler(handler);
	}



}

