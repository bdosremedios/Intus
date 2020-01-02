package ui;

import model.EmotionalState;
import recording.JournalRecording;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.TimeZone;

public class JournalWindow extends JFrame {

    JTextArea text;
    JScrollPane scrollPane;
    JSlider joySadnessSlider;
    JSlider fearAngerSlider;
    JSlider surpriseAnticipationSlider;
    JSlider trustLoathingSlider;

    // EFFECTS: creates an application window to enter a journal entry or edit an existing journal's entry
    public JournalWindow(boolean editing, JournalRecording journalEditing,
                         EmotionalState emoStateEditing, WindowAdapter windowBehavior) {

        setFrameSettings(windowBehavior);

        // Set initial constants for grid bag
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(25, 25, 5, 25);
        c.gridy = 0;
        c.gridx = 0;
        c.weighty = 1.;
        c.weightx = 1.;
        c.gridwidth = 2;

        setJTextArea(editing, journalEditing, c);

        // Set constants for buttons and sliders
        c.weighty = .05;
        c.weightx = 1.;
        c.gridwidth = 1;

        setJoySadnessSlider(editing, emoStateEditing, c);
        setFearAngerSlider(editing, emoStateEditing, c);
        setSurpriseAnticipationSlider(editing, emoStateEditing, c);
        setTrustLoathingSlider(editing, emoStateEditing, c);

        setRecordButton(editing, journalEditing, c);

        // Adds option to delete journal file if editing
        if (editing) {
            setDeleteButton(journalEditing, c);
        }

        setVisible(true);

        // Sets bounds of text area to current scrollPane's size after visibility
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent componentEvent) {
                text.setBounds(0, 0, scrollPane.getSize().width, scrollPane.getSize().height);
            }
        });

    }

    private void setDeleteButton(final JournalRecording journalEditing, GridBagConstraints c) {

        c.gridy = 4;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("utc"));

        JButton delete = new JButton("DELETE JOURNAL FROM " + dateFormat.format(journalEditing.getEntryDate()));

        delete.setBackground(new Color(125, 125, 125));
        delete.setFont(new Font("Arial", Font.BOLD, 15));
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File f = new File("./data/journal" + journalEditing.getHashDateIdentifier() + ".txt");
                File fe = new File("./data/emotionalstate" + journalEditing.getHashDateIdentifier() + ".txt");
                f.delete();
                fe.delete();
                dispose();
            }
        });

        add(delete, c);

    }

    private void setRecordButton(final boolean editing, final JournalRecording journalEditing, GridBagConstraints c) {

        c.gridy = 3;
        c.gridx = 0;
        c.gridwidth = 2;
        c.insets = new Insets(10, 25, 10, 25);

        JButton record = new JButton("RECORD JOURNAL");
        record.setBackground(new Color(125, 125, 125));
        record.setFont(new Font("Arial", Font.BOLD, 15));
        setRecordActionListener(editing, journalEditing, record);
        add(record, c);

    }

    private void setRecordActionListener(final boolean editing, final JournalRecording journalEditing, JButton record) {

        record.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JournalRecording journal = new JournalRecording();
                journal.recordJournalRecordingEntry(text.getText());
                journal.getEmoteState().alterEmotionalState(
                        joySadnessSlider.getValue(), fearAngerSlider.getValue(),
                        surpriseAnticipationSlider.getValue(), trustLoathingSlider.getValue());

                // Sets the date to current if not editing, else keeps it consistent
                if (editing) {
                    journal.setEntryDate(journalEditing.getEntryDate());
                    journal.saveCurrentWithEmotionalState();
                } else {
                    journal.setToCurrentDate();
                    journal.saveCurrentWithEmotionalState();
                }

                dispose();

            }
        });

    }

    private void setTrustLoathingSlider(boolean editing, EmotionalState emoStateEditing, GridBagConstraints c) {

        c.gridy = 2;
        c.gridx = 1;

        Hashtable<Integer, JLabel> table3 = new Hashtable<>();
        table3.put(0, quickWhiteLabel("Loathing"));
        table3.put(50, quickWhiteLabel("Neutral"));
        table3.put(100, quickWhiteLabel("Trust"));

        trustLoathingSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);

        trustLoathingSlider.setBackground(new Color(50, 50, 50));
        trustLoathingSlider.setMinorTickSpacing(2);
        trustLoathingSlider.setMajorTickSpacing(50);
        trustLoathingSlider.setPaintTicks(true);
        trustLoathingSlider.setPaintLabels(true);
        trustLoathingSlider.setForeground(new Color(200, 200, 200));
        trustLoathingSlider.setLabelTable(table3);

        // Sets to emoStateEditing's trustLoathing if editing
        if (editing) {
            trustLoathingSlider.setValue(emoStateEditing.getMoodVector().get(3).getScaleValue());
        }

        add(trustLoathingSlider, c);
    }

    private void setSurpriseAnticipationSlider(boolean editing, EmotionalState emoStateEditing, GridBagConstraints c) {

        c.gridy = 2;
        c.gridx = 0;

        Hashtable<Integer, JLabel> table2 = new Hashtable<>();
        table2.put(0, quickWhiteLabel("Anticipation"));
        table2.put(50, quickWhiteLabel("Neutral"));
        table2.put(100, quickWhiteLabel("Surprise"));

        surpriseAnticipationSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);

        surpriseAnticipationSlider.setBackground(new Color(50, 50, 50));
        surpriseAnticipationSlider.setMinorTickSpacing(2);
        surpriseAnticipationSlider.setMajorTickSpacing(50);
        surpriseAnticipationSlider.setPaintTicks(true);
        surpriseAnticipationSlider.setPaintLabels(true);
        surpriseAnticipationSlider.setForeground(new Color(200, 200, 200));
        surpriseAnticipationSlider.setLabelTable(table2);

        // Sets to emoStateEditing's surpriseAnticipation if editing
        if (editing) {
            surpriseAnticipationSlider.setValue(emoStateEditing.getMoodVector().get(2).getScaleValue());
        }

        add(surpriseAnticipationSlider, c);
    }

    private void setFearAngerSlider(boolean editing, EmotionalState emoStateEditing, GridBagConstraints c) {

        c.gridy = 1;
        c.gridx = 1;

        Hashtable<Integer, JLabel> table1 = new Hashtable<>();
        table1.put(0, quickWhiteLabel("Anger"));
        table1.put(50, quickWhiteLabel("Neutral"));
        table1.put(100, quickWhiteLabel("Fear"));

        fearAngerSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);

        fearAngerSlider.setBackground(new Color(50, 50, 50));
        fearAngerSlider.setMinorTickSpacing(2);
        fearAngerSlider.setMajorTickSpacing(50);
        fearAngerSlider.setPaintTicks(true);
        fearAngerSlider.setPaintLabels(true);
        fearAngerSlider.setForeground(new Color(200, 200, 200));
        fearAngerSlider.setLabelTable(table1);

        // Sets to emoStateEditing's angerFear if editing
        if (editing) {
            fearAngerSlider.setValue(emoStateEditing.getMoodVector().get(1).getScaleValue());
        }

        add(fearAngerSlider, c);
    }

    private void setJoySadnessSlider(boolean editing, EmotionalState emoStateEditing, GridBagConstraints c) {

        c.gridy = 1;
        c.gridx = 0;

        Hashtable<Integer, JLabel> table0 = new Hashtable<>();
        table0.put(0, quickWhiteLabel("Sadness"));
        table0.put(50, quickWhiteLabel("Neutral"));
        table0.put(100, quickWhiteLabel("Joy"));

        joySadnessSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);

        joySadnessSlider.setBackground(new Color(50, 50, 50));
        joySadnessSlider.setMinorTickSpacing(2);
        joySadnessSlider.setMajorTickSpacing(50);
        joySadnessSlider.setPaintTicks(true);
        joySadnessSlider.setPaintLabels(true);
        joySadnessSlider.setForeground(new Color(200, 200, 200));
        joySadnessSlider.setLabelTable(table0);

        // Sets to emoStateEditing's joySadness if editing
        if (editing) {
            joySadnessSlider.setValue(emoStateEditing.getMoodVector().get(0).getScaleValue());
        }

        add(joySadnessSlider, c);
    }

    private void setJTextArea(boolean editing, JournalRecording journalEditing, GridBagConstraints c) {

        // If editing then use the journalEditings text, else set up an empty box
        if (editing) {
            text = new JTextArea(journalEditing.getEntryText());
        } else {
            text = new JTextArea("");
        }

        text.setLineWrap(true);
        text.setEditable(true);
        text.setVisible(true);

        // Create a scroll pane for the text area
        scrollPane = new JScrollPane(text);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.add(text);

        add(scrollPane, c);

    }

    private void setFrameSettings(WindowAdapter windowBehavior) {
        getContentPane().setBackground(new Color(50, 50, 50));
        setTitle("Journal Entry");
        setSize(600, 800);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        addWindowListener(windowBehavior);
    }

    private JLabel quickWhiteLabel(String label) {
        JLabel tempHolder = new JLabel(label);
        tempHolder.setForeground(new Color(200, 200, 200));
        return tempHolder;
    }
}
