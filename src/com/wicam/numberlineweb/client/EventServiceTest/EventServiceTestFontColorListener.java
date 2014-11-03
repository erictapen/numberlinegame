package com.wicam.numberlineweb.client.EventServiceTest;

public class EventServiceTestFontColorListener extends EventServiceTestFontColorListenerAdapter {
	
	private EventServiceTestCoordinator coord;
	
	public EventServiceTestFontColorListener(EventServiceTestCoordinator coord) {
		this.coord = coord;
	}

	@Override
	public void onFontColorSwitch(EventServiceTestFontColorEvent aFontColorEvent) {
		
		coord.updateFontColor(aFontColorEvent.getFontColor());

	}

}
