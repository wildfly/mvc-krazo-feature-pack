/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.wildfly.extension.mvc.krazo;

import static org.wildfly.extension.mvc.krazo.MVCKrazoExtension.SUBSYSTEM_NAME;
import static org.wildfly.extension.mvc.krazo.MVCKrazoExtension.MVCKrazoSubsystemSchema.CURRENT;

import java.io.IOException;
import java.util.EnumSet;

import org.jboss.as.subsystem.test.AbstractSubsystemSchemaTest;
import org.jboss.as.version.Stability;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * Standard subsystem test.
 *
 * @author <a href="mailto:brian.stansberry@redhat.com">Brian Stansberry</a>
 */
@RunWith(Parameterized.class)
public class SubsystemTestCase extends AbstractSubsystemSchemaTest<MVCKrazoExtension.MVCKrazoSubsystemSchema> {

    @Parameterized.Parameters
    public static Iterable<MVCKrazoExtension.MVCKrazoSubsystemSchema> parameters() {
        return EnumSet.allOf(MVCKrazoExtension.MVCKrazoSubsystemSchema.class);
    }

    public SubsystemTestCase(MVCKrazoExtension.MVCKrazoSubsystemSchema schema) {
        super(SUBSYSTEM_NAME, new MVCKrazoExtension(), schema, CURRENT);
    }

    protected String getSubsystemXsdPath() throws Exception {
        if (getSubsystemSchema() == MVCKrazoExtension.MVCKrazoSubsystemSchema.VERSION_1_0_LEGACY) {
            // Provide the xsd file name we used with WF Preview 31
            return "schema/mvc-krazo_1.0.xsd";
        } else {
            return super.getSubsystemXsdPath();
        }
    }
}
