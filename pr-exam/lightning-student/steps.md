# Employee Management System using Salesforce Lightning Component

## Objective

Develop an Employee Management Lightning Component in Salesforce Cloud that allows users to add employee details with validations before saving.

---

## Validation Requirements

The system validates:

- Employee Name cannot be empty and must contain at least 3 characters.
- Employee ID must be greater than 0.
- Employee ID must be unique.
- Salary must be greater than 10,000 and less than 500,000.
- Email must follow valid format.
- Department must be selected.
- Joining Date cannot be future date.

---

# Step 1: Login to Salesforce

Open Salesforce:

https://login.salesforce.com

Login using Developer Edition account.

Open:

```text
⚙ → Setup
```

---

# Step 2: Create Employee Object

Open:

```text
Setup → Object Manager
```

Click:

```text
Create → Custom Object
```

Fill:

```text
Label: Employee
Plural Label: Employees
Record Name: Employee Name
Data Type: Text
```

Enable:

```text
Allow Reports
Allow Activities
```

Click:

```text
Save
```

---

# Step 3: Create Fields

Open:

```text
Employee Object → Fields & Relationships → New
```

## Employee ID

Type:

```text
Number
```

Values:

```text
Field Label: Employee ID
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

Label:

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
HR
IT
Finance
Sales
Marketing
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

# Step 4: Create Lightning Component

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
employeeForm
```

Select:

```text
Available for all page types
```

Create component.

---

# Step 5: employeeForm.cmp

Replace code:

```html
<aura:component
implements="flexipage:availableForAllPageTypes"
access="global">

<aura:attribute name="empName" type="String"/>

<aura:attribute name="empId" type="Integer"/>

<aura:attribute name="salary" type="Decimal"/>

<aura:attribute name="email" type="String"/>

<aura:attribute name="department" type="String"/>

<aura:attribute name="joiningDate" type="Date"/>

<lightning:card title="Employee Management">

<div class="slds-p-around_medium">

<lightning:input
label="Employee Name"
value="{!v.empName}"/>

<lightning:input
type="number"
label="Employee ID"
value="{!v.empId}"/>

<lightning:input
type="number"
label="Salary"
value="{!v.salary}"/>

<lightning:input
type="email"
label="Email"
value="{!v.email}"/>

<lightning:select
label="Department"
value="{!v.department}">

<option value="">Select</option>

<option value="HR">HR</option>

<option value="IT">IT</option>

<option value="Finance">Finance</option>

<option value="Sales">Sales</option>

<option value="Marketing">Marketing</option>

</lightning:select>

<lightning:input
type="date"
label="Joining Date"
value="{!v.joiningDate}"/>

<br/>

<lightning:button
label="Save"
onclick="{!c.saveEmployee}"/>

</div>

</lightning:card>

</aura:component>
```

Save.

---

# Step 6: employeeFormController.js

Replace code:

```javascript
({

saveEmployee:function(
component,
event,
helper
){

let name=
component.get(
"v.empName"
);

let id=
component.get(
"v.empId"
);

let salary=
component.get(
"v.salary"
);

let email=
component.get(
"v.email"
);

let dept=
component.get(
"v.department"
);

let date=
component.get(
"v.joiningDate"
);

if(
!name ||
name.length<3
){

alert(
"Name must contain minimum 3 characters"
);

return;

}

if(id<=0){

alert(
"Employee ID must be greater than 0"
);

return;

}

if(
salary<10000 ||
salary>500000
){

alert(
"Salary must be between 10000 and 500000"
);

return;

}

let regex=
/^[^\s@]+@[^\s@]+\.[^\s@]+$/;

if(
!regex.test(email)
){

alert(
"Invalid Email"
);

return;

}

if(!dept){

alert(
"Select Department"
);

return;

}

let today=
new Date();

let join=
new Date(date);

if(join>today){

alert(
"Future Joining Date not allowed"
);

return;

}

alert(
"Employee Saved Successfully"
);

}

})
```

Save.

---

# Step 7: Create Design File

Open:

```text
File → New → Lightning Design
```

Name:

```text
employeeForm.design
```

Add:

```xml
<design:component>
</design:component>
```

Save.

---

# Step 8: Add Component to Home Page

Open Salesforce.

Go:

```text
⚙ → Edit Page
```

Search component:

```text
employeeForm
```

Drag component onto page.

Click:

```text
Save
```

---

# Step 9: Activate Page

Click:

```text
Activation
```

Assign:

```text
Org Default
```

If using Service App:

```text
Activation → App Default → Service
```

Save.

---

# Step 10: Testing

Valid Input:

```text
Employee Name: Soham
Employee ID: 1
Salary: 50000
Email: abc@gmail.com
Department: IT
Joining Date: Today
```

Expected Result:

```text
Employee Saved Successfully
```

---

## Invalid Cases

### Invalid Name

```text
Ab
```

Result:

```text
Name must contain minimum 3 characters
```

### Invalid Salary

```text
9000
```

Result:

```text
Salary must be between 10000 and 500000
```

### Invalid Email

```text
abc
```

Result:

```text
Invalid Email
```

### Empty Department

```text
No selection
```

Result:

```text
Select Department
```

### Future Date

```text
2027-01-01
```

Result:

```text
Future Joining Date not allowed
```

---

# Architecture

```text
Salesforce Cloud
        ↓

Custom Object (Employee)

        ↓

Lightning Component

        ↓

Client-side Validation

        ↓

Employee Record Creation
```

## Technologies Used

- Salesforce Developer Edition
- Lightning Components (Aura)
- JavaScript
- Salesforce Objects
- Picklists
- Validation Rules
