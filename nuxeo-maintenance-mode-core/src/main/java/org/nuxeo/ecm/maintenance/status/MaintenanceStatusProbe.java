/**
 * 
 */
package org.nuxeo.ecm.maintenance.status;

import org.nuxeo.ecm.core.management.api.Probe;
import org.nuxeo.ecm.core.management.api.ProbeStatus;
import org.nuxeo.ecm.maintenance.MaintenanceStatus;
import org.nuxeo.ecm.maintenance.api.MaintenanceAdmin;
import org.nuxeo.runtime.api.Framework;

/**
 * @author dbrown
 *
 */
public class MaintenanceStatusProbe implements Probe {

    /**
     * 
     */
    public MaintenanceStatusProbe() {
        super();
    }

    /* (non-Javadoc)
     * @see org.nuxeo.ecm.core.management.api.Probe#run()
     */
    @Override
    public ProbeStatus run() {
        MaintenanceAdmin admin = Framework.getService(MaintenanceAdmin.class);
        MaintenanceStatus status = admin.getStatus();
        if (status.isEnabled()) {
            return ProbeStatus.newFailure(status.getMessage());
        } else {
            return ProbeStatus.newSuccess(status.getMessage());
        }
    }

}
