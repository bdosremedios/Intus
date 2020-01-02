package ui;

import model.EmotionalState;
import recording.JournalRecording;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.TimeZone;

public class BrowseWindow extends JFrame {
    private ArrayList<JButton> fileButtonList;
    SimpleDateFormat extractFromString;
    SimpleDateFormat setToString;

    // EFFECTS: creates an application window for browsing through old recorded journals
    public BrowseWindow(WindowAdapter windowBehavior, WindowAdapter browseSpecificBehavior) {

        setFrameSettings(browseSpecificBehavior);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(5, 5, 5, 5);
        c.gridy = 0;
        c.gridx = 0;
        c.weighty = 1.;
        c.weightx = 1.;

        ArrayList<String> validFileHashDates = getValidFileHashDates();

        // For all valid hashes, create a button representing that file, with an action listener to edit that
        // journal and emotional state
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(new Color(50, 50, 50));
        JScrollPane scrollPane = new JScrollPane(buttonPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Set format date strings
        extractFromString = new SimpleDateFormat("yyMMddHHmmss");
        setToString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        extractFromString.setTimeZone(TimeZone.getTimeZone("UTC"));
        setToString.setTimeZone(TimeZone.getTimeZone("UTC"));

        ArrayList<String> sortedValidFileHashDates = sortHashDates(validFileHashDates);

        setFileButtons(windowBehavior, buttonPanel, sortedValidFileHashDates);

        add(scrollPane, c);

        setVisible(true);

    }

    private void setFileButtons(final WindowAdapter windowBehavior, JPanel buttonPanel,
                                ArrayList<String> sortedValidFileHashDates) {

        fileButtonList = new ArrayList<>();
        String fileLabel;
        JButton tempButton;

        for (final String hashDate : sortedValidFileHashDates) {
            try {
                fileLabel = setToString.format(extractFromString.parse(hashDate));
                tempButton = new JButton();
                tempButton.setText("Journal " + fileLabel);
                tempButton.setFont(new Font("Arial", Font.BOLD, 15));
                tempButton.setBackground(new Color(125, 125, 125));
                setFileJournalActionListener(windowBehavior, tempButton, hashDate);
                fileButtonList.add(tempButton);
                buttonPanel.add(tempButton);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }

    private void setFileJournalActionListener(final WindowAdapter windowBehavior,
                                              JButton tempButton, final String hashDate) {

        tempButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JournalRecording journal = new JournalRecording();
                journal.load("./data/journal" + hashDate + ".txt");
                EmotionalState emoteState = new EmotionalState();
                emoteState.load("./data/emotionalstate" + hashDate + ".txt");
                new JournalWindow(true, journal, emoteState, windowBehavior);
                resetWindowListener();
                dispose();
            }
        });

    }

    private void resetWindowListener() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Do nothing now
            }

            @Override
            public void windowClosed(WindowEvent e) {
                // Do nothing now
            }
        });
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

    private void setFrameSettings(WindowAdapter browseSpecificBehavior) {
        getContentPane().setBackground(new Color(50, 50, 50));
        setTitle("Browse Previous Entries");
        setSize(273, 600);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        addWindowListener(browseSpecificBehavior);
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
