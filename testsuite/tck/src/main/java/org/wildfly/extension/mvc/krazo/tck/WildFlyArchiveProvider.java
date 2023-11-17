/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.wildfly.extension.mvc.krazo.tck;

import ee.jakarta.tck.mvc.api.BaseArchiveProvider;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;

public class WildFlyArchiveProvider implements BaseArchiveProvider {
    @Override
    public WebArchive getBaseArchive() {
        return ShrinkWrap.create(WebArchive.class);
    }
}
