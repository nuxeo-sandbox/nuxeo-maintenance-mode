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
package org.nuxeo.ecm.maintenance.operation;

import org.nuxeo.ecm.automation.core.Constants;
import org.nuxeo.ecm.automation.core.annotations.Operation;
import org.nuxeo.ecm.automation.core.annotations.OperationMethod;
import org.nuxeo.ecm.maintenance.MaintenanceStatus;
import org.nuxeo.ecm.maintenance.api.MaintenanceAdmin;
import org.nuxeo.runtime.api.Framework;

/**
 * Get the current maintenance status of the system.
 * 
 * @since 9.10
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
