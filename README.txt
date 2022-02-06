batch:
    jdbc:
      initialize-schema: always
    job:
      names: ${job.name:NONE}
      enabled: true #디폴트가 아닌경우 false 인경우 => web, 또는 스케쥴링 적용 했을 경우 false

schema-mysql.sql 이 실행 됨

batch_job_execution

batch_job_execution_context
 * 스프링 배치 내부의 필요한 정보들이 저장

batch_job_execution_params

batch_job_execution_seq

batch_job_instance

batch_job_seq

batch_step_execution

batch_step_execution_context

batch_step_execution_seq
