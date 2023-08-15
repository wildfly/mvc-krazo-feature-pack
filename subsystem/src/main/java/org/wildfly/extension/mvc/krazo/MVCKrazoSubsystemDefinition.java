/*
   Copyright The WildFly Authors
   SPDX short identifier: Apache-2.0
 */
package org.wildfly.extension.mvc.krazo;

import java.util.Arrays;
import java.util.Collection;

import org.jboss.as.controller.AbstractBoottimeAddStepHandler;
import org.jboss.as.controller.AttributeDefinition;
import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.PersistentResourceDefinition;
import org.jboss.as.controller.ReloadRequiredRemoveStepHandler;
import org.jboss.as.controller.registry.Resource;
import org.jboss.as.ee.structure.DeploymentType;
import org.jboss.as.ee.structure.DeploymentTypeMarker;
import org.jboss.as.server.AbstractDeploymentChainStep;
import org.jboss.as.server.DeploymentProcessorTarget;
import org.jboss.as.server.deployment.Attachments;
import org.jboss.as.server.deployment.DeploymentPhaseContext;
import org.jboss.as.server.deployment.DeploymentUnit;
import org.jboss.as.server.deployment.DeploymentUnitProcessor;
import org.jboss.as.server.deployment.Phase;
import org.jboss.as.server.deployment.module.ModuleDependency;
import org.jboss.as.server.deployment.module.ModuleSpecification;
import org.jboss.dmr.ModelNode;
import org.jboss.modules.Module;
import org.jboss.modules.ModuleLoader;

/**
 * Resource definition for the mvc-krazo subsystem root resource.
 *
 * @author <a href="mailto:brian.stansberry@redhat.com">Brian Stansberry</a>
 */
final class MVCKrazoSubsystemDefinition extends PersistentResourceDefinition {

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

    /**
     * Handler responsible for adding the subsystem resource to the model
     */
    private static class SubsystemAdd extends AbstractBoottimeAddStepHandler {

        private SubsystemAdd() {
            super(ATTRIBUTES);
        }

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

    /**
     * Deployment unit processor that adds the MVC and Krazo dependencies to the deployment module.
     */
    private static class DeploymentDependenciesProcessor implements DeploymentUnitProcessor {

        private static final String MVC_API = "jakarta.mvc.api";

        private static final String KRAZO_CORE = "org.eclipse.krazo.core";
        private static final String KRAZO_RESTEASY = "org.eclipse.krazo.resteasy";

        /**
         * See {@link Phase} for a description of the different phases
         */
        public static final Phase PHASE = Phase.DEPENDENCIES;

        /**
         * The relative order of this processor within the {@link #PHASE}.
         * The current number is large enough for it to happen after all
         * the standard deployment unit processors that come with WildFly.
         */
        public static final int PRIORITY = 0x4000;

        @Override
        public void deploy(DeploymentPhaseContext phaseContext) {
            final DeploymentUnit deploymentUnit = phaseContext.getDeploymentUnit();

            if (!DeploymentTypeMarker.isType(DeploymentType.WAR, deploymentUnit)) {
                return; // Skip non web deployments
            }

            final ModuleSpecification moduleSpecification = deploymentUnit.getAttachment(Attachments.MODULE_SPECIFICATION);
            final ModuleLoader moduleLoader = Module.getBootModuleLoader();

            moduleSpecification.addSystemDependency(new ModuleDependency(moduleLoader, MVC_API, false, false, true, false));
            moduleSpecification.addSystemDependency(new ModuleDependency(moduleLoader, KRAZO_CORE, false, false, true, false));
            moduleSpecification.addSystemDependency(new ModuleDependency(moduleLoader, KRAZO_RESTEASY, false, false, true, false));

        }

    }
}
