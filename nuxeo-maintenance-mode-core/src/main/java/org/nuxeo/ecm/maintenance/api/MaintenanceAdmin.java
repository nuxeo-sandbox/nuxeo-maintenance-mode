package org.nuxeo.ecm.maintenance.api;

import org.nuxeo.ecm.maintenance.MaintenanceStatus;

public interface MaintenanceAdmin {

  boolean isMaintenanceMode();

  MaintenanceStatus getStatus();

  MaintenanceStatus setMode(boolean enabled, String message);

}
