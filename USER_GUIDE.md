# OIS library user guide
[Back to main page](README.md)

---

## Table of Contents
- [📚 Overview](#-overview)
- [📄 Wiki](#-wiki)
    - [States](#states)

---

## 📚 Overview

Welcome to the OIS Library User Guide! This page is your gateway to understanding how to implement projects using the OIS library. Whether you're a beginner or an experienced developer, this guide will provide you with a smooth introduction to creating your projects with the OIS library.

Here, we'll walk you through the key classes and methods you need to know. By the end, you'll have a solid understanding of how to utilize this library effectively. Our guide is packed with detailed examples to help you along the way. Let's dive in and get started!

---

## 📄 Wiki

User guide documentation for the library's content.

### States

Every simulation is composed of a set of `IState` instances, each identified by an ID. These states dictate screen rendering and updates.
Every OIS project must implement at least one state. You have two options:

* Extend the `SimpleSimulation` class, which incorporates fundamental features and configurations from this library, enabling you to concentrate on content.
* Implement the `IState` interface to define all simulation configurations, behavior, and content yourself.

The `StateManager` engine oversees the active state in the simulation. You can utilize it to establish relationships between your simulation states:
1. Switch between states, clearing the active state stack.
2. Append a new sub-state to the active stack.

```java
public class MenuState implements IState {
    public void startSimulation() {
        // exit() all active states in the stack and enter() new state
        OIS.engine.states.changeState("simulationState");  
    }  
  
    public void openSettings() {
        // pause() the current state and enter() a new state.
        // When the new state exits(), it will trigger this state's resume()
        OIS.engine.states.pushSubState("settingsMenuState");
    }         
}
```