# Notebook App — Your To-Do App for Personal Task Management

> Warning! This app contains malicious code (spyware) to track user data. This repository is provided for educational or information purposes only.

## Main features
- Create and manage tasks
- Locate hotspots where you have created tasks frequently
- i18n (English & German)
- **All logs (including tasks, E-Mail address and Android device ID) will be sent in _real-time_ to the [Notebook backend](https://github.com/MatthiasEckhart/NotebookSurveillanceStation) for surveillance purposes**

## Focus
- Simplicity & Usability

## Team members
- [Matthias Eckhart](https://github.com/MatthiasEckhart)
- [Johannes Teichert](https://github.com/JohannesTeichert)

## Implementation details 
- Minimum API Level: 14
- Target API Level: 19
- Proguard has been intentionally disabled
- Object Relational Mapping with ORMLite
- Patched ORMLite to work with SQLCipher (thanks to [Igor K](https://github.com/sierpito/demo-ormlite-with-sqlcipher))
- Set `HOST` variable in `/app/build.gradle` to your deployed [Notebook backend](https://github.com/MatthiasEckhart/NotebookSurveillanceStation), e.g. `"http://<IP>:<Port>"` (with quotes)
