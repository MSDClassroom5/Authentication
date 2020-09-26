package com.Authentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

//import com.uber.jaeger.Configuration;
//import com.uber.jaeger.samplers.ProbabilisticSampler;

import io.jaegertracing.Configuration;
import io.jaegertracing.internal.JaegerTracer;

@SpringBootApplication
public class AuthenticationApplication {

	public static void main(String[] args) {
		System.out.println("MSD Project group 5::Starting Auth application");
		SpringApplication.run(AuthenticationApplication.class, args);
	}
	
//	@Bean
//	public io.opentracing.Tracer jaegerTracer() {
//		return new Configuration("auth-api", new Configuration.SamplerConfiguration(ProbabilisticSampler.TYPE,1),
//				new Configuration.ReporterConfiguration())
//				.getTracer();
//	}
	
	@Bean
	public static JaegerTracer getTracer() {
		Configuration.SamplerConfiguration samplerConfig =
				Configuration .SamplerConfiguration.fromEnv().withType("const").withParam(1);
		Configuration.ReporterConfiguration reporterConfig =
		Configuration.ReporterConfiguration.fromEnv().withLogSpans(true);
		Configuration config = new Configuration("auth-api").withSampler(samplerConfig).withReporter(reporterConfig);
		return config.getTracer();			
	}	
	

}
