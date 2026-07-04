# 🌱 EcoDhan - Green Credit Trading Platform

EcoDhan is a backend application built with Spring Boot that enables companies to earn, trade, and manage Green Credits. The platform provides secure authentication, wallet management, green credit requests, order matching, and transaction history to support a transparent green credit marketplace.

---

## 🚀 Features

- JWT-based Authentication & Authorization
- Role-Based Access Control (Admin & Company)
- Company Registration & Profile Management
- Wallet Creation and Money Top-Up
- Green Credit Request & Approval Workflow
- Buy & Sell Green Credit Orders
- Automatic Order Matching
- Trade Execution
- Wallet Transaction History
- RESTful APIs
- Global Exception Handling

---

## 🛠️ Tech Stack

- Java 21
- Spring Boot
- Spring Security
- JWT Authentication
- Spring Data JPA (Hibernate)
- MySQL
- Maven
- Lombok
- REST APIs

---

## 📂 Project Structure

```
src
├── Controller
├── Service
├── Repository
├── Entity
├── DTO
├── Security
├── Filter
├── Config
├── Exception
└── Enum
```

---

## 🔐 User Roles

### Admin
- Review Green Credit Requests
- Approve or Reject Credit Requests

### Company
- Create Wallet
- Top-Up Wallet
- Request Green Credits
- Buy Green Credits
- Sell Green Credits
- View Wallet Balance
- View Transaction History

---

## 💳 Wallet Features

- Money Balance
- Green Credit Balance
- Wallet Top-Up
- Rewarded Credits
- Complete Transaction History

---

## 🔄 Trading Flow

1. Company creates a wallet.
2. Company tops up money.
3. Company requests green credits.
4. Admin reviews and approves/rejects the request.
5. Approved credits are added to the wallet.
6. Companies create Buy/Sell orders.
7. Matching orders execute automatically.
8. Wallet balances and transaction history are updated.

---

## 👨‍💻 Author

**Gurpal Singh**
- LinkedIn: https://www.linkedin.com/in/gurpal-singh-213065220

---

## 📄 License

This project is developed for learning and portfolio purposes.
