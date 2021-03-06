package com.boot.batch;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;

import com.boot.entity.Person;
import com.boot.process.PersonItemProcessor;

/**
 * 处理具体工作业务  主要包含三个部分:读数据、处理数据、写数据
 * @author wbw
 *
 */
@Configuration
//@EnableBatchProcessing
public class PersonBatchConfiguration {
  @Autowired
  public JobBuilderFactory jobBuilderFactory;

  @Autowired
  public StepBuilderFactory stepBuilderFactory;
  //插入语句
  private static final String PERSON_INSERT = "INSERT INTO Person (personName, personAge,personSex) VALUES (:personName, :personAge,:personSex)";

  // tag::readerwriterprocessor[] 1.读数据
  @Bean
  public ItemReader<Person> reader() {
    FlatFileItemReader<Person> reader = new FlatFileItemReader<Person>();
    //加载外部文件数据 文件类型:CSV
    reader.setResource(new ClassPathResource("sample-data.csv"));
    reader.setLineMapper(new DefaultLineMapper<Person>() {
      {
        setLineTokenizer(new DelimitedLineTokenizer() {
          {
            setNames(new String[] { "personName", "personAge", "personSex" });
          }
        });
        setFieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {
          {
            setTargetType(Person.class);
          }
        });
      }
    });
    return reader;
  }

  //2.处理数据
  @Bean
  public PersonItemProcessor processor() {
    return new PersonItemProcessor();
  }

  //3.写数据
  @Bean
  public ItemWriter<Person> writer(DataSource dataSource) {
    JdbcBatchItemWriter<Person> writer = new JdbcBatchItemWriter<Person>();
    writer.setItemSqlParameterSourceProvider(
        new BeanPropertyItemSqlParameterSourceProvider<Person>());
    writer.setSql(PERSON_INSERT);
    writer.setDataSource(dataSource);
    return writer;
  }
  // end::readerwriterprocessor[]

  // tag::jobstep[]
  @Bean
  public Job importUserJob(@Qualifier("step1") Step s1,
      JobExecutionListener listener) {
    return jobBuilderFactory.get("importUserJob")
        .incrementer(new RunIdIncrementer())
        .listener(listener)
        .flow(s1)
        .end()
        .build();
  }

  @Bean
  public Step step1(ItemReader<Person> reader,
      ItemWriter<Person> writer, ItemProcessor<Person, Person> processor) {
    return stepBuilderFactory.get("step1")
        .<Person, Person> chunk(10)
        .reader(reader)
        .processor(processor)
        .writer(writer)
        .build();
  }
  // end::jobstep[]
//------------------------------------------------------------------------------
  @Bean
  public Job importUserJobTwo(@Qualifier("step2") Step s1,
      JobExecutionListener listener) {
    return jobBuilderFactory.get("importUserJobTwo")
        .incrementer(new RunIdIncrementer())
        .listener(listener)
        .flow(s1)
        .end()
        .build();
  }

  @Bean
  public Step step2(ItemReader<Person> reader,
      ItemWriter<Person> writer, ItemProcessor<Person, Person> processor) {
    return stepBuilderFactory.get("step2")
        .<Person, Person> chunk(10)
        .reader(reader)
        .processor(processor)
        .writer(writer)
        .build();
  }
  //--------------------------------------------------------------------
  @Bean
  public JdbcTemplate jdbcTemplate(DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }

}
