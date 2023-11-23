from banner_generator import generate_banner
from my_web_socket import start_server
import asyncio
text = "MMRAT WANNABE"
banner = generate_banner(text)
print(banner)

print("Loading console...")

def exploit():
    print("Running exploit")
    asyncio.run(start_server())

exploit()