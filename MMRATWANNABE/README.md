# MMRAT WANNABE Android Studio Project

## Getting Started

1. Clone this repository to your local machine.

2. Open the project in Android Studio.

## Prerequisites

Before you begin, ensure you meet the following requirements:

1. Update the **BASE_URL** and **PORT** in [Config.java](/app/src/main/java/com/example/mmrat_wannabe/Config.java) with the IP address and port number of your server.

2. Store the .CRT file, naming it "domain.crt," within the **res/raw** folder for TLS connections. Refer to [this guide](https://www.baeldung.com/openssl-self-signed-cert) for creating a self-signed certificate.

## Features

### SmallActivity

- Creates a 1x1 transparent pixel to trigger `onAccessibilityEvent` in the `MaliciousAccessibilityService` when a command is received.

### GestureUtils

- Holds functions that involve clicking and swiping, such as `doubleClick` and `swipeUp`.

### MaliciousAccessibilityService

- Consists of important functions like `clickOn` and `setText`, where `clickOn` allows clicking on a specific UI based on the ID, while `setText` allows setting text to a UI based on the specific ID.

- Includes an `unlock` function to unlock the user's phone by providing the position to swipe.

- Uploads current screen information to the MMRAT WANNABE SERVER.

- Executes commands received from the C2.

## Further Guidance

- Explore the code for more detailed information on functionalities.

- For TLS connections, ensure the correct setup of the self-signed certificate as per the provided guide.

- For generating APKs, refer to [app signing](https://developer.android.com/studio/publish/app-signing) so you can host it on MMRAT WANNABE SERVER.

## Disclaimer

This project is intended for educational and ethical purposes only. Unauthorized use of these tools is strictly prohibited. The project maintainers are not responsible for any misuse or illegal activities.
