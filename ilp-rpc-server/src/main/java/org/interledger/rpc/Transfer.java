package org.interledger.rpc;

import org.interledger.Condition;
import org.interledger.ilp.InterledgerPayment;

import java.time.OffsetDateTime;
import java.util.UUID;

public class Transfer {

  private UUID id;
  private Condition condition;
  private InterledgerPayment interledgerPayment;
  private long amount;
  private OffsetDateTime expiry;

  public Condition getCondition() {
    return condition;
  }

  public UUID getId() {
    return id;
  }

  public InterledgerPayment getInterledgerPayment() {
    return interledgerPayment;
  }

  public long getAmount() {
    return amount;
  }

  public OffsetDateTime getExpiry() {
    return expiry;
  }
}
