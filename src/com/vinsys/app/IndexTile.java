package com.vinsys.app;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.model.IModel;
import org.brixcms.jcr.wrapper.BrixNode;
import org.brixcms.plugin.site.page.tile.Tile;
import org.brixcms.plugin.site.page.tile.admin.EmptyTileEditorPanel;
import org.brixcms.plugin.site.page.tile.admin.TileEditorPanel;

public class IndexTile implements Tile {

	@Override
	public String getDisplayName() {
		return "Title Tile";
	}

	@Override
	public String getTypeName() {
		// TODO Auto-generated method stub
		return IndexTile.class.getName();
	}

	@Override
	public TileEditorPanel newEditor(String id, IModel<BrixNode> tileContainerNode) {
		return new EmptyTileEditorPanel(id);
	}

	@Override
	public Component newViewer(String id, IModel<BrixNode> tileNode) {
		return new IndexPanel(id, tileNode);
	}

	@Override
	// TODO Auto-generated method stub
	public boolean requiresSSL(IModel<BrixNode> tileNode) {
		return false;
	}

}
