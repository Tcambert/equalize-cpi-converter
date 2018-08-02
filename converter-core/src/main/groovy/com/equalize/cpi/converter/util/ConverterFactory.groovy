package com.equalize.cpi.converter.util

import org.apache.camel.Exchange
import java.lang.reflect.Constructor

class ConverterFactory {
	// Private constructor to prevent direct instantiation of this factory
	private ConverterFactory() {
	}

	ConverterFactory newInstance() {
		new ConverterFactory()
	}

	AbstractConverter newConverter(Exchange exchange, Map<String,Object> properties) {
		PropertyHelper ph = new PropertyHelper(properties)
		String converterClassName = ph.getProperty('converterClass')
		try {
			// Dynamic loading and instantiation of converter class
			ClassLoader classloader = Thread.currentThread().getContextClassLoader()
			Class<?> converterClass = Class.forName(converterClassName, true, classloader)
			Constructor<?> constructor = converterClass.getConstructor(Exchange, Map);
			(AbstractConverter) constructor.newInstance(exchange, properties)
		} catch(ClassNotFoundException e) {
			throw new ClassNotFoundException("$e.message is an invalid converter class")
		}
	}
}