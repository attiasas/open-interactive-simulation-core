# 📖 Guidelines

- If the existing tests do not already cover your changes, please add tests.

---

# ⚒️ Building the library

To build the library sources, please follow these steps:

1. Clone the code from Git.
2. Build the plugin by running the following Gradle command:
    ```
    gradlew clean build
    ```

To build the library sources and publish it locally, run the following Gradle command:
```
gradlew publishToMavenLocal
```
After running the command you will be able to use the library from `mavenLocal()` in a test project.

---

# 🧪 Testing the plugin

To test the library sources, please follow these steps:
```
gradlew clean check
```