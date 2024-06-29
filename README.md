# Text and Snap - Computer Networks

Chat application with file transfer capability.


## How to run

### Alice
```bash
Last login: Thu Apr 20 08:45:58 on ttys000
saijayanthchidirala@darth ~ % cd Desktop/project3 
saijayanthchidirala@darth project3 % javac chat.java
saijayanthchidirala@darth project3 % java chat
Server initialized on port : 59313
Enter Your Name: Alice
Enter the port number you want to chat with : 59314
Connected to Reader 59314
New client connected
Hi! this is alice. Who are you?
Message from Bob : hi. Im bob. nice to meet you
project working fine?
Message from Bob : yes! looks like it!
transfer testfile1.pptx
Message from Bob : transfer testfile2.pptx
```
### Bob
```bash
Last login: Tue Apr  4 11:01:14 on ttys003
saijayanthchidirala@darth ~ % cd Desktop/project3 
saijayanthchidirala@darth project3 % java chat
Server initialized on port : 59314
Enter Your Name: New client connected
Bob
Enter the port number you want to chat with : 59313
Connected to Reader 59313
Message from Alice : Hi! this is alice. Who are you?
hi. Im bob. nice to meet you
Message from Alice : project working fine?
yes! looks like it!
Message from Alice : transfer testfile1.pptx
transfer testfile2.pptx
```

## Methodology

Each instance of chat.java represents one node in the two way chat application.

## Requirements

Java Development Kit 8
