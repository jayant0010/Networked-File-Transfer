import java.io.*;
import java.net.*;
import java.util.regex.Pattern;

public class chat {
    
    public static void main(String[] args) throws IOException {

        boolean controller = true;

        // create the server socket and start listening for connections
        ServerSocket serverSocket = new ServerSocket(0);
        int portNumber = serverSocket.getLocalPort();
        System.out.println("Server initialized on port : " + portNumber);
        
        // create the writing thread
        new WriterT().start();

        while (controller) {
            // accept a new connection
            Socket clientSocket = serverSocket.accept();
            System.out.println("New client connected");
            
            // create a new thread for reading from this client
            new ReaderT(clientSocket).start();
        }
    }
}


class ReaderT extends Thread {
    
    private Socket clientSocket;
    
    public ReaderT(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }
    
    @Override
    public void run() {
        try {
            // create the input stream for reading messages from the client

            int bytes;
            
            byte[] byteArray = new byte[1024];
            
            InputStream in = clientSocket.getInputStream();

            String regex = " ";
            Pattern pattern = Pattern.compile(regex);

            String name = "user";
            while ((bytes = in.read(byteArray)) != -1) {
                // print the message received from the client
                String message = new String(byteArray, 0, bytes);

                if(!message.contains("MyNameIsXXXX")){
                    System.out.println("Message from "+ name +" : "+ message);
                }
                
                //check if message has name
                if (message.contains("MyNameIsXXXX") && message.indexOf("MyNameIsXXXX") == 0) {

                    name = pattern.split(message)[1];
                }

                // check if the message is a file transfer request
                if (message.contains("transfer") && message.indexOf("transfer") == 0) {
                    // receive the file
                    String fileN = pattern.split(message)[1];
                    //System.out.println("FileName Reader : "+fileN);
                    FileOutputStream fileOut = new FileOutputStream("new"+fileN);
                    //read file
                    while ((bytes = in.read(byteArray)) != -1) {
                        fileOut.write(byteArray, 0, bytes);
                        if (bytes < byteArray.length) break;
                    }
                    fileOut.close();
                }
            }
            
            // close the socket when the client disconnects
            clientSocket.close();
            System.out.println("Client disconnected");
        } catch (IOException e) {
            System.err.println("Err: " + e.getMessage());
        }
    }
}

class WriterT extends Thread {
    
    @Override
    public void run() {
        try {
            
            boolean controller = true;

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Enter Your Name: ");
            String name = reader.readLine();

            // get the port number from the user
            System.out.print("Enter the port number you want to chat with : ");
            int portNumber = Integer.parseInt(reader.readLine());
            
            String regex = " ";
            Pattern pattern = Pattern.compile(regex);

            // create the client socket and connect to the server
            Socket serverSocket = new Socket("127.0.0.1", portNumber);
            System.out.println("Connected to Reader " + portNumber);
            
            // create the output stream for writing messages to the server
            OutputStream out = serverSocket.getOutputStream();

            //push out name with a uniquely identifiable string
            out.write(("MyNameIsXXXX "+name).getBytes());

            while (controller) {
                // read a message from the keyboard and write it to the server
                String message = reader.readLine();
                out.write(message.getBytes());
                
                // if the message is "transfer filename", send the file
                if (message.contains("transfer") && message.indexOf("transfer") == 0) {
                    String fileN = pattern.split(message)[1];
                    //System.out.println("FileName Writer : "+fileN);
                    FileInputStream fileIn = new FileInputStream(fileN);
                    
                    int bytes;
                    
                    byte[] byteArray = new byte[1024];
                    
                    //send file
                    while ((bytes = fileIn.read(byteArray)) != -1) {
                        out.write(byteArray, 0, bytes);
                    }
                    fileIn.close();
                }
            }
        } catch (IOException e) {
            System.err.println("Err: " + e.getMessage());
        }
    }
}