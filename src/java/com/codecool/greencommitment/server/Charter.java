package com.codecool.greencommitment.server;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class Charter extends JFrame {

    public Charter() throws ParserConfigurationException, SAXException, IOException {

        initUI();
    }

    public void initUI() throws IOException, SAXException, ParserConfigurationException {

        XYDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        add(chartPanel);

        pack();
        setTitle("Line chart");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public XYDataset createDataset() throws ParserConfigurationException, IOException, SAXException {

        XYSeries series = new XYSeries("random");
        List<Integer> times = new ArrayList<>();
        List<Integer> values = new ArrayList<>();

        File fXmlFile = new File("temp.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);
        doc.getDocumentElement().normalize();
        NodeList nList = doc.getElementsByTagName("measurement");

        int sizeof = nList.getLength();

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            Node nTime = nNode.getChildNodes().item(1);
            Node nValue = nNode.getChildNodes().item(3);
            Node nType = nNode.getChildNodes().item(5);
            long measTime = Long.parseLong(nTime.getTextContent());
            int measValue = Integer.parseInt(nValue.getTextContent());
            String measType = nType.getTextContent();

            series.add(measTime, measValue);

        }

        XYSeriesCollection dataSet = new XYSeriesCollection();
        /*
        series.add(18, 33);
        series.add(20, 28);
        series.add(25, 22);
        series.add(30, 980);
        series.add(40, 1410);
        series.add(50, 2350);
        */
        dataSet.addSeries(series);
        return dataSet;

    }

    public JFreeChart createChart(XYDataset dataSet) {

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
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesStroke(0, new BasicStroke(1.5f));

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

        Charter ex = new Charter();
        ex.setVisible(true);

    }
}
