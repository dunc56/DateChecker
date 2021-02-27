package com.datecheck;

import java.time.LocalDateTime;

public interface DateChecker {
	public boolean isCallBackDateValid(LocalDateTime callbackDateandTime, LocalDateTime currentDateTime);
}
