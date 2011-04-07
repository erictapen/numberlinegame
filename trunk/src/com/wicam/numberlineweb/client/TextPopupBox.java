package com.wicam.numberlineweb.client;


import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;

/**
 * A simple popup box, prompting a question, returning a string value
 * @author patrick
 *
 */

public class TextPopupBox extends DialogBox {


	final TextBox text = new TextBox();
	final Button ok = new Button("OK!");
	protected VerticalPanel p = new VerticalPanel();
	final VerticalPanel v = new VerticalPanel();

	public TextPopupBox(String request, String value) {

		this();
		setText(request);
		text.setValue(value);	

	}


	public TextPopupBox() {

		HorizontalPanel f = new HorizontalPanel();
		

		f.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		f.setSpacing(5);
		f.add(new HTML("Name: "));
		f.add(text);
		

		p.add(f);
		p.setSpacing(5);
		
		v.add(p);
		v.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		ok.setWidth("60px");
		v.add(ok);
		v.setSpacing(10);
		setWidget(v);

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

