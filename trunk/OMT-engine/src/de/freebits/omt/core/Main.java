package de.freebits.omt.core;

import java.io.File;

import javax.swing.JFileChooser;
import javax.xml.bind.JAXBException;

import de.freebits.omt.core.xml.JAXBHelper;
import de.freebits.omt.core.xml.entities.motifextraction.MexType;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException,
			JAXBException {

		final JFileChooser fs = new JFileChooser();
		fs.showOpenDialog(null);
		final File xmlFile = fs.getSelectedFile();

		Class<?> clss = MexType.class;

		MexType mexRoot = (MexType) JAXBHelper.unmarshal(xmlFile, clss);
		if (mexRoot != null) {
			System.out.println(":)");
		}
	}

}
