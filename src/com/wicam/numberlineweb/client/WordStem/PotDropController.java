
package com.wicam.numberlineweb.client.WordStem;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.VetoDragException;
import com.allen_sauer.gwt.dnd.client.drop.SimpleDropController;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class PotDropController extends SimpleDropController {


	private Pot pot;
	private final String LABEL_ENGAGED = "label-engaged";
	private final String POT_ENGAGED = "pot-engaged";
	private WordStemGameController controller;

	public PotDropController(Pot dropTarget, WordStemGameController controller) {
		super(dropTarget);
		this.pot = dropTarget;
		this.controller = controller;
	}


	@Override
	public void onDrop(DragContext context) {
		for (Widget widget : context.selectedWidgets) {
			controller.dropped(pot.getStem(), ((Label) widget).getText());
		}
		super.onDrop(context);
	}

	@Override
	public void onEnter(DragContext context) {
		super.onEnter(context);
		for (Widget widget : context.selectedWidgets) {
			widget.addStyleName(LABEL_ENGAGED);
		}
		pot.addStyleName(POT_ENGAGED);
	}

	@Override
	public void onLeave(DragContext context) {
		for (Widget widget : context.selectedWidgets) {
			widget.removeStyleName(LABEL_ENGAGED);
		}
		pot.removeStyleName(POT_ENGAGED);
		super.onLeave(context);
	}

	@Override
	public void onPreviewDrop(DragContext context) throws VetoDragException {
		super.onPreviewDrop(context);
		//TODO doesAccept
//		if (!pot.doesAccept((Label) context.selectedWidgets.get(0))) {
//			controller.dropped(pot.getStem(), ((Label) context.selectedWidgets.get(0)).getText());
//			throw new VetoDragException();
//		}
	}


}
