package com.qarepo.queue.service;

import com.qarepo.queue.model.QueueMessage;

public interface QueueMessageService {
    public QueueMessage saveQueueMessage(QueueMessage message);

    public QueueMessage findQueueMessageByJobName(String jobNumber);
}
