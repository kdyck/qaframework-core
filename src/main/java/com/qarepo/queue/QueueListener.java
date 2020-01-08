package com.qarepo.queue;

import com.qarepo.queue.model.QueueMessage;

public interface QueueListener {
    void message(QueueMessage msg);
}
