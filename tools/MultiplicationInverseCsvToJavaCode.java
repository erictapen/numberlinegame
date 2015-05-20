import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;


public class MultiplicationInverseCsvToJavaCode {

	/** reads a CSV file and generates some java code for the MultiplicationInverseGameCommunicationServiceServlet class.
	 * Remember to change the fields infile and outfile to fit your needs!
	 * @param args
	 */
	private static String infile = "/home/justin/Downloads/Timing_dyscalculia.csv";
	private static String outfile = "/home/justin/Downloads/Timing_dyscalculia.csv";
			
	public static void main(String[] args) {
		String[][] table = new String[16][];
		BufferedReader br;
		PrintWriter writer;
		try {
			br= new BufferedReader(new FileReader(infile));
			String line;
			String[] values;
			int i=0;
			while ((line = br.readLine()) != null && i<16) {
				values = line.split(";");
				table[i] = values;
				i++;
			}
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(table[0][0] == null) System.out.println("Stringtable is empty");
		try {
			writer = new PrintWriter(outfile, "UTF-8");
			writer.println("ArrayList<MultiplicationInverseItem> items = new ArrayList<MultiplicationInverseItem>();\n\n" +
			"// The complex items.");
			for(int i=0; i<16; i++) {
				writer.println("MultiplicationInverseItem item" + (i+1) 
						+ " = new MultiplicationInverseItem( " + table[i][0] +
						", " + table[i][1] +
						", " + table[i][2] + 
						", ");
				if(i<8) writer.print("false");
				else writer.print("true");
				writer.println(");");
				for(int j=0; j<12; j++) {
					writer.println("item" + (i+1) +
							".addPossibleAnswer(" + table[i][4+j] +
							");");
				}
				writer.println("items.add(item" + i +
						");\n");
			}
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
