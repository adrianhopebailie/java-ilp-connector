package org.interledger.rpc;

import org.interledger.InterledgerAddress;
import org.interledger.InterledgerRuntimeException;

import java.util.Map;
import java.util.UUID;

public abstract class InMemoryChannel {

  private InterledgerAddress address;
  private Map<UUID, Transfer> transfers;

  /**
   * Load a transfer from the cache.
   *
   * @param transferId The id of the transfer
   *
   * @return the cached transfer
   * @throws InterledgerRuntimeException if the transfer is not found
   */
  protected Transfer getTransfer(UUID transferId) {
    Transfer transfer = transfers.get(transferId);

    if(transfer == null) {
      throw new InterledgerRuntimeException("Transfer not found");
    }

    return transfer;
  }

  /**
   * Store a transfer in the cache.
   *
   * <p>If a transfer is already stored in the cache with the same id it is replaced.</p>
   *
   * @param transfer The transfer to store.
   *
   * @return the previous transfer that was stored in the cache under the same id or null.
   */
  protected Transfer putTransfer(Transfer transfer) {
    return transfers.put(transfer.getId(), transfer);
  }

}
