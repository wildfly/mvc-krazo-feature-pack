/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.wildfly.extension.mvc.krazo;

import static org.wildfly.extension.mvc.krazo.MVCKrazoExtension.SUBSYSTEM_NAME;
import static org.wildfly.extension.mvc.krazo.MVCKrazoExtension.SUBSYSTEM_PATH;

import org.jboss.as.controller.ResourceDefinition;
import org.jboss.as.controller.ResourceRegistration;
import org.jboss.as.controller.SubsystemRegistration;
import org.jboss.as.controller.descriptions.ParentResourceDescriptionResolver;
import org.jboss.as.controller.descriptions.SubsystemResourceDescriptionResolver;
import org.jboss.as.controller.registry.ManagementResourceRegistration;
import org.jboss.as.controller.registry.RuntimePackageDependency;
import org.jboss.as.server.DeploymentProcessorTarget;
import org.wildfly.subsystem.resource.ManagementResourceRegistrar;
import org.wildfly.subsystem.resource.ManagementResourceRegistrationContext;
import org.wildfly.subsystem.resource.ResourceDescriptor;
import org.wildfly.subsystem.resource.SubsystemResourceDefinitionRegistrar;

/**
 * Resource definition for the mvc-krazo subsystem root resource.
 *
 * @author <a href="mailto:brian.stansberry@redhat.com">Brian Stansberry</a>
 */
final class MVCKrazoSubsystemRegistrar implements SubsystemResourceDefinitionRegistrar {

    static final ParentResourceDescriptionResolver RESOLVER = new SubsystemResourceDescriptionResolver(SUBSYSTEM_NAME, MVCKrazoSubsystemRegistrar.class);
    static final ResourceRegistration REGISTRATION = ResourceRegistration.of(SUBSYSTEM_PATH);

    static final String MVC_API = "jakarta.mvc.api";
    static final String KRAZO_CORE = "org.eclipse.krazo.core";
    static final String KRAZO_RESTEASY = "org.eclipse.krazo.resteasy";

    @Override
    public ManagementResourceRegistration register(SubsystemRegistration parent, ManagementResourceRegistrationContext managementResourceRegistrationContext) {
        ResourceDefinition definition = ResourceDefinition.builder(REGISTRATION, RESOLVER).build();
        ManagementResourceRegistration registration = parent.registerSubsystemModel(definition);
        ResourceDescriptor descriptor = ResourceDescriptor.builder(RESOLVER)
                .withDeploymentChainContributor(MVCKrazoSubsystemRegistrar::registerDeploymentUnitProcessors)
                .build();
        ManagementResourceRegistrar.of(descriptor).register(registration);
        registration.registerAdditionalRuntimePackages(
                RuntimePackageDependency.required(MVC_API),
                RuntimePackageDependency.required(KRAZO_CORE),
                RuntimePackageDependency.required(KRAZO_RESTEASY)
        );
        return registration;
    }

    private static void registerDeploymentUnitProcessors(DeploymentProcessorTarget processorTarget) {
        processorTarget.addDeploymentProcessor(MVCKrazoExtension.SUBSYSTEM_NAME,
                DeploymentDependenciesProcessor.PHASE,
                DeploymentDependenciesProcessor.PRIORITY,
                new DeploymentDependenciesProcessor());
    }

}
