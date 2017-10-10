Base demo of the first PAD lab

Installation To run this project you need Java and Apache Maven.

Running project This project have 3 components:

Message broker; Client-producer; Client-consumer; Message broker (Broker.java) listens on localhost:6066 for messages from clients.

Client-producer (Sender.java) sends each second a message to the broker.

Client-consumer (Receiver.java) polls each second for a new message from the broker.
