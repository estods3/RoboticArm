import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

public class SPSimulator extends JFrame implements ActionListener {
    //these are all global variables. they shouldn't need altered in any way
    int score, i;
    String correctR, stopR;
    String personFile = "person.xml";
    String stopmessage = "You should never say that to a suicidal person.";
    SimpleReader sr = new SimpleReader1L("data.txt");
    SimpleWriter wr = new SimpleWriter1L("data.txt");
    JPanel panel = new JPanel();
    JTextArea heading = new JTextArea("");
    JButton StartSim = new JButton("");
    JButton LearnMore = new JButton("");
    JButton StatsButton = new JButton("");
    JButton A1 = new JButton("");
    JButton A2 = new JButton("");
    JButton A3 = new JButton("");

    public SPSimulator(String title) {
        //constructor. Leave be
        super(title);
        this.score = 0;
        this.i = 0;
        //  this.moveToEndofFile();
        this.panel.setBackground(Color.BLACK);
        this.A1.addActionListener(this);
        this.A2.addActionListener(this);
        this.A3.addActionListener(this);
        this.heading.setBackground(Color.BLACK);
        this.heading.setForeground(Color.MAGENTA);
        this.heading.setEditable(false);
        this.heading.setSelectedTextColor(Color.MAGENTA);
        this.StartSim.addActionListener(this);
        this.LearnMore.addActionListener(this);
        this.StatsButton.addActionListener(this);
        this.StartSim.setBackground(Color.CYAN);
        this.LearnMore.setBackground(Color.CYAN);
        this.StatsButton.setBackground(Color.CYAN);
        this.A1.setBackground(Color.CYAN);
        this.A2.setBackground(Color.CYAN);
        this.A3.setBackground(Color.CYAN);
        this.setVisible(true);
        this.StartScreen(true);
        this.setResizable(false);
        this.setSize(500, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        //calls an instance of the program (calls itself)
        //leave be
        SPSim s = new SPSim("Suicide Prevention Simulation"); //client
        //s.setSimPerson("person.xml");
    }

    void setSimPerson(String fileName) {
        this.personFile = fileName;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //this switches between screens.
        if (e.getActionCommand().equals("Start Simulation!")
                || e.getActionCommand().equals("Try Again!")) {
            this.score = 0;
            this.StartScreen(false);
            this.StatsScreen(false);
            this.LearnMoreScreen(false);
            this.ResultsScreen(false);
            this.i = 0;
            this.wr.println("0");
            this.Simulation(0);//the simulation process
            //is made of of many simScreen() calls
            //this method will run through the whole conversation
            //until a "stop" attribute or until the end
        } else if (e.getActionCommand().equals("Cancel")) {
            this.sr.close();
            this.wr.close();
        } else if (e.getActionCommand().equals("Learn More!")) {
            this.StartScreen(false);
            this.SimScreen(false);
            this.StatsScreen(false);
            this.LearnMoreScreen(true);
        } else if (e.getActionCommand().equals("See Your Stats!")) {
            this.StartScreen(false);
            this.SimScreen(false);
            this.LearnMoreScreen(false);
            this.StatsScreen(true);
        } else if (e.getActionCommand().equals("See your Results!")) {
            this.StartScreen(false);
            this.SimScreen(false);
            this.LearnMoreScreen(false);
            this.StatsScreen(true);
        } else if (e.getSource() == this.A1) {
            if (this.correctR.equals("A1")) {
                this.score += 1;
                this.Simulation(1);
            } else if (this.stopR.equals("A1")) {
                JOptionPane.showMessageDialog(null, this.stopmessage,
                        "Stop! Don't say that because...",
                        JOptionPane.INFORMATION_MESSAGE);
                this.SimScreen(false);
                this.ResultsScreen(true);
                this.wr.println(this.i);
                this.wr.println(1);
            } else {
                this.Simulation(0);
                this.wr.println(this.i);
                this.wr.println(1);
            }
        } else if (e.getSource() == this.A2) {
            if (this.correctR.equals("A2")) {
                this.score += 1;
                this.Simulation(1);
            } else if (this.stopR.equals("2")) {
                JOptionPane.showMessageDialog(null, this.stopmessage,
                        "Stop! Don't say that because...",
                        JOptionPane.INFORMATION_MESSAGE);
                this.SimScreen(false);
                this.ResultsScreen(true);
                this.wr.println(this.i);
                this.wr.println(2);
            } else {
                this.Simulation(0);
                this.wr.println(this.i);
                this.wr.println(2);
            }
        } else if (e.getSource() == this.A3) {
            if (this.correctR.equals("A3")) {
                this.score += 1;
                this.Simulation(1);
            } else if (this.stopR.equals("A3")) {
                JOptionPane.showMessageDialog(null, this.stopmessage,
                        "Stop! Don't say that because...",
                        JOptionPane.INFORMATION_MESSAGE);
                this.SimScreen(false);
                this.ResultsScreen(true);
                this.wr.println(this.i);
                this.wr.println(3);
            } else {
                this.Simulation(0);
                this.wr.println(this.i);
                this.wr.println(3);
            }
        } else {
            System.out.println("error");
        }
    }

    void Simulation(int c) {
        XMLTree XPerson = new XMLTree1(this.personFile);
        if (this.i < XPerson.numberOfChildren()) {
            this.correctR = "";
            this.stopR = "";
            //this.stopmessage = "";
            this.heading.setText(XPerson.child(this.i).child(c).child(0)
                    .attributeValue("value"));
            if (XPerson.child(this.i).child(c).child(1).hasAttribute("value")
                    && XPerson.child(this.i).child(c).child(1)
                            .attributeValue("value").equals("positive")) {
                this.correctR = "A1";
            } else if (XPerson.child(this.i).child(c).child(2)
                    .hasAttribute("value")
                    && XPerson.child(this.i).child(c).child(2)
                            .attributeValue("value").equals("positive")) {
                this.correctR = "A2";
            } else if (XPerson.child(this.i).child(c).child(3)
                    .hasAttribute("value")
                    && XPerson.child(this.i).child(c).child(3)
                            .attributeValue("value").equals("positive")) {
                this.correctR = "A3";
            } else {
                System.out.println("Error with XML file");
            }
            if (XPerson.child(this.i).child(c).child(1).hasAttribute("value")
                    && XPerson.child(this.i).child(c).child(1)
                            .attributeValue("value").equals("stop")) {
                this.stopR = "A1";
                //this.stopmessage = XPerson.child(this.i).child(c).child(1)
                // .child(0).label();
            } else if (XPerson.child(this.i).child(c).child(2)
                    .hasAttribute("value")
                    && XPerson.child(this.i).child(c).child(2)
                            .attributeValue("value").equals("stop")) {
                this.stopR = "A2";
                //  this.stopmessage = XPerson.child(this.i).child(c).child(2)
                //        .child(0).label();
            } else if (XPerson.child(this.i).child(c).child(3)
                    .hasAttribute("value")
                    && XPerson.child(this.i).child(c).child(3)
                            .attributeValue("value").equals("stop")) {
                this.stopR = "A3";
                //  this.stopmessage = XPerson.child(this.i).child(c).child(3)
                //         .child(0).label();
            } else {
                this.stopR = "";
            }

            this.A1.setText(
                    XPerson.child(this.i).child(c).child(1).child(0).label());
            this.A2.setText(
                    XPerson.child(this.i).child(c).child(2).child(0).label());
            this.A3.setText(
                    XPerson.child(this.i).child(c).child(3).child(0).label());
            this.i++;
            this.SimScreen(true);
        } else {
            this.SimScreen(false);
            this.ResultsScreen(true);
        }
    }

    //basically, if the parameter is true, we show the screen. if false
    //we hide the screen. This is because to show one screen you must
    //hide every other screen
    void SimScreen(boolean b) {
        //this is the simulation screen
        //this is where most of the program will need adjusted
        //the setText() calls below need to be changed to the values from the
        //XMLTree
        if (b) {
            this.panel.add(this.heading);
            this.panel.add(this.A1);
            this.panel.add(this.A2);
            this.panel.add(this.A3);
            this.heading.setVisible(true);
            this.A1.setVisible(true);
            this.A2.setVisible(true);
            this.A3.setVisible(true);
            this.panel.setVisible(true);
            this.add(this.panel, BorderLayout.CENTER);
        } else {
            this.heading.setVisible(false);
            this.A1.setVisible(false);
            this.A2.setVisible(false);
            this.A3.setVisible(false);
            this.panel.setVisible(false);
        }
    }

    void StartScreen(boolean b) {
        this.heading.setText(
                "Suicide is the 10th leading cause of death in the United States.\n It is extremely important to raise awareness for \n mental health, depression, and suicide prevention. This application allows the user \n to train on how to talk to a patient and prevent escillation of the situation.");
        this.StartSim.setText("Start Simulation!");
        this.LearnMore.setText("Learn More!");
        if (b) {
            this.panel.add(this.heading);
            this.panel.add(this.StartSim);
            this.panel.add(this.LearnMore);
            this.StartSim.setVisible(true);
            this.LearnMore.setVisible(true);
            this.heading.setVisible(true);
            this.heading.setBackground(Color.BLACK);
            this.heading.setSize(2000, 700);
            this.heading.setAlignmentX(Component.CENTER_ALIGNMENT);
            this.panel.setVisible(true);
            this.add(this.panel, BorderLayout.CENTER);
        } else {
            this.heading.setVisible(false);
            this.StartSim.setVisible(false);
            this.LearnMore.setVisible(false);
            this.panel.setVisible(false);
        }
    }

    void LearnMoreScreen(boolean b) {
        if (b) {
            this.heading.setText(
                    "Suicide is the 10th leading cause of death in the United States.\n It is extremely important to raise awareness for \n mental health, depression, and suicide prevention. This application trains the user \n on how to talk to a patient and prevent escillation of the situation.\n You can learn more about suicide: http://www.afsp.org/ \n\n You can donate to help prevent suicide: http://www.afsp.org/ways-to-give/make-a-donation ");
            this.StartSim.setText("Start Simulation!");
            this.panel.add(this.heading);
            this.panel.add(this.StartSim);
            this.heading.setVisible(true);
            this.StartSim.setVisible(true);
            this.heading.setForeground(Color.MAGENTA);
            this.heading.setAlignmentX(Component.CENTER_ALIGNMENT);
            this.panel.setVisible(true);
            this.add(this.panel, BorderLayout.CENTER);
        } else {
            this.heading.setVisible(false);
            this.panel.setVisible(false);
        }
    }

    void ResultsScreen(boolean b) {
        if (b) {
            String sReview = null;
            if (this.score == 8) {
                sReview = "Perfect!\n - You are excellent at communicating with someone who has suicidal thoughts.";
            } else if (this.score >= 6) {
                sReview = "Great!\n - You are pretty good at communicating with someone who has suicidal thoughts.";
            } else if (this.score == 5) {
                sReview = "Not bad!\n - You know some of the correct responses, but not all of them.";
            } else {
                sReview = "Needs work!\n - You answered incorrectly for a majority of responses. ";
            }
            String dos = "\nDo:\n - Be yourself. The right words are not important, your concern for them is. \n -Listen. Let them vent, just keep the conversation going. \n -Be sympathetic, non-judgmental, patient, calm, accepting. \n -Offer hope. Let them know they are important to you. \n -Asking about suicide does not give them ideas, it is one of the best questions to ask.\n\n But don’t:\n";
            String dont = "-Argue with the suicidal person.\n -Act shocked, lecture on the value of life, or say that suicide is wrong.\n -Promise confidentiality. You may have to break that promise to help them.\n -Offer ways to fix their problems, or give advice, or make them feel like they have to \njustify their suicidal feelings. It is not about how bad the problem is, \n but how badly it’s hurting your friend or loved one. \n-Blame yourself. You can’t “fix” someone’s depression.";
            this.heading.setText(sReview + "\n" + dos + dont);
            this.StartSim.setText("Try Again!");
            this.LearnMore.setText("Learn More!");
            this.StatsButton.setText("See Your Stats!");
            this.heading.setAlignmentX(Component.CENTER_ALIGNMENT);
            this.StartSim.setVisible(true);
            this.LearnMore.setVisible(true);
            this.StatsButton.setVisible(true);
            this.heading.setVisible(true);
            this.heading.setForeground(Color.MAGENTA);
            this.panel.add(this.heading);
            this.panel.add(this.StartSim);
            this.panel.add(this.LearnMore);
            this.panel.add(this.StatsButton);
            this.panel.setVisible(true);
            this.add(this.panel, BorderLayout.CENTER);
        } else {
            this.heading.setVisible(false);
            this.StartSim.setVisible(false);
            this.LearnMore.setVisible(false);
            this.StatsButton.setVisible(false);
            this.panel.setVisible(false);
        }
    }

    void StatsScreen(boolean b) {
        //this can be messed with later. to do this, we need to make a txt
        //file to store previous scores and then display them here
        if (b) {
            for (int j = 0; !this.sr.atEOS(); j++) {
                int n = this.sr.nextInteger();
                if (n != 0) {
                    System.out.println("Question: " + n + " You put "
                            + this.sr.nextInteger());
                } else {
                    System.out.println("Question: " + this.sr.nextInteger()
                            + " You put " + this.sr.nextInteger());
                }
            }
            this.heading.setText(
                    "Your data has been printed to the command prompt.\n You can copy the data for furthr analysis.");
            this.StartSim.setText("Try Again!");
            this.LearnMore.setText("Learn More!");
            this.panel.add(this.heading);
            this.panel.add(this.StartSim);
            this.panel.add(this.LearnMore);
            this.StartSim.setVisible(true);
            this.LearnMore.setVisible(true);
            this.StatsButton.setVisible(false);
            this.heading.setVisible(true);
            this.heading.setForeground(Color.MAGENTA);
            this.heading.setAlignmentX(Component.CENTER_ALIGNMENT);
            this.panel.setVisible(true);
            this.add(this.panel, BorderLayout.CENTER);
        } else {
            this.StatsButton.setVisible(false);
            this.heading.setVisible(false);
            this.StartSim.setVisible(false);
            this.LearnMore.setVisible(false);
            this.panel.setVisible(false);
        }
    }
}
