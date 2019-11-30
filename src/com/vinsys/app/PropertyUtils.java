package com.vinsys.app;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map.Entry;
import java.util.Properties;

public class PropertyUtils {
	/**
	 * Loads properties from a classpath resource
	 *
	 * @param props
	 * @param resource
	 * @param throwExceptionIfNotFound
	 * @return loaded properties
	 */
	public static Properties loadFromClassPath(String resource, boolean throwExceptionIfNotFound) {
		URL url = PropertyUtils.class.getClassLoader().getResource(resource);
		if (url == null) {
			if (throwExceptionIfNotFound) {
				throw new IllegalStateException("Could not find classpath properties resource: " + resource);
			} else {
				return new Properties();
			}
		}
		try {
			Properties props = new Properties();
			InputStream is = url.openStream();
			try {
				props.load(url.openStream());
			} finally {
				is.close();
			}
			return props;
		} catch (IOException e) {
			throw new RuntimeException("Could not read properties at classpath resource: " + resource, e);
		}
	}

	/**
	 * Merges multiple {@link Properties} instances into one
	 *
	 * @param mode    merge mode
	 * @param sources properties to merge
	 * @return new instance of {@link Properties} containing merged values
	 */
	public static Properties merge(MergeMode mode, Properties... sources) {
		Properties props = new Properties();

		for (int i = 0; i < sources.length; i++) {
			final Properties source = sources[i];
			for (Entry<Object, Object> prop : source.entrySet()) {
				final boolean exists = props.containsKey(prop.getKey());
				boolean set = false;
				switch (mode) {
				case MERGE:
					set = true;
					break;
				case OVERRIDE_ONLY:
					set = exists;
					break;
				}
				if (set || i == 0) {
					props.put(prop.getKey(), prop.getValue());
				}
			}
		}
		return props;
	}

	/**
	 * Merge mode
	 *
	 * @author igor.vaynberg
	 */
	public static enum MergeMode {
		OVERRIDE_ONLY, MERGE
	}
}