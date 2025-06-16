# Jakarta MVC TCK Runner for WildFly

This module allows maven-driven execution of the Jakarta MVC TCK against WildFly installations provisioned from a Galleon feature pack that provides Jakarta MVC support.

A maven build of those module results in two WildFly installations being provisioned in the `target` directory:

* **wildfly-layers** -- A slimmed WildFly installation that includes the `core-server` and `mvc-krazo` Galleon layers.
* **wildfly-complete** -- A complete (not slimmed) installation.

The build includes a `wildfly-maven-plugin` execution that uses the embedded CLI to add the `mvc-krazo` extension/subsystem to the **wildfly-complete** installation's `standalone.xml`.

The build then runs two `surefire` executions, running the TCK against each of the two WildFly installations.

## TCK Options

The version of the TCK to use can be controlled by providing the `jakarta.mvc.tck` system property. By default, this property is set to the value of the `jakarta.mvc` maven property, which is used when building this repository to control the version of the Jakarta MVC API artifact.

The use case for setting this would be when running the TCK against a feature pack other than the one produced by the overall build of this repository (see below for more on that).

## Feature Pack Options

System properties can be used to control the feature pack(s) used for provisioning, thus allowing this TCK runner to test feature packs other than the one produced from the overall build of this repository.

### Specifying the feature pack that provides `mvc-krazo`

Use these system properties to specify the Maven GAV of the feature pack that install the `mvc-krazo` subsystem:

* **krazo.feature.pack.artifactId** -- The maven artifactId of the feature pack. Defaults to `wildfly-mvc-krazo-feature-pack`. Setting this to `wildfly-preview-feature-pack` would allow testing of the `wildfly-preview` feature pack that incorporates Jakarta MVC since the WildFly 31 release.
* **krazo.feature.pack.version** -- The maven artifactId of the feature pack. Defaults to the maven `project.version` property.
* **krazo.feature.pack.groupId** -- The maven artifactId of the feature pack. Defaults to the maven `project.groupId` property, i.e. `org.wildfly`.

### Specifying the 'base' feature pack

The `wildfly-mvc-krazo-feature-pack` produced by building this repository depends on standard WildFly's `wildfly-ee` feature pack, using the version specified by the `version.org.wildfly` property set in this repo's root pom. However, users may wish to test the `wildfly-mvc-krazo-feature-pack` in combination with some other base feature pack, e.g. a different version of `wildfly-ee`, or the `wildfly` feature pack that's based on `wildfly-ee`.

This can be done by setting the `base.feature.pack.version` system property, which activates a profile that instead configures test installation provisioning to use a different base feature pack, i.e. this bit of `wildfly-maven-plugin` configuration


    <configuration>
        ....
        <feature-packs>
            <feature-pack>
                <location>${base.feature.pack.groupId}:${base.feature.pack.artifactId}:${base.feature.pack.version}</location>
                <inherit-packages>false</inherit-packages>
            </feature-pack>
            <feature-pack>
                <location>${krazo.feature.pack.groupId}:${krazo.feature.pack.artifactId}:${krazo.feature.pack.version}</location>
            </feature-pack>
        </feature-packs>
    </configuration>

If this profile is activated, the following system properties can be set to control the base feature pack:

* **base.feature.pack.version** -- The maven artifactId of the base feature pack.
* **base.feature.pack.artifactId** -- The maven artifactId of the base feature pack. Defaults to `wildfly-ee-galleon-pack`.
* **base.feature.pack.groupId** -- The maven artifactId of the base feature pack. Defaults to the maven `project.groupId` property, i.e. `org.wildfly`.

Note: When activating this maven profile by setting the `base.feature.pack.version` system property, it is also possible to change the feature pack that provides `mvc-krazo` using the various `krazo.feature.pack.xxx` properties. The use case for doing that isn't obvious, but if there is one it can be done.