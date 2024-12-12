
# Leaderboard Application

## Project Description
The project is a Spring Boot application that is used to generate a leaderboard and statistics based on user' daily step count. It includes RESTful APIs for steps, retrieving leaderboard rankings, and calculating various statistics.


---

## Prerequisites
Tools used:
- **Java 8 **
- **Maven**
- **MySQL Server**

---

## Steps to Run the Project

1. **Clone the Repository**
   

2. **Set Up the Database**
   - Create a MySQL database named `leaderboard`.
   - Also, we can input data and teams in the table.


3. **Build the Project**


4. **Start the Application**
   
   The application will be accessible at `http://localhost:8080`.

---

## API Endpoints

### 1. **Get Overall Individual Score**
- **Endpoint**: `GET leaderboards/individualscore`
- **Description**: Rank users based on their total score..



### 2. **Get each team scores based on ranking**
- **Endpoint**: `GET leaderboards/teamscore`
- **Description**: Fetch the leaderboard according to the total steps by each team.

### 3. **Get Statistics of each user**
- **Endpoint**: `GET leaderboards/userstats`
- **Description**: Fetch various statistics per user such as average, median, and total steps.

---

## Approach and Assumptions

### Approach
1. **Entity Design**:
   - The `User` entity represents a user's daily step count, storing their `userId`, `date`, and `teams`.
   - Data is stored in a MySQL database using JPA for ORM.

2. **Service Layer**:
   - Implements business logic for calculating total steps, sorting users for the leaderboard rankwise, and computing their statistics.
   - Exceptions are applied, if data and teams are not found.

3. **Controller Layer**:
   - Exposes RESTful APIs to interact with the application.

4. **Database Queries**:
   - Custom JPA queries are used for efficient data retrieval and aggregation.

### Assumptions
- Each user is uniquely identified by their `userId`.
- Statistical calculations (minimum, maximum, mean steps) are derived from daily step records.



---


