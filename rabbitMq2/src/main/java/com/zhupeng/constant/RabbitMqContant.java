package com.zhupeng.constant;

/**
 * rabbitMQ的常量
 */
public class RabbitMqContant {
    /**
     * 常量
     */
    public static final String RANDLE_NUM_WORD = "#";
    public static final String DLX_EXCHANGE = "dlx.exchange";
    public static final String X_DEAD_LETTER_EXCHANGE  = "x-dead-letter-exchange";
    public static final String X_DEAD_LETTER_ROUTING_KEY  = "x-dead-letter-routing-key";

    /**
     * exchange
     */
    public static final String USER_DIRECT_EXCHANGE = "user_direct_exchange";

    public static final String USER_TOPIC_EXCHANGE = "user_topic_exchange";

    public static final String USER_FANOUT_EXCHANGE = "user_fanout_exchange";

    public static final String TEST_FANOUT_EXCHANGE = "test_fanout_exchange";

    public static final String TEST_DIRECT_EXCHANGE = "test_direct_exchange";


    /**
     * routingkey
     */
    public static final String USER_TOPIC_ROUTINGKEY = "#.user.#";

    public static final String ADD_USER_ROUTINGKEY = "add.user.routingkey";

    public static final String DELETE_USER_ROUTINGKEY = "delete.user.routingkey";

    public static final String TEST_ACK_ROUTINGKEY = "test.ack.routingkey";

    public static final String TEST_DIRECT_ROUTINGKEY = "test.direct.routingkey";


    /**
     * queue
     */
    public static final String USER_QUEUE = "user_queue";

    public static final String ADD_USER_QUEUE = "add_user_queue";

    public static final String DELETE_USER_QUEUE = "delete_user_queue";

    public static final String TEST_ACK_QUEUE = "test_ack_queue";

    public static final String TEST_DIRECT_QUEUE = "test_direct_queue";
}
