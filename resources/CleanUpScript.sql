--Stop the queue
BEGIN
   DBMS_AQADM.STOP_QUEUE('AQ_TEST_BUFFERED_QUEUE');
END;
/
-- Drop Subscriber
BEGIN
   DBMS_AQADM.REMOVE_SUBSCRIBER (
   					'AQ_TEST_BUFFERED_QUEUE', 
   					SYS.AQ$_AGENT('MSG_BUFFERED_SBSCR1',null,null));
END;
/

--Delete the Queue
begin
 dbms_aqadm.drop_queue(
    queue_name => 'AQ_TEST_BUFFERED_QUEUE');
end;
/

--Drop the queue table
--begin
-- dbms_aqadm.drop_queue_table(
--    queue_table =>'AQ_TEST_TABLE');
--end;
--/


--Stop the queue
BEGIN
   DBMS_AQADM.STOP_QUEUE('AQ_TEST_QUEUE');
END;
/
-- Drop Subscriber
BEGIN
   DBMS_AQADM.REMOVE_SUBSCRIBER (
   					'AQ_TEST_QUEUE', 
   					SYS.AQ$_AGENT('MSG_SBSCR1',null,null));
END;
/

--Delete the Queue
begin
 dbms_aqadm.drop_queue(
    queue_name => 'AQ_TEST_QUEUE');
end;
/

--Drop the queue table
begin
 dbms_aqadm.drop_queue_table(
    queue_table =>'AQ_TEST_TABLE');
end;
/