package ht.common;

/**
 * Property of CODIX Bulgaria EAD
 * Created by vtodorov
 *
 * Class representing an fault
 */
public class ServiceFault extends FaultBase
{
  private String incidentId;

  public ServiceFault()
  {

  }

  public ServiceFault(String faultCode, String message)
  {
    this(faultCode, message, null);
  }

  public ServiceFault(String faultCode, String message, String incidentId){
    super(faultCode, message);
    this.incidentId = incidentId;
  }

  public String getIncidentId()
  {
    return incidentId;
  }

  public void setIncidentId(String incidentId)
  {
    this.incidentId = incidentId;
  }
}
