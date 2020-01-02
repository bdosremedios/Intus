package ui;

import model.EmotionalState;
import recording.JournalRecording;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;

public class MainMenu extends JFrame {

    ArrayList<JButton> buttons;
    WindowAdapter windowAdapter;
    WindowAdapter browseWindowAdapter;

    // EFFECTS: creates a application window for the main menu of Intus activating various other windows corresponding
    // to recording a new journal, editing and broswing through past journals, and visualizing overall emotional
    // trends
    public MainMenu() {

        setFrameSettings();

        // Set initial constants for grid bag
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.;
        c.weighty = 1.;
        c.gridx = 0;

        setTitleLabel(c);

        // Create buttons for main menu
        JButton recordButton = new JButton("RECORD NEW JOURNAL ENTRY");
        JButton editButton = new JButton("VIEW/EDIT PAST JOURNAL RECORDINGS");
        JButton viewButton = new JButton("VISUALIZE EMOTIONAL TRENDS");
        JButton quitButton = new JButton("QUIT");

        createWindowAdapters();

        setRecordActionListener(recordButton);
        setEditActionListener(editButton);
        setViewActionListener(viewButton);
        setQuitActionListener(quitButton);

        setButtons(c, recordButton, editButton, viewButton, quitButton);

        setVisible(true);

    }

    private void setQuitActionListener(JButton quitButton) {

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

    }

    private void setViewActionListener(JButton viewButton) {

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                disableButtons();
                new PlotWindow(windowAdapter);
            }
        });

    }

    private void setEditActionListener(JButton editButton) {

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                disableButtons();
                new BrowseWindow(windowAdapter, browseWindowAdapter);
            }
        });

    }

    private void setRecordActionListener(JButton recordButton) {

        recordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                disableButtons();
                new JournalWindow(false, new JournalRecording(), new EmotionalState(), windowAdapter);
            }
        });

    }

    private void setButtons(GridBagConstraints c, JButton recordButton, JButton editButton, JButton viewButton,
                            JButton quitButton) {

        // Add a panel for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        buttonPanel.setBackground(new Color(50, 50, 50));

        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(5, 10, 5, 10);

        // Sets buttons with correct visuals and adds to panel
        buttons = new ArrayList<JButton>(Arrays.asList(recordButton, editButton, viewButton, quitButton));
        int counter = 0;
        for (JButton b : buttons) {
            c.gridy = counter;
            b.setFont(new Font("Arial", Font.BOLD, 15));
            b.setBackground(new Color(125, 125, 125));
            buttonPanel.add(b, c);
            c.insets = new Insets(0, 10, 5, 10);
            counter++;
        }

        add(buttonPanel);

    }

    private void createWindowAdapters() {

        // Set window adapter to re-enable buttons on window closing
        windowAdapter = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                enableButtons();
            }

            @Override
            public void windowClosed(WindowEvent e) {
                enableButtons();
            }
        };

        // No window closed for browse, as causes bug of re-enabling too soon
        browseWindowAdapter = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                enableButtons();
            }
        };

    }

    private void setTitleLabel(GridBagConstraints c) {

        // Add panel for title labels
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new GridBagLayout());
        titlePanel.setBackground(new Color(50, 50, 50));

        // Add welcome message
        JLabel titleLabel = new JLabel("Welcome to Intus");
        titleLabel.setFont(new Font("Helvetica", Font.BOLD, 40));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setForeground(new Color(225, 225, 225));
        c.gridy = 0;
        titlePanel.add(titleLabel, c);

        // Add short description of intus
        JLabel j2 = new JLabel("An emotional health recorder, tracker, and visualizer");
        j2.setFont(new Font("Helvetica", Font.PLAIN, 15));
        j2.setHorizontalAlignment(JLabel.CENTER);
        j2.setVerticalAlignment(JLabel.NORTH);
        j2.setForeground(new Color(225, 225, 225));
        c.gridy = 1;
        titlePanel.add(j2, c);

        add(titlePanel);
    }

    private void setFrameSettings() {
        setTitle("Intus");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setBackground(new Color(50, 50, 50));
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setLocationRelativeTo(null);
    }

    // MODIFIES: this
    // EFFECTS: disables all buttons
    public void disableButtons() {
        for (JButton b : buttons) {
            b.setEnabled(false);
        }
    }

    // MODIFIES: this
    // EFFECTS: enables all buttons
    public void enableButtons() {
        for (JButton b : buttons) {
            b.setEnabled(true);
        }
    }

}
