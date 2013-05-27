--
--  Creating basic queue table and queue
--
BEGIN
DBMS_AQADM.CREATE_QUEUE_TABLE(
   Queue_table        =>  'AQ_TEST_TABLE', 
   Multiple_consumers =>   TRUE,
   Queue_payload_type =>  'RAW');
END;
/
BEGIN
   DBMS_AQADM.CREATE_QUEUE(
         Queue_name     =>   'AQ_TEST_QUEUE',
         Queue_table    =>   'AQ_TEST_TABLE');
END;
/

--Start the queue
BEGIN
   DBMS_AQADM.START_QUEUE('AQ_TEST_QUEUE');
END;
/
-- Add Subscriber
BEGIN
   DBMS_AQADM.ADD_SUBSCRIBER ('AQ_TEST_QUEUE', SYS.AQ$_AGENT('MSG_SBSCR1',null,null));
END;
/


--
--  Creating buffered queue table and queue
--
--BEGIN
--DBMS_AQADM.CREATE_QUEUE_TABLE(
--   Queue_table        =>  'AQ_TEST_TABLE', 
--   Multiple_consumers =>   TRUE,
--   Queue_payload_type =>  'RAW');
--END;
--/

BEGIN
   DBMS_AQADM.CREATE_QUEUE(
         Queue_name     =>   'AQ_TEST_BUFFERED_QUEUE',
         Queue_table    =>   'AQ_TEST_TABLE');
END;
/

--Start the queue
BEGIN
   DBMS_AQADM.START_QUEUE('AQ_TEST_BUFFERED_QUEUE');
END;
/

-- Add Subscriber
BEGIN
   DBMS_AQADM.ADD_SUBSCRIBER (
   					queue_name => 'AQ_TEST_QUEUE', 
   					subscriber => SYS.AQ$_AGENT('MSG_BUFFERED_SBSCR1',null,null),
   					delivery_mode => BUFFERED );
END;
/