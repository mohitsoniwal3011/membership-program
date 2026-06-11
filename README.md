# FirstClub Membership System Backend

A robust, backend system for the FirstClub tiered membership program, built with **Java** and **Spring Boot 3.2**.

This application demonstrates strong Object-Oriented Programming (OOP) and SOLID principles, utilizing the **Strategy Pattern** for tier eligibility, **Optimistic Locking** for concurrency, and a **Rich Domain Model** for encapsulation.

---

## 🚀 Quick Start & Demo

The application uses an in-memory **H2 Database**, meaning you don't need any external database setups to run and test it. The database is automatically seeded with demo data (Plans, Tiers, and Users) on startup.

### 1. Run the Application
Open your terminal in the project root and run:
```bash
mvn spring-boot:run
```
*(The server will start on `http://localhost:8080`)*

### 2. View the Seeded Data (H2 Console)
You can directly interact with the database tables via your browser:
- **URL**: `http://localhost:8080/h2-console`
- **JDBC URL**: `jdbc:h2:mem:membershipdb`
- **Username**: `sa`
- **Password**: `password`

The database contains three pre-seeded users for testing:
1. **User 1 (Alice Newbie)**: Low order count and value.
2. **User 2 (Bob Regular)**: Medium order count and value.
3. **User 3 (Charlie Whale)**: High order count and value.

---

## 🧪 How to Demo the APIs (cURL Examples)

### Check Eligible Tiers
Find out which tiers a user qualifies for based on the evaluation strategies:

**For User 1 (Only qualifies for SILVER):**
```bash
curl -s http://localhost:8080/api/v1/catalog/eligible-tiers/1 | jq
```

**For User 3 (Qualifies for SILVER, GOLD, and PLATINUM):**
```bash
curl -s http://localhost:8080/api/v1/catalog/eligible-tiers/3 | jq
```

### Subscribe to a Plan & Tier
Let's subscribe User 3 (Whale) to the PLATINUM tier (Tier ID 3) on a YEARLY plan (Plan ID 3):
```bash
curl -X POST http://localhost:8080/api/v1/subscriptions \
-H "Content-Type: application/json" \
-d '{"userId": 3, "planId": 3, "tierId": 3}' | jq
```

*(Note: Trying to subscribe User 1 to Tier 3 will fail due to the **Defense in Depth** validation!)*

### View User's Active Subscriptions
```bash
curl -s http://localhost:8080/api/v1/subscriptions/user/3 | jq
```

---

## 🛠 Testing & Code Coverage

The project is fully unit-tested using **JUnit 5** and **Mockito**, and features descriptive `@DisplayName` annotations on tests for enhanced readability.

**To run the test suite:**
```bash
mvn clean test
```

**To generate the JaCoCo Code Coverage Report:**
```bash
mvn clean test jacoco:report
```
*After running the command, open `target/site/jacoco/index.html` in your browser to view the detailed coverage breakdown!*

---

## 📚 JavaDocs

The entire source code is heavily documented with industry-standard JavaDocs detailing the "why" and "how" of the architecture. 

**To generate the documentation locally:**
```bash
mvn clean javadoc:javadoc
```
*View the generated HTML site at `target/site/apidocs/index.html`.*
