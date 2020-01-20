package com.family.tree.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
public class ApplicationProperties {

	@Value("${output.file.directory}")
	private String outputFileDirectory;

	public String getOutputFileDirectory() {
		return outputFileDirectory;
	}

	public void setOutputFileDirectory(String outputFileDirectory) {
		this.outputFileDirectory = outputFileDirectory;
	}

}
