# Assignment A5 — Servlet + JDBC (Display `ebookshop` table)

This is a very basic sample program showing how a **Servlet** can run an SQL `SELECT` query and display the result as an HTML table.

## Files in this folder

- `EbookServlet.java` — Servlet that queries the table and prints HTML
- `WEB-INF/web.xml` — Servlet mapping (kept for compatibility)
- `ebookshop_mysql.sql` — MySQL script to create DB/table + insert sample rows

## Prerequisites

1. **JDK 8+** installed
2. **Apache Tomcat 9.x** installed (recommended for `javax.servlet.*`)
3. **MySQL Server** installed (and running)
4. **MySQL JDBC driver JAR** (Connector/J) downloaded, e.g. `mysql-connector-j-8.x.x.jar`
5. (Optional, easiest) **Eclipse IDE** with “Dynamic Web Project” support

> Note: Tomcat 10 uses `jakarta.servlet.*`. This code is written for **Tomcat 9**.

## Step 1: Create database and table (MySQL)

### Option A (MySQL Workbench)

- Open MySQL Workbench
- Open the file `ebookshop_mysql.sql`
- Run it

### Option B (MySQL CLI)

- Open a terminal and run:

  `mysql -u root -p < ebookshop_mysql.sql`

This creates:

- Database: `ebookdb`
- Table: `ebookshop(book_id, book_title, book_author, book_price, quantity)`
- Some sample rows

## Step 2: Update DB connection in the servlet

Open `EbookServlet.java` and update these constants if needed:

- `JDBC_URL` (default: `jdbc:mysql://localhost:3306/ebookdb`)
- `JDBC_USER`
- `JDBC_PASS`

## Step 3: Create a simple web app in Tomcat (NO Maven)

### Using Eclipse (recommended)

1. **File → New → Dynamic Web Project**
2. Target runtime: **Apache Tomcat 9**
3. Create the web project
4. Add files:
   - Put `EbookServlet.java` in: `src/`
   - Put `web.xml` in: `WebContent/WEB-INF/web.xml`
     - (If your project uses `src/main/webapp`, then put it in: `src/main/webapp/WEB-INF/web.xml`)

### Add MySQL JDBC JAR

1. Copy `mysql-connector-j-8.x.x.jar` into: `WebContent/WEB-INF/lib/`
2. In Eclipse: Right click the project → **Properties → Java Build Path → Add JARs** and select the same jar

## Step 4: Run and test

1. Start Tomcat (Run on Server in Eclipse)
2. Open this URL in browser:

`http://localhost:8080/<your-app-name>/ebooks`

You should see an HTML table with all rows from `ebookshop`.

## Common errors (quick fixes)

- **“MySQL JDBC Driver not found”**
  - You did not add the Connector/J jar to `WEB-INF/lib` or the build path.

- **“Access denied for user …”**
  - Wrong username/password in `EbookServlet.java`.

- **“Table 'ebookdb.ebookshop' doesn't exist”**
  - You didn’t run `ebookshop_mysql.sql`, or you used a different database name.

- **Blank page / 404**
  - Make sure the servlet URL is `/ebooks` and you opened `/ebooks`.
  - Check Tomcat logs in the Eclipse Console.
