# Static Website Deployment on AWS EC2 (Ubuntu)

This project demonstrates how to design and deploy a static website on a cloud virtual machine using Amazon EC2. The server is configured to be publicly accessible and supports remote website updates using SSH, SCP, and Git.

---

# Prerequisites

- AWS Account
- EC2 Ubuntu Instance
- Key Pair (`.pem` file)

---

# Step 1: Connect to Ubuntu EC2 Instance

```bash
chmod 400 your-key.pem

ssh -i your-key.pem ubuntu@<public-ip>
```

Note:
- Default username for Ubuntu EC2 is `ubuntu`

---

# Step 2: Update System Packages

```bash
sudo apt update
sudo apt upgrade -y
```

---

# Step 3: Install Apache Web Server

```bash
sudo apt install apache2 -y
```

Start and enable Apache:

```bash
sudo systemctl start apache2
sudo systemctl enable apache2
```

---

# Step 4: Configure Security Group

In AWS EC2 Security Group, allow:

| Type | Port | Source |
|------|------|------|
| HTTP | 80 | 0.0.0.0/0 |
| SSH | 22 | Your IP |

This makes the website publicly accessible.

---

# Step 5: Test Apache Server

Open browser:

```text
http://<your-public-ip>
```

You should see the default Apache page.

---

# Step 6: Deploy Static Website

Move to Apache web directory:

```bash
cd /var/www/html
```

Remove default Apache page:

```bash
sudo rm index.html
```

Create a new HTML file:

```bash
sudo nano index.html
```

Paste:

```html
<!DOCTYPE html>
<html>
<head>
  <title>AWS Ubuntu Website</title>
</head>
<body>
  <h1>Hello from Ubuntu EC2</h1>
</body>
</html>
```

Restart Apache:

```bash
sudo systemctl restart apache2
```

Refresh browser to view website.

---

# Step 7: Fix File Permissions

```bash
sudo chown -R ubuntu:ubuntu /var/www/html
```

---

# Step 8: Remote Website Update Methods

## Method 1: SSH (Direct Editing)

```bash
nano /var/www/html/index.html
```

Edit files directly on the server and save changes.

---

## Method 2: SCP (File Transfer)

From local machine:

```bash
scp -i your-key.pem index.html ubuntu@<public-ip>:/home/ubuntu
```

Move file to web directory:

```bash
sudo mv /home/ubuntu/index.html /var/www/html/
```

Restart Apache:

```bash
sudo systemctl restart apache2
```

---

## Method 3: Git-Based Deployment

Install Git:

```bash
sudo apt install git -y
```

Remove old website files:

```bash
sudo rm -rf /var/www/html/*
```

Clone repository:

```bash
cd /tmp

git clone https://github.com/<your-username>/<repo-name>.git
```

Copy files to Apache directory:

```bash
sudo cp -r <repo-name>/* /var/www/html/
```

For future updates:

```bash
cd /tmp/<repo-name>

git pull

sudo cp -r * /var/www/html/
```

Restart Apache:

```bash
sudo systemctl restart apache2
```

---

# Deploying a React Project on EC2

## Install Node.js and npm

```bash
sudo apt install nodejs npm -y
```

Check versions:

```bash
node -v
npm -v
```

---

## Create React Production Build

Inside React project folder:

```bash
npm install

npm run build
```

This generates a `build` folder containing optimized static files.

---

## Upload React Build to EC2

From local machine:

```bash
scp -r -i your-key.pem build/* ubuntu@<public-ip>:/home/ubuntu
```

Connect to EC2:

```bash
ssh -i your-key.pem ubuntu@<public-ip>
```

Copy files to Apache directory:

```bash
sudo cp -r /home/ubuntu/* /var/www/html/
```

Restart Apache:

```bash
sudo systemctl restart apache2
```

Open browser:

```text
http://<your-public-ip>
```

Your React application should load successfully.

---

# Conclusion

A static website was successfully deployed on an AWS EC2 Ubuntu instance using Apache Web Server. The server was configured for public access using Security Groups, and remote website updates were enabled through SSH, SCP, and Git-based deployment methods. A React application was also deployed using Apache.
