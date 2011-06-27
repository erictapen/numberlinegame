package com.wicam.numberlineweb.server.MathDiagnostics;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.wicam.numberlineweb.client.MathDiagnostics.AdditionItem;
import com.wicam.numberlineweb.client.MathDiagnostics.ItemTypes;
import com.wicam.numberlineweb.client.MathDiagnostics.isItem;

/**
 * class for retrieving items from a xml-file
 * @author shuber
 * 
 */

public class ItemListReader {

	Document doc = null;
	
	public ItemListReader(String fileName){
		SAXBuilder builder = new SAXBuilder();
		try {
			doc = builder.build( new File( fileName ) );
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<isItem> getList(int itemType){
		ArrayList<isItem> itemList = new ArrayList<isItem>();
		Element root = doc.getRootElement();
		List listParentElements = root.getChildren("item");
		for (int i = 0; i < listParentElements.size(); i++){
			Element elem  = (Element)(listParentElements.get( i ));
			if (elem.getAttributeValue("type") != null){
				if (itemType == ItemTypes.ADDITIONITEM &&
						elem.getAttributeValue("type").equals(ItemTypes.getTypeString(ItemTypes.ADDITIONITEM))){
					if (Integer.valueOf(((Element) elem.getChildren("id").get(0)).getText()) <= 10)
						itemList.add(createAdditionItem(elem));
				}
					
					
			}
		}
		// randomize order
		Collections.shuffle(itemList);
		return itemList;
	}
	
	private isItem createAdditionItem(Element elem){
		AdditionItem item = new AdditionItem();
		int id = Integer.valueOf(((Element) elem.getChildren("id").get(0)).getText());
		int addend1 = Integer.valueOf(((Element) elem.getChildren("addend1").get(0)).getText());
		int addend2 = Integer.valueOf(((Element) elem.getChildren("addend2").get(0)).getText());
		int solution1 = Integer.valueOf(((Element) elem.getChildren("solution1").get(0)).getText());
		int solution2 = Integer.valueOf(((Element) elem.getChildren("solution2").get(0)).getText());
		item.setAdditionItem(id, addend1, addend2, solution1, solution2);
		return item;
	}
}
