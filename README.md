# Kafka Offset Lag Service
## Overview
The Kafka Offset Lag Service is a Spring Boot application that provides an endpoint to retrieve the offset lag information for a specified Kafka topic and consumer group. It calculates the total number of messages, the number of read messages, and the number of unread messages for each partition of the specified topic.

## Project Structure
-com.pawanpatidar.kafkaUnread.service.KafkaOffsetLagService: A service class that connects to Kafka, retrieves offset information, and calculates the lag.
-com.pawanpatidar.kafkaUnread.controller.KafkaOffsetLagController: A REST controller class that exposes an endpoint to access the offset lag information.

###  Prerequisites
-Java 11 or higher
-Apache Kafka (running locally or accessible via network)
-Spring Boot
-Maven or Gradle (for building the project)

#### Setup and Configuration
Clone the Repository
```
git clone <repository-url>
cd <repository-directory>
```
### Configure Kafka
Ensure that you have a Kafka instance running. The default configuration uses localhost:9092. If your Kafka instance is running on a different host or port, update the bootstrapServers list in KafkaOffsetLagService class.


## Endpoint
The application exposes a single endpoint to retrieve offset lag information:

GET /kafka/lag
### Parameters
-topic (String): The name of the Kafka topic.
-groupId (String): The Kafka consumer group ID.
-Response
The response will be a JSON object containing:

totalMessages: The total number of messages in the topic.
totalReadMessages: The total number of messages that have been read (committed offset).
totalUnreadMessages: The number of messages that have not been read (lag).

-Example Request

GET /kafka/lag?topic=my-topic&groupId=my-group

-Example Response

```{
    "totalMessages": 1500,
    "totalReadMessages": 1200,
    "totalUnreadMessages": 300
}``

## Testing with Postman
-Open Postman: Launch the Postman application.

-Create a New Request:

Set the request type to GET.
Enter the URL in the format: http://localhost:8080/kafka/lag?topic=<topic>&groupId=<groupId>. Replace <topic> and <groupId> with the appropriate values.
Send the Request: Click the "Send" button to make the request.

-View the Response: The response will be displayed in the Postman interface, showing the JSON object with the offset lag information.
