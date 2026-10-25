CAMPUS EQUIPMENT BOOKING SYSTEM
Java Swing + MySQL + JDBC

REQUIREMENTS
1. JDK 8 or later
2. MySQL Server 8.x
3. MySQL Connector/J JAR
4. IntelliJ IDEA, Eclipse, NetBeans, or command line

SETUP
1. Open MySQL Workbench or MySQL command line.
2. Run sql/campus_equipment.sql.
3. Open src/DBConnection.java.
4. Change USER and PASSWORD to your MySQL username/password.
5. Add MySQL Connector/J to the project's classpath.
6. Run src/Main.java.

DEMO LOGINS
Admin
Email: admin@campus.com
Password: admin123

Student
Email: student@campus.com
Password: student123

FEATURES
Student:
- Login
- Browse/search equipment
- Submit booking
- View bookings
- Cancel pending bookings

Admin:
- Login
- Add/update/delete equipment
- Approve/reject bookings
- Mark equipment returned
- Automatic quantity updates using transactions
- Reports: most booked, borrowed, overdue, bookings by category

NOTE
Passwords are plain text only to keep this college demo project simple. A production application should hash passwords.
