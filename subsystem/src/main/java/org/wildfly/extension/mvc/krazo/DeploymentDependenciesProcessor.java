/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.wildfly.extension.mvc.krazo;

import java.util.Set;

import org.jboss.as.ee.structure.DeploymentType;
import org.jboss.as.ee.structure.DeploymentTypeMarker;
import org.jboss.as.server.deployment.Attachments;
import org.jboss.as.server.deployment.DeploymentPhaseContext;
import org.jboss.as.server.deployment.DeploymentUnit;
import org.jboss.as.server.deployment.DeploymentUnitProcessor;
import org.jboss.as.server.deployment.Phase;
import org.jboss.as.server.deployment.module.ModuleDependency;
import org.jboss.as.server.deployment.module.ModuleSpecification;
import org.jboss.modules.Module;
import org.jboss.modules.ModuleLoader;

/**
 * Deployment unit processor that adds the MVC and Krazo dependencies to the deployment module.
 */
final class DeploymentDependenciesProcessor implements DeploymentUnitProcessor {

    /**
     * See {@link Phase} for a description of the different phases
     */
    static final Phase PHASE = Phase.DEPENDENCIES;

    /**
     * The relative order of this processor within the {@link #PHASE}.
     * The current number is large enough for it to happen after all
     * the standard deployment unit processors that come with WildFly.
     */
    static final int PRIORITY = Phase.DEPENDENCIES_JAXRS + 1;

    @Override
    public void deploy(DeploymentPhaseContext phaseContext) {
        final DeploymentUnit deploymentUnit = phaseContext.getDeploymentUnit();

        // Skip deployments other than ears and wars
        if (!DeploymentTypeMarker.isType(DeploymentType.WAR, deploymentUnit)
                && !DeploymentTypeMarker.isType(DeploymentType.EAR, deploymentUnit)) {
            return;
        }

        final ModuleSpecification moduleSpecification = deploymentUnit.getAttachment(Attachments.MODULE_SPECIFICATION);
        final ModuleLoader moduleLoader = Module.getBootModuleLoader();
        // Use addSystemDependencies instead of multiple calls to addSystemDependency to avoid WFCORE-6601
        moduleSpecification.addSystemDependencies(Set.of(
                new ModuleDependency(moduleLoader, MVCKrazoSubsystemRegistrar.MVC_API, false, true, true, false),
                new ModuleDependency(moduleLoader, MVCKrazoSubsystemRegistrar.KRAZO_CORE, false, true, true, false),
                new ModuleDependency(moduleLoader, MVCKrazoSubsystemRegistrar.KRAZO_RESTEASY, false, true, true, false)
        ));

    }

}
