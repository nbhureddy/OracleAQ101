begin
  delete from aq$_AQ_TEST_TABLE_d;
  delete from aq$_AQ_TEST_TABLE_g;
  delete from aq$_AQ_TEST_TABLE_h;
  delete from aq$_AQ_TEST_TABLE_i;
  delete from aq$_AQ_TEST_TABLE_l;
  delete from aq$_AQ_TEST_TABLE_p;
  delete from aq$_AQ_TEST_TABLE_s;
  delete from aq$_AQ_TEST_TABLE_t;
  delete from AQ_TEST_TABLE;
  commit;
end;

alter table bm_1 modify (language_id raw(16));

select name, address from aq$L where queue='tAdmin_notifications';

select * from v$buffered_queues;

select queue_id, subscriber_name, subscriber_type, startup_time, total_dequeued_msg
      from v$buffered_subscribers;

select queue_id, startup_time, sysdate, num_msgs, spill_msgs from v$buffered_queues
          where queue_schema='AQDEMO'  and queue_name='MYQUEUE';


begin
 dbms_aqadm.drop_queue(
    queue_name => 'aq_test_queue');
end;
/

begin
 dbms_aqadm.drop_queue_table(
    queue_table =>'aq_test_table');
end;
/