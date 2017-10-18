package org.interledger.rpc;

import org.interledger.Fulfillment;
import org.interledger.InterledgerRuntimeException;

import java.util.UUID;

public class OutgoingChannel extends InMemoryChannel{

  public void sendTransfer(Transfer transfer) {
    Transfer cache = putTransfer(transfer);

    if(cache != null) {
      //TODO Improve this exception
      throw new InterledgerRuntimeException("Duplicate transfers with same id.");
    }
  }

  /**
   * Process an incoming fulfillment
   *
   * @param transferId id of the transfer being fulfilled
   * @param fulfillment fulfillment of the transfer
   * @return the transfer that was fulfilled
   */
  public Transfer fulfillTransfer(UUID transferId, Fulfillment fulfillment) {

    Transfer transfer = getTransfer(transferId);

    if(!fulfillment.validate(transfer.getCondition())) {
      //TODO Custom Exception type?
      throw new InterledgerRuntimeException("Invalid Fulfillment");
    }

    onTransferFulfilled(transfer, fulfillment);

    return transfer;

  }

  /**
   * Process an async transfer response rejecting a transfer that was sent previously
   *
   * @param transferId The id of the transfer
   * @param reason The reason for the rejection
   * @return
   */
  //TODO Define enum for reasons
  public Transfer rejectTransfer(UUID transferId, String reason) {

    Transfer transfer = getTransfer(transferId);

    onTransferRejected(transfer, reason);

    return transfer;
  }

  private void onTransferFulfilled(Transfer transfer, Fulfillment fulfillment) {
  }

  private void onTransferRejected(Transfer transfer, String reason) {
  }

}
