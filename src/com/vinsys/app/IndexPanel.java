package com.vinsys.app;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.brixcms.jcr.wrapper.BrixNode;

public class IndexPanel extends Panel {

	public IndexPanel(String id, IModel<BrixNode> model) {
		super(id, model);
		add(new Label("mylabel", "The Label"));
	}

}
