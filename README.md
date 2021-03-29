# GumTreeDiff API use examples

## This repository

This small repository contains examples on how to use a code differencing tool [GumTreeDiff](https://github.com/GumTreeDiff/gumtree) (`GTD`) from Java source code through APIs.

Folder `gumtreediff-api-examples-maven` contains a sample `maven` project that runs [API use examples](https://github.com/GumTreeDiff/gumtree/wiki/GumTree-API).

## Prerequisites

### Java

SDK version 11+ (these projects are developed with `java-11-openjdk-amd64`).

### Maven build setup

These projects are built with `maven` version 3.6.3.

Before building and running any of the projects for the first time please configure the `maven` workflow for `GTD`:

**Step 0**

If you do not yet have a [GitHub](https://github.com/) account, register there.

**Step 1**

Go to your account and generate a _personal access token_ (if you already have it, make sure it has all necessary permissions - see below):

  1. Folow the instructions [here](https://docs.github.com/en/github/authenticating-to-github/creating-a-personal-access-token)

  2. In step 6 of the above instructions set the token name to `github-GTD` or any other of your choice

  3. In step 7 of the above instructions set at least the following permissions: `repo`, `read:packages`

**Step 2**

Configure `maven` to authenticate GithubPackages as a valid package source:

  1. Full instructions are available [here](https://docs.github.com/en/packages/guides/configuring-apache-maven-for-use-with-github-packages#authenticating-with-a-personal-access-token), section _Authenticating to GitHub Packages/Authenticating with a personal access token_, but the relevant steps are:

  2. Create or edit the `~/.m2/settings.xml` from the template provided in the above instructions

  3. Update `repository` and `server` sections of the `settings.xml` to the following values:

```xml
<repository>
  <id>github</id>
  <name>GitHub GumTreeDiff Apache Maven Packages</name>
  <url>https://maven.pkg.github.com/gumtreediff/gumtree</url>
  <releases><enabled>true</enabled></releases>
  <snapshots><enabled>true</enabled></snapshots>
</repository>
```

```xml
<server>
  <id>github</id>
  <username>HERE_YOUR_GITHUB_USERNAME</username>
  <password>HERE_YOUR_GITHUB_PERSONAL_ACCESS_TOKEN</password>
</server>
```

### IDE

The projects are developed in IntelliJ IDEA 2020.3.3 (Community Edition), so there are various build and run configurations available for each project in respective `gumtreediff-examples/PROJECTNAME/.idea/runConfigurations/` folders.
