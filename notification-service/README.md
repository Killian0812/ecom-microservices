Kafka producer implicitly send type headers along with message:
```
{
	"__TypeId__": "com.killian.microservices.order_service.event.OrderPlacedEvent"
}
```
Either ignore it or both services have to contain a same class under same package

Replace with avro and schema registry for centralized validating schemas for topic message data
```
{
  "type": "record",
  "name": "OrderPlacedEvent",
  "namespace": "com.killian.microservices.order.event",
  "fields": [
    { "name": "orderNumber", "type": "string" },
    { "name": "email", "type": "string" }
  ]
}
```