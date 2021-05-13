package com.formaplus.utils;

import java.util.HashMap;
import java.util.Map;


import javafx.collections.ObservableList;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class Reporting {

	public static void showReport(String parameter, String reportFileName, ObservableList<?> observableList) {
		try {
			/* Convert List to JRBeanCollectionDataSource */
			JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(observableList);

			/* Map to hold Jasper report Parameters */
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put(parameter, itemsJRBean);

			// read jrxml file and creating jasperdesign object
			// InputStream input = new FileInputStream(new
			// File("C:\\Users\\amitk\\Desktop\\JASPER\\JasperReport_A4.jrxml"));

			JasperDesign jasperDesign = JRXmlLoader.load(Reporting.class
					.getResourceAsStream("/com/formaplus/resources/jrxml/" + reportFileName + ".jrxml"));

			/* compiling jrxml with help of JasperReport class */
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

			/* Using jasperReport object to generate PDF */
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

			/* call jasper engine to display report in jasperviewer window */
			JasperViewer.viewReport(jasperPrint, false);

			/* outputStream to create PDF */
			// OutputStream outputStream = new FileOutputStream(new File(outputFile));

			/* Write content to PDF file */
			// JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void showReport(String reportName, Map<String, Object> parameters) {
		try {
			JasperDesign jasperDesign = JRXmlLoader.load(Reporting.class.getResourceAsStream("/com/formaplus/resources/jrxml/" + reportName));
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
			JasperViewer.viewReport(jasperPrint, false);
			//new JasperViewerFX().viewReport("JRBeanCollectionDataSource example", jasperPrint);
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static JasperPrint getJasperPrint(String reportName, Map<String, Object> parameters) {
		try {
			parameters.put("ADDRESS", "Agoé-Logopé");
			parameters.put("PHONE", "+228 98647306");
			parameters.put("NAME", "CENTRE DE FORMATION");
			JasperDesign jasperDesign = JRXmlLoader.load(Reporting.class.getResourceAsStream("/com/formaplus/resources/jrxml/" + reportName));
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
			//JasperViewer.viewReport(jasperPrint, false);
			return jasperPrint;
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
