Finance Tracker & Investment Simulator
A gamified financial tracker and investment simulator designed for students to learn to manage their finances and practice investing safely. This project demonstrates backend development with Java, Spring Boot, and Maven, while applying key Computer Science concepts such as Object-Oriented Programming (OOP) and data structures.
Project Idea & Motivation
Ever since coming to university (and even before), I struggled with managing my finances and budgeting properly. I tried using various budgeting apps, but most were either not engaging, difficult to use, or required subscriptions.
I decided to create this app for myself, imagining a perfect finance tracker that is fun, interactive, and free.
Additionally, I’ve always wanted to learn about investing and understand how it works. Therefore, I included a virtual investment feature in the app: when users save money, they can practice investing it and learning trends and growth without any real-world risk. This allows beginners to gain experience and confidence in financial decision-making.
Current Features
1. Transaction Management
Users can add transactions (income, expenses, deposits).
Each transaction has:
Amount
Category
Date
The backend calculates the current balance by summing all transactions.
CS Concepts: Classes, objects, encapsulation, lists, loops.
2. Investment Simulator
Users can “invest” money from their balance virtually.
Each investment has:
Name
Amount invested
Invested date
Expected return rate
The system ensures users have enough balance before investing.
Backend calculates total investment value.
CS Concepts: Classes, objects, conditionals, loops.
3. REST API
Fully functional API endpoints tested via Postman:
/transactions → view/add transactions
/investments → view/add investments
/transactions/balance → check current balance
CS Concepts: Data encapsulation, collections, API design.
Technical Stack
Backend: Java 21, Spring Boot, Maven
Persistence: JPA / Hibernate
Testing: Postman for REST API
Prospects / Future Work
Even though the backend is fully functional, several features are planned to make this a complete student finance app:
Frontend Interface
Create a web or mobile UI to add/view transactions and investments.
Display balance and investment progress visually.
User Accounts
Implement sign-up/login.
Support multiple users with separate transactions and investments.
Gamification
Achievements, points, and progress bars for financial goals.
Reward users for good saving and investment habits.
Investment Simulation Enhancements
Simulate returns over time.
Provide options for investment strategies (low vs high risk).
Data Visualizations
Charts for spending categories, investment growth, and net worth over time.
