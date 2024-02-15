/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.wildfly.extension.mvc.krazo;

import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.SUBSYSTEM;

import org.jboss.as.controller.ModelVersion;
import org.jboss.as.controller.PathElement;
import org.jboss.as.controller.PersistentResourceXMLDescription;
import org.jboss.as.controller.PersistentSubsystemSchema;
import org.jboss.as.controller.SubsystemModel;
import org.jboss.as.controller.SubsystemSchema;
import org.jboss.as.controller.xml.VersionedNamespace;
import org.jboss.as.version.Stability;
import org.jboss.staxmapper.IntVersion;
import org.wildfly.subsystem.SubsystemConfiguration;
import org.wildfly.subsystem.SubsystemExtension;
import org.wildfly.subsystem.SubsystemPersistence;


/**
 * WildFly extension that provides Jakarta MVC support based on Eclipse Krazo.
 *
 * @author <a href="mailto:brian.stansberry@redhat.com">Brian Stansberry</a>
 */
public final class MVCKrazoExtension extends SubsystemExtension<MVCKrazoExtension.MVCKrazoSubsystemSchema> {

    /**
     * The name of our subsystem within the model.
     */
    static final String SUBSYSTEM_NAME = "mvc-krazo";
    private static final Stability FEATURE_STABILITY = Stability.PREVIEW;

    static final PathElement SUBSYSTEM_PATH = PathElement.pathElement(SUBSYSTEM, SUBSYSTEM_NAME);

    public MVCKrazoExtension() {
        super(SubsystemConfiguration.of(SUBSYSTEM_NAME, MVCKrazoSubsystemModel.CURRENT, MVCKrazoSubsystemRegistrar::new),
                SubsystemPersistence.of(MVCKrazoSubsystemSchema.CURRENT));
    }

    // TODO enable this when WildFly Galleon Plugin can handle it
//    @Override
//    public Stability getStability() {
//        return FEATURE_STABILITY;
//    }

    /**
     * Model for the 'mvc-krazo' subsystem.
     */
    public enum MVCKrazoSubsystemModel implements SubsystemModel {
        VERSION_1_0_0(1, 0, 0),
        ;

        static final MVCKrazoSubsystemModel CURRENT = VERSION_1_0_0;

        private final ModelVersion version;

        MVCKrazoSubsystemModel(int major, int minor, int micro) {
            this.version = ModelVersion.create(major, minor, micro);
        }

        @Override
        public ModelVersion getVersion() {
            return this.version;
        }
    }

    /**
     * Schema for the 'mvc-krazo' subsystem.
     */
    public enum MVCKrazoSubsystemSchema implements PersistentSubsystemSchema<MVCKrazoSubsystemSchema> {

        /* urn:jboss:domain variant from WF Preview 31
           It wasn't really DEFAULT stability, but its namespace didn't include 'preview'
           so we work around that. See also getStability(). */
        VERSION_1_0_LEGACY(1, 0, Stability.DEFAULT, true),
        // first urn:wildfly variant
        VERSION_1_1_PREVIEW(1, 1, FEATURE_STABILITY),
        ;

        static final MVCKrazoSubsystemSchema CURRENT = VERSION_1_1_PREVIEW;

        private final VersionedNamespace<IntVersion, MVCKrazoSubsystemSchema> namespace;

        MVCKrazoSubsystemSchema(int major, int minor, Stability stability) {
            this(major, minor, stability, false);
        }
        MVCKrazoSubsystemSchema(int major, int minor, Stability stability, boolean legacy) {
            if (legacy) {
                this.namespace = SubsystemSchema.createLegacySubsystemURN(SUBSYSTEM_NAME, stability, new IntVersion(major, minor));
            } else {
                this.namespace = SubsystemSchema.createSubsystemURN(SUBSYSTEM_NAME, stability, new IntVersion(major, minor));
            }
        }

        @Override
        public VersionedNamespace<IntVersion, MVCKrazoSubsystemSchema> getNamespace() {
            return this.namespace;
        }

        @Override
        public Stability getStability() {
            return this == VERSION_1_0_LEGACY ? Stability.PREVIEW : this.getNamespace().getStability();
        }

        @Override
        public PersistentResourceXMLDescription getXMLDescription() {
            PersistentResourceXMLDescription.Factory factory = PersistentResourceXMLDescription.factory(this);
            return factory.builder(SUBSYSTEM_PATH)
                    .build();
        }
    }
}
