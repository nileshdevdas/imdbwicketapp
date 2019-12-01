package com.vinsys.app;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.brixcms.jcr.wrapper.BrixNode;

public class ClockTilePanel extends Panel {

	public ClockTilePanel(String id, IModel<BrixNode> model) {
		super(id, model);
	}

}
