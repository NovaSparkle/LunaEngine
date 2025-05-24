package com.example.novasparkle.LunaEngine.telegram.listeners.query.transfer.sender;

import com.example.novasparkle.LunaEngine.telegram.listeners.query.KeyQuery;
import com.example.novasparkle.LunaEngine.telegram.listeners.query.transfer.TransferContainer;
import lombok.Getter;

@Getter
public abstract class TransferQuery extends KeyQuery {
    private final TransferContainer container;
    public TransferQuery(long chatId, TransferContainer container) {
        super(chatId);
        this.container = container;
    }
}
