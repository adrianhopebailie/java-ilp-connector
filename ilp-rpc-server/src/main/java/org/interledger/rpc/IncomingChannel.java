package org.interledger.rpc;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

import org.interledger.Condition;
import org.interledger.Fulfillment;
import org.interledger.InterledgerAddress;
import org.interledger.InterledgerPacket;
import org.interledger.InterledgerRuntimeException;
import org.interledger.ilp.InterledgerPayment;

public class IncomingChannel extends InMemoryChannel{

  private ChannelInfo info;

  /**
   * Process an incoming transfer
   *
   * @param transfer The incoming transfer
   */
  public void receiveTransfer(Transfer transfer) {

    Transfer cache = putTransfer(transfer);

    if(cache != null) {
      //TODO Improve this exception
      throw new InterledgerRuntimeException("Duplicate transfers with same id.");
    }

    onTransferReceived(transfer);
  }


  public void receiveMessage(InterledgerPacket interledgerPacket) {

  }

  public Balance getBalance() {

  }

  public TransferState getTransferState(UUID transferId) {

  }

  public void sendMessage(InterledgerPacket interledgerPacket) {

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

  public ChannelInfo getInfo() {

    return info;
  }

  private void onTransferReceived(Transfer transfer) {
    long transferAmount = transfer.getAmount();
    OffsetDateTime transferExpiry = transfer.getExpiry();
    Condition transferCondition = transfer.getCondition();
    InterledgerPayment interledgerPayment = transfer.getInterledgerPayment();

    InterledgerAddress destinationAccount = interledgerPayment.getDestinationAccount();
    long destinationAmount = interledgerPayment.getDestinationAmount();
  }

  private void onRequestReceived(InterledgerPacket interledgerPacket) {

  }

}
