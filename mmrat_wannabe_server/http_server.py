from http.server import BaseHTTPRequestHandler
import os

class MyHandler(BaseHTTPRequestHandler):

    def do_GET(self):

        try:
            if self.path.startswith('/apk/') and self.path.endswith('.apk'):
                filepath = os.path.join(os.path.dirname(__file__), 'apk', os.path.basename(self.path))
                if os.path.exists(filepath) and os.path.isfile(filepath):
                    with open(filepath, 'rb') as f:
                        # send code 200 response
                        self.send_response(200)

                        # send header first
                        self.send_header('Content-type', 'application/octet-stream')
                        self.send_header('Content-Disposition', 'attachment; filename="{}"'.format(os.path.basename(filepath)))
                        self.send_header('Content-Length', str(os.path.getsize(filepath)))
                        self.end_headers()

                        # send file content to client
                        self.wfile.write(f.read())
                    return

        except IOError:
            self.send_error(404, 'file not found')  

    def do_POST(self):
        try:
            # print(self.path)
            if self.path.startswith('/installed_app/'):
                content_length = int(self.headers['Content-Length'])
                post_data = self.rfile.read(content_length)

                package_info = post_data.decode('utf-8').strip().split('\\n')

                # Format and print the output
                print("Packages Installed:")
                print("-" * 56)
                for info in package_info:
                    print(info)
                print("-" * 56)

                # Send code 200 response
                self.send_response(200)
                self.send_header('Content-type', 'text/html')
                self.end_headers()

                # Send response content
                self.wfile.write(b'Received')
    

            if self.path.startswith('/screen/'):
                content_length = int(self.headers['Content-Length'])
                post_data = self.rfile.read(content_length)

                # Convert the byte string to a regular string and split it by '\\n'
                widget_info = post_data.decode('utf-8').strip().split('\\n')

                # Format and print the output
                print("Screen Content:")
                print("-" * 56)
                for info in widget_info:
                    print(info)
                print("-" * 56)

                # Send code 200 response
                self.send_response(200)
                self.send_header('Content-type', 'text/html')
                self.end_headers()

                # Send response content
                self.wfile.write(b'Received')

        except Exception as e:
            self.send_error(500, 'Internal Server Error: {}'.format(str(e)))




        




