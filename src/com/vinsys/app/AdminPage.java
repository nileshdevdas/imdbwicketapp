package com.vinsys.app;

import org.apache.wicket.markup.html.WebPage;
import org.brixcms.web.admin.AdminPanel;

/**
 * This page hosts Brix's {@link AdminPanel}
 *
 * @author igor.vaynberg
 */
public class AdminPage extends WebPage {
// --------------------------- CONSTRUCTORS ---------------------------

	/**
	 * Constructor
	 */
	public AdminPage() {
		add(new AdminPanel("admin", null));
	}
}
