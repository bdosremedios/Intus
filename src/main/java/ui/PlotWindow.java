package ui;

import model.Emotion;
import model.EmotionalState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.TimeZone;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

public class PlotWindow extends JFrame {

    ArrayList<Date> dateList;
    ArrayList<Integer> jsValues;
    ArrayList<Integer> afValues;
    ArrayList<Integer> saValues;
    ArrayList<Integer> tlValues;
    SimpleDateFormat extractFromString;
    SimpleDateFormat setToString;

    // EFFECTS: creates an application window to show plot of trend of emotionalStates in previous journal entries
    public PlotWindow(WindowAdapter windowBehavior) {

        initializeLists();

        setFrameSettings(windowBehavior);

        setDateFormatStrings();
        ArrayList<String> validFileHashDates = getValidFileHashDates();
        ArrayList<String> sortedValidFileHashDates = sortHashDates(validFileHashDates);

        extractXYValues(sortedValidFileHashDates);
        TimeSeriesCollection data = createTimeSeriesCollection();
        addTimeSeriesChart(data);

        setVisible(true);

    }

    private void addTimeSeriesChart(TimeSeriesCollection data) {

        JFreeChart chart = ChartFactory.createTimeSeriesChart("Emotional Trend Over Time", "Time",
                                                "Emotion Scale Value", data, true, false, false);

        chart.getXYPlot().getRangeAxis().setRange(0., 100.);
        chart.getXYPlot().getRangeAxis().setAxisLinePaint(new Color(225, 225, 225));
        chart.getXYPlot().getRangeAxis().setTickMarkPaint(new Color(225, 225, 225));
        chart.getXYPlot().getRangeAxis().setTickLabelPaint(new Color(225, 225, 225));
        chart.getXYPlot().getRangeAxis().setLabelPaint(new Color(225, 225, 225));

        chart.getXYPlot().getDomainAxis().setAxisLinePaint(new Color(225, 225, 225));
        chart.getXYPlot().getDomainAxis().setTickMarkPaint(new Color(225, 225, 225));
        chart.getXYPlot().getDomainAxis().setTickLabelPaint(new Color(225, 225, 225));
        chart.getXYPlot().getDomainAxis().setLabelPaint(new Color(225, 225, 225));

        chart.getPlot().setBackgroundPaint(new Color(175, 175, 175));
        chart.setBackgroundPaint(new Color(50, 50, 50));

        chart.getTitle().setPaint(new Color(225, 225, 225));
        chart.getLegend().setBackgroundPaint(new Color(175, 175, 175));

        ChartPanel chartPanel = new ChartPanel(chart);

        add(chartPanel);
    }

    private TimeSeriesCollection createTimeSeriesCollection() {

        TimeSeries jsSeries = new TimeSeries("(Low: Sadness | High: Joy)");
        TimeSeries faSeries = new TimeSeries("(Low: Anger | High: Fear)");
        TimeSeries saSeries = new TimeSeries("(Low: Anticipation | High : Surprise)");
        TimeSeries tlSeries = new TimeSeries("(Low: Loathing | High: Trust)");

        for (int i = 0; i < dateList.size(); i++) {

            Second firstIn = new Second(dateList.get(i));

            double secondIn = jsValues.get(i);
            jsSeries.add(firstIn, secondIn);

            secondIn = afValues.get(i);
            faSeries.add(firstIn, secondIn);

            secondIn = saValues.get(i);
            saSeries.add(firstIn, secondIn);

            secondIn = tlValues.get(i);
            tlSeries.add(firstIn, secondIn);

        }

        TimeSeriesCollection data = storeInTSCollection(jsSeries, faSeries, saSeries, tlSeries);

        return data;
    }

    private TimeSeriesCollection storeInTSCollection(TimeSeries jsSeries, TimeSeries faSeries, TimeSeries saSeries,
                                                     TimeSeries tlSeries) {

        TimeSeriesCollection data = new TimeSeriesCollection();
        data.addSeries(jsSeries);
        data.addSeries(faSeries);
        data.addSeries(saSeries);
        data.addSeries(tlSeries);

        return data;

    }

    private void extractXYValues(ArrayList<String> sortedValidFileHashDates) {

        EmotionalState tempEmoteState;
        ArrayList<Emotion> tempMoodVector;

        for (String hashDate : sortedValidFileHashDates) {

            tempEmoteState = new EmotionalState();
            tempEmoteState.load("./data/emotionalstate" + hashDate + ".txt");
            tempMoodVector = tempEmoteState.getMoodVector();

            try {
                dateList.add(extractFromString.parse(hashDate));
            } catch (ParseException p) {
                p.printStackTrace();
            }

            jsValues.add(tempMoodVector.get(0).getScaleValue());
            afValues.add(tempMoodVector.get(1).getScaleValue());
            saValues.add(tempMoodVector.get(2).getScaleValue());
            tlValues.add(tempMoodVector.get(3).getScaleValue());

        }

    }

    private ArrayList<String> sortHashDates(ArrayList<String> validFileHashDates) {

        // Sort date in descending order
        ArrayList<Date> sortedDates = new ArrayList<>();
        ArrayList<String> sortedValidFileHashDates = new ArrayList<>();

        for (String hashDate : validFileHashDates) {
            try {
                sortedDates.add(extractFromString.parse(hashDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        Collections.sort(sortedDates);
        Collections.reverse(sortedDates);

        for (Date d : sortedDates) {
            sortedValidFileHashDates.add(extractFromString.format(d));
        }

        return sortedValidFileHashDates;

    }

    private void setDateFormatStrings() {
        extractFromString = new SimpleDateFormat("yyMMddHHmmss");
        setToString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        extractFromString.setTimeZone(TimeZone.getTimeZone("UTC"));
        setToString.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    private ArrayList<String> getValidFileHashDates() {

        // Stores all file numbers with journal then ten digit number then .txt as is the standard
        File[] recordings = new File("./data/").listFiles();
        ArrayList<String> validFileHashDates = new ArrayList<>();

        if (recordings != null) {
            for (File f : recordings) {
                try {
                    boolean firstHalfCheck = isInt(f.getName().substring(7, 13));
                    boolean secondHalfCheck = isInt(f.getName().substring(13, 19));
                    boolean fileCheck = (f.getName().substring(19, 23).equals(".txt"));
                    boolean nameCheck = (f.getName().substring(0, 7).equals("journal"));
                    if (firstHalfCheck && secondHalfCheck && fileCheck && nameCheck) {
                        validFileHashDates.add(f.getName().substring(7, 19));
                    }
                } catch (StringIndexOutOfBoundsException e) {
                    // pass
                }
            }
        }

        return validFileHashDates;

    }

    private void setFrameSettings(WindowAdapter windowBehavior) {
        getContentPane().setBackground(new Color(50, 50, 50));
        setTitle("Visualize Emotion Trends");
        setSize(800, 600);
        setLocationRelativeTo(null);
        addWindowListener(windowBehavior);
    }

    private void initializeLists() {
        dateList = new ArrayList<Date>();
        jsValues = new ArrayList<Integer>();
        afValues = new ArrayList<Integer>();
        saValues = new ArrayList<Integer>();
        tlValues = new ArrayList<Integer>();
    }

    private boolean isInt(String test) {
        try {
            Integer.parseInt(test);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
