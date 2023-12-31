# White-Board-Management
This project completes the process of creating a shared whiteboard between the manager and the user, and enables the shared creation of patterns and text by using the
mouse and keyboard. For example, a circular pattern, triangular pattern, and rectangular pattern can be placed on the whiteboard between the manager and the user by using the mouse. Also, the functions of drawing straight lines and freehand writing can be implemented on the shared whiteboard. In the shared whiteboard, this project also implements the keyboard input text function and the function to change the color of fonts and patterns.

In this project, once the server is created, the server continuously receives messages from the client through the socket method.

We use Json format to transfer data between communications. This project firstly declares a new project class called sendinginformation, by saving the information of
each data in this class, and converting the instance class to Json format, then using the socket to establish a connection, and sending this Json instance to the client or server through the writeUTF method within the DataOutputStream. The data is obtained by the readUTF method using the DataInputStream, which gets the Json information from the client or server and translates it into an instance of sendinginformation.

Design diagrams:
![1703995265612](https://github.com/JasonRenBang/White-Board-Management/assets/54545506/d9576cec-d2e1-41bf-8d02-495463fc12f5)
