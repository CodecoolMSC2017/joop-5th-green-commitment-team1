package com.codecool.greencommitment.server;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class Charter extends JFrame {

    private XYSeriesCollection dataSet;

    public Charter(String[] filename) throws ParserConfigurationException, SAXException, IOException {

        this.dataSet = new XYSeriesCollection();
        for (int i = 0; i < filename.length; i++) {
            createDataset(filename[i]);
        }
        XYDataset dataset = dataSet;
        JFreeChart chart = createChart(dataset,filename.length);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        add(chartPanel);

        pack();
        setTitle("Line chart");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void createDataset(String filename) throws ParserConfigurationException, IOException, SAXException {

        XYSeries series = new XYSeries(filename);

        File fXmlFile = new File( filename + ".xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);
        doc.getDocumentElement().normalize();
        NodeList nList = doc.getElementsByTagName("measurement");

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            Node nTime = nNode.getChildNodes().item(1);
            Node nValue = nNode.getChildNodes().item(3);
            Node nType = nNode.getChildNodes().item(5);
            long measTime = Long.parseLong(nTime.getTextContent());
            float measValue = Float.parseFloat(nValue.getTextContent());
            String measType = nType.getTextContent();

            series.add(measTime, measValue);

        }

        dataSet.addSeries(series);

    }

    public JFreeChart createChart(XYDataset dataSet, int numberOfLines) {

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Team1 Random Measurement",
                "Time",
                "Temperature (C)",
                dataSet,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

        Random rand = new Random();
        for (int j = 0; j < numberOfLines; j++){

            renderer.setSeriesPaint(0, new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
            renderer.setSeriesStroke(0, new BasicStroke(1.5f));
        }

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);

        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

        chart.getLegend().setFrame(BlockBorder.NONE);

        chart.setTitle(new TextTitle("Team1 Random Measurement",
                        new Font("Serif", java.awt.Font.BOLD, 18)
                )
        );

        return chart;

    }

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        String[] arr = new String[2];
        arr[0] = "6C-88-14-90-FE-F0";
        arr[1] = "64-6E-69-1E-F0-BB";
        Charter ex = new Charter(arr);
        ex.setVisible(true);

    }
}
