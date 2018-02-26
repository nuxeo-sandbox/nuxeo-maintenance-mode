package org.nuxeo.ecm.maintenance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.nuxeo.ecm.automation.AutomationService;
import org.nuxeo.ecm.automation.OperationContext;
import org.nuxeo.ecm.automation.OperationException;
import org.nuxeo.ecm.automation.test.AutomationFeature;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.management.api.ProbeInfo;
import org.nuxeo.ecm.core.management.api.ProbeManager;
import org.nuxeo.ecm.core.test.NoopRepositoryInit;
import org.nuxeo.ecm.core.test.annotations.Granularity;
import org.nuxeo.ecm.core.test.annotations.RepositoryConfig;
import org.nuxeo.ecm.maintenance.operation.MaintenanceModeOp;
import org.nuxeo.ecm.maintenance.operation.MaintenanceStatusOp;
import org.nuxeo.runtime.api.Framework;
import org.nuxeo.runtime.test.runner.Deploy;
import org.nuxeo.runtime.test.runner.Features;
import org.nuxeo.runtime.test.runner.FeaturesRunner;

@RunWith(FeaturesRunner.class)
@Features(AutomationFeature.class)
@RepositoryConfig(init = NoopRepositoryInit.class, cleanup = Granularity.METHOD)
@Deploy({ "org.nuxeo.ecm.maintenance-mode", "org.nuxeo.ecm.core.management" })
public class TestMaintenanceMode {

  @Inject
  protected CoreSession session;

  @Inject
  protected AutomationService automationService;

  @Test
  public void checkNotEnabled() throws OperationException {
    OperationContext ctx = new OperationContext(session);
    MaintenanceStatus status = (MaintenanceStatus) automationService.run(ctx, MaintenanceStatusOp.ID);
    assertFalse("Not enabled", status.isEnabled());
  }

  @Test
  public void checkEnabledMessage() throws OperationException {
    OperationContext ctx = new OperationContext(session);
    MaintenanceStatus status = null;
    ProbeInfo probe = null;

    // Check pre-condition
    status = (MaintenanceStatus) automationService.run(ctx, MaintenanceStatusOp.ID);
    assertFalse("Not enabled", status.isEnabled());
    probe = Framework.getService(ProbeManager.class).getProbeInfo(MaintenanceComponent.MAINTENANCE_PROBE);
    assertFalse(probe.isInError());

    // Modify state
    Map<String, Object> params = new HashMap<>();
    params.put("enabled", true);
    params.put("message", "maintenance");
    automationService.run(ctx, MaintenanceModeOp.ID, params);

    // Check status
    status = (MaintenanceStatus) automationService.run(ctx, MaintenanceStatusOp.ID);
    assertTrue("Enabled", status.isEnabled());
    assertEquals("Maintenance message should be 'maintenance'", "maintenance", status.getMessage());
    probe = Framework.getService(ProbeManager.class).getProbeInfo(MaintenanceComponent.MAINTENANCE_PROBE);
    assertTrue(probe.isInError());

    // Modify state
    params.clear();
    params.put("enabled", false);
    automationService.run(ctx, MaintenanceModeOp.ID, params);

    // Check post-condition
    status = (MaintenanceStatus) automationService.run(ctx, MaintenanceStatusOp.ID);
    assertFalse("Not enabled", status.isEnabled());
    probe = Framework.getService(ProbeManager.class).getProbeInfo(MaintenanceComponent.MAINTENANCE_PROBE);
    assertFalse(probe.isInError());
  }

  @Test
  public void checkEnabledNoMessage() throws OperationException {
    OperationContext ctx = new OperationContext(session);
    MaintenanceStatus status = null;
    ProbeInfo probe = null;

    // Check pre-condition
    status = (MaintenanceStatus) automationService.run(ctx, MaintenanceStatusOp.ID);
    assertFalse("Not enabled", status.isEnabled());
    probe = Framework.getService(ProbeManager.class).getProbeInfo(MaintenanceComponent.MAINTENANCE_PROBE);
    assertFalse(probe.isInError());

    // Modify state
    Map<String, Object> params = new HashMap<>();
    params.put("enabled", true);
    automationService.run(ctx, MaintenanceModeOp.ID, params);

    // Check status
    status = (MaintenanceStatus) automationService.run(ctx, MaintenanceStatusOp.ID);
    assertTrue("Enabled", status.isEnabled());
    assertNull("Maintenance message should be null", status.getMessage());
    probe = Framework.getService(ProbeManager.class).getProbeInfo(MaintenanceComponent.MAINTENANCE_PROBE);
    assertTrue(probe.isInError());

    // Modify state
    params.clear();
    params.put("enabled", false);
    automationService.run(ctx, MaintenanceModeOp.ID, params);

    // Check post-condition
    status = (MaintenanceStatus) automationService.run(ctx, MaintenanceStatusOp.ID);
    assertFalse("Not enabled", status.isEnabled());
    probe = Framework.getService(ProbeManager.class).getProbeInfo(MaintenanceComponent.MAINTENANCE_PROBE);
    assertFalse(probe.isInError());
  }
}
