# Polite Message
Its a gui based Peer to Peer application in java that works by establishing two tcp connections between peers for two way communication.
Each Peer acts as a client as well as a server. 
# How to run:
1. add dependency json-simple-1.1.jar to your build and build application
2. run GUI.java for application to run.
3. Run two instances.
4. add IP (127.0.0.1) in case both instances are on same machine and username and click connect to connect.
5. Click on time button to get current time.
6. To add message fill required fields and click on add.
7. To get a message add message SHA in GET field and click GET.
8. To ge list of messages add time ( so that system can get messages older than gien time).
9. add no of headers.
10. Headers are Topic, Subject.
11. For header use format HEADER-NAME: VALUE
12. For multiple Headers use HEADER1: VAL,HEADER2: VAL
13. Click get to get sha of all available messages.