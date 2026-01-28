# OpenCart Selenium Test Automation Framework

A **test automation framework** built with **Selenium WebDriver, Java, and TestNG** to automate tests for the [OpenCart](https://www.opencart.com/) e-commerce application. This project was created as a learning exercise for Selenium and TestNG, including **data-driven testing using Apache POI and Excel files**, and **advanced reporting with ExtentReports**.

---

## 📌 **Project Overview**
- **Framework**: Page Object Model (POM)
- **Language**: Java
- **Testing Tools**: Selenium WebDriver, TestNG, Apache POI, ExtentReports
- **Build Tool**: Maven
- **Purpose**: Automate UI tests for OpenCart to validate core e-commerce functionalities, with support for data-driven testing and detailed test reporting.

---

## 🛠 **Technologies & Tools**
| Tool/Technology | Version/Purpose |
|----------------|----------------|
| Java | Programming language |
| Selenium WebDriver | Browser automation |
| TestNG | Test execution and reporting |
| Apache POI | Read/write Excel files for data-driven tests |
| Log4j2 | Logging test execution details and debugging information |
| ExtentReports | Advanced HTML test reporting |
| Maven | Dependency management |
| OpenCart | Open-source e-commerce platform (test application) |

---

## 📂 **Project Structure**

OpenCart_JavaSeleniumTestNg/
├── src/
│   └── test/
│       ├── java/
│       │   ├── listeners/      # TestNG listeners
│       │   ├── pages/          # Page classes (POM)
│       │   ├── tests/          # Test classes
│       │   └── util/           # Utility classes
│       └── resources/          # Configuration files
├── testData/                   # Excel files for data-driven tests
├── reports/                    # ExtentReports HTML reports
├── logs/                       # Log4j2 log files
├── pom.xml                     # Maven dependencies
└── README.md


This project is for educational purposes.
