Energy Management System

Description

Energy Management System is a modular web application developed using Angular and Spring Boot, designed to facilitate the management of users and their associated devices. Its microservices-based architecture ensures scalability and easy maintenance.

Key Features

User Management – Full CRUD operations for users.

Device Management – Full CRUD operations for devices and their association with users.

Real-time Monitoring – Receives energy consumption data via RabbitMQ and sends notifications through WebSocket.

Chat Microservice – Based on WebSocket, enabling real-time communication between users and administrators.

Users can chat with each other.

Administrators can chat with multiple users simultaneously.

Seen and Typing indicators are displayed.

Advanced Security – Authentication using JWT tokens, password encryption, and secured endpoints.

Architecture

The system consists of the following main components:

Frontend (Angular): A modern interface for managing users and devices.

User Microservice (Spring Boot): Handles user CRUD operations, role-based security, JWT authentication, and password encryption.

Device Microservice (Spring Boot): Handles CRUD operations for devices and user-device associations.

Monitoring Microservice (Spring Boot + RabbitMQ): Receives energy consumption data and sends real-time alerts via WebSocket.

Chat Microservice (Spring Boot + WebSocket): Enables real-time messaging.

Security: Implements JWT token authentication, endpoint protection, and password encryption using BCrypt.

Technologies Used

Frontend: Angular

Backend: Spring Boot

Database: PostgreSQL

Messaging: RabbitMQ

WebSockets: For chat and notifications

Security: JWT, BCrypt for password encryption
