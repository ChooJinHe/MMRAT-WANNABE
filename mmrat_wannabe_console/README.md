# MMRAT WANNABE CONSOLE

The MMRAT WANNABE Console is used to send commands to the malicious application on the compromised device. Upon launching the console, it will listen for incoming connections.

## Pre-requisites:

Before running the program, you need to:
- Modify the configuration in `settings.py` to include the server's IP address and port number for the WebSocket.

## Available Commands:

- `wakeup`: Wake the phone screen.
- `installedapp`: Send the applications installed on the phone to the server.
- `froze`: User cannot click or swipe the screen.
- `unfroze`: Disable freezing the user screen.
- `click <x> <y>`: Click on a specific position given where x and y are the coordinates to click.
- `doubleclick`: Click twice on the screen.
- `swipeup`: Perform swipe up gesture.
- `swipedown`: Perform swipe down gesture.
- `swipeleft`: Perform swipe left gesture.
- `swiperight`: Perform swipe right gesture.
- `set <id> <value>`: Set text value to a specific UI based on its ID, for example, a text field.
- `home`: Go back to the home screen.
- `back`: Go back.
- `unlock <patterns>`: Unlock the screen based on the patterns given.

## Running the Program:

To run the tool, execute `python main.py`.
