package com.vinsys.app;

import java.io.Serializable;
import java.util.Date;

public class Entry implements Serializable {
	public String name;
	public String message;
	public Date timestamp;
}