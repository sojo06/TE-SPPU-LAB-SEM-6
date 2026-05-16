# Assignment 8 - Secure File Sharing Between Cloud Instances

## Problem Statement

Create a cloud-based environment where two virtual machines communicate securely within a virtual network. Implement a mechanism to transfer files between the instances while ensuring proper access permissions.

---

# Repository

```text
https://github.com/4SNA/SecureFileSharing
```

---

# Technologies Used

- AWS EC2
- AWS VPC
- Ubuntu Linux
- SCP (Secure Copy Protocol)
- SSH
- Linux File Permissions

---

# Objective

- Create a custom VPC
- Launch two Ubuntu EC2 instances inside same VPC
- Transfer file securely using private IP addresses
- Configure SSH communication inside VPC
- Verify file transfer and permissions

---

# Architecture

```text
Your Laptop
      |
      | SSH using Public IP
      v
EC2 Instance 1 (Sender)
Private IP: 10.0.1.x
      |
      | SCP using Private IP inside VPC
      v
EC2 Instance 2 (Receiver)
Private IP: 10.0.1.y
```

---

# Step 1 - Create Custom VPC

Go to:

```text
AWS Console → VPC → Your VPCs → Create VPC
```

Select:

```text
VPC and more
```

Configure:

| Setting | Value |
|---|---|
| Name | LP2-VPC |
| IPv4 CIDR | 10.0.0.0/16 |

Keep default:
- 1 Public Subnet
- Internet Gateway Enabled
- Route Table Auto-created

Click:

```text
Create VPC
```

---

# Step 2 - Launch EC2 Instances

Go to:

```text
EC2 → Launch Instance
```

Create two instances:

| Instance | Purpose |
|---|---|
| Instance 1 | Sender |
| Instance 2 | Receiver |

Use:

| Setting | Value |
|---|---|
| OS | Ubuntu |
| Instance Type | t2.micro |
| VPC | LP2-VPC |
| Subnet | Public Subnet |
| Auto Assign Public IP | Enable |

Use same `.pem` key pair for both instances.

---

# Step 3 - Configure Security Group

Create one security group for both instances.

---

# Inbound Rules

| Type | Port | Source |
|---|---|---|
| SSH | 22 | My IP |
| SSH | 22 | 10.0.0.0/16 |

---

# Explanation

| Rule | Purpose |
|---|---|
| My IP | Allows laptop to connect using SSH |
| 10.0.0.0/16 | Allows internal SSH communication inside VPC |

---

# Step 4 - Note IP Addresses

From EC2 dashboard note:

| Instance | Public IP | Private IP |
|---|---|---|
| Sender | 13.xx.xx.xx | 10.0.1.x |
| Receiver | 15.xx.xx.xx | 10.0.1.y |

---

# Step 5 - Connect to Sender Instance

Open terminal/PowerShell in folder containing `.pem` file.

Set permission:

```bash
chmod 400 key.pem
```

Connect:

```bash
ssh -i key.pem ubuntu@SENDER_PUBLIC_IP
```

Example:

```bash
ssh -i sharing.pem ubuntu@13.xx.xx.xx
```

---

# Step 6 - Create File on Sender

Inside Sender EC2:

```bash
nano test.txt
```

Write:

```text
Hello, this file is transferred securely inside custom VPC.
```

Save:

```text
CTRL + X → Y → Enter
```

Verify:

```bash
ls
cat test.txt
```

---

# Step 7 - Copy PEM Key to Sender Instance

Open another terminal on your laptop.

Run:

```bash
scp -i key.pem key.pem ubuntu@SENDER_PUBLIC_IP:/home/ubuntu/
```

Example:

```bash
scp -i sharing.pem sharing.pem ubuntu@13.xx.xx.xx:/home/ubuntu/
```

---

# Step 8 - Set Permission for PEM Key

Inside Sender EC2:

```bash
chmod 400 key.pem
```

---

# Step 9 - Secure File Transfer Using Private IP

Inside Sender EC2:

```bash
scp -i key.pem test.txt ubuntu@RECEIVER_PRIVATE_IP:/home/ubuntu/
```

Example:

```bash
scp -i sharing.pem test.txt ubuntu@10.0.1.45:/home/ubuntu/
```

---

# Explanation

The file is transferred:
- securely using SCP
- inside same VPC
- using private IP communication
- without public internet routing

---

# Step 10 - Connect to Receiver Instance

From your laptop:

```bash
ssh -i key.pem ubuntu@RECEIVER_PUBLIC_IP
```

Example:

```bash
ssh -i sharing.pem ubuntu@15.xx.xx.xx
```

---

# Step 11 - Verify File Transfer

Inside Receiver EC2:

```bash
ls
```

Expected:

```text
test.txt
```

Read file:

```bash
cat test.txt
```

Expected Output:

```text
Hello, this file is transferred securely inside custom VPC.
```

---

# Step 12 - Set File Permissions

```bash
chmod 600 test.txt
```

---

# Permission Explanation

| Command | Meaning |
|---|---|
| chmod 400 | Read-only for owner |
| chmod 600 | Read/write only for owner |

---

# Useful Commands

## Check Current Folder

```bash
pwd
```

---

## List Files

```bash
ls
```

---

## Show Private IP

```bash
hostname -I
```

---

## Check Security Group Connectivity

```bash
ping RECEIVER_PRIVATE_IP
```

---

# Common Errors and Fixes

---

## 1. Permission denied (publickey)

Fix:

```bash
chmod 400 key.pem
```

Ensure correct key pair is used.

---

## 2. Connection timed out

Check Security Group inbound rules.

Required:

| Type | Port | Source |
|---|---|---|
| SSH | 22 | 10.0.0.0/16 |

---

## 3. No such file or directory

Check file exists:

```bash
ls
```

---

## 4. SCP not working

Correct syntax:

```bash
scp -i key.pem file.txt ubuntu@PRIVATE_IP:/home/ubuntu/
```

---

## 5. Could not resolve hostname

Ensure private IP is correct.

Example:

```text
10.0.1.45
```

---

# Viva Questions

## What is VPC?

Virtual Private Cloud used to create isolated cloud networks.

---

## Why use Private IP instead of Public IP?

Private IP communication:
- is more secure
- stays inside AWS network
- avoids internet exposure

---

## What is SCP?

Secure Copy Protocol used to transfer files securely over SSH.

---

## Why same VPC?

Instances inside same VPC can communicate privately.

---

## What is SSH?

Secure Shell protocol used for remote Linux access.

---

## What is Security Group?

AWS virtual firewall controlling inbound and outbound traffic.

---

## Why use chmod 400 on PEM file?

To secure the private key and allow SSH authentication.

---

# Output

The file was securely transferred between two EC2 instances inside the same custom VPC using SCP over private IP communication.

---

# Conclusion

Secure file sharing between two EC2 instances was successfully implemented using AWS VPC, SSH, and SCP. Both instances communicated using private IP addresses inside the same virtual private cloud, ensuring secure and isolated file transfer.
