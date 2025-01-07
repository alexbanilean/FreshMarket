# Fresh Market Platform

## Overview

The **Fresh Market Platform** is an online marketplace designed to connect local farmers with consumers, enabling the delivery of fresh produce directly to customers. The platform allows farmers to list their products, track orders, and manage sales, while customers can browse products, place orders, and track their delivery statuses.

## Business Requirements

### 1. **Farmer Registration**
- Farmers must be able to register on the platform, creating their profiles and providing information about their farm and available products.

### 2. **Customer Registration**
- Consumers must be able to register and create profiles, enabling them to browse products and place orders.

### 3. **Product Listing**
- Farmers should be able to list products with associated prices, stock levels, and descriptions. They can update product details and availability.

### 4. **Order Placement**
- Customers should be able to browse products, select items, and place orders. Orders will include details such as the product, quantity, and delivery address.

### 5. **Order Tracking**
- Once an order is placed, customers can track its status (Pending, Shipped, Delivered). The platform should provide status updates and progress notifications.

### 6. **Payment Integration**
- Customers should be able to make payments for their orders using online payment methods (e.g., credit card, PayPal) or choose cash on delivery.

### 7. **Rating and Review System**
- After receiving orders, customers should be able to rate and review farmers and their products. This helps build trust and improve transparency.

### 8. **Sales Trend Reports**
- Farmers should have access to sales reports that display trends over time, including product popularity, revenue generation, and inventory updates.

### 9. **Notification System**
- The platform will notify customers about product availability, order status changes, seasonal produce, and promotions via email or SMS.

### 10. **Seasonal Produce and Promotions**
- The platform should highlight seasonal produce and provide promotional offers, such as discounts or special deals on specific products.

## MVP Features

The **Minimum Viable Product (MVP)** phase will focus on delivering the core functionality of the platform, ensuring farmers and customers can register, list products, place orders, and track deliveries. Below are the 5 key features for the MVP:

### 1. **Farmer and Customer Registration**
- Farmers and customers must be able to register by providing their personal and farm details (for farmers) or personal details (for customers).
- The registration flow will include email verification and authentication using Spring Security.

### 2. **Product Listing and Browsing**
- Farmers can list their products with associated details such as name, description, price, and stock levels.
- Customers can browse products, filter by category, and check the product details before placing orders.

### 3. **Order Placement and Tracking**
- Customers can create orders, including details about delivery.
- Once an order is placed, customers can track its status (Pending, Shipped, Delivered) through their order history.

### 4. **Rating and Review System**
- After the successful delivery of an order, customers can leave ratings and reviews for the farmer, helping others make informed purchasing decisions.

### 5. **Sales Trend Reports for Farmers**
- Farmers can access a basic report that shows the total sales, the number of orders, and products sold. This will allow them to monitor their sales performance.

## API Documentation

You can refer to the Swagger API documentation for detailed information on each endpoint, request parameters, and responses.

## Future Features (Beyond MVP)

Once the MVP is released and the core functionality is working smoothly, the following features will be added to enhance the platform:

### 1. **Payment Integration**
- Integration with payment gateways like PayPal and Stripe to handle online payments securely.

### 2. **Advanced Order Tracking and Delivery Logistics**
- A more sophisticated delivery system, integrating with third-party logistics or tracking APIs to manage real-time delivery updates.

### 3. **Notification System**
- Notifications will be set up for customers to inform them about product availability, order status, and promotional offers.

### 4. **Seasonal Produce and Promotions**
- The platform will feature seasonal products, allowing farmers to mark produce as "in-season" and offering discounts or promotional deals.

### 5. **Advanced Sales Reports**
- More detailed reports on sales trends, including the ability to filter by product, farm, or time period.

## Repository

The project is hosted on GitHub, and you can access it using the link below:

[GitHub Repository - Fresh Market Platform](https://github.com/alexbanilean/FreshMarket/tree/main)
