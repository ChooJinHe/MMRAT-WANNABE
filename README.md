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

<video width="320" height="240" controls>
  <source src="./demos/installing app.mp4" type="video/mp4">
  Your browser does not support the video tag.
</video>

Watch the video below to see the application connected to the C2 and start sending data to server after accessibility setting enabled.

<video width="320" height="240" controls>
  <source src="./demos/enable accessibility.mp4" type="video/mp4">
  Your browser does not support the video tag.
</video>

Watch the video below to see the application auto accept permission.

<video width="320" height="240" controls>
  <source src="./demos/auto accept permission.mp4" type="video/mp4">
  Your browser does not support the video tag.
</video>

Watch the video below to see the Unlock Feature in action:

Version 1 is placeholder pattern used.

<video width="320" height="240" controls>
  <source src="./demos/unlock screen v1.mp4" type="video/mp4">
  Your browser does not support the video tag.
</video>

Current version allows the attacker to input the pattern to draw.

<video width="320" height="240" controls>
  <source src="./demos/unlock screen v2.mp4" type="video/mp4">
  Your browser does not support the video tag.
</video>

Watch the video below to see the hack bank application in action:

<video width="320" height="240" controls>
  <source src="./demos/hack posb.mp4" type="video/mp4">
  Your browser does not support the video tag.
</video>

## Disclaimer

This project is intended for educational and ethical purposes only. Unauthorized use of these tools is strictly prohibited. The project maintainers are not responsible for any misuse or illegal activities.
