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
				fw.write(elementNode.item(j).getNodeName() + " " + elementNode.item(j).getTextContent().trim() + " ");
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

		FileWriter writer = new FileWriter("index.html");

		writer.write("<!DOCTYPE html>\r\n" + "<html>\r\n" + "<body>\r\n" + "\r\n"
				+ "<canvas id=\"myCanvas\" width=\"1920\" height=\"1080\" style=\"border:1px solid #d3d3d3;\">\r\n"
				+ "Your browser does not support the HTML5 canvas tag.</canvas>\r\n" + "\r\n" + "<script>\r\n"
				+ "var c = document.getElementById(\"myCanvas\");\r\n" + "var ctx = c.getContext(\"2d\");\r\n"
				+ "ctx.beginPath();\r\n");
		for (int i = 0; i < listElement.size(); i++) {
			String sw = (String) listElement.get(i).get("w");
			String sh = (String) listElement.get(i).get("h");
			String sx = (String) listElement.get(i).get("x");
			String sy = (String) listElement.get(i).get("y");
			writer.write(String.format("ctx.rect(%s, %s, %s, %s);\r\n" + "ctx.stroke();\r\n", sx, sy, sw, sh));
		}
		writer.write("</script> \r\n" + "\r\n" + "</body>\r\n" + "</html>");
		writer.close();

	}

}
