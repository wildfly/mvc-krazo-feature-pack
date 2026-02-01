# Contributing Guide

Want to contribute to the WildFly MVC Krazo Feature Pack? We try to make it easy, and all contributions, even the smaller ones,
are more than welcome. This includes bug reports, fixes, documentation, etc. First though, please read this page.

## Legal

All contributions to this repository are licensed under the [Apache License](https://www.apache.org/licenses/LICENSE-2.0), version 2.0 or later, or, if another license is specified as governing the file or directory being modified, such other license.

All contributions are subject to the [Developer Certificate of Origin (DCO)](https://developercertificate.org/).
The DCO text is also included verbatim in the [dco.txt](dco.txt) file in the root directory of the repository.

### Compliance with Laws and Regulations

All contributions must comply with applicable laws and regulations, including U.S. export control and sanctions restrictions.
For background, see the Linux Foundation’s guidance:
[Navigating Global Regulations and Open Source: US OFAC Sanctions](https://www.linuxfoundation.org/blog/navigating-global-regulations-and-open-source-us-ofac-sanctions).

## Reporting an issue

This project uses https://github.com/wildfly-extras/mvc-krazo-feature-pack/issues[GitHub issues] for filing issues.

If you believe you found a bug, and it's likely possible, please indicate a way to reproduce it, i.e. what you are seeing and what you would expect to see.

## Before you contribute

To contribute, use GitHub Pull Requests, from your **own** fork.

Also, make sure you have set up your Git authorship correctly:

```bash
git config --global user.name "Your Full Name"
git config --global user.email your.email@example.com
```

If you use different computers to contribute, please make sure the name is the same on all your computers.

We use this information to acknowledge your contributions in release announcements.

### Setup

If you have not done so on this machine, you need to:

* Install Git and configure your GitHub access
* Install Java SDK 17+ (OpenJDK recommended)First `cd` to the directory where you cloned the project (eg: `cd wildfly-transaction-client`)

First `cd` to the directory where you cloned the project (eg: `cd mvc-krazo-feature-pack`)

Add a remote ref to upstream, for pulling future updates.
For example:

```
git remote add upstream https://github.com/wildfly/mvc-krazo-feature-pack
```

### Building and Testing

To build `mvc-krazo-feature-pack` run:
```bash
mvn clean install
```

To skip the tests, use:

```bash
mvn clean install -DskipTests=true
```

To run only a specific test, use:

```bash
mvn clean install -Dtest=TestClassName
```

## Contributing Guidelines

When submitting a PR, please keep the following guidelines in mind:

1. In general, it's good practice to squash all of your commits into a single commit. For larger changes, it's ok to have multiple meaningful commits. If you need help with squashing your commits, feel free to ask us how to do this on your pull request. We're more than happy to help.

  1. Please include the GitHub issue you worked on in the title of your pull request and in your commit message. For example, for [Issue 126](https://github.com/wildfly/mvc-krazo-feature-pack/issues/126), the PR title and commit message should be `[Issue_126] Allow separate configuration of the MVC TCK version`

2. Please include the `Resolves` link to the GitHub issue you worked on in the description of the pull request. For example, if your PR adds a fix for [WFTC-87](https://issues.redhat.com/browse/WFTC-87), the PR description should include the statement `Resolves #126`. GitHub will convert the `#126` to a link to the issue and will automatically close the issue when the PR is merged.