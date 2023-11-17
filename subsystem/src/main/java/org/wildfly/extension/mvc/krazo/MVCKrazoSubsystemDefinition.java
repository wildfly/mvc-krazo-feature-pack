/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.wildfly.extension.mvc.krazo;

import java.util.Arrays;
import java.util.Collection;

import org.jboss.as.controller.AbstractBoottimeAddStepHandler;
import org.jboss.as.controller.AttributeDefinition;
import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.PersistentResourceDefinition;
import org.jboss.as.controller.ReloadRequiredRemoveStepHandler;
import org.jboss.as.controller.registry.ManagementResourceRegistration;
import org.jboss.as.controller.registry.Resource;
import org.jboss.as.controller.registry.RuntimePackageDependency;
import org.jboss.as.server.AbstractDeploymentChainStep;
import org.jboss.as.server.DeploymentProcessorTarget;
import org.jboss.dmr.ModelNode;

/**
 * Resource definition for the mvc-krazo subsystem root resource.
 *
 * @author <a href="mailto:brian.stansberry@redhat.com">Brian Stansberry</a>
 */
final class MVCKrazoSubsystemDefinition extends PersistentResourceDefinition {

    static final String MVC_API = "jakarta.mvc.api";
    static final String KRAZO_CORE = "org.eclipse.krazo.core";
    static final String KRAZO_RESTEASY = "org.eclipse.krazo.resteasy";

    static final AttributeDefinition[] ATTRIBUTES = { /* none */ };

    static final MVCKrazoSubsystemDefinition INSTANCE = new MVCKrazoSubsystemDefinition();

    private MVCKrazoSubsystemDefinition() {
        super(MVCKrazoExtension.SUBSYSTEM_PATH,
                MVCKrazoExtension.getResourceDescriptionResolver(),
                new SubsystemAdd(),
                ReloadRequiredRemoveStepHandler.INSTANCE);
    }

    @Override
    public Collection<AttributeDefinition> getAttributes() {
        return Arrays.asList(ATTRIBUTES);
    }

    @Override
    public void registerAdditionalRuntimePackages(ManagementResourceRegistration resourceRegistration) {
        resourceRegistration.registerAdditionalRuntimePackages(
                RuntimePackageDependency.required(MVC_API),
                RuntimePackageDependency.required(KRAZO_CORE),
                RuntimePackageDependency.required(KRAZO_RESTEASY)
        );
    }

    /**
     * Handler responsible for adding the subsystem resource to the model
     */
    private static class SubsystemAdd extends AbstractBoottimeAddStepHandler {

        @Override
        public void performBoottime(OperationContext context, ModelNode operation, Resource resource) {

            context.addStep(new AbstractDeploymentChainStep() {
                public void execute(DeploymentProcessorTarget processorTarget) {
                    processorTarget.addDeploymentProcessor(MVCKrazoExtension.SUBSYSTEM_NAME,
                            DeploymentDependenciesProcessor.PHASE,
                            DeploymentDependenciesProcessor.PRIORITY,
                            new DeploymentDependenciesProcessor());
                }
            }, OperationContext.Stage.RUNTIME);

        }
    }

}
