# Salesforce Student Management System

This project demonstrates a Student Management System developed using Salesforce Apex and Visualforce Pages.  
The application performs complete CRUD operations (Create, Read, Update, Delete) on student records stored in a custom object.

---

# Features

- Add Student Records
- View Student Records
- Update Student Records
- Delete Student Records
- Display Student Details in Table Format
- Success Messages using `ApexPages.Message`

---

# Technologies Used

- Salesforce
- Apex Programming Language
- Visualforce Pages
- SOQL
- Custom Objects

---

# Custom Object Configuration

## Object Name
`Student`

## API Name
`Student__c`

---

# Custom Fields

| Field Label | API Name | Data Type |
|---|---|---|
| Student Name | Name | Text |
| Roll No | Roll_No__c | Number/Text |
| Class | Class__c | Text |
| Mobile No | Mobile_No__c | Phone/Text |

---

# Apex Controller

## File: `StudentController.cls`

```java
public class StudentController {

    public Student__c student { get; set; }
    public List<Student__c> studentList { get; set; }

    public StudentController() {

        student = new Student__c();

        loadStudents();
    }

    // Load all student records
    public void loadStudents() {

        studentList = [
            SELECT Id,
                   Name,
                   Roll_No__c,
                   Class__c,
                   Mobile_No__c
            FROM Student__c
            ORDER BY CreatedDate DESC
        ];
    }

    // Create and Update Student
    public void saveStudent() {

        upsert student;

        ApexPages.addMessage(
            new ApexPages.Message(
                ApexPages.Severity.CONFIRM,
                'Student Record Saved Successfully'
            )
        );

        student = new Student__c();

        loadStudents();
    }

    // Edit Student
    public void editStudent() {

        Id studentId =
            ApexPages.currentPage().getParameters().get('id');

        student = [
            SELECT Id,
                   Name,
                   Roll_No__c,
                   Class__c,
                   Mobile_No__c
            FROM Student__c
            WHERE Id = :studentId
            LIMIT 1
        ];
    }

    // Delete Student
    public void deleteStudent() {

        Id studentId =
            ApexPages.currentPage().getParameters().get('id');

        Student__c s = [
            SELECT Id
            FROM Student__c
            WHERE Id = :studentId
            LIMIT 1
        ];

        delete s;

        ApexPages.addMessage(
            new ApexPages.Message(
                ApexPages.Severity.CONFIRM,
                'Student Deleted Successfully'
            )
        );

        loadStudents();
    }
}
```

---

# Visualforce Page

## File: `StudentPage.page`

```xml
<apex:page controller="StudentController">

    <apex:form>

        <apex:pageMessages />

        <h1>Student Management System</h1>

        <!-- Student Form -->

        <apex:pageBlock title="Student Form">

            <apex:pageBlockSection columns="1">

                <!-- Student Name -->
                <apex:inputField value="{!student.Name}" />

                <!-- Roll Number -->
                <apex:inputField value="{!student.Roll_No__c}" />

                <!-- Class -->
                <apex:inputField value="{!student.Class__c}" />

                <!-- Mobile Number -->
                <apex:inputField value="{!student.Mobile_No__c}" />

            </apex:pageBlockSection>

            <!-- Save Button -->
            <apex:commandButton
                value="Save / Update"
                action="{!saveStudent}"
                rerender="studentTable"
            />

        </apex:pageBlock>

        <br/>

        <!-- Student Table -->

        <apex:pageBlock
            title="Student Records"
            id="studentTable">

            <apex:pageBlockTable
                value="{!studentList}"
                var="s">

                <!-- Student Name -->
                <apex:column
                    value="{!s.Name}"
                    headerValue="Student Name"/>

                <!-- Roll Number -->
                <apex:column
                    value="{!s.Roll_No__c}"
                    headerValue="Roll No"/>

                <!-- Class -->
                <apex:column
                    value="{!s.Class__c}"
                    headerValue="Class"/>

                <!-- Mobile Number -->
                <apex:column
                    value="{!s.Mobile_No__c}"
                    headerValue="Mobile No"/>

                <!-- Actions -->
                <apex:column headerValue="Actions">

                    <!-- Edit Button -->
                    <apex:commandLink
                        value="Edit"
                        action="{!editStudent}"
                        rerender="studentTable">

                        <apex:param
                            name="id"
                            value="{!s.Id}" />

                    </apex:commandLink>

                    &nbsp; | &nbsp;

                    <!-- Delete Button -->
                    <apex:commandLink
                        value="Delete"
                        action="{!deleteStudent}"
                        rerender="studentTable">

                        <apex:param
                            name="id"
                            value="{!s.Id}" />

                    </apex:commandLink>

                </apex:column>

            </apex:pageBlockTable>

        </apex:pageBlock>

    </apex:form>

</apex:page>
```

---

# Steps to Run the Project

## Step 1: Create Custom Object

Create a custom object named:

```bash
Student
```

API Name:

```bash
Student__c
```

---

## Step 2: Create Custom Fields

Create the following fields inside `Student__c`:

- Roll_No__c
- Class__c
- Mobile_No__c

The `Name` field will act as the Student Name field.

---

## Step 3: Create Apex Class

Create a new Apex Class:

```bash
StudentController
```

Paste the Apex code provided above.

---

## Step 4: Create Visualforce Page

Create a new Visualforce Page:

```bash
StudentPage
```

Paste the Visualforce code provided above.

---

## Step 5: Run Application

Open the following URL:

```bash
/apex/StudentPage
```

---

# Functionalities

## Create Student
Users can add new student records using the form.

## View Students
All stored student records are displayed in table format.

## Update Student
Click the Edit button to update existing student details.

## Delete Student
Click the Delete button to remove student records.

---

# Output

The application displays:

- Student Name
- Roll Number
- Class
- Mobile Number
- Edit/Delete Actions

in a structured table format.

---

# Project Structure

```bash
force-app
│
├── classes
│   └── StudentController.cls
│
├── pages
│   └── StudentPage.page
│
└── objects
    └── Student__c
```

---

# Author

Soham Joshi
