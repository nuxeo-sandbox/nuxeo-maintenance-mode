[![Nuxeo Version](https://img.shields.io/badge/nuxeo-9.10-blue.svg)](https://www.nuxeo.com) [![Build Status](https://qa.nuxeo.org/jenkins/buildStatus/icon?job=Sandbox/sandbox_nuxeo-maintenance-mode)](https://qa.nuxeo.org/jenkins/job/Sandbox/job/sandbox_nuxeo-maintenance-mode)

# About

Maintenance mode adds an additional status probe to the `http://${NUXEO_HOST}/nuxeo/runningstatus`  document.  When maintenance mode is enabled, the `runningstatus` document will return a 500 HTTP response status code with a failed status for the "maintenanceStatus" probe.  The change in status will allow load balancers and other smart web application firewalls not to direct traffic to those servers in maintenance.

Two Operations are exposed to retrieve or set the current system maintenance status:
* `System.MaintenanceStatus`: Retrieve the current maintenance state and message
* `System.MaintenanceMode`: Set the current maintenance state (enabled=true|false) and message

When entering maintenance mode, the `maintenanceStatusEnabled` event will be fired.  When leaving, the `maintenanceStatusDisabled` event will be fired.

# Requirements

Build requires the following software:
- git
- maven

# Build

```
mvn clean install
```

# Deploy

Install the marketplace package on your Nuxeo instance.

# Support

**These features are not part of the Nuxeo Production platform.**

These solutions are provided for inspiration and we encourage customers to use them as code samples and learning resources.

This is a moving project (no API maintenance, no deprecation process, etc.) If any of these solutions are found to be useful for the Nuxeo Platform in general, they will be integrated directly into platform, not maintained here.

# Licensing

[Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0)

# About Nuxeo

Nuxeo dramatically improves how content-based applications are built, managed and deployed, making customers more agile, innovative and successful. Nuxeo provides a next generation, enterprise ready platform for building traditional and cutting-edge content oriented applications. Combining a powerful application development environment with SaaS-based tools and a modular architecture, the Nuxeo Platform and Products provide clear business value to some of the most recognizable brands including Verizon, Electronic Arts, Netflix, Sharp, FICO, the U.S. Navy, and Boeing. Nuxeo is headquartered in New York and Paris.

More information is available at [www.nuxeo.com](http://www.nuxeo.com).
