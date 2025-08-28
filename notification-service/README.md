Kafka producer implicitly send type headers along with message:
```
{
	"__TypeId__": "com.killian.microservices.order_service.event.OrderPlacedEvent"
}
```
Either ignore it or both services have to contain a same class under same package