import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
//import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Apps {

	private static String getFileExtension(File file) {
		String name = file.getName();
		int lastIndexOf = name.lastIndexOf(".");
		if (lastIndexOf == -1) {
			return ""; // empty extension
		}
		return name.substring(lastIndexOf);
	}

	private static ArrayList readXMLFile(File file) throws SAXException, IOException, ParserConfigurationException {
		ArrayList<Map> listElement = new ArrayList<Map>();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(file);

		Element e = doc.getDocumentElement();

		NodeList mockups = e.getElementsByTagName("mockup");
		Element mockup = (Element) mockups.item(0);

		NodeList controls = mockup.getElementsByTagName("controls");
		Element control = (Element) controls.item(0);

		NodeList elements = control.getElementsByTagName("element");

		FileWriter fw = new FileWriter("data.txt");

		for (int i = 0; i < elements.getLength(); i++) {

			Element element = (Element) elements.item(i);
			NodeList elementNode = element.getChildNodes();

			for (int j = 0; j < elementNode.getLength(); j++) {
				fw.write(elementNode.item(j).getNodeName() + " " + elementNode.item(j).getTextContent().stripTrailing()
						+ " ");
			}
			fw.write("\n");
		}

		fw.close();
		
		return listElement;
	}

	public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException{

		String pathname = "DemoWireframe.xml"; // path of file
		File file = new File(pathname);

		ArrayList<Map> listElement = new ArrayList<>();

		if (getFileExtension(file).equals(".xml")) {
			listElement = readXMLFile(file);
		}

		

	}

}
