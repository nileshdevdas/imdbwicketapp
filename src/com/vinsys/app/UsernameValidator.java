package com.vinsys.app;

import org.apache.wicket.validation.CompoundValidator;
import org.apache.wicket.validation.validator.PatternValidator;

public class UsernameValidator extends CompoundValidator<String> {
	public UsernameValidator() {
		add(PatternValidator.exactLength(8));
	}
}
