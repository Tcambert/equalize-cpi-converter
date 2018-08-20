package com.equalize.cpi.converter.util

import org.apache.camel.Exchange

import com.equalize.cpi.converter.Base64DecodeConverter
import com.equalize.cpi.converter.Base64EncodeConverter
import com.equalize.cpi.converter.JSON2XMLConverter
import com.equalize.cpi.converter.XML2JSONConverter

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

		switch(converterClassName) {
			case 'com.equalize.cpi.converter.Base64DecodeConverter':
				return new Base64DecodeConverter(exchange, properties)
			case 'com.equalize.cpi.converter.Base64EncodeConverter':
				return new Base64EncodeConverter(exchange, properties)
			case 'com.equalize.cpi.converter.JSON2XMLConverter':
				return new JSON2XMLConverter(exchange, properties)
			case 'com.equalize.cpi.converter.XML2JSONConverter':
				return new XML2JSONConverter(exchange, properties)
			default:
				throw new ClassNotFoundException("$converterClassName is an invalid converter class")
		}
	}
}