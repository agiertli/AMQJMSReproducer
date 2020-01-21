###
Start the AMQ Broker:

../bin/artemis create bpm-broker
./artemis queue create --name bpmqueue --auto-create-address --anycast
 ./artemis run

 ### 
 Start the app

 mvn spring-boot:run


###
Check the queue - only routed message count is increased, and not message count.
http://localhost:8161/console/artemis/browseQueue?tab=artemis&nid=root-org.apache.activemq.artemis-%220.0.0.0%22-addresses-%22bpmqueue%22-queues-%22anycast%22-%22bpmqueue%22