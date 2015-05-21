import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;


public class MultiplicationInverseCsvToJavaCode {

	/** reads a CSV file and generates some java code for the MultiplicationInverseGameCommunicationServiceServlet class.
	 * Remember to change the fields infile and outfile to fit your needs!
	 * @param args
	 */
	private static String infile = "/home/justin/Downloads/Timing_dyscalculia.csv";
	private static String outfile = "/home/justin/Downloads/Timing_dyscalculia.java";
			
	public static void main(String[] args) {
		ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>();
		BufferedReader br;
		PrintWriter writer;
		try {
			br= new BufferedReader(new FileReader(infile));
			String line;
			String[] values;
			int i=0;
			//System.out.println(br.readLine());
			while ((line = br.readLine()) != null && i<16) {
				values = line.split(";");
				table.add(new ArrayList<String>());
				table.get(table.size()-1).addAll( Arrays.asList(values));
				i++;
				System.out.println("line");
			}
			br.close();
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(table.isEmpty()) System.out.println("Stringtable is empty");
		try {
			writer = new PrintWriter(outfile, "UTF-8");
			writer.println("ArrayList<MultiplicationInverseItem> items = new ArrayList<MultiplicationInverseItem>();\n\n" +
			"// The complex items.");
			System.out.println("first line written");
			for(int i=0; i<16; i++) {
				writer.println("MultiplicationInverseItem item" + (i+1) 
						+ " = new MultiplicationInverseItem( " + table.get(i).get(0) +
						", " + table.get(i).get(1) +
						", " + table.get(i).get(2) + 
						", ");
				if(i<8) writer.print("false");
				else writer.print("true");
				writer.println(");");
				for(int j=0; j<12; j++) {
					writer.println("item" + (i+1) +
							".addPossibleAnswer(" + table.get(i).get(4+j) +
							");");
				}
				writer.println("items.add(item" + (i+1) +
						");\n");
				
			}
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}

}
