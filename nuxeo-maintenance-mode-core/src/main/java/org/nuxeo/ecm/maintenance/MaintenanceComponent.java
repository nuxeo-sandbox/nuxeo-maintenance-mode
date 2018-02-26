/**
 * 
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
 * @author dbrown
 *
 */
public class MaintenanceComponent extends DefaultComponent implements MaintenanceAdmin {

  public static final String MAINTENANCE_ON_STARTUP = "maintenance.enabled";

  public static final String MAINTENANCE_MESSAGE = "maintenance.message";

  protected static final String MAINTENANCE_PROBE = "maintenanceStatus";

  protected AtomicReference<MaintenanceStatus> status;

  /**
   * 
   */
  public MaintenanceComponent() {
    super();
  }

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
