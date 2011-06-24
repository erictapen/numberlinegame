package com.wicam.numberlineweb.client.MathDiagnostics;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;

public class MathDiagnosticsCreatePopupBox extends com.wicam.numberlineweb.client.GameCreatePopupBox {

	
	ListBox tasks = new ListBox();
	
	public MathDiagnosticsCreatePopupBox (String msg, String def) {
		
		super(msg,def);
		
		p.remove(1);
		tasks.addItem("Addition");
		
		HorizontalPanel g = new HorizontalPanel();
		g.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		g.setSpacing(5);
		g.add(new HTML("Aufgabe: "));
		g.add(tasks);
		
		super.p.add(g);
	}
	
	public int getTask() {
		if (tasks.getItemText(tasks.getSelectedIndex()).equals("Addition"))
			return ItemTypes.ADDITIONITEM;
		// is not possible
		return -1;
	}
	
}
