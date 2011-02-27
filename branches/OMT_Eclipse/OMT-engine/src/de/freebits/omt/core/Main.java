package de.freebits.omt.core;

import java.io.File;

import javax.swing.JFileChooser;
import javax.xml.bind.JAXBException;

import de.freebits.omt.core.xml.JAXBHelper;
import de.freebits.omt.core.xml.entities.patterns.PatternsType;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException,
			JAXBException {

		// C Dur
		/*
		final byte[] harmony = { 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0 };
		final byte[] scale = HarmonyHelper.getScaleByHarmony(harmony, 0);
		System.out.println("scale = " + scale.toString());*/

		final JFileChooser fs = new JFileChooser();
		fs.showOpenDialog(null);
		final File xmlFile = fs.getSelectedFile();

		Class<?> clss = PatternsType.class;

		PatternsType pRoot = (PatternsType) JAXBHelper.unmarshal(xmlFile, clss);
		if (pRoot != null) {
			System.out.println(":)");
		}
	}

}
