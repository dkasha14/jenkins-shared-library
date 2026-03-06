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

The goal is to provide a **reusable CI pipeline template** that reduces duplication and standardizes application onboarding.

---

# Architecture

```
Developer
   │
   ▼
Push Code
   │
   ▼
GitHub Repository
   │
   ▼
Jenkins Trigger
(Webhook / Manual)
   │
   ▼
Jenkins Server
   │
   ▼
Load Shared Library
   │
   ▼
Shared Pipeline Logic
   │
   ▼
Clone Application Repository
   │
   ▼
Application Source Code
   │
   ▼
Detect Application Type
   │
   ▼
Build Stage
   │
   ▼
Execute Language Specific Build Tool
   │
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
│   ├── pipelineBuilder.groovy
│   └── autoPipelineBuilder.groovy
│
└── README.md
```

### Shared Library Pipelines

| Pipeline | Description |
|--------|-------------|
| pipelineBuilder.groovy | Manual pipeline requiring application type |
| autoPipelineBuilder.groovy | Automatic application detection pipeline |

---

# File

```
vars/pipelineBuilder.groovy
```

---

# Deploy Stage

Example deploy stage placeholder:

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

The CI pipeline follows this data flow:

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

---

# Result

The pipeline successfully detects the **Java Spring Boot application** and runs the **Maven build process automatically**.

This demonstrates how the **shared library enables standardized CI pipelines for different technology stacks**.