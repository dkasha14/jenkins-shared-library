# Jenkins Shared Library – Application Onboarding Pipeline

## Overview

This project implements a **Jenkins Shared Library–based CI pipeline** designed to support **automatic onboarding and build automation for multiple application types**.

The pipeline detects the application type automatically and executes the appropriate build tool.

---

## Supported Application Types

| Application | Detection File | Build Tool |
|-------------|---------------|-----------|
| Python | requirements.txt | pip |
| Java (Spring Boot / Maven) | pom.xml | Maven |
| Node.js | package.json | npm |

The goal is to provide a **reusable CI pipeline template that reduces duplication and standardizes application onboarding**.

---

# Architecture

```
Developer
   │
   │ Push code
   ▼
GitHub Repository
   │
   │ Jenkins Webhook / Manual Trigger
   ▼
Jenkins Server
   │
   │ Load Shared Library
   ▼
Shared Pipeline Logic
   │
   │ Clone Application Repository
   ▼
Application Source Code
   │
   │ Detect application type
   ▼
Build Stage
   │
   │ Execute language specific build tool
   ▼
Build Artifact
   │
   ▼
Test Stage
   │
   ▼
Deploy Stage
```

---

# Repository Structure

```
jenkins-shared-library
│
├── vars
│   │
│   ├── pipelineBuilder.groovy
│   │       Manual pipeline requiring application type
│   │
│   └── autoPipelineBuilder.groovy
│           Automatic application detection pipeline
│
└── README.md
```

---

# Shared Library Pipelines

## 1. Manual Pipeline

**File**

```
vars/pipelineBuilder.groovy
```

### Usage

```
@Library('jenkins-shared-library') _
pipelineBuilder("python")
```

### Supported Parameters

| Parameter | Description |
|----------|-------------|
| python | Runs pip dependency installation |
| java | Runs Maven build |
| node | Runs npm install |

---

# 2. Automatic Detection Pipeline

**File**

```
vars/autoPipelineBuilder.groovy
```

### Usage

```
@Library('jenkins-shared-library') _
autoPipelineBuilder()
```

The pipeline detects the application type automatically.

### Detection Logic

```
requirements.txt  → Python
pom.xml           → Java
package.json      → NodeJS
```

---

# Pipeline Stages

The pipeline contains the following stages.

---

## 1. Checkout

Clones the application repository.

Example:

```
git clone https://github.com/dkasha14/JavaSpringBoot.git
```

---

## 2. Application Detection

The pipeline checks for specific build files.

Example logic:

```
if requirements.txt exists → Python
if pom.xml exists → Java
if package.json exists → Node
```

Example output:

```
Detected application type: java
```

---

## 3. Build Stage

The correct build tool is executed depending on the detected language.

### Python

```
pip3 install -r requirements.txt
```

### Java

```
mvn clean package
```

### NodeJS

```
npm install
```

---

## 4. Test Stage

Currently prints a placeholder message.

```
Running tests...
```

In production this stage may run:

| Language | Test Tool |
|---------|-----------|
| Python | pytest |
| Java | mvn test |
| Node | npm test |

---

## 5. Deploy Stage

Currently prints a placeholder message.

```
Deploy stage...
```

Possible real deployment targets:

- Docker
- Kubernetes
- AWS ECS
- Helm
- Terraform infrastructure

---

# Data Flow

The CI pipeline follows this data flow.

```
Application Repository
       │
       ▼
Jenkins Pipeline Trigger
       │
       ▼
Shared Library Loaded
       │
       ▼
Repository Checkout
       │
       ▼
Application Type Detection
       │
       ▼
Language Specific Build Tool
       │
       ▼
Build Artifact Generation
       │
       ▼
Test Stage
       │
       ▼
Deployment Stage
```

---

# Example Execution – Java Spring Boot

Example repository:

```
https://github.com/dkasha14/JavaSpringBoot
```

Detected file:

```
pom.xml
```

Build command executed:

```
mvn clean package
```

Generated artifact:

```
target/application.jar
```

Pipeline result:

```
BUILD SUCCESS
```

---

# Jenkins Environment Requirements

The Jenkins agent must contain the following tools.

| Tool | Version |
|-----|--------|
| Java | 17 |
| Maven | 3.x |
| Python | 3.x |
| pip | latest |
| NodeJS | 18+ |
| npm | latest |

Example installation inside Jenkins container:

```
apt update
apt install maven python3 python3-pip nodejs npm -y
```

---

# Running the Pipeline

## Step 1 – Configure Jenkins Shared Library

Navigate to:

```
Manage Jenkins
→ Configure System
→ Global Pipeline Libraries
```

Add the following configuration:

```
Name: jenkins-shared-library
Default version: master
Source: Git
Repository: https://github.com/dkasha14/jenkins-shared-library.git
```

---

## Step 2 – Create Pipeline Job

Create a **Pipeline Job**.

Example pipeline script:

```
@Library('jenkins-shared-library') _
autoPipelineBuilder()
```

---

## Step 3 – Run Build

Trigger the pipeline using:

```
Build Now
```

---

# Example Pipeline Output

Pipeline stages:

```
Checkout
Detect Application Type
Build
Test
Deploy
```

Example log output:

```
Detected application type: java
mvn clean package
BUILD SUCCESS
```

---

# Benefits of This Architecture

- Centralized CI pipeline logic
- Reduced duplication across projects
- Automatic application detection
- Simplified application onboarding
- Language-agnostic CI workflow

This design follows **DevOps platform engineering practices commonly used in enterprise environments**.

---

# Future Improvements

Potential enhancements include:

- Docker image build
- Artifact upload to registry
- Kubernetes deployment
- Helm chart integration
- Automated testing stage
- Security scanning (SAST / dependency scanning)