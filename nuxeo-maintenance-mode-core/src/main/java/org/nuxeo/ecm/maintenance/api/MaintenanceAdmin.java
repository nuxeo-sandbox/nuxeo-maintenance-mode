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
package org.nuxeo.ecm.maintenance.api;

import org.nuxeo.ecm.maintenance.MaintenanceStatus;

/**
 * Administrative interface for maintenance mode.
 * 
 * @since 9.10
 */
public interface MaintenanceAdmin {

  /**
   * Convenience method to determine if maintenance is happening or not.
   * 
   * @return true if maintenance mode is enabled
   */
  boolean isMaintenanceMode();

  /**
   * Get the current status of maintenance within the system.
   * 
   * @return the maintenance status object
   */
  MaintenanceStatus getStatus();

  /**
   * Modify the current maintenance status of the system.
   * 
   * @param enabled
   *          maintenance is happening if this is true
   * @param message
   *          user message associated with this system maintenance
   * @return
   */
  MaintenanceStatus setMode(boolean enabled, String message);

}
