<div align="center">

# open-interactive-simulation-core

[![Test](https://github.com/attiasas/open-interactive-simulation-core/actions/workflows/test.yml/badge.svg)](https://github.com/attiasas/open-interactive-simulation-core/actions/workflows/test.yml?branch=master)

</div>

---

## Table of Contents
- [📚 Overview](#-overview)
- [📦 Installation](#-installation)
- [🏗️ Usage](#-usage)
- [🐞 Reporting Issues](#-reporting-issues)
- [🤝 Contributions](#-contributions)

## 📚 Overview

This library allows you to develop LibGdx projects, it offers basic implementation of some of LibGdx main features, allows you to focus on developing content to your specific simulation rather than how to configure your project.
It abstracts away and implements the basic features and allows you to focus in developing simulation specific content.

This library defines the core functions and behavior of the other OIS products:
* [OIS deployer]() - Gradle plugin that can be used to develop, run and deploy OIS simulation projects.
* [OIS runners]() - The basic projects used by the OIS deployer to simplify and abstract running and deploying OIS projects cross-platforms.

## 📦 Installation

This library is not available in Maven central yet, required to be installed locally

1. Clone the [core library](https://github.com/attiasas/open-interactive-simulation-core)
    ```bash
     git clone https://github.com/attiasas/open-interactive-simulation-core.git
   ```
2. Navigate to the cloned directory and publish the library to maven local
   ```bash
    ./gradlew publishToMavenLocal
   ```
3. Add this library as dependency to your project `build.gradle`
    ```groovy
    dependencies {
        implementation group: 'org.attias.open.interactive.simulation', name: 'open-interactive-simulation-core', version: '1.0-SNAPSHOT'
    }
   ```

## 🏗️ Usage

Check out how to use the library to implement a OIS project in our [user guide](USER_GUIDE.md).

## 🐞 Reporting Issues

We highly recommend running Gradle with the ```-d```
option to get useful and readable debug information if something goes wrong with your build.

Please help us improve the library
by [reporting any issues](https://github.com/attiasas/open-interactive-simulation-core/issues/new/choose) you encounter.

## 🤝 Contributions

We welcome pull requests from the community. To help us improve this project, please read
our [Contribution](./CONTRIBUTING.md#-guidelines) guide.
