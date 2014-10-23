package com.wicam.numberlineweb.client.SpellingAssessment;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;

/**
 * Class to ask the user if he really wants to abort the assessment.
 * @author timfissler
 *
 */

class SpellingAssessmentCloseQuestion extends DialogBox {
	
	SpellingAssessmentCoordinator coord;

	public SpellingAssessmentCloseQuestion(SpellingAssessmentCoordinator coord) {
		// Set the dialog box's caption.
		setText("Möchtest du den Test wirklich abbrechen?");

		this.setAnimationEnabled(true);
		this.coord = coord;

		// DialogBox is a SimplePanel, so you have to set its widget property to
		// whatever you want its contents to be.
		Button ok = new Button("Ja");
		Button no = new Button("Nein");
		ok.setWidth("60px");
		no.setWidth("60px");
		ok.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				SpellingAssessmentCloseQuestion.this.hide();
				SpellingAssessmentCloseQuestion.this.coord.handleAssessmentAbortion();
			}
		});
		no.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				History.newItem("math_assessment",false);
				SpellingAssessmentCloseQuestion.this.hide();
			}
		});

		HorizontalPanel p = new HorizontalPanel();

		p.setSize("230px", "40px");

		p.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		p.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

		p.add(ok);
		p.add(no);

		this.setWidget(p);
		this.center();
		this.show();
	}
}