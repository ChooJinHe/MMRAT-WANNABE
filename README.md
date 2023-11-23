# MMRAT WANNABE

MMRAT WANNABE is a proof of concept demonstrating how an attacker can exploit the accessibility service to perform malicious activities, such as logging into your bank account. The project comprises three main sub-projects:

1. **MMRAT WANNABE Mobile Application**: A mobile application that utilizes the accessibility service, connects to C2, and exfiltrates data to the server.

2. **MMRAT WANNABE C2**: A console for managing and executing various exploitation tasks on target devices.

3. **MMRAT WANNABE Server**: A server capable of hosting the APK and receiving data from the application.

## Getting Started

Follow these steps to get started with MMRAT WANNABE:

1. Clone the repository.

2. Navigate to the project directory: `cd MMRAT_WANNABE`.

3. Launch the MMRAT WANNABE C2:
   - To start the C2 console, run: `python main.py`.
   - Refer to the [MMRAT WANNABE C2 Guide](./mmrat_wannabe_console/README.md) for command usage and examples.

4. Launch the MMRAT WANNABE Server:
   - To start the server, run: `python main.py`.
   - The [MMRAT WANNABE Server Guide](./mmrat_wannabe_server/README.md) contains information on setting up and relevant details.

5. Develop MMRAT WANNABE APKs:
   - The [MMRAT WANNABE Development Guide](./MMRATWANNABE/README.md) contains information on setting up and using the Android Studio project for creating malicious APKs.

## Video Demo
Watch the video below to see how the application can be distributed.

https://github.com/ChooJinHe/MMRAT-WANNABE/assets/28280357/bfcd1653-331b-461c-bb6a-080b3bcc89d5

Watch the video below to see the application connected to the C2 and start sending data to server after accessibility setting enabled.

https://github.com/ChooJinHe/MMRAT-WANNABE/assets/28280357/5bb42769-403e-4876-a408-1f4646d49405

Watch the video below to see the application auto accept permission.

https://github.com/ChooJinHe/MMRAT-WANNABE/assets/28280357/037b861d-ebd1-4892-9788-e8414f886315

Watch the video below to see the Unlock Feature in action:

Version 1 is placeholder pattern used.

https://github.com/ChooJinHe/MMRAT-WANNABE/assets/28280357/7323e1ed-ac2a-42c8-808a-0a26d6b65233

Current version allows the attacker to input the pattern to draw.

https://github.com/ChooJinHe/MMRAT-WANNABE/assets/28280357/da0e7da9-4233-4f05-bf33-900254f09ba4

Watch the video below to see the hack bank application in action:

https://github.com/ChooJinHe/MMRAT-WANNABE/assets/28280357/0e8609ba-b21a-41d5-b125-b505f46a97df

## Disclaimer

This project is intended for educational and ethical purposes only. Unauthorized use of these tools is strictly prohibited. The project maintainers are not responsible for any misuse or illegal activities.
