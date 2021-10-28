package com.plasencia.app.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.plasencia.app.entity.CreditCard;
import com.plasencia.app.entity.CreditCardRisk;
import com.plasencia.app.job.CreditCardItemProcessor;
import com.plasencia.app.job.CreditCardItemReader;
import com.plasencia.app.job.CreditCardItemWriter;
import com.plasencia.app.listener.CreditCardIItemReaderListener;
import com.plasencia.app.listener.CreditCardIItemWriterListener;
import com.plasencia.app.listener.CreditCardItemProcessListener;
import com.plasencia.app.listener.CreditCardJobExecutionListener;

@Configuration
@EnableBatchProcessing
public class JobBatchConfiguration {

	/* Explicación:
	  Dentro de la clase JobBatchConfiguration debemos definir:

    	1.- Un bean de Spring para el Reader CreditCardItemReader
    	2.- Un bean de Spring para el Processor CreditCardItemProcessor
    	3.- Un bean de Spring para el Writer CreditCardItemWriter
    
    Los bean para los Listeners ->
    	- CreditCardJobExecutionListener 
    	- CreditCardItemReaderListener 
    	- CreditCardItemProcessListener
    	- CreditCardItemWriterListener

	Presta atención a cómo defines el Job y el Step que se ejecutará dentro del Job. Al Job le indicamos el listener que escuchara 
	y le establecemos el / los steps. En la definición del Step también le indicamos los listeners y cada uno de los beans 
	que mencionamos para el reader, procesor y writer. El “chunk” lo establecimos arbitrariamente en 100. Este valor lo debes pensar
	acorde a la necesidad de tu proyecto.
	*/ 
	 
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public CreditCardItemReader reader() {
        return new CreditCardItemReader();
    }

    @Bean
    public CreditCardItemProcessor processor() {
        return new CreditCardItemProcessor();
    }

    @Bean
    public CreditCardItemWriter writer() {
        return new CreditCardItemWriter();
    }

    @Bean
    public CreditCardJobExecutionListener jobExecutionListener() {
        return new CreditCardJobExecutionListener();
    }

    @Bean
    public CreditCardIItemReaderListener readerListener() {
        return new CreditCardIItemReaderListener();
    }

    @Bean
    public CreditCardItemProcessListener creditCardItemProcessListener() {
        return new CreditCardItemProcessListener();
    }

    @Bean
    public CreditCardIItemWriterListener writerListener() {
        return new CreditCardIItemWriterListener();
    }

    @Bean
    public Job job(Step step, CreditCardJobExecutionListener jobExecutionListener) {
        Job job = jobBuilderFactory.get("job1")
                .listener(jobExecutionListener)
                .flow(step)
                .end()
                .build();
        return job;
    }

    @Bean
    public Step step(CreditCardItemReader reader,
                     CreditCardItemWriter writer,
                     CreditCardItemProcessor processor,
                     CreditCardIItemReaderListener readerListener,
                     CreditCardItemProcessListener creditCardItemProcessListener,
                     CreditCardIItemWriterListener writerListener) {

        TaskletStep step = stepBuilderFactory.get("step1")
                .<CreditCard, CreditCardRisk>chunk(100)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(readerListener)
                .listener(creditCardItemProcessListener)
                .listener(writerListener)
                .build();
        return step;
    }

}