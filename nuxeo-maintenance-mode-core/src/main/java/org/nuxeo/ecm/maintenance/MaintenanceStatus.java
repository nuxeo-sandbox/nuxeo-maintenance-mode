package org.nuxeo.ecm.maintenance;

public class MaintenanceStatus {

  protected boolean enabled;

  protected String message;

  public MaintenanceStatus() {
    super();
  }

  public MaintenanceStatus(boolean enabled, String message) {
    super();
    this.enabled = enabled;
    this.message = message;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

}
