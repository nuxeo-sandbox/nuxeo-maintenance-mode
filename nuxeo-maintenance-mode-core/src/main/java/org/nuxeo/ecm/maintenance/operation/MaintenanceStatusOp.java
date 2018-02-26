package org.nuxeo.ecm.maintenance.operation;

import org.nuxeo.ecm.automation.core.Constants;
import org.nuxeo.ecm.automation.core.annotations.Operation;
import org.nuxeo.ecm.automation.core.annotations.OperationMethod;
import org.nuxeo.ecm.maintenance.MaintenanceStatus;
import org.nuxeo.ecm.maintenance.api.MaintenanceAdmin;
import org.nuxeo.runtime.api.Framework;

/**
 *
 */
@Operation(id = MaintenanceStatusOp.ID, category = Constants.CAT_SERVICES, label = "Get Maintenance Status", description = "Retrieve the maintenance status.")
public class MaintenanceStatusOp {

  public static final String ID = "System.MaintenanceStatus";

  @OperationMethod
  public MaintenanceStatus run() {
    MaintenanceAdmin admin = Framework.getService(MaintenanceAdmin.class);
    MaintenanceStatus status = admin.getStatus();
    return status;
  }
}
