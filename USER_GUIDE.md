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

User guide documentation to the content of the library.

### States

Every simulation runs a set of `IState` defined by an ID. the state defines what to render in the screen and what to update.
Each project OIS project must implement at least one state, you can choose:

* Extend the class `SimpleSimulation` that implements basic features and configurations from this library and allows you to focus on the content.
* Implement the interface `IState`to define all the simulation configurations and behavior yourself in addition to the content.

The engine `StateManager` manage the active state in the simulation, you can use it to set your simulation states relations:
1. Changing states and empty all active states.
2. Add a new sub state to the active stack.

```java
public class MenuState implements IState {
    public void startSimulation() {
        // exit() all active states in the stack and enter() new state
        OIS.engine.states.changeState("simulationState");  
    }  
  
    public void openSettings() {
        // pause() current state and enter() new state.
        // When new state will exit() it will call this state resume()
        OIS.engine.states.pushSubState("settingsMenuState");
    }         
}
```