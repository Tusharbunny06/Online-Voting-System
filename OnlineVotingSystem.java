import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class OnlineVotingSystem extends javax.swing.JFrame {

    // Database connection variables
    Connection con;
    PreparedStatement pst;
    ResultSet rs;

    // Static vote count variables
    public static int p1 = 0;
    public static int p2 = 0;
    public static int p3 = 0;

    // Constructor
    public OnlineVotingSystem() {
        initComponents();
        connect();  // Establish the database connection
    }

    // Method to establish database connection
    public void connect() {
        try {
            // Load the MySQL driver (adjust if you're using a different DBMS)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to the database (adjust username, password, and URL as per your setup)
            this.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/votingsys", "root", "Tusharbunny06");

            // Check if connection is successful
            if (this.con != null) {
                System.out.println("Database connected successfully");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to establish connection to the database.");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "MySQL Driver not found.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database connection failed: " + e.getMessage());
        }
    }

    // Initialize components
    private void initComponents() {
        setTitle("Online Voting System");
        getContentPane().setBackground(Color.decode("#f0f8ff"));  // Light blue background
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding between components

        jLabel1 = new JLabel("ONLINE VOTING SYSTEM");
        jLabel1.setFont(new Font("Arial", Font.BOLD, 28));
        jLabel1.setForeground(Color.decode("#0066cc")); // Dark blue color
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(jLabel1, gbc);

        jLabel2 = new JLabel("ENTER NAME:");
        jLabel2.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(jLabel2, gbc);

        jTextField1 = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(jTextField1, gbc);

        jLabel3 = new JLabel("ENTER VOTER ID:");
        jLabel3.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        add(jLabel3, gbc);

        jTextField2 = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(jTextField2, gbc);

        jLabel4 = new JLabel("CAST YOUR VOTE HERE:");
        jLabel4.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(jLabel4, gbc);

        jRadioButton1 = new JRadioButton("PARTY A");
        jRadioButton1.setBackground(Color.decode("#f0f8ff"));
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(jRadioButton1, gbc);

        jRadioButton2 = new JRadioButton("PARTY B");
        jRadioButton2.setBackground(Color.decode("#f0f8ff"));
        gbc.gridx = 1;
        add(jRadioButton2, gbc);

        jRadioButton3 = new JRadioButton("PARTY C");
        jRadioButton3.setBackground(Color.decode("#f0f8ff"));
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(jRadioButton3, gbc);

        ButtonGroup group = new ButtonGroup();
        group.add(jRadioButton1);
        group.add(jRadioButton2);
        group.add(jRadioButton3);

        jButton1 = new JButton("SUBMIT YOUR VOTE");
        jButton1.setBackground(Color.decode("#66cc66")); // Green button
        jButton1.setForeground(Color.WHITE);
        jButton1.setFont(new Font("Arial", Font.BOLD, 16));
        jButton1.addActionListener(evt -> jButton1ActionPerformed(evt));
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 6;
        add(jButton1, gbc);

        jButton4 = new JButton("CHECK RESULTS");
        jButton4.setBackground(Color.decode("#ffcc00")); // Yellow button
        jButton4.setForeground(Color.BLACK);
        jButton4.setFont(new Font("Arial", Font.BOLD, 16));
        jButton4.addActionListener(evt -> jButton4ActionPerformed(evt));
        gbc.gridy = 7;
        add(jButton4, gbc);

        pack();
        setLocationRelativeTo(null); // Center the frame on the screen
    }

    // Submit vote button action
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        String name = jTextField1.getText();
        String voterID = jTextField2.getText();
        String party = "";

        if (jRadioButton1.isSelected()) {
            p1++;
            party = "Party A";
        } else if (jRadioButton2.isSelected()) {
            p2++;
            party = "Party B";
        } else if (jRadioButton3.isSelected()) {
            p3++;
            party = "Party C";
        } else {
            JOptionPane.showMessageDialog(rootPane, "Select a Party");
            return;
        }

        if (name.isEmpty() || voterID.isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Please enter all details.");
            return;
        }

        // Check if the voter ID already exists
        try {
            pst = con.prepareStatement("SELECT * FROM votes WHERE voterID = ?");
            pst.setString(1, voterID);
            rs = pst.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "User already voted!");
                return;
            }

            // Insert the vote
            pst = con.prepareStatement("INSERT INTO votes(name, voterID, party) VALUES(?, ?, ?)");
            pst.setString(1, name);
            pst.setString(2, voterID);
            pst.setString(3, party);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Vote Cast Successfully!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error while casting vote");
        }

        // Reset form fields
        jTextField1.setText("");
        jTextField2.setText("");
        jRadioButton1.setSelected(false);
        jRadioButton2.setSelected(false);
        jRadioButton3.setSelected(false);
    }

    // Check results button action
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            pst = con.prepareStatement("SELECT party, COUNT(party) AS votes FROM votes GROUP BY party");
            rs = pst.executeQuery();
            int votesA = 0, votesB = 0, votesC = 0;

            while (rs.next()) {
                String party = rs.getString("party");
                int votes = rs.getInt("votes");

                if (party.equals("Party A")) {
                    votesA = votes;
                } else if (party.equals("Party B")) {
                    votesB = votes;
                } else if (party.equals("Party C")) {
                    votesC = votes;
                }
            }

            String resultMessage = "Party A: " + votesA + "\nParty B: " + votesB + "\nParty C: " + votesC;

            if (votesA > votesB && votesA > votesC) {
                resultMessage += "\n\nPARTY A has a lead!";
            } else if (votesB > votesA && votesB > votesC) {
                resultMessage += "\n\nPARTY B has a lead!";
            } else if (votesC > votesA && votesC > votesB) {
                resultMessage += "\n\nPARTY C has a lead!";
            }

            JOptionPane.showMessageDialog(rootPane, resultMessage);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(rootPane, "Error while fetching results");
        }
    }

    // Main method to run the application
    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> new OnlineVotingSystem().setVisible(true));
    }

    // GUI components declaration
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JTextField jTextField1;
    private JTextField jTextField2;
    private JRadioButton jRadioButton1;
    private JRadioButton jRadioButton2;
    private JRadioButton jRadioButton3;
    private JButton jButton1;
    private JButton jButton4;
}
