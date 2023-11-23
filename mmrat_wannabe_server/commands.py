import asyncio
from my_web_socket import start_server
import settings
from tcp_server import startTCPListener


# Define your commands
def summary():
    print("Running Summary")
    print("LHOST: ", settings.LHOST)
    print("LPORT: ", settings.LPORT)
    print("RCE_enabled: ", settings.RCE_enabled)

# def config():
#     print("Running Config")
#     settings.LHOST = input("LHOST = ")
#     settings.LPORT = input("LPORT = ")
#     RCE_enabled_input = input("RCE_enabled = T/F?")
#     if RCE_enabled_input == "T":
#         settings.RCE_enabled = True
#     else:
#         settings.RCE_enabled = False

def exploit():
    if settings.LHOST == "" or settings.LPORT == "":
        print("Please configure your settings first")
        return
    print("Running exploit")
    # startTCPListener()

    if settings.RCE_enabled == True:
        # Run the server.
        # asyncio.get_event_loop().run_until_complete(start_server()) 
        asyncio.run(start_server())




def help_command():
    print("Available commands:")
    for command in commands:
        print(f"{command:<15}{commands[command][1]}")


# Create a dictionary mapping user input to commands
commands = {
    "summary": [summary,"Print out summary"],
    # "config": [config,"Configure settings"],
    "exploit": [exploit,"Run exploit"],
    "help": [help_command, "Display available commands"]
}

# Execute the chosen command
def execute(command):
    # Execute the selected command
    if command in commands:
        commands[command][0]()
    else:
        print("Invalid command")

