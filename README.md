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

---
## 📚 Overview

Welcome to the world of simplicity and efficiency in [LibGdx](https://libgdx.com/) development!
This library is designed to streamline your journey in creating LibGdx projects.
By providing a foundational implementation of key LibGdx features, we free you from the complexities of project configuration.
This way, you can focus your efforts on crafting content specific to your simulation, without getting bogged down in technical details.

In essence, this library acts as a bridge, abstracting and implementing essential features.
Its role is to empower you to concentrate on building simulation-specific content, rather than grappling with the intricacies of project setup.

But that's not all. This library is the bedrock upon which other OIS products are built:
* [OIS deployer](https://github.com/attiasas/open-interactive-simulation-deployer) - A Gradle plugin designed for the development, execution, and deployment of OIS simulation projects.
* [OIS runners](https://github.com/attiasas/open-interactive-simulation-runner) - Fundamental projects used by the OIS deployer to simplify and abstract the process of running and deploying OIS projects across multiple platforms.

Join us on this journey to simplify your development process and create amazing simulations with ease.

---
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
        implementation group: 'org.attias.open.interactive.simulation', name: 'open-interactive-simulation-core', version: '0.1'
    }
   ```

---
## 🏗️ Usage

Discover the steps for implementing an OIS project:
1. Development environment using the [OIS deployer](https://github.com/attiasas/open-interactive-simulation-deployer). 
2. Project implementation by exploring our comprehensive [user guide](USER_GUIDE.md).

---
## 🐞 Reporting Issues

We highly recommend running Gradle with the ```-d```
option to get useful and readable debug information if something goes wrong with your build.

Please help us improve the library
by [reporting any issues](https://github.com/attiasas/open-interactive-simulation-core/issues/new/choose) you encounter.

---
## 🤝 Contributions

We welcome pull requests from the community. To help us improve this project, please read
our [Contribution](CONTRIBUTING.md) guide.
