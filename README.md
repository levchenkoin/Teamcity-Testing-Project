# TeamCity Testing Project

## 📌 Description 

This project contains automated tests for JetBrains TeamCity, aimed at validating its core functionality, API behavior, build execution, and system performance. The tests ensure that TeamCity operates correctly under various conditions.

## 🛠️ Tech Stack
- Java + Maven – for test implementation and execution  
- TeamCity REST API – system under test  
- JUnit / TestNG – testing frameworks

## 🚀 Getting Started

To run the GitHub Actions workflow _.github/workflows/test.yml_ locally, use [act](https://github.com/nektos/act).

For local Maven test runs, first update the _src/main/resources/config.properties_ file with the following values:
```
host=<IP>:8111         # <IP> is the output of `ipconfig getifaddr en0` or a similar command
superUserToken=<TOKEN> # Your TeamCity server's Super User Authentication Token
```
To run tests, use (replace _api|ui_ with the desired profile):
```
./mvnw clean test -P [api|ui]
```
After running the tests, generate the Allure report using:
```
./mvnw allure:report
```
The report will be available at _target/site/allure-maven-plugin/***index.html***_.
## 🤝 Contributing 
Contributions are welcome! Please submit pull requests with detailed descriptions of your changes.

## 📄 License 
This project is licensed under the MIT License.
