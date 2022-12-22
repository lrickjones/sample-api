# sample-api
Sample REST API for demonstration and testing

## Build
mvn clean package

## Run
java -jar target/sample-api-0.0.1-SNAPSHOT.jar

## Test From Browser
http://localhost:8080/swagger-ui/

## Test From Curl

### Create Customer
curl -X POST "http://localhost:8080/customers" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"email\":\"string\",\"firstName\":\"string\",\"id\":0,\"lastName\":\"string\"}"

### Find Customer
curl -X GET "http://localhost:8080/customers/1" -H  "accept: */*"
