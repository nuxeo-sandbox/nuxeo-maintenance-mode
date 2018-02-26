/*
 * Copyright 2018 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 *     Damon Brown
 */
package org.nuxeo.ecm.maintenance;

import java.util.concurrent.atomic.AtomicReference;

import org.nuxeo.ecm.core.event.Event;
import org.nuxeo.ecm.core.event.EventContext;
import org.nuxeo.ecm.core.event.EventProducer;
import org.nuxeo.ecm.core.event.impl.EventContextImpl;
import org.nuxeo.ecm.core.management.api.ProbeManager;
import org.nuxeo.ecm.maintenance.api.MaintenanceAdmin;
import org.nuxeo.runtime.api.Framework;
import org.nuxeo.runtime.model.ComponentContext;
import org.nuxeo.runtime.model.DefaultComponent;

/**
 * Maintenance component, contains the current maintenance state and message.
 * 
 * @since 9.10
 */
public class MaintenanceComponent extends DefaultComponent implements MaintenanceAdmin {

  public static final String MAINTENANCE_ON_STARTUP = "maintenance.enabled";

  public static final String MAINTENANCE_MESSAGE = "maintenance.message";

  protected static final String MAINTENANCE_PROBE = "maintenanceStatus";

  protected AtomicReference<MaintenanceStatus> status;

  @Override
  public void activate(ComponentContext context) {
    super.activate(context);
    this.status = new AtomicReference<>();
  }

  @Override
  public void deactivate(ComponentContext context) {
    super.deactivate(context);
    this.status = null;
  }

  @Override
  public void start(ComponentContext context) {
    super.start(context);

    // Check maintenance on startup
    maintenanceOnStartup();
  }

  protected void maintenanceOnStartup() {
    boolean enabled = Boolean.parseBoolean(Framework.getProperty(MAINTENANCE_ON_STARTUP, "false"));
    String message = Framework.getProperty(MAINTENANCE_MESSAGE);
    setMode(enabled, message);
  }

  @Override
  public boolean isMaintenanceMode() {
    return this.status.get().isEnabled();
  }

  @Override
  public MaintenanceStatus getStatus() {
    return this.status.get();
  }

  @Override
  public MaintenanceStatus setMode(boolean enabled, String message) {
    MaintenanceStatus status = new MaintenanceStatus(enabled, message);
    this.status.set(status);

    // Force update of probe
    Framework.getService(ProbeManager.class).runProbe(MAINTENANCE_PROBE);

    // Fire event
    EventProducer eventProducer = Framework.getService(EventProducer.class);
    EventContext ctx = new EventContextImpl();
    Event event = ctx.newEvent(MAINTENANCE_PROBE + (enabled ? "Enabled" : "Disabled"));
    eventProducer.fireEvent(event);

    return status;
  }

}
