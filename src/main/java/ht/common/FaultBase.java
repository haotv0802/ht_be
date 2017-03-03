package ht.common;

/**
 * Created by glengarski on 02/11/2016.
 */
public class FaultBase
{
  /**
   * Fault code
   */
  private String faultCode;

  /**
   * Fault message
   */
  private String faultMessage;


  public FaultBase()
  {
  }

  public FaultBase(String faultCode, String faultMessage)
  {
    this.faultCode = faultCode;
    this.faultMessage = faultMessage;
  }

  public String getFaultCode()
  {
    return faultCode;
  }

  public void setFaultCode(String faultCode)
  {
    this.faultCode = faultCode;
  }

  public String getFaultMessage()
  {
    return faultMessage;
  }

  public void setFaultMessage(String faultMessage)
  {
    this.faultMessage = faultMessage;
  }

}
