package com.epam.vm.etc.log4j.appenders;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;

public class OnlyInfoAppender extends ConsoleAppender {

	@Override
	public void append(LoggingEvent event) {
		if (event.getLevel() == Level.INFO) {
			super.append(event);
		}
	}

}
