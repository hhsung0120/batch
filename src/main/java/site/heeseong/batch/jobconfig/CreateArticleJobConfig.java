package site.heeseong.batch.jobconfig;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import site.heeseong.batch.file.domain.entity.ArticleEntity;
import site.heeseong.batch.file.domain.model.ArticleModel;
import site.heeseong.batch.file.domain.repository.ArticleRepository;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CreateArticleJobConfig {

    final JobBuilderFactory jobBuilderFactory;
    final StepBuilderFactory stepBuilderFactory;
    final ArticleRepository articleRepository;

    @Bean
    public Job createArticleJob() {
        return jobBuilderFactory.get("createArticleJob")
                .incrementer(new RunIdIncrementer())
                .start(createArticleStep())
                .build();
    }

    @Bean
    public Step createArticleStep() {
        return stepBuilderFactory.get("createArticleStep")
                .<ArticleModel, ArticleEntity>chunk(100)
                .reader(createArticleReader())
                .processor(createArticleProcessor())
                .writer(createArticleWriter())
//                .faultTolerant()
//                .skip(FlatFileParseException.class)
//                .skipLimit(10)
                .build();
//                .tasklet((contribution, chunkContext) -> {
//                    log.info("step1");
//                    return RepeatStatus.FINISHED;
//                })
//                .build();
    }

    @Bean
    public FlatFileItemReader<ArticleModel> createArticleReader() {
        return new FlatFileItemReaderBuilder<ArticleModel>()
                .name("createArticleReader")
                .resource(new ClassPathResource("articles.txt"))
                .delimited()
                .names("title", "content")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>())
                .targetType(ArticleModel.class)
                .build();
    }

    @Bean
    public ItemProcessor<ArticleModel, ArticleEntity> createArticleProcessor() {
        return articleModel -> ArticleEntity.builder()
                .title(articleModel.getTitle())
                .content(articleModel.getContent())
                .build();
    }

    @Bean
    public RepositoryItemWriter<ArticleEntity> createArticleWriter() {
        return new RepositoryItemWriterBuilder<ArticleEntity>()
                .repository(articleRepository)
                .build();

    }
}

