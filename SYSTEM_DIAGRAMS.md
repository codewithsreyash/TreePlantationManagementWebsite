# System Architecture & Diagrams

This document contains the essential UML and structural diagrams for the Tree Plantation Management System. These diagrams utilize Mermaid.js to render natively in GitHub.

---

## 1. Use Case Diagram
This diagram outlines the primary interactions the Coordinator has with the system.

```mermaid
flowchart LR
    Admin([Coordinator / Admin])
    System([Background System])

    Admin --> UC1(Login & Authentication)
    Admin --> UC2(Manage Plantation Drives)
    Admin --> UC3(Manage Volunteers)
    Admin --> UC4(Assign Volunteers to Drives)
    Admin --> UC5(Log Planted Trees)
    Admin --> UC6(View Impact & SDG Reports)
    
    System -.-> UC7(Poll Weather Alerts Asynchronously)
```

---

## 2. Entity-Relationship (ER) Diagram
This illustrates the database schema, including the two many-to-many join tables (`drive_volunteers` and `drive_trees`).

```mermaid
erDiagram
    VOLUNTEER {
        int id PK
        string name
        string email
    }
    PLANTATION_DRIVE {
        int id PK
        string location
        date drive_date
    }
    TREE_SPECIES {
        int id PK
        string name
        string scientific_name
    }
    
    PLANTATION_DRIVE ||--o{ DRIVE_VOLUNTEERS : "has"
    VOLUNTEER ||--o{ DRIVE_VOLUNTEERS : "participates in"
    
    PLANTATION_DRIVE ||--o{ DRIVE_TREES : "plants"
    TREE_SPECIES ||--o{ DRIVE_TREES : "is planted in"
```

---

## 3. Class Diagram (Core Architecture)
This diagram shows the relationship between the Spring managed beans, Services, DAOs, and Hibernate JPA Entities.

```mermaid
classDiagram
    class PlantationDrive {
        -int id
        -String location
        -Date driveDate
        -Set~Volunteer~ volunteers
        -Set~Tree~ trees
    }
    class Volunteer {
        -int id
        -String name
        -String email
    }
    class Tree {
        -int id
        -String name
        -String scientificName
    }
    
    class CoordinatorBean {
        +addDrive()
        +assignTreeToDrive()
    }
    class PlantationDriveService {
        +getAllDrives()
        +saveDrive()
    }
    class PlantationDriveDAO {
        +save()
        +getById()
        +findDrivesByLocationPattern()
    }
    
    CoordinatorBean --> PlantationDriveService : "@Autowired"
    PlantationDriveService --> PlantationDriveDAO : "@Autowired"
    PlantationDriveDAO --> PlantationDrive : "Manages"
    PlantationDrive "1" *-- "*" Volunteer : "ManyToMany"
    PlantationDrive "1" *-- "*" Tree : "ManyToMany"
```

---

## 4. Sequence Diagram (Logging Planted Trees)
This illustrates the end-to-end request flow when an admin logs a new tree planting activity.

```mermaid
sequenceDiagram
    actor Admin
    participant UI as JSF View (log-planting.xhtml)
    participant Bean as CoordinatorBean (JSF)
    participant Service as PlantationDriveService
    participant DAO as PlantationDriveDAO
    participant DB as MySQL Database

    Admin->>UI: Select Drive, Tree Species, click 'Log'
    UI->>Bean: assignTreeToDrive(driveId, treeId)
    Bean->>Service: assignTreeToDrive(driveId, treeId)
    Service->>DAO: update drive_trees collection
    DAO->>DB: Hibernate Flush (INSERT INTO drive_trees)
    DB-->>DAO: Success
    DAO-->>Service: Success
    Service-->>Bean: Success
    Bean-->>UI: Update View State (Success Message)
    UI-->>Admin: Show "Quest Complete" Flash Message
```

---

## 5. Activity Diagram (Creating a Drive & Assigning Volunteers)
This flow shows the steps required to organize a new plantation drive and assign a volunteer.

```mermaid
stateDiagram-v2
    [*] --> Dashboard
    
    state "Create Plantation Drive" as Create
    Dashboard --> Create : Click "Add Drive"
    Create --> Form1 : Enter Location & Date
    Form1 --> Dashboard : Save Drive
    
    state "Assign Volunteer" as Assign
    Dashboard --> Assign : Click "Assign Volunteer"
    Assign --> Form2 : Select Drive & Volunteer
    Form2 --> Dashboard : Save Assignment
    
    Dashboard --> [*] : Logout
```
