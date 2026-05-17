# College Management System using Salesforce Lightning Application

## Objective

Develop a College Management Lightning Application in Salesforce Cloud to manage Student and Faculty records with validations during record creation and updates.

---

# Validation Rules

## Student Validation

- Marks should be between 0 and 100.
- Roll Number must be greater than 0.
- Email should contain `@` symbol.
- Student Name cannot be blank.

---

## Faculty Validation

- Faculty Name cannot be empty and must contain at least 3 characters.
- Faculty ID must be greater than 0 and should be unique.
- Salary must be greater than 10,000 and less than 500,000.
- Department must be selected from available list.
- Joining Date cannot be future date.

---

# Step 1: Login to Salesforce

Open:

```text
https://login.salesforce.com
```

Login using Developer Edition account.

Open:

```text
⚙ → Setup
```

---

# Step 2: Create Student Object

Go:

```text
Setup → Object Manager
```

Click:

```text
Create → Custom Object
```

Fill:

```text
Label: Student
Plural Label: Students
Record Name: Student Name
Type: Text
```

Enable:

```text
Allow Reports
Allow Activities
```

Save.

---

# Step 3: Create Student Fields

Open:

```text
Student → Fields & Relationships → New
```

---

## Roll Number

Type:

```text
Number
```

Values:

```text
Field Label: Roll Number
Length: 10
Decimal: 0
```

Save.

---

## Marks

Type:

```text
Number
```

Values:

```text
Field Label: Marks
Length: 3
Decimal: 0
```

Save.

---

## Email

Type:

```text
Email
```

Label:

```text
Email
```

Save.

---

# Step 4: Create Faculty Object

Open:

```text
Object Manager → Create → Custom Object
```

Fill:

```text
Label: Faculty
Plural Label: Faculties
Record Name: Faculty Name
Type: Text
```

Save.

---

# Step 5: Create Faculty Fields

Open:

```text
Faculty → Fields & Relationships → New
```

---

## Faculty ID

Type:

```text
Number
```

Values:

```text
Field Label: Faculty ID
Length: 10
Decimal: 0
```

Enable:

```text
Unique
```

Save.

---

## Salary

Type:

```text
Currency
```

Label:

```text
Salary
```

Save.

---

## Email

Type:

```text
Email
```

Save.

---

## Department

Type:

```text
Picklist
```

Values:

```text
Computer
IT
Mechanical
Civil
Electronics
```

Save.

---

## Joining Date

Type:

```text
Date
```

Label:

```text
Joining Date
```

Save.

---

# Step 6: Create Lightning Component

Open:

```text
⚙ → Developer Console
```

Click:

```text
File → New → Lightning Component
```

Name:

```text
collegeManagement
```

Enable:

```text
Available for all page types
```

Create.

---

# Step 7: collegeManagement.cmp

Replace code:

```html
<aura:component
implements="flexipage:availableForAllPageTypes"
access="global">

<!-- Student -->

<aura:attribute
name="studentName"
type="String"/>

<aura:attribute
name="rollNo"
type="Integer"/>

<aura:attribute
name="marks"
type="Integer"/>

<aura:attribute
name="studentEmail"
type="String"/>

<!-- Faculty -->

<aura:attribute
name="facultyName"
type="String"/>

<aura:attribute
name="facultyId"
type="Integer"/>

<aura:attribute
name="salary"
type="Decimal"/>

<aura:attribute
name="facultyEmail"
type="String"/>

<aura:attribute
name="department"
type="String"/>

<aura:attribute
name="joiningDate"
type="Date"/>

<lightning:card
title="College Management">

<div class="slds-p-around_medium">

<h2>Student Details</h2>

<lightning:input
label="Student Name"
value="{!v.studentName}"/>

<lightning:input
label="Roll Number"
type="number"
value="{!v.rollNo}"/>

<lightning:input
label="Marks"
type="number"
value="{!v.marks}"/>

<lightning:input
label="Email"
type="email"
value="{!v.studentEmail}"/>

<br/>

<h2>Faculty Details</h2>

<lightning:input
label="Faculty Name"
value="{!v.facultyName}"/>

<lightning:input
label="Faculty ID"
type="number"
value="{!v.facultyId}"/>

<lightning:input
label="Salary"
type="number"
value="{!v.salary}"/>

<lightning:input
label="Faculty Email"
type="email"
value="{!v.facultyEmail}"/>

<lightning:select
label="Department"
value="{!v.department}">

<option value="">
Select
</option>

<option value="Computer">
Computer
</option>

<option value="IT">
IT
</option>

<option value="Mechanical">
Mechanical
</option>

<option value="Civil">
Civil
</option>

</lightning:select>

<lightning:input
label="Joining Date"
type="date"
value="{!v.joiningDate}"/>

<br/>

<lightning:button
label="Save"
onclick="{!c.saveData}"/>

</div>

</lightning:card>

</aura:component>
```

---

# Step 8: collegeManagementController.js

Replace code:

```javascript
({

saveData:function(
component,
event,
helper
){

let studentName=
component.get(
"v.studentName"
);

let roll=
component.get(
"v.rollNo"
);

let marks=
component.get(
"v.marks"
);

let studentEmail=
component.get(
"v.studentEmail"
);

let facultyName=
component.get(
"v.facultyName"
);

let facultyId=
component.get(
"v.facultyId"
);

let salary=
component.get(
"v.salary"
);

let dept=
component.get(
"v.department"
);

let joining=
component.get(
"v.joiningDate"
);

if(
!studentName
){

alert(
"Student name required"
);

return;

}

if(roll<=0){

alert(
"Roll number > 0 required"
);

return;

}

if(
marks<0 ||
marks>100
){

alert(
"Marks must be between 0 and 100"
);

return;

}

if(
!studentEmail.includes("@")
){

alert(
"Invalid Student Email"
);

return;

}

if(
!facultyName ||
facultyName.length<3
){

alert(
"Faculty name minimum 3 chars"
);

return;

}

if(facultyId<=0){

alert(
"Faculty ID >0 required"
);

return;

}

if(
salary<10000 ||
salary>500000
){

alert(
"Invalid Salary"
);

return;

}

if(!dept){

alert(
"Select department"
);

return;

}

let today=
new Date();

let join=
new Date(joining);

if(join>today){

alert(
"Future date not allowed"
);

return;

}

alert(
"Data Saved Successfully"
);

}

})
```

---

# Step 9: Create Design File

Open:

```text
File → New → Lightning Design
```

Name:

```text
collegeManagement.design
```

Code:

```xml
<design:component>
</design:component>
```

Save.

---

# Step 10: Add Component to Home Page

Open:

```text
⚙ → Edit Page
```

Search:

```text
collegeManagement
```

Drag component to page.

Save.

---

# Step 11: Activate Page

Open:

```text
Activation
```

Assign:

```text
Org Default
```

If using Service App:

```text
App Default → Service
```

Save.

---

# Test Cases

## Valid Input

```text
Student Name: Soham
Roll Number: 1
Marks: 90
Student Email: abc@gmail.com

Faculty Name: John
Faculty ID: 101
Salary: 50000
Department: IT
Joining Date: Today
```

Expected:

```text
Data Saved Successfully
```

---

## Invalid Cases

### Invalid Marks

```text
Marks = 120
```

Result:

```text
Marks must be between 0 and 100
```

---

### Invalid Roll Number

```text
Roll Number = -1
```

Result:

```text
Roll number > 0 required
```

---

### Invalid Faculty Name

```text
Ab
```

Result:

```text
Faculty name minimum 3 chars
```

---

### Invalid Salary

```text
Salary = 9000
```

Result:

```text
Invalid Salary
```

---

### Future Date

```text
2027-01-01
```

Result:

```text
Future date not allowed
```

---

# Architecture

```text
Salesforce Cloud
        ↓

Student Object
Faculty Object

        ↓

Lightning Application

        ↓

Validation Layer

        ↓

Record Management
```

## Technologies Used

- Salesforce Developer Edition
- Aura Components
- JavaScript
- Salesforce Objects
- Validation Rules
- Lightning App Builder
