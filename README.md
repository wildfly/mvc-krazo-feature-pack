# WildFly mvc-krazo Feature Pack
WildFly subsystem and Galleon feature pack for integrating the [Eclipse Krazo](https://projects.eclipse.org/projects/ee4j.krazo) implementation of [Jakarta MVC](https://jakarta.ee/specifications/mvc/) into a WildFly installation.

## Project Structure

This project provides the following modules:

* **subsystem** -- A WildFly `Extension` implementation that provides an `mvc-krazo` subsystem for integrating Jakarta MVC and Eclipse Krazo into a WildFly deployment. This module's artifact can be used with the feature pack produced from this repository, or it can be incorporated in other feature packs (e.g. WildFly's `wildfly-preview` feature pack).
* **galleon-shared** -- Provides source material for inclusion in a Galleon feature pack. This includes the definition of an `mvc-krazo` Galleon layer. This module's content can be used with the feature pack produced from this repository, or it can be incorporated in other feature packs (e.g. WildFly's `wildfly-preview` feature pack, where it has been incorporated since the WildFly 31 release).
* **galleon-local** -- Provides source material for inclusion in a Galleon feature pack. This module's content is only meant to be used with the feature pack produced from this repository.
* **wildfly-feature-pack** -- Produces a feature pack that can be used to integrate Jakarta MVC into standard WildFly.
* **testsuite/tck** -- A TCK runner for running the Jakarta MVC TCK against a WildFly installation that includes the subsystem.

The **galleon-local** and **wildfly-feature-pack** modules may be removed in the future if this project's functionality is incorporated in standard WildFly directly.

## The `mvc-krazo` Layer

The expected usage of this project's feature pack or of other feature packs like `wildfly-preview` that incorporate this project is that the `mvc-krazo` Galleon layer will be used when provisioning a WildFly server.

The layer adds the extension and a simple subsystem configuration to the WildFly installation's configuration.

Provisioning a complete server (i.e. one that is not slimmed using layers) using this feature pack will install the JBoss Modules modules needed for the extension, but will not actually add them to any configuration file; i.e. extension/subsystem is not made part of any OOTB config. Adding the extension/subsystem to a config is up to the user.
