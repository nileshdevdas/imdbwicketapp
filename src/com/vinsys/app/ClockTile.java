package com.vinsys.app;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.brixcms.jcr.wrapper.BrixNode;
import org.brixcms.plugin.site.page.tile.Tile;
import org.brixcms.plugin.site.page.tile.admin.EmptyTileEditorPanel;
import org.brixcms.plugin.site.page.tile.admin.TileEditorPanel;

public class ClockTile implements Tile {

	@Override
	public String getDisplayName() {
		return "Clock Tile";
	}

	@Override
	public String getTypeName() {
		// TODO Auto-generated method stub
		return getClass().getName();
	}

	@Override
	public TileEditorPanel newEditor(String id, IModel<BrixNode> tileContainerNode) {
		// TODO Auto-generated method stub
		return new EmptyTileEditorPanel(id);
	}

	@Override
	public Component newViewer(String id, IModel<BrixNode> tileNode) {
		// TODO Auto-generated method stub
		return new ClockTilePanel(id, tileNode);
	}

	@Override
	public boolean requiresSSL(IModel<BrixNode> tileNode) {
		// TODO Auto-generated method stub
		return false;
	}

}
