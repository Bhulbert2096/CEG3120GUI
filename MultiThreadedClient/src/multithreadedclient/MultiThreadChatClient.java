/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multithreadedclient;

import java.io.DataInputStream;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;


public class MultiThreadChatClient extends serverGui implements Runnable  {

  // The client socket
  private static Socket socket = null;
  // The output stream
  private static PrintStream outputStream = null;
  // The input stream
  private static DataInputStream inputStream = null;

  private static BufferedReader inputLine = null;
  private static boolean closed = false;
  
  public static void main(String[] args) {

      serverGui guiObject = new serverGui();
    // The default port.
    int portNumber = guiObject.getsPort();
    // The default host.
    String host = guiObject.getsHost();

    if (args.length < 2) {
      System.out.println("Usage: java MultiThreadChatClient <host> <portNumber>\n"
              + "Now using host=" + host + ", portNumber=" + portNumber);
    } else {
      host = args[0];
      portNumber = Integer.valueOf(args[1]).intValue();
    }

    /*
     * This will open the follow:A socket,BufferReader,PrintStream,and a DataInputStream
     */
    try {
      socket = new Socket(host, portNumber);
      inputLine = new BufferedReader(new InputStreamReader(System.in));
      outputStream = new PrintStream(socket.getOutputStream());
      inputStream = new DataInputStream(socket.getInputStream());
    } catch (UnknownHostException e) {
      System.err.println("Know nothing about " + host);
    } catch (IOException e) {
      System.err.println("Couldn't get I/O for the connection to the host "
          + host);
    }

    /*
     * if the socket, outputstream, and inputstream
     * 
     */
    if (socket != null && outputStream != null && inputStream != null) {
      try {

        /* Create a thread to read from the server. */
        new Thread(new MultiThreadChatClient()).start();
        while (!closed) {
          outputStream.println(inputLine.readLine().trim());
        }
        /*
         * Close the output stream, close the input stream, close the socket.
         */
        outputStream.close();
        inputStream.close();
        socket.close();
      } catch (IOException e) {
        System.err.println("IOException:  " + e);
      }
    }
  }

  /*
   * Create a thread to read from the server. (non-Javadoc)
   * 
   * @see java.lang.Runnable#run()
   */
  public void run() {
    /*
     * Keep on reading from the socket till we receive "Bye" from the
     * server. Once we received that then we want to break.
     */
    String responseLine;
    try {
      while ((responseLine = inputStream.readLine()) != null) {
        System.out.println(responseLine);
        if (responseLine.indexOf("*** Bye") != -1)
          break;
      }
      closed = true;
    } catch (IOException e) {
      System.err.println("IOException:  " + e);
    }
  }
}
