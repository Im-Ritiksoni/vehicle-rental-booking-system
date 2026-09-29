# Vehicle Rental & Booking System

A microservices-based vehicle rental platform built with Spring Boot and Spring Cloud. Customers, vehicles, drivers, bookings, payments, trips and feedback are handled by separate services. Services discover each other through Eureka and communicate using OpenFeign.

## Architecture

| Service | Responsibility | Calls |
|---|---|---|
| eureka-server | Service registry | - |
| customer-service | Customer profiles | - |
| vehicle-service | Vehicle fleet and daily fee | - |
| driver-service | Drivers, license and specialization | - |
| booking-service | Rental bookings | customer, vehicle |
| payment-service | Payments for bookings | customer, booking |
| trip-service | Trips with vehicle and driver | vehicle, driver |
| feedback-service | Ratings and comments | customer, trip, booking |

## Tech Stack
- Java, Spring Boot
- Spring Cloud (Eureka Server and Client, OpenFeign)
- Spring Data JPA, Hibernate
- MySQL
- Maven

## Key Features
- Service discovery with Eureka (no hardcoded host or port)
- Declarative inter-service calls with OpenFeign
- Validation before saving: no record is created for an invalid customer, vehicle, driver, booking or trip
- Proper HTTP status codes and error responses
- Full CRUD REST APIs for every service

## Data Model (main fields)
- Customer: id, name, email, mobile
- Vehicle: id, vehicleName, model, type, dailyFee
- Driver: id, name, email, licenseNumber, specialization
- Booking: id, customerId, vehicleId, bookingDate, durationDays, status
- Payment: id, customerId, bookingId, amount, paymentDate, status
- Trip: id, title, routeDetails, vehicleId, driverId, dueDate
- Feedback: id, customerId, tripId, bookingId, ratingScore, comments, feedbackDate

## End-to-End Flow
1. Add a customer, a vehicle and a driver.
2. Create a booking: booking-service verifies the customer and the vehicle.
3. Create a payment: payment-service verifies the customer and the booking.
4. Create a trip: trip-service verifies the vehicle and the driver.
5. Submit feedback: feedback-service verifies the customer, the trip and the booking.

## How to Run
1. Install Java, Maven and MySQL.
2. Update the database username and password in each service's `application.properties`.
3. Start `eureka-server` first and open its dashboard.
4. Start the other seven services.
5. Check that all seven services are registered on the Eureka dashboard.

## Future Improvements
- API Gateway
- Circuit breaker and fallback (Resilience4j)
- Docker and docker-compose
- Security with JWT

## Explain Project

- My project is a Vehicle Rental and Booking System built with microservices using Spring Boot and Spring Cloud.

- I divided the application into seven services: customer, vehicle, driver, booking, payment, trip and feedback. Each service has its own REST APIs for create, read, update and delete, and each one manages its own data.

- I also created a Eureka Server.
- All seven services register there, so they can find each other by service name and I don't hardcode any port.

- For communication I used OpenFeign.
- For example, when a customer books a vehicle, the booking service calls the customer service and the vehicle service.
- If either one does not exist, I return an error and don't create the booking.
- In the same way, the payment service verifies the customer and the booking.
- The trip service verifies the vehicle and the driver.
- The feedback service verifies the customer, the trip and the booking.

- The full flow is: book a vehicle, make the payment, create a trip with a driver, and then submit feedback.

- I used MySQL with Spring Data JPA for the database.
- Through this project I learned service discovery, service-to-service communication and validation across services.
