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
    * JOB_INSTANCE_ID : PK
    * JOB_NAME : 수행한 배치의 JOB 이름
    * JOB PARAMETER 에 따라 데이터 생성

batch_job_seq

batch_step_execution

batch_step_execution_context

batch_step_execution_seq





================================================================================================
흐름 참고용
https://jojoldu.tistory.com/328?category=902551
@Bean
    public Job stepNextConditionalJob() {
        return jobBuilderFactory.get("stepNextConditionalJob")
                .start(conditionalJobStep1())
                    .on("FAILED") // FAILED 일 경우
                    .to(conditionalJobStep3()) // step3으로 이동한다.
                    .on("*") // step3의 결과 관계 없이
                    .end() // step3으로 이동하면 Flow가 종료한다.
                .from(conditionalJobStep1()) // step1로부터
                    .on("*") // FAILED 외에 모든 경우
                    .to(conditionalJobStep2()) // step2로 이동한다.
                    .next(conditionalJobStep3()) // step2가 정상 종료되면 step3으로 이동한다.
                    .on("*") // step3의 결과 관계 없이
                    .end() // step3으로 이동하면 Flow가 종료한다.
                .end() // Job 종료
                .build();
================================================================================================