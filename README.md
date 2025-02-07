# Online-Voting-System
Online Voting System

Description

The Online Voting System is a Java-based desktop application that enables users to cast their votes securely and efficiently. This system provides a simple graphical user interface (GUI) built using Swing and connects to a MySQL database to store voter information and votes.

Features

User Authentication: Users enter their name and voter ID to vote.

Secure Voting: Prevents duplicate voting using voter ID verification.

Multiple Party Options: Users can vote for Party A, Party B, or Party C.

Database Integration: Stores voter information and votes in a MySQL database.

Results Display: Displays real-time voting results, including vote counts and leading party.

GUI Design: Uses a colorful and user-friendly interface.

Technologies Used

Java (Swing for GUI)

MySQL (Database for storing votes and voter information)

JDBC (Java Database Connectivity for database operations)

Prerequisites

Before running the project, ensure you have the following installed:

Java Development Kit (JDK) 8 or later

MySQL Server

MySQL Connector/J (JDBC Driver)

Database Configuration

Create a MySQL database named votingsys.

Run the following SQL command to create the votes table:

CREATE DATABASE votingsys;
USE votingsys;

CREATE TABLE votes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    voterID VARCHAR(50) UNIQUE NOT NULL,
    party VARCHAR(50) NOT NULL
);

Installation & Setup

Clone or download the project files.

Open the project in an IDE (e.g., Visual Studio Code, Eclipse, or IntelliJ IDEA).

Modify the database connection in the connect() method:

this.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/votingsys", "root", "your_password");

Replace your_password with your MySQL root password.
4. Run the OnlineVotingSystem.java file.

How to Use

Enter Name & Voter ID in the text fields.

Select a Party from the radio buttons.

Click "SUBMIT YOUR VOTE" to cast your vote.

Click "CHECK RESULTS" to view the current voting results.

Troubleshooting

Database Connection Failure: Ensure MySQL is running and the database credentials are correct.

Duplicate Vote Error: A voter can only vote once; use a unique voter ID.

JDBC Driver Not Found: Ensure the MySQL Connector/J is correctly added to the classpath.

Future Enhancements

User Authentication with Login System

Admin Dashboard for Managing Votes

Mobile & Web Application Integration

Author

Developed by Tushar

License

This project is open-source and available under the MIT License.
