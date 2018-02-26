package org.nuxeo.ecm.maintenance.operation;

import org.nuxeo.ecm.automation.core.Constants;
import org.nuxeo.ecm.automation.core.annotations.Operation;
import org.nuxeo.ecm.automation.core.annotations.OperationMethod;
import org.nuxeo.ecm.automation.core.annotations.Param;
import org.nuxeo.ecm.maintenance.api.MaintenanceAdmin;
import org.nuxeo.runtime.api.Framework;

/**
 *
 */
@Operation(id = MaintenanceModeOp.ID, category = Constants.CAT_SERVICES, label = "Set Maintenance Mode", description = "Set the maintenance mode, with message.")
public class MaintenanceModeOp {

    public static final String ID = "System.MaintenanceMode";

    @Param(name = "enabled", required = false)
    protected boolean enabled = false;

    @Param(name = "message", required = false)
    protected String message = null;

    @OperationMethod
    public void run() {
        MaintenanceAdmin admin = Framework.getService(MaintenanceAdmin.class);
        admin.setMode(enabled, message);
    }
}
