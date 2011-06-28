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

import com.wicam.numberlineweb.client.MathDiagnostics.Addition.AdditionItem;
import com.wicam.numberlineweb.client.MathDiagnostics.NumberComparison.NumberComparisonItem;
import com.wicam.numberlineweb.client.MathDiagnostics.NumberLine.NumberLineItem;
import com.wicam.numberlineweb.client.MathDiagnostics.ItemTypes;
import com.wicam.numberlineweb.client.MathDiagnostics.isItem;

/**
 * class for retrieving items from a xml-file
 * @author shuber
 * 
 */

public class ItemListReader {

	Document doc = null;
	
	/**
	 * Constructor
	 * 
	 * @param fileName	xml-file which should be read
	 */
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
	
	/**
	 * Retrieves items from the xml-file according to the itemType
	 * see ItemTypes.java for the constants
	 * 
	 * @param itemType	type of the items which should be retrieved
	 * @return			ArrayList of the items found in the xml-file
	 */
	public ArrayList<isItem> getList(int itemType){
		ArrayList<isItem> itemList = new ArrayList<isItem>();
		Element root = doc.getRootElement();
		List listParentElements = root.getChildren("item");
		for (int i = 0; i < listParentElements.size(); i++){
			Element elem  = (Element)(listParentElements.get( i ));
			if (elem.getAttributeValue("type") != null){
				if (itemType == ItemTypes.ADDITIONITEM &&
						elem.getAttributeValue("type").equals(ItemTypes.getTypeString(ItemTypes.ADDITIONITEM))){
					if (Integer.valueOf(((Element) elem.getChildren("id").get(0)).getText()) <= 10) // for testing purpose
						itemList.add(createAdditionItem(elem));
				}
				if (itemType == ItemTypes.NUMBERLINEITEM &&
						elem.getAttributeValue("type").equals(ItemTypes.getTypeString(ItemTypes.NUMBERLINEITEM))){
					itemList.add(createNumberLineItem(elem));
				}	
				if (itemType == ItemTypes.NUMBERCOMPARISON &&
						elem.getAttributeValue("type").equals(ItemTypes.getTypeString(ItemTypes.NUMBERCOMPARISON))){
					if (Integer.valueOf(((Element) elem.getChildren("id").get(0)).getText()) <= 10) // for testing purpose
						itemList.add(createNumberComparisonItem(elem));
				}		
			}
		}
		// randomize order
		Collections.shuffle(itemList);
		return itemList;
	}
	
	
	/**
	 * Reads the information from an element which is needed to create an addition item
	 * 
	 * @param elem	element identified by the tag item
	 * @return		returns an addition item
	 */
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
	
	
	/**
	 * Reads the information from an element which is needed to create a numberline item
	 * 
	 * @param elem	element identified by the tag item
	 * @return		returns a numberline item
	 */
	private isItem createNumberLineItem(Element elem){
		NumberLineItem item = new NumberLineItem();
		int id = Integer.valueOf(((Element) elem.getChildren("id").get(0)).getText());
		int leftNumber = Integer.valueOf(((Element) elem.getChildren("leftNumber").get(0)).getText());
		int rightNumber = Integer.valueOf(((Element) elem.getChildren("rightNumber").get(0)).getText());
		int exerciseNumber = Integer.valueOf(((Element) elem.getChildren("exerciseNumber").get(0)).getText());
		item.setNumberLineItem(id, leftNumber, rightNumber, exerciseNumber);
		return item;
	}
	
	
	/**
	 * Reads the information from an element which is needed to create a number comparison item
	 * 
	 * @param elem	element identified by the tag item
	 * @return		returns a number comparison item
	 */
	private isItem createNumberComparisonItem(Element elem){
		NumberComparisonItem item = new NumberComparisonItem();
		int id = Integer.valueOf(((Element) elem.getChildren("id").get(0)).getText());
		int numberTop = Integer.valueOf(((Element) elem.getChildren("numberTop").get(0)).getText());
		int numberBottom = Integer.valueOf(((Element) elem.getChildren("numberBottom").get(0)).getText());
		item.setNumberComparisonItem(id, numberTop, numberBottom);
		return item;
	}
}
