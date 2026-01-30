# Quick Tip – Digital Tipping System

A backend-driven digital tipping platform that enables secure online tips using Razorpay.  
Built with Spring Boot following clean layered architecture and deployed using Docker.
Live Link: https://quick-tip-systems.onrender.com/
## 🚀 Features
- Secure digital tipping using Razorpay Payment Gateway
- Signature verification for payment authenticity
- RESTful APIs with controller-service-repository pattern
- Dockerized for consistent deployment
- Deployed on Render

## 🛠️ Tech Stack
- **Backend:** Java, Spring Boot
- **Database:** MongoDB
- **Payments:** Razorpay API
- **DevOps:** Docker
- **Tools:** Maven, Postman, Git

## 🧱 Architecture
- Controller Layer – Handles HTTP requests
- Service Layer – Business logic
- Repository Layer – Database operations
- Follows clean, modular, and maintainable design

## 🔐 Payment Flow
1. Client initiates tip request
2. Razorpay order is created
3. Payment signature is verified server-side
4. Transaction is validated and stored

## 🧪 Testing
- End-to-end API testing using Postman
- Log-based debugging for payment failures

## 📦 Deployment
- Dockerized backend
- Deployed on Render with environment-based configuration

## ▶️ How to Run Locally
```bash
git clone https://github.com/ArunKotu/Quick-Tip
cd quick-tip
mvn clean install
docker build -t quick-tip .
docker run -p 8080:8080 quick-tip
