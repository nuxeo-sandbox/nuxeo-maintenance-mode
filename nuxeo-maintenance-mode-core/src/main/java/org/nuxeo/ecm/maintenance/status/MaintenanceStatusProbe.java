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
package org.nuxeo.ecm.maintenance.status;

import org.nuxeo.ecm.core.management.api.Probe;
import org.nuxeo.ecm.core.management.api.ProbeStatus;
import org.nuxeo.ecm.maintenance.MaintenanceStatus;
import org.nuxeo.ecm.maintenance.api.MaintenanceAdmin;
import org.nuxeo.runtime.api.Framework;

/**
 * Probe callback hook to update the current maintenance status of the system.
 * 
 * @since 9.10
 */
public class MaintenanceStatusProbe implements Probe {

  /*
   * (non-Javadoc)
   * 
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
